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

import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.exportimport.web.constants.ExportImportPortletKeys;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.exportimport.LARFileException;
import com.liferay.portlet.exportimport.LARFileNameException;
import com.liferay.portlet.exportimport.LARFileSizeException;
import com.liferay.portlet.exportimport.LARTypeException;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.ExportImportHelper;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.MissingReference;
import com.liferay.portlet.exportimport.lar.MissingReferences;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;
import com.liferay.portlet.exportimport.service.ExportImportService;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.InputStream;
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
		"mvc.command.name=exportImport"
	},
	service = MVCActionCommand.class
)
public class ExportImportMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = ActionUtil.getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		actionRequest = ActionUtil.getWrappedActionRequest(actionRequest, null);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT);

			hideDefaultSuccessMessage(actionRequest);

			return;
		}

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				_importLayoutsMVCActionCommand.addTempFileEntry(
					actionRequest,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId());

				validateFile(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId());
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				_importLayoutsMVCActionCommand.deleteTempFileEntry(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId());
			}
			else if (cmd.equals(Constants.EXPORT)) {
				hideDefaultSuccessMessage(actionRequest);

				exportData(actionRequest, portlet);

				sendRedirect(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.IMPORT)) {
				hideDefaultSuccessMessage(actionRequest);

				importData(
					actionRequest,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId());

				SessionMessages.add(
					actionRequest,
					PortalUtil.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_CLOSE_REFRESH_PORTLET,
					portlet.getPortletId());

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				_importLayoutsMVCActionCommand.handleUploadException(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME +
						portlet.getPortletId(),
					e);
			}
			else {
				if ((e instanceof LARFileException) ||
					(e instanceof LARFileNameException) ||
					(e instanceof LARFileSizeException) ||
					(e instanceof LARTypeException) ||
					(e instanceof LocaleException) ||
					(e instanceof NoSuchLayoutException) ||
					(e instanceof PortletIdException) ||
					(e instanceof PrincipalException) ||
					(e instanceof StructureDuplicateStructureKeyException)) {

					SessionErrors.add(actionRequest, e.getClass());
				}
				else {
					_log.error(e, e);

					SessionErrors.add(
						actionRequest,
						ExportImportMVCActionCommand.class.getName());
				}
			}
		}
	}

	protected void exportData(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long plid = ParamUtil.getLong(actionRequest, "plid");
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String fileName = ParamUtil.getString(
				actionRequest, "exportFileName");

			Map<String, Serializable> exportPortletSettingsMap =
				ExportImportConfigurationSettingsMapFactory.
					buildExportPortletSettingsMap(
						themeDisplay.getUserId(), plid, groupId,
						portlet.getPortletId(), actionRequest.getParameterMap(),
						themeDisplay.getLocale(), themeDisplay.getTimeZone(),
						fileName);

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						themeDisplay.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
						exportPortletSettingsMap);

			_exportImportService.exportPortletInfoAsFileInBackground(
				exportImportConfiguration);
		}
		catch (Exception e) {
			if (e instanceof LARFileNameException) {
				throw e;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
	}

	protected void importData(
			ActionRequest actionRequest, InputStream inputStream)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = ParamUtil.getLong(actionRequest, "plid");
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Portlet portlet = ActionUtil.getPortlet(actionRequest);

		Map<String, Serializable> importPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportPortletSettingsMap(
					themeDisplay.getUserId(), plid, groupId,
					portlet.getPortletId(), actionRequest.getParameterMap(),
					themeDisplay.getLocale(), themeDisplay.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					themeDisplay.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					importPortletSettingsMap);

		_exportImportService.importPortletInfoInBackground(
			exportImportConfiguration, inputStream);
	}

	protected void importData(ActionRequest actionRequest, String folderName)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId(), folderName);

		InputStream inputStream = null;

		try {
			inputStream = _dlFileEntryLocalService.getFileAsStream(
				fileEntry.getFileEntryId(), fileEntry.getVersion(), false);

			importData(actionRequest, inputStream);

			_importLayoutsMVCActionCommand.deleteTempFileEntry(
				groupId, folderName);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setExportImportService(
		ExportImportService exportImportService) {

		_exportImportService = exportImportService;
	}

	@Reference(unbind = "-")
	protected void setImportLayoutsMVCActionCommand(
		ImportLayoutsMVCActionCommand importLayoutsMVCActionCommand) {

		_importLayoutsMVCActionCommand = importLayoutsMVCActionCommand;
	}

	protected void validateFile(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String folderName)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(
			groupId, themeDisplay.getUserId(), folderName);

		InputStream inputStream = null;

		try {
			inputStream = _dlFileEntryLocalService.getFileAsStream(
				fileEntry.getFileEntryId(), fileEntry.getVersion(), false);

			MissingReferences missingReferences = validateFile(
				actionRequest, inputStream);

			Map<String, MissingReference> weakMissingReferences =
				missingReferences.getWeakMissingReferences();

			if (weakMissingReferences.isEmpty()) {
				return;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if ((weakMissingReferences != null) &&
				!weakMissingReferences.isEmpty()) {

				jsonObject.put(
					"warningMessages",
					StagingUtil.getWarningMessagesJSONArray(
						themeDisplay.getLocale(), weakMissingReferences));
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected MissingReferences validateFile(
			ActionRequest actionRequest, InputStream inputStream)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = ParamUtil.getLong(actionRequest, "plid");
		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		Portlet portlet = ActionUtil.getPortlet(actionRequest);

		Map<String, Serializable> importPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportPortletSettingsMap(
					themeDisplay.getUserId(), plid, groupId,
					portlet.getPortletId(), actionRequest.getParameterMap(),
					themeDisplay.getLocale(), themeDisplay.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					themeDisplay.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					importPortletSettingsMap);

		return _exportImportService.validateImportPortletInfo(
			exportImportConfiguration, inputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportMVCActionCommand.class);

	private volatile DLFileEntryLocalService _dlFileEntryLocalService;
	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private volatile ExportImportService _exportImportService;
	private volatile ImportLayoutsMVCActionCommand
		_importLayoutsMVCActionCommand;

}