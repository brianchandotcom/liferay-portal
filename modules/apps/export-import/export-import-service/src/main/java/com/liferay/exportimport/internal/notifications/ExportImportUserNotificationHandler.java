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

package com.liferay.exportimport.internal.notifications;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
	service = UserNotificationHandler.class
)
public class ExportImportUserNotificationHandler
	extends BaseUserNotificationHandler {

	public ExportImportUserNotificationHandler() {
		setOpenDialog(true);
		setPortletId(ExportImportPortletKeys.EXPORT_IMPORT);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		Locale locale = _portal.getLocale(serviceContext.getRequest());

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					"com.liferay.staging.lang");

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					getExportImportConfiguration(
						jsonObject.getLong("exportImportConfigurationId"));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return LanguageUtil.get(
				resourceBundle,
				"the-process-referenced-by-this-notification-does-not-exist");
		}

		String typeLabel = ExportImportConfigurationConstants.getTypeLabel(
			exportImportConfiguration.getType());

		String message = "x-" + typeLabel;

		int status = jsonObject.getInt("status");

		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			message += "-process-finished-successfully";
		}
		else if (status == BackgroundTaskConstants.STATUS_FAILED) {
			message += "-process-failed";
		}
		else {
			return "Unable to process notification: " +
				HtmlUtil.escape(jsonObject.toString());
		}

		long backgroundTaskId = jsonObject.getLong("backgroundTaskId");

		BackgroundTaskDisplay backgroundTaskDisplay =
			_backgroundTaskDisplayFactory.getBackgroundTaskDisplay(
				backgroundTaskId);

		String processName = backgroundTaskDisplay.getDisplayName(
			serviceContext.getRequest());

		return LanguageUtil.format(resourceBundle, message, processName);
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		PortletURL renderURL = PortletURLFactoryUtil.create(
			serviceContext.getRequest(), ExportImportPortletKeys.EXPORT_IMPORT,
			PortletRequest.RENDER_PHASE);

		renderURL.setParameter("mvcPath", "/view_export_import.jsp");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long backgroundTaskId = jsonObject.getLong("backgroundTaskId");

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return StringPool.BLANK;
		}

		renderURL.setParameter(
			"backgroundTaskId", String.valueOf(backgroundTaskId));

		renderURL.setParameter("backURL", serviceContext.getCurrentURL());

		return renderURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportUserNotificationHandler.class);

	@Reference
	private BackgroundTaskDisplayFactory _backgroundTaskDisplayFactory;

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}