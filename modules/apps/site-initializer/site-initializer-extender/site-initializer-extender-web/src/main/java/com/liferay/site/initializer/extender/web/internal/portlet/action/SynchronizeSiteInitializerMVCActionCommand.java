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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerFactory;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.initializer.extender.web.internal.constants.SiteInitializerExtenderPortletKeys;

import java.io.File;
import java.io.InputStream;

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

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-165482"))) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.getGroup(
			themeDisplay.getScopeGroupId());

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String siteInitializerKey = unicodeProperties.get("siteInitializerKey");

		if (Validator.isNull(siteInitializerKey)) {
			return;
		}

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"siteInitializerFile")) {

			if (inputStream != null) {
				_initialize(
					group.getGroupId(), inputStream, siteInitializerKey);

				return;
			}
		}

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

		if (siteInitializer == null) {
			return;
		}

		siteInitializer.initialize(group.getGroupId());
	}

	private void _initialize(
			long groupId, InputStream inputStream, String siteInitializerKey)
		throws Exception {

		File tempFile = FileUtil.createTempFile();

		FileUtil.write(tempFile, inputStream);

		File tempFolder = FileUtil.createTempFolder();

		FileUtil.unzip(tempFile, tempFolder);

		tempFile.delete();

		try {
			SiteInitializer siteInitializer = _siteInitializerFactory.create(
				new File(tempFolder, "site-initializer"), siteInitializerKey);

			siteInitializer.initialize(groupId);
		}
		finally {
			tempFolder.delete();
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerFactory _siteInitializerFactory;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

}