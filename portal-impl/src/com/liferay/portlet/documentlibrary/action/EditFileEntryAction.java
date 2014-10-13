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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;
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
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
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

	public static final String TEMP_RANDOM_SUFFIX = "--tempRandomSuffix--";

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		FileEntry fileEntry = null;

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException();
				}
				else if (uploadException.isExceededSizeLimit()) {
					throw new FileSizeException(uploadException.getCause());
				}

				throw new PortalException(uploadException.getCause());
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.ADD_DYNAMIC) ||
					 cmd.equals(Constants.UPDATE) ||
					 cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				fileEntry = updateFileEntry(
					portletConfig, actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				addMultipleFileEntries(
					portletConfig, actionRequest, actionResponse);

				hideDefaultSuccessMessage(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				addTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntry(actionRequest, false);
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
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				deleteFileEntry(actionRequest, true);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertFileEntry(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else if (cmd.equals(Constants.PREVIEW)) {
			}
			else if (!windowState.equals(LiferayWindowState.POP_UP)) {
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
					if (!windowState.equals(LiferayWindowState.POP_UP)) {
						sendRedirect(actionRequest, actionResponse);
					}
					else {
						redirect = PortalUtil.escapeRedirect(
							ParamUtil.getString(actionRequest, "redirect"));

						if (Validator.isNotNull(redirect)) {
							if (cmd.equals(Constants.ADD) &&
								(fileEntry != null)) {

								String portletId = HttpUtil.getParameter(
									redirect, "p_p_id", false);

								String namespace =
									PortalUtil.getPortletNamespace(portletId);

								redirect = HttpUtil.addParameter(
									redirect, namespace + "className",
									DLFileEntry.class.getName());
								redirect = HttpUtil.addParameter(
									redirect, namespace + "classPK",
									fileEntry.getFileEntryId());
							}

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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
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

				return actionMapping.findForward(
					"portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_library.edit_file_entry";

		return actionMapping.findForward(getForward(renderRequest, forward));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/portlet/document_library/" +
					"upload_multiple_file_entries_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		List<KeyValuePair> validFileNameKVPs = new ArrayList<KeyValuePair>();
		List<KeyValuePair> invalidFileNameKVPs = new ArrayList<KeyValuePair>();

		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName", new String[0], false);

		for (String selectedFileName : selectedFileNames) {
			addMultipleFileEntries(
				portletConfig, actionRequest, actionResponse, selectedFileName,
				validFileNameKVPs, invalidFileNameKVPs);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (KeyValuePair validFileNameKVP : validFileNameKVPs) {
			String fileName = validFileNameKVP.getKey();
			String originalFileName = validFileNameKVP.getValue();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("added", Boolean.TRUE);
			jsonObject.put("fileName", fileName);
			jsonObject.put("originalFileName", originalFileName);

			jsonArray.put(jsonObject);
		}

		for (KeyValuePair invalidFileNameKVP : invalidFileNameKVPs) {
			String fileName = invalidFileNameKVP.getKey();
			String errorMessage = invalidFileNameKVP.getValue();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("added", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
			jsonObject.put("fileName", fileName);
			jsonObject.put("originalFileName", fileName);

			jsonArray.put(jsonObject);
		}

		writeJSON(actionRequest, actionResponse, jsonArray);
	}

	protected void addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String selectedFileName,
			List<KeyValuePair> validFileNameKVPs,
			List<KeyValuePair> invalidFileNameKVPs)
		throws Exception {

		String originalSelectedFileName = selectedFileName;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(actionRequest, "repositoryId");
		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String description = ParamUtil.getString(actionRequest, "description");
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		FileEntry tempFileEntry = null;

		try {
			tempFileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, selectedFileName);

			selectedFileName = DLUtil.getFileName(
				tempFileEntry.getGroupId(), tempFileEntry.getFolderId(),
				tempFileEntry.getFileName());

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

			validFileNameKVPs.add(
				new KeyValuePair(selectedFileName, originalSelectedFileName));

			return;
		}
		catch (Exception e) {
			String errorMessage = getAddMultipleFileEntriesErrorMessage(
				portletConfig, actionRequest, actionResponse, e);

			invalidFileNameKVPs.add(
				new KeyValuePair(originalSelectedFileName, errorMessage));
		}
		finally {
			if (tempFileEntry != null) {
				TempFileEntryUtil.deleteTempFileEntry(
					tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void addTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		StringBundler sb = new StringBundler(5);

		sb.append(FileUtil.stripExtension(sourceFileName));
		sb.append(TEMP_RANDOM_SUFFIX);
		sb.append(StringUtil.randomString());

		String extension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNotNull(extension)) {
			sb.append(StringPool.PERIOD);
			sb.append(extension);
		}

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("file");

			String contentType = uploadPortletRequest.getContentType("file");

			FileEntry fileEntry = DLAppServiceUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, _TEMP_FOLDER_NAME,
				sb.toString(), inputStream, contentType);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("name", sourceFileName);
			jsonObject.put("title", fileEntry.getTitle());
			jsonObject.put("uuid", fileEntry.getUuid());

			writeJSON(actionRequest, actionResponse, jsonObject);
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

		FileEntry fileEntry = DLAppServiceUtil.moveFileEntryToTrash(
			fileEntryId);

		if (fileEntry.getModel() instanceof DLFileEntry) {
			TrashUtil.addTrashSessionMessages(
				actionRequest, (DLFileEntry)fileEntry.getModel());
		}

		hideDefaultSuccessMessage(actionRequest);
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
				themeDisplay.getScopeGroupId(), folderId, _TEMP_FOLDER_NAME,
				fileName);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put("deleted", Boolean.FALSE);
			jsonObject.put("errorMessage", errorMessage);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected String getAddMultipleFileEntriesErrorMessage(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, Exception e)
		throws Exception {

		String errorMessage = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (e instanceof AntivirusScannerException) {
			AntivirusScannerException ase = (AntivirusScannerException)e;

			errorMessage = themeDisplay.translate(ase.getMessageKey());
		}
		else if (e instanceof AssetCategoryException) {
			AssetCategoryException ace = (AssetCategoryException)e;

			AssetVocabulary assetVocabulary = ace.getVocabulary();

			String vocabularyTitle = StringPool.BLANK;

			if (assetVocabulary != null) {
				vocabularyTitle = assetVocabulary.getTitle(
					themeDisplay.getLocale());
			}

			if (ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY) {
				errorMessage = themeDisplay.translate(
					"please-select-at-least-one-category-for-x",
					vocabularyTitle);
			}
			else if (ace.getType() ==
						AssetCategoryException.TOO_MANY_CATEGORIES) {

				errorMessage = themeDisplay.translate(
					"you-cannot-select-more-than-one-category-for-x",
					vocabularyTitle);
			}
		}
		else if (e instanceof DuplicateFileException) {
			errorMessage = themeDisplay.translate(
				"the-folder-you-selected-already-has-an-entry-with-this-name." +
					"-please-select-a-different-folder");
		}
		else if (e instanceof FileExtensionException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-extension-x",
					StringUtil.merge(
						getAllowedFileExtensions(
							portletConfig, actionRequest, actionResponse)));
		}
		else if (e instanceof FileNameException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-name");
		}
		else if (e instanceof FileSizeException) {
			long fileMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.DL_FILE_MAX_SIZE);

			if (fileMaxSize == 0) {
				fileMaxSize = PrefsPropsUtil.getLong(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
			}

			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
				TextFormatter.formatStorageSize(
					fileMaxSize, themeDisplay.getLocale()));
		}
		else if (e instanceof InvalidFileEntryTypeException) {
			errorMessage = themeDisplay.translate(
				"the-document-type-you-selected-is-not-valid-for-this-folder");
		}
		else {
			errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-saving-your-document");
		}

		return errorMessage;
	}

	protected String[] getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
		}
		else {
			ThemeDisplay themeDisplay = (ThemeDisplay)
				portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			DLPortletInstanceSettings dlPortletInstanceSettings =
				DLPortletInstanceSettings.getInstance(
					themeDisplay.getLayout(), portletDisplay.getId());

			Set<String> extensions = new HashSet<String>();

			String[] mimeTypes = dlPortletInstanceSettings.getMimeTypes();

			for (String mimeType : mimeTypes) {
				extensions.addAll(MimeTypesUtil.getExtensions(mimeType));
			}

			return extensions.toArray(new String[extensions.size()]);
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
		else if (e instanceof AntivirusScannerException ||
				 e instanceof DuplicateFileException ||
				 e instanceof DuplicateFolderNameException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileMimeTypeException ||
				 e instanceof FileNameException ||
				 e instanceof FileSizeException ||
				 e instanceof LiferayFileItemException ||
				 e instanceof NoSuchFolderException ||
				 e instanceof SourceFileNameException ||
				 e instanceof StorageFieldRequiredException) {

			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if ((uploadException != null) && !cmd.equals(Constants.ADD_TEMP)) {
				String uploadExceptionRedirect = ParamUtil.getString(
					actionRequest, "uploadExceptionRedirect");

				actionResponse.sendRedirect(uploadExceptionRedirect);

				SessionErrors.add(actionRequest, e.getClass());

				return;
			}
			else if (!cmd.equals(Constants.ADD_DYNAMIC) &&
					 !cmd.equals(Constants.ADD_MULTIPLE) &&
					 !cmd.equals(Constants.ADD_TEMP)) {

				if (e instanceof AntivirusScannerException) {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					SessionErrors.add(actionRequest, e.getClass());
				}

				return;
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				hideDefaultErrorMessage(actionRequest);
			}

			if (e instanceof AntivirusScannerException ||
				e instanceof DuplicateFileException ||
				e instanceof FileExtensionException ||
				e instanceof FileNameException ||
				e instanceof FileSizeException) {

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);

				response.setContentType(ContentTypes.TEXT_HTML);
				response.setStatus(HttpServletResponse.SC_OK);

				String errorMessage = StringPool.BLANK;
				int errorType = 0;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (e instanceof AntivirusScannerException) {
					AntivirusScannerException ase =
						(AntivirusScannerException)e;

					errorMessage = themeDisplay.translate(ase.getMessageKey());
					errorType =
						ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				}

				if (e instanceof DuplicateFileException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-unique-document-name");
					errorType =
						ServletResponseConstants.SC_DUPLICATE_FILE_EXCEPTION;
				}
				else if (e instanceof FileExtensionException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-extension-x",
						StringUtil.merge(
							getAllowedFileExtensions(
								portletConfig, actionRequest, actionResponse)));
					errorType =
						ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
				}
				else if (e instanceof FileNameException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-name");
					errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
				}
				else if (e instanceof FileSizeException) {
					long fileMaxSize = PrefsPropsUtil.getLong(
						PropsKeys.DL_FILE_MAX_SIZE);

					if (fileMaxSize == 0) {
						fileMaxSize = PrefsPropsUtil.getLong(
							PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
					}

					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-size-no-larger" +
							"-than-x",
						TextFormatter.formatStorageSize(
							fileMaxSize, themeDisplay.getLocale()));

					errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("message", errorMessage);
				jsonObject.put("status", errorType);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}

			if (e instanceof AntivirusScannerException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}
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
				throw new NoSuchFolderException("{folderId=" + folderId + "}");
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
					PortletDisplay portletDisplay =
						themeDisplay.getPortletDisplay();

					DLPortletInstanceSettings dlPortletInstanceSettings =
						DLPortletInstanceSettings.getInstance(
							themeDisplay.getLayout(), portletDisplay.getId());

					String[] mimeTypes =
						dlPortletInstanceSettings.getMimeTypes();

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

			if (uploadException != null) {
				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException();
				}
				else if (uploadException.isExceededSizeLimit()) {
					throw new FileSizeException(uploadException.getCause());
				}
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