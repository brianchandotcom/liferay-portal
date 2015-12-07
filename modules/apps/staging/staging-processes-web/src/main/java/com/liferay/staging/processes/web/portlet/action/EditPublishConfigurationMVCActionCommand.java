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

package com.liferay.staging.processes.web.portlet.action;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.staging.processes.web.constants.StagingProcessesPortletKeys;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StagingProcessesPortletKeys.STAGING_PROCESSES,
		"mvc.command.name=editPublishConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditPublishConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

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

			if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
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

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
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

	@Reference
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	protected void unsetExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditPublishConfigurationMVCActionCommand.class);

	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

}