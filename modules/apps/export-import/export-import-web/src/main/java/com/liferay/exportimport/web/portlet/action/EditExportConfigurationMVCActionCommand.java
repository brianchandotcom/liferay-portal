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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationHelper;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationService;
import com.liferay.portlet.exportimport.service.ExportImportService;
import com.liferay.portlet.trash.service.TrashEntryService;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
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
		"mvc.command.name=editExportConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditExportConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

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

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				actionRequest, groupId,
				ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);

		SessionMessages.add(
			actionRequest, portletId + "settingsMap", settingsMap);
	}

	protected void deleteExportImportConfiguration(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		long[] deleteExportImportConfigurationIds = null;

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		if (exportImportConfigurationId > 0) {
			deleteExportImportConfigurationIds =
				new long[] {exportImportConfigurationId};
		}
		else {
			deleteExportImportConfigurationIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteExportImportConfigurationIds"),
				0L);
		}

		List<TrashedModel> trashedModels = new ArrayList<>();

		for (long deleteExportImportConfigurationId :
				deleteExportImportConfigurationIds) {

			if (moveToTrash) {
				ExportImportConfiguration exportImportConfiguration =
					_exportImportConfigurationService.
						moveExportImportConfigurationToTrash(
							deleteExportImportConfigurationId);

				trashedModels.add(exportImportConfiguration);
			}
			else {
				_exportImportConfigurationService.
					deleteExportImportConfiguration(
						deleteExportImportConfigurationId);
			}
		}

		if (moveToTrash && !trashedModels.isEmpty()) {
			TrashUtil.addTrashSessionMessages(actionRequest, trashedModels);

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateExportConfiguration(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteExportImportConfiguration(actionRequest, false);
			}
			else if (cmd.equals(Constants.EXPORT)) {
				long exportImportConfigurationId = ParamUtil.getLong(
					actionRequest, "exportImportConfigurationId");

				_exportImportService.exportLayoutsAsFileInBackground(
					exportImportConfigurationId);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteExportImportConfiguration(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.RELAUNCH)) {
				relaunchExportLayoutConfiguration(actionRequest);
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

	protected void relaunchExportLayoutConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		_exportImportService.exportLayoutsAsFileInBackground(
			exportImportConfigurationId);
	}

	protected void restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationService(
		ExportImportConfigurationService exportImportConfigurationService) {

		_exportImportConfigurationService = exportImportConfigurationService;
	}

	@Reference(unbind = "-")
	protected void setExportImportService(
		ExportImportService exportImportService) {

		_exportImportService = exportImportService;
	}

	@Reference(unbind = "-")
	protected void setTrashEntryService(TrashEntryService trashEntryService) {
		_trashEntryService = trashEntryService;
	}

	protected ExportImportConfiguration updateExportConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		long exportImportConfigurationId = ParamUtil.getLong(
			actionRequest, "exportImportConfigurationId");

		if (exportImportConfigurationId > 0) {
			return ExportImportConfigurationHelper.
				updateExportLayoutExportImportConfiguration(actionRequest);
		}
		else {
			return ExportImportConfigurationHelper.
				addExportLayoutExportImportConfiguration(actionRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditExportConfigurationMVCActionCommand.class);

	private ExportImportConfigurationService _exportImportConfigurationService;
	private ExportImportService _exportImportService;
	private TrashEntryService _trashEntryService;

}