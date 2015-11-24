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
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.exportimport.LARFileException;
import com.liferay.portlet.exportimport.LARFileSizeException;
import com.liferay.portlet.exportimport.LARTypeException;
import com.liferay.portlet.exportimport.LayoutImportException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=importLayouts"
	},
	service = {ImportLayoutsMVCActionCommand.class, MVCActionCommand.class}
)
public class ImportLayoutsMVCActionCommand extends BaseMVCActionCommand {

	protected void addTempFileEntry(
			ActionRequest actionRequest, String folderName)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		checkExceededSizeLimit(uploadPortletRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		deleteTempFileEntry(groupId, folderName);

		InputStream inputStream = null;

		try {
			String sourceFileName = uploadPortletRequest.getFileName("file");

			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			_layoutService.addTempFileEntry(
				groupId, folderName, sourceFileName, inputStream, contentType);
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (cause instanceof FileUploadBase.IOFileUploadException) {
					if (_log.isInfoEnabled()) {
						_log.info("Temporary upload was cancelled");
					}
				}

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}
			}
			else {
				throw e;
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void checkExceededSizeLimit(HttpServletRequest request)
		throws PortalException {

		UploadException uploadException = (UploadException)request.getAttribute(
			WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable cause = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit() ||
				uploadException.isExceededUploadRequestSizeLimit()) {

				throw new LARFileSizeException(cause);
			}

			throw new PortalException(cause);
		}
	}

	protected void deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String folderName)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			String fileName = ParamUtil.getString(actionRequest, "fileName");

			_layoutService.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), folderName, fileName);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	protected void deleteTempFileEntry(long groupId, String folderName)
		throws PortalException {

		String[] tempFileNames = _layoutService.getTempFileNames(
			groupId, folderName);

		for (String tempFileEntryName : tempFileNames) {
			_layoutService.deleteTempFileEntry(
				groupId, folderName, tempFileEntryName);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(
					actionRequest, ExportImportHelper.TEMP_FOLDER_NAME);

				validateFile(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempFileEntry(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME);
			}
			else if (cmd.equals(Constants.IMPORT)) {
				hideDefaultSuccessMessage(actionRequest);

				importData(actionRequest, ExportImportHelper.TEMP_FOLDER_NAME);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				handleUploadException(
					actionRequest, actionResponse,
					ExportImportHelper.TEMP_FOLDER_NAME, e);
			}
			else {
				if ((e instanceof LARFileException) ||
					(e instanceof LARFileSizeException) ||
					(e instanceof LARTypeException)) {

					SessionErrors.add(actionRequest, e.getClass());
				}
				else if ((e instanceof LayoutPrototypeException) ||
						 (e instanceof LocaleException)) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					_log.error(e, e);

					SessionErrors.add(
						actionRequest, LayoutImportException.class.getName());
				}
			}
		}
	}

	protected void handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String folderName, Exception e)
		throws Exception {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(ContentTypes.TEXT_HTML);
		response.setStatus(HttpServletResponse.SC_OK);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		deleteTempFileEntry(themeDisplay.getScopeGroupId(), folderName);

		JSONObject jsonObject = StagingUtil.getExceptionMessagesJSONObject(
			themeDisplay.getLocale(), e, (ExportImportConfiguration)null);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);

		ServletResponseUtil.write(
			response, String.valueOf(jsonObject.getInt("status")));
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

			importData(actionRequest, fileEntry.getTitle(), inputStream);

			deleteTempFileEntry(groupId, folderName);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void importData(
			ActionRequest actionRequest, String fileName,
			InputStream inputStream)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					themeDisplay.getUserId(), groupId, privateLayout, null,
					actionRequest.getParameterMap(), themeDisplay.getLocale(),
					themeDisplay.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					themeDisplay.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		_exportImportService.importLayoutsInBackground(
			exportImportConfiguration, inputStream);
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
	protected void setLayoutService(LayoutService layoutService) {
		_layoutService = layoutService;
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

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					themeDisplay.getUserId(), groupId, privateLayout, null,
					actionRequest.getParameterMap(), themeDisplay.getLocale(),
					themeDisplay.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					themeDisplay.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		return _exportImportService.validateImportLayoutsFile(
			exportImportConfiguration, inputStream);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportLayoutsMVCActionCommand.class);

	private volatile DLFileEntryLocalService _dlFileEntryLocalService;
	private volatile ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private volatile ExportImportService _exportImportService;
	private volatile LayoutService _layoutService;

}