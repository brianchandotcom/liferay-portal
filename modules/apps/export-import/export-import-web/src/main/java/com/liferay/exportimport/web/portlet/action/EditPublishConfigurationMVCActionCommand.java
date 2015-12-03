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

package com.liferay.exportimport.web.portlet.action;

import com.liferay.exportimport.web.constants.ExportImportPortletKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationHelper;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=editPublishConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditPublishConfigurationMVCActionCommand
	extends EditExportConfigurationMVCActionCommand {

	@Override
	protected void addSessionMessages(ActionRequest actionRequest)
		throws Exception {

		String portletId = PortalUtil.getPortletId(actionRequest);
		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		SessionMessages.add(
			actionRequest, portletId + "exportImportConfigurationId",
			exportImportConfigurationId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		SessionMessages.add(actionRequest, portletId + "name", name);
		SessionMessages.add(
			actionRequest, portletId + "description", description);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		int exportImportConfigurationType =
			ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_REMOTE;

		boolean localPublishing = ParamUtil.getBoolean(
			actionRequest, "localPublishing");

		if (localPublishing) {
			exportImportConfigurationType =
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL;
		}

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				actionRequest, groupId, exportImportConfigurationType);

		SessionMessages.add(
			actionRequest, portletId + "settingsMap", settingsMap);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long exportImportConfigurationId = ParamUtil.getLong(
				actionRequest, "exportImportConfigurationId");

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updatePublishConfiguration(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExportImportConfiguration(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteExportImportConfiguration(actionRequest, true);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
				StagingUtil.publishLayouts(
					themeDisplay.getUserId(), exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
				StagingUtil.copyRemoteLayouts(exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.RELAUNCH)) {
				relaunchPublishLayoutConfiguration(
					themeDisplay.getUserId(), actionRequest);
			}
			else if (Validator.isNull(cmd)) {
				addSessionMessages(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	protected void relaunchPublishLayoutConfiguration(
			long userId, ActionRequest actionRequest)
		throws PortalException {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, BackgroundTaskConstants.BACKGROUND_TASK_ID);

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.getExportImportConfiguration(
				MapUtil.getLong(taskContextMap, "exportImportConfigurationId"));

		if (exportImportConfiguration.getType() ==
				ExportImportConfigurationConstants.TYPE_PUBLISH_LAYOUT_LOCAL) {

			StagingUtil.publishLayouts(userId, exportImportConfiguration);
		}
		else if (exportImportConfiguration.getType() ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE) {

			StagingUtil.copyRemoteLayouts(exportImportConfiguration);
		}
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	protected ExportImportConfiguration updatePublishConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		boolean localPublishing = ParamUtil.getBoolean(
			actionRequest, "localPublishing");

		if (exportImportConfigurationId > 0) {
			if (localPublishing) {
				return ExportImportConfigurationHelper.
					updatePublishLayoutLocalExportImportConfiguration(
						actionRequest);
			}
			else {
				return ExportImportConfigurationHelper.
					updatePublishLayoutRemoteExportImportConfiguration(
						actionRequest);
			}
		}
		else {
			if (localPublishing) {
				return ExportImportConfigurationHelper.
					addPublishLayoutLocalExportImportConfiguration(
						actionRequest);
			}
			else {
				return ExportImportConfigurationHelper.
					addPublishLayoutRemoteExportImportConfiguration(
						actionRequest);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditPublishConfigurationMVCActionCommand.class);

	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}