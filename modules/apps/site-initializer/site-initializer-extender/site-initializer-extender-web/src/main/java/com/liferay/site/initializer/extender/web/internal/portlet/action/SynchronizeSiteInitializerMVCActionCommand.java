/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.initializer.extender.web.internal.portlet.action;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerFactory;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.initializer.extender.web.internal.constants.SiteInitializerExtenderPortletKeys;

import java.io.File;
import java.io.InputStream;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"javax.portlet.name=" + SiteInitializerExtenderPortletKeys.SITE_INITIALIZER,
		"mvc.command.name=/site_initializer/synchronize_site_initializer"
	},
	service = MVCActionCommand.class
)
public class SynchronizeSiteInitializerMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-165482")) {
			return;
		}

		Callable<Group> callable = new GroupCallable(actionRequest);

		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			_log.error(throwable);

			SessionErrors.add(actionRequest, throwable.getClass());

			throw new Exception(throwable);
		}
	}

	private void _initialize(
			long groupId, InputStream inputStream, String siteInitializerKey)
		throws Exception {

		File tempFile = FileUtil.createTempFile();

		FileUtil.write(tempFile, inputStream);

		File tempFolder = FileUtil.createTempFolder();

		try {
			FileUtil.unzip(tempFile, tempFolder);

			SiteInitializer siteInitializer = _siteInitializerFactory.create(
				new File(tempFolder, "site-initializer"), siteInitializerKey);

			siteInitializer.initialize(groupId);
		}
		finally {
			FileUtil.deltree(tempFile);
			FileUtil.deltree(tempFolder);
		}
	}

	private Group _updateGroup(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupService.getGroup(themeDisplay.getSiteGroupId());

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String siteInitializerKey = unicodeProperties.get("siteInitializerKey");

		if (Validator.isNull(siteInitializerKey)) {
			return null;
		}

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"siteInitializerFile")) {

			if (inputStream != null) {
				_initialize(
					group.getGroupId(), inputStream, siteInitializerKey);

				return _groupService.getGroup(themeDisplay.getSiteGroupId());
			}
		}

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

		if (siteInitializer == null) {
			return null;
		}

		siteInitializer.initialize(group.getGroupId());

		return _groupService.getGroup(themeDisplay.getSiteGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynchronizeSiteInitializerMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerFactory _siteInitializerFactory;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	private class GroupCallable implements Callable<Group> {

		@Override
		public Group call() throws Exception {
			try {
				return _updateGroup(_actionRequest);
			}
			catch (Exception exception) {
				PermissionCacheUtil.clearCache(
					_portal.getUserId(_actionRequest));

				throw exception;
			}
		}

		private GroupCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}