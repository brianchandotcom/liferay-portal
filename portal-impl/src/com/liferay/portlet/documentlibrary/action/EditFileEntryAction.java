/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 */
public class EditFileEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleFileEntries(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				deleteTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				cancelFileEntriesCheckOut(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				checkInFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				checkOutFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE)) {
				moveFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertFileEntry(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (cmd.equals(Constants.PREVIEW)) {
			}
			else if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else if (!windowState.equals(LiferayWindowState.POP_UP)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateLockException ||
				e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				if (e instanceof DuplicateLockException) {
					DuplicateLockException dle = (DuplicateLockException)e;

					SessionErrors.add(
						actionRequest, dle.getClass().getName(), dle.getLock());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof DuplicateFileException ||
					 e instanceof DuplicateFolderNameException ||
					 e instanceof FileExtensionException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof NoSuchFolderException ||
					 e instanceof SourceFileNameException) {

				if (e instanceof DuplicateFileException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION);
				}
				else if (e instanceof FileExtensionException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION);
				}
				else if (e instanceof FileNameException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_NAME_EXCEPTION);
				}
				else if (e instanceof FileSizeException) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(actionResponse);

					response.setStatus(
						ServletResponseConstants.SC_FILE_SIZE_EXCEPTION);
				}

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getFileEntry(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_file_entry";

		return mapping.findForward(getForward(renderRequest, forward));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/document_library/" +
					"upload_multiple_file_entries_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void addMultipleFileEntries(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String description = ParamUtil.getString(actionRequest, "description");
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");
		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName");

		List<String> validUpdateFileEntries = new ArrayList<String>();
		List<KeyValuePair> invalidUpdateFileEntries =
			new ArrayList<KeyValuePair>();

		for (String selectedFileName : selectedFileNames) {
			String contentType = MimeTypesUtil.getContentType(
				selectedFileName);

			File file = TempFileUtil.getTempFile(
				themeDisplay.getUserId(), selectedFileName, _TEMP_FOLDERNAME);

			try {
				if (Validator.isNotNull(selectedFileName) &&
					!file.exists()) {

					file.createNewFile();
				}

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						DLFileEntry.class.getName(), actionRequest);

				serviceContext.setAttribute(
					"fileEntryTypeId", fileEntryTypeId);
				serviceContext.setAttribute(
					"sourceFileName", selectedFileName);

				HashMap<String, Fields> fieldsMap = getFieldsMap(
					actionRequest, fileEntryTypeId);

				serviceContext.setAttribute("fieldsMap", fieldsMap);

				// Add file entry

				FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
					repositoryId, folderId, contentType, selectedFileName,
					description, changeLog, file, serviceContext);

				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, DLFileEntry.class.getName(),
					fileEntry.getFileEntryId(), -1);

				AssetPublisherUtil.addRecentFolderId(
					actionRequest, DLFileEntry.class.getName(), folderId);

				validUpdateFileEntries.add(selectedFileName);
			}
			catch (Exception e) {
				String errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(),
					"an-unexpected-error-occurred-while-saving-your-" +
						"document");

				if (e instanceof DuplicateFileException) {
					errorMessage = LanguageUtil.get(
						themeDisplay.getLocale(),
						"the-folder-you-selected-already-has-an-entry-" +
							"with-this-name.-please-select-a-different-" +
							"folder");
				}
				else if (e instanceof FileExtensionException) {
					errorMessage = LanguageUtil.format(
						themeDisplay.getLocale(),
						"please-enter-a-file-with-a-valid-extension-x",
						StringUtil.merge(
							PrefsPropsUtil.getStringArray(
								PropsKeys.DL_FILE_EXTENSIONS,
								StringPool.COMMA)));
				}
				else if (e instanceof FileNameException) {
					errorMessage = LanguageUtil.get(
						themeDisplay.getLocale(),
						"please-enter-a-file-with-a-valid-file-name");
				}
				else if (e instanceof FileSizeException) {
					long maxSizeMB = PrefsPropsUtil.getLong(
						PropsKeys.DL_FILE_MAX_SIZE)	/ 1024 / 1024;

					errorMessage = LanguageUtil.format(
						themeDisplay.getLocale(),
						"file-size-is-larger-than-x-megabytes",	maxSizeMB);
				}

				invalidUpdateFileEntries.add(new KeyValuePair(
					selectedFileName, errorMessage));
			}
			finally {
				FileUtil.delete(file);
			}
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String validUpdateFileEntry : validUpdateFileEntries) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("fileName", validUpdateFileEntry);
			jsonObject.put("added", Boolean.TRUE);

			jsonArray.put(jsonObject);
		}

		for (KeyValuePair invalidUpdateFileEntry : invalidUpdateFileEntries) {
			String fileName = invalidUpdateFileEntry.getKey();
			String errorMessage = invalidUpdateFileEntry.getValue();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("fileName", fileName);
			jsonObject.put("added", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);

			jsonArray.put(jsonObject);
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response =
			PortalUtil.getHttpServletResponse(actionResponse);
		InputStream is = new UnsyncByteArrayInputStream(
			jsonArray.toString().getBytes());
		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		ServletResponseUtil.sendFile(
			request, response, null, is, contentType);
	}

	protected void addTempFileEntry(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long folderId = ParamUtil.getLong(uploadRequest, "folderId");

		File file = uploadRequest.getFile("file");
		String sourceFileName = uploadRequest.getFileName("file");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DLAppServiceUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), folderId, sourceFileName,
			_TEMP_FOLDERNAME, file);
	}

	protected void cancelFileEntriesCheckOut(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			DLAppServiceUtil.cancelCheckOut(fileEntryId);
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < fileEntryIds.length; i++) {
				DLAppServiceUtil.cancelCheckOut(fileEntryIds[i]);
			}
		}
	}

	protected void checkInFileEntries(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fileEntryId > 0) {
			DLAppServiceUtil.checkInFileEntry(
				fileEntryId, false, StringPool.BLANK, serviceContext);
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < fileEntryIds.length; i++) {
				DLAppServiceUtil.checkInFileEntry(
					fileEntryIds[i], false, StringPool.BLANK, serviceContext);
			}
		}
	}

	protected void checkOutFileEntries(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId);
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < fileEntryIds.length; i++) {
				DLAppServiceUtil.checkOutFileEntry(fileEntryIds[i]);
			}
		}
	}

	protected void deleteFileEntries(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			DLAppServiceUtil.deleteFileEntry(fileEntryId);
		}
		else {
			long[] deleteFileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < deleteFileEntryIds.length; i++) {
				DLAppServiceUtil.deleteFileEntry(deleteFileEntryIds[i]);
			}
		}
	}

	protected void deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fileName = ParamUtil.getString(actionRequest, "fileName");

		long folderId = ParamUtil.getLong(actionRequest, "folderId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			DLAppServiceUtil.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, fileName,
				_TEMP_FOLDERNAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response =
			PortalUtil.getHttpServletResponse(actionResponse);
		InputStream is = new UnsyncByteArrayInputStream(
			jsonObject.toString().getBytes());
		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		ServletResponseUtil.sendFile(
			request, response, null, is, contentType);
	}

	protected HashMap<String, Fields> getFieldsMap(
			ActionRequest actionRequest, long fileEntryTypeId)
		throws PortalException, SystemException {

		HashMap<String, Fields> fieldsMap = new HashMap<String, Fields>();

		if (fileEntryTypeId <= 0) {
			return fieldsMap;
		}

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			String namespace = String.valueOf(
				ddmStructure.getStructureId());

			Set<String> fieldNames = ddmStructure.getFieldNames();

			Fields fields = new Fields();

			for (String name : fieldNames) {
				Field field = new Field();

				field.setName(name);

				String value = ParamUtil.getString(
					actionRequest, namespace + name);

				field.setValue(value);

				fields.put(field);
			}

			fieldsMap.put(ddmStructure.getStructureKey(), fields);
		}

		return fieldsMap;
	}

	protected void moveFileEntries(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		if (fileEntryId > 0) {
			DLAppServiceUtil.moveFileEntry(
				fileEntryId, newFolderId, serviceContext);
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < fileEntryIds.length; i++) {
				DLAppServiceUtil.moveFileEntry(
					fileEntryIds[i], newFolderId, serviceContext);
			}
		}
	}

	protected void revertFileEntry(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String version = ParamUtil.getString(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		DLAppServiceUtil.revertFileEntry(fileEntryId, version, serviceContext);
	}

	protected void updateFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);

		long fileEntryId = ParamUtil.getLong(uploadRequest, "fileEntryId");

		long repositoryId = ParamUtil.getLong(uploadRequest, "repositoryId");
		long folderId = ParamUtil.getLong(uploadRequest, "folderId");
		String sourceFileName = uploadRequest.getFileName("file");
		String title = ParamUtil.getString(uploadRequest, "title");
		String description = ParamUtil.getString(uploadRequest, "description");
		String changeLog = ParamUtil.getString(uploadRequest, "changeLog");
		long fileEntryTypeId = ParamUtil.getLong(
			uploadRequest, "fileEntryTypeId");
		boolean majorVersion = ParamUtil.getBoolean(
			uploadRequest, "majorVersion");

		File file = uploadRequest.getFile("file");

		if (Validator.isNotNull(sourceFileName) && !file.exists()) {
			file.createNewFile();
		}

		String contentType = uploadRequest.getContentType("file");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		if (fileEntryTypeId != -1) {
			serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);
		}

		serviceContext.setAttribute("sourceFileName", sourceFileName);

		HashMap<String, Fields> fieldsMap = getFieldsMap(
			actionRequest, fileEntryTypeId);

		serviceContext.setAttribute("fieldsMap", fieldsMap);

		FileEntry fileEntry = null;

		if (cmd.equals(Constants.ADD)) {
			if (Validator.isNull(title)) {
				title = sourceFileName;
			}

			// Add file entry

			fileEntry = DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, contentType, title, description,
				changeLog, file, serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), -1);
		}
		else {

			// Update file entry

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntryId, sourceFileName, contentType, title, description,
				changeLog, majorVersion, file, serviceContext);
		}

		AssetPublisherUtil.addRecentFolderId(
			actionRequest, DLFileEntry.class.getName(), folderId);
	}

	private static final String _TEMP_FOLDERNAME = "document_temp_upload";

}