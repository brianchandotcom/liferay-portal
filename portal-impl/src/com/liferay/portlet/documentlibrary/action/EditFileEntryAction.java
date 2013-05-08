/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.InvalidFileEntryTypeException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.IOFileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 * @author Kenneth Chang
 */
public class EditFileEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		FileEntry fileEntry = null;

		try {
			if (Validator.isNull(cmd)) {
				UploadException uploadException =
					(UploadException)actionRequest.getAttribute(
						WebKeys.UPLOAD_EXCEPTION);

				if (uploadException != null) {
					if (uploadException.isExceededSizeLimit()) {
						throw new FileSizeException(uploadException.getCause());
					}

					throw new PortalException(uploadException.getCause());
				}
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.ADD_DYNAMIC) ||
					 cmd.equals(Constants.UPDATE) ||
					 cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				fileEntry = updateFileEntry(
					portletConfig, actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleFileEntries(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntry(
					(LiferayPortletConfig)portletConfig, actionRequest, false);
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
				moveFileEntries(actionRequest, false);
			}
			else if (cmd.equals(Constants.MOVE_FROM_TRASH)) {
				moveFileEntries(actionRequest, true);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFileEntry(
					(LiferayPortletConfig)portletConfig, actionRequest, true);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertFileEntry(actionRequest);
			}

			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else if (cmd.equals(Constants.PREVIEW)) {
			}
			else if (!cmd.equals(Constants.MOVE_FROM_TRASH)) {
				sendRedirect(actionRequest, actionResponse);
			}
			else {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");
				int workflowAction = ParamUtil.getInteger(
					actionRequest, "workflowAction",
					WorkflowConstants.ACTION_SAVE_DRAFT);

				if ((fileEntry != null) &&
					(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

					redirect = getSaveAndContinueRedirect(
						portletConfig, actionRequest, fileEntry, redirect);

					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					WindowState windowState = actionRequest.getWindowState();

					if (!windowState.equals(LiferayWindowState.POP_UP)) {
						sendRedirect(actionRequest, actionResponse);
					}
					else {
						redirect = PortalUtil.escapeRedirect(
							ParamUtil.getString(actionRequest, "redirect"));

						if (Validator.isNotNull(redirect)) {
							actionResponse.sendRedirect(redirect);
						}
					}
				}
			}
		}
		catch (Exception e) {
			handleUploadException(
				portletConfig, actionRequest, actionResponse, cmd, e);
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
				e instanceof NoSuchFileVersionException ||
				e instanceof NoSuchRepositoryEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

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

		List<String> validFileNames = new ArrayList<String>();
		List<KeyValuePair> invalidFileNameKVPs = new ArrayList<KeyValuePair>();

		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName");

		for (String selectedFileName : selectedFileNames) {
			addMultipleFileEntries(
				actionRequest, actionResponse, selectedFileName, validFileNames,
				invalidFileNameKVPs);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String validFileName : validFileNames) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("added", Boolean.TRUE);
			jsonObject.put("fileName", validFileName);

			jsonArray.put(jsonObject);
		}

		for (KeyValuePair invalidFileNameKVP : invalidFileNameKVPs) {
			String fileName = invalidFileNameKVP.getKey();
			String errorMessage = invalidFileNameKVP.getValue();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("added", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
			jsonObject.put("fileName", fileName);

			jsonArray.put(jsonObject);
		}

		writeJSON(actionRequest, actionResponse, jsonArray);
	}

	protected void addMultipleFileEntries(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String selectedFileName, List<String> validFileNames,
			List<KeyValuePair> invalidFileNameKVPs)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String description = ParamUtil.getString(actionRequest, "description");
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		FileEntry tempFileEntry = null;

		try {
			tempFileEntry = TempFileUtil.getTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				selectedFileName, _TEMP_FOLDER_NAME);

			String mimeType = tempFileEntry.getMimeType();
			InputStream inputStream = tempFileEntry.getContentStream();
			long size = tempFileEntry.getSize();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), actionRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, selectedFileName, mimeType,
				selectedFileName, description, changeLog, inputStream, size,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), -1);

			AssetPublisherUtil.addRecentFolderId(
				actionRequest, DLFileEntry.class.getName(), folderId);

			validFileNames.add(selectedFileName);

			return;
		}
		catch (Exception e) {
			String errorMessage = getAddMultipleFileEntriesErrorMessage(
				themeDisplay, e);

			invalidFileNameKVPs.add(
				new KeyValuePair(selectedFileName, errorMessage));
		}
		finally {
			if (tempFileEntry != null) {
				TempFileUtil.deleteTempFile(tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void addTempFileEntry(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			DLAppServiceUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, sourceFileName,
				_TEMP_FOLDER_NAME, inputStream, contentType);
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) &&
				(uploadException.getCause() instanceof IOFileUploadException)) {

				// Cancelled a temporary upload

			}
			else if ((uploadException != null) &&
					 uploadException.isExceededSizeLimit()) {

				throw new FileSizeException(uploadException.getCause());
			}
			else {
				throw e;
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fileEntryId > 0) {
			DLAppServiceUtil.checkOutFileEntry(fileEntryId, serviceContext);
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);

			for (int i = 0; i < fileEntryIds.length; i++) {
				DLAppServiceUtil.checkOutFileEntry(
					fileEntryIds[i], serviceContext);
			}
		}
	}

	protected void deleteFileEntry(
			LiferayPortletConfig liferayPortletConfig,
			ActionRequest actionRequest, boolean moveToTrash)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId == 0) {
			return;
		}

		String version = ParamUtil.getString(actionRequest, "version");

		if (Validator.isNotNull(version)) {
			DLAppServiceUtil.deleteFileVersion(fileEntryId, version);

			return;
		}

		if (!moveToTrash) {
			DLAppServiceUtil.deleteFileEntry(fileEntryId);

			return;
		}

		DLAppServiceUtil.moveFileEntryToTrash(fileEntryId);

		Map<String, String[]> data = new HashMap<String, String[]>();

		data.put(
			"restoreFileEntryIds", new String[] {String.valueOf(fileEntryId)});

		SessionMessages.add(
			actionRequest,
			liferayPortletConfig.getPortletId() +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
			data);

		SessionMessages.add(
			actionRequest,
			liferayPortletConfig.getPortletId() +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
	}

	protected void deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			DLAppServiceUtil.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, fileName,
				_TEMP_FOLDER_NAME);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected String getAddMultipleFileEntriesErrorMessage(
			ThemeDisplay themeDisplay, Exception e)
		throws Exception {

		String errorMessage = null;

		if (e instanceof AssetCategoryException) {
			AssetCategoryException ace = (AssetCategoryException)e;

			AssetVocabulary assetVocabulary = ace.getVocabulary();

			String vocabularyTitle = StringPool.BLANK;

			if (assetVocabulary != null) {
				vocabularyTitle = assetVocabulary.getTitle(
					themeDisplay.getLocale());
			}

			if (ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY) {
				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"please-select-at-least-one-category-for-x",
					vocabularyTitle);
			}
			else if (ace.getType() ==
						AssetCategoryException.TOO_MANY_CATEGORIES) {

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"you-cannot-select-more-than-one-category-for-x",
					vocabularyTitle);
			}
		}
		else if (e instanceof DuplicateFileException) {
			errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-folder-you-selected-already-has-an-entry-with-this-name." +
					"-please-select-a-different-folder");
		}
		else if (e instanceof FileExtensionException) {
			errorMessage = LanguageUtil.format(
				themeDisplay.getLocale(),
				"please-enter-a-file-with-a-valid-extension-x",
				StringUtil.merge(
					PrefsPropsUtil.getStringArray(
						PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)));
		}
		else if (e instanceof FileNameException) {
			errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"please-enter-a-file-with-a-valid-file-name");
		}
		else if (e instanceof FileSizeException) {
			long maxSizeMB = PrefsPropsUtil.getLong(
				PropsKeys.DL_FILE_MAX_SIZE) / 1024 / 1024;

			errorMessage = LanguageUtil.format(
				themeDisplay.getLocale(),
				"file-size-is-larger-than-x-megabytes", maxSizeMB);
		}
		else if (e instanceof InvalidFileEntryTypeException) {
			errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-document-type-you-selected-is-not-valid-for-this-folder");
		}
		else {
			errorMessage = LanguageUtil.get(
				themeDisplay.getLocale(),
				"an-unexpected-error-occurred-while-saving-your-document");
		}

		return errorMessage;
	}

	protected String[] getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException, SystemException {

		PortletPreferences preferences = portletRequest.getPreferences();

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			preferences = PortletPreferencesFactoryUtil.getPortletSetup(
				portletRequest, portletResource);
		}

		String portletName = portletConfig.getPortletName();

		if (portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			Set<String> extensions = new HashSet<String>();

			String[] mimeTypes = DLUtil.getMediaGalleryMimeTypes(
				preferences, portletRequest);

			for (String mimeType : mimeTypes) {
				extensions.addAll(MimeTypesUtil.getExtensions(mimeType));
			}

			return extensions.toArray(new String[extensions.size()]);
		}
		else {
			return com.liferay.portal.kernel.util.PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			FileEntry fileEntry, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl portletURL = new PortletURLImpl(
			actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);

		String referringPortletResource = ParamUtil.getString(
			actionRequest, "referringPortletResource");

		portletURL.setParameter(
			"referringPortletResource", referringPortletResource, false);

		portletURL.setParameter(
			"groupId", String.valueOf(fileEntry.getGroupId()), false);
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()), false);
		portletURL.setParameter(
			"version", String.valueOf(fileEntry.getVersion()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	protected void handleUploadException(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String cmd, Exception e)
		throws Exception {

		if (e instanceof AssetCategoryException ||
			e instanceof AssetTagException) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		else if (e instanceof DuplicateFileException ||
				 e instanceof DuplicateFolderNameException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileMimeTypeException ||
				 e instanceof FileNameException ||
				 e instanceof FileSizeException ||
				 e instanceof NoSuchFolderException ||
				 e instanceof SourceFileNameException ||
				 e instanceof StorageFieldRequiredException) {

			if (!cmd.equals(Constants.ADD_DYNAMIC) &&
				!cmd.equals(Constants.ADD_MULTIPLE) &&
				!cmd.equals(Constants.ADD_TEMP)) {

				SessionErrors.add(actionRequest, e.getClass());

				return;
			}

			if (e instanceof DuplicateFileException ||
				e instanceof FileExtensionException ||
				e instanceof FileNameException ||
				e instanceof FileSizeException) {

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				response.setContentType(ContentTypes.TEXT_HTML);
				response.setStatus(HttpServletResponse.SC_OK);

				int errorType = 0;
				String errorMessage = StringPool.BLANK;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (e instanceof DuplicateFileException) {
					errorType =
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;

					errorMessage = LanguageUtil.get(
						themeDisplay.getLocale(),
						"please-enter-a-unique-document-name");
				}
				else if (e instanceof FileExtensionException) {
					errorType =
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;

					errorMessage = LanguageUtil.format(
						themeDisplay.getLocale(),
						"document-names-must-end-with-one-of-the-following-" +
							"extensions",
						StringUtil.merge(
							getAllowedFileExtensions(
								portletConfig, actionRequest, actionResponse)));
				}
				else if (e instanceof FileNameException) {
					errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;

					errorMessage = LanguageUtil.get(
						themeDisplay.getLocale(),
						"please-enter-a-file-with-a-valid-file-name");
				}
				else if (e instanceof FileSizeException) {
					errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;

					long fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.DL_FILE_MAX_SIZE);

					if (fileMaxSize == 0) {
						fileMaxSize = PrefsPropsUtil.getLong(
							PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
					}

					fileMaxSize /= 1024;

					errorMessage = LanguageUtil.format(
						themeDisplay.getLocale(),
						"please-enter-a-file-with-a-valid-file-size-no-larger" +
							"-than-x", fileMaxSize);
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("status", errorType);
				jsonObject.put("message", errorMessage);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}

			SessionErrors.add(actionRequest, e.getClass());
		}
		else if (e instanceof DuplicateLockException ||
				 e instanceof InvalidFileVersionException ||
				 e instanceof NoSuchFileEntryException ||
				 e instanceof PrincipalException) {

			if (e instanceof DuplicateLockException) {
				DuplicateLockException dle = (DuplicateLockException)e;

				SessionErrors.add(actionRequest, dle.getClass(), dle.getLock());
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}

			setForward(actionRequest, "portlet.document_library.error");
		}
		else {
			throw e;
		}
	}

	protected void moveFileEntries(
			ActionRequest actionRequest, boolean moveFromTrash)
		throws Exception {

		long[] fileEntryIds = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			fileEntryIds = new long[] {fileEntryId};
		}
		else {
			fileEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "fileEntryIds"), 0L);
		}

		long newFolderId = ParamUtil.getLong(actionRequest, "newFolderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		for (long moveFileEntryId : fileEntryIds) {
			if (moveFromTrash) {
				DLAppServiceUtil.moveFileEntryFromTrash(
					moveFileEntryId, newFolderId, serviceContext);
			}

			else {
				DLAppServiceUtil.moveFileEntry(
					moveFileEntryId, newFolderId, serviceContext);
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

	protected FileEntry updateFileEntry(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(uploadPortletRequest, Constants.CMD);

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		long repositoryId = ParamUtil.getLong(
			uploadPortletRequest, "repositoryId");
		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");
		String title = ParamUtil.getString(uploadPortletRequest, "title");
		String description = ParamUtil.getString(
			uploadPortletRequest, "description");
		String changeLog = ParamUtil.getString(
			uploadPortletRequest, "changeLog");
		boolean majorVersion = ParamUtil.getBoolean(
			uploadPortletRequest, "majorVersion");

		if (folderId > 0) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			if (folder.getGroupId() != themeDisplay.getScopeGroupId()) {
				throw new NoSuchFolderException();
			}
		}

		InputStream inputStream = null;

		try {
			String contentType = uploadPortletRequest.getContentType("file");

			long size = uploadPortletRequest.getSize("file");

			if ((cmd.equals(Constants.ADD) ||
				 cmd.equals(Constants.ADD_DYNAMIC)) &&
				(size == 0)) {

				contentType = MimeTypesUtil.getContentType(title);
			}

			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_DYNAMIC) || (size > 0)) {

				String portletName = portletConfig.getPortletName();

				if (portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
					String portletResource = ParamUtil.getString(
						actionRequest, "portletResource");

					PortletPreferences portletPreferences = null;

					if (Validator.isNotNull(portletResource)) {
						PortletPreferencesFactoryUtil.getPortletSetup(
							actionRequest, portletResource);
					}
					else {
						portletPreferences = actionRequest.getPreferences();
					}

					String[] mimeTypes = DLUtil.getMediaGalleryMimeTypes(
						portletPreferences, actionRequest);

					if (Arrays.binarySearch(mimeTypes, contentType) < 0) {
						throw new FileMimeTypeException(contentType);
					}
				}
			}

			inputStream = uploadPortletRequest.getFileAsStream("file");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			FileEntry fileEntry = null;

			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_DYNAMIC)) {

				// Add file entry

				fileEntry = DLAppServiceUtil.addFileEntry(
					repositoryId, folderId, sourceFileName, contentType, title,
					description, changeLog, inputStream, size, serviceContext);

				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, DLFileEntry.class.getName(),
					fileEntry.getFileEntryId(), -1);

				if (cmd.equals(Constants.ADD_DYNAMIC)) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("fileEntryId", fileEntry.getFileEntryId());

					writeJSON(actionRequest, actionResponse, jsonObject);
				}
			}
			else if (cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				// Update file entry and checkin

				fileEntry = DLAppServiceUtil.updateFileEntryAndCheckIn(
					fileEntryId, sourceFileName, contentType, title,
					description, changeLog, majorVersion, inputStream, size,
					serviceContext);
			}
			else {

				// Update file entry

				fileEntry = DLAppServiceUtil.updateFileEntry(
					fileEntryId, sourceFileName, contentType, title,
					description, changeLog, majorVersion, inputStream, size,
					serviceContext);
			}

			AssetPublisherUtil.addRecentFolderId(
				actionRequest, DLFileEntry.class.getName(), folderId);

			return fileEntry;
		}
		catch (Exception e) {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) &&
				uploadException.isExceededSizeLimit()) {

				throw new FileSizeException(uploadException.getCause());
			}

			throw e;
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final String _TEMP_FOLDER_NAME =
		EditFileEntryAction.class.getName();

}