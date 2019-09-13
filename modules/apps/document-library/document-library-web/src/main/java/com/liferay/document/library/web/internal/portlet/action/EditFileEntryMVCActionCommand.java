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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileEntryLockException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileMimeTypeException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileEntryTypeException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.RequiredFileException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.dynamic.data.mapping.kernel.StorageFieldRequiredException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.service.TrashEntryService;
import com.liferay.upload.UploadResponseHandler;

import java.io.IOException;
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
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 * @author Kenneth Chang
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry",
		"mvc.command.name=/document_library/upload_multiple_file_entries"
	},
	service = MVCActionCommand.class
)
public class EditFileEntryMVCActionCommand extends BaseMVCActionCommand {

	public static final String TEMP_FOLDER_NAME =
		EditFileEntryMVCActionCommand.class.getName();

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		FileEntry fileEntry = null;

		PortletConfig portletConfig = getPortletConfig(actionRequest);

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (cmd.equals(Constants.ADD_TEMP)) {
					if (cause instanceof FileUploadBase.IOFileUploadException) {
						if (_log.isInfoEnabled()) {
							_log.info("Temporary upload was cancelled");
						}
					}
				}
				else {
					if (uploadException.isExceededFileSizeLimit()) {
						throw new FileSizeException(cause);
					}

					if (uploadException.isExceededLiferayFileItemSizeLimit()) {
						throw new LiferayFileItemException(cause);
					}

					if (uploadException.isExceededUploadRequestSizeLimit()) {
						throw new UploadRequestSizeException(cause);
					}

					throw new PortalException(cause);
				}
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.ADD_DYNAMIC) ||
					 cmd.equals(Constants.UPDATE) ||
					 cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				UploadPortletRequest uploadPortletRequest =
					_portal.getUploadPortletRequest(actionRequest);

				String sourceFileName = uploadPortletRequest.getFileName(
					"file");

				try {
					fileEntry = _updateFileEntry(
						portletConfig, actionRequest, actionResponse,
						uploadPortletRequest);
				}
				catch (PortalException pe) {
					if (!cmd.equals(Constants.ADD_DYNAMIC) &&
						Validator.isNotNull(sourceFileName)) {

						SessionErrors.add(
							actionRequest, RequiredFileException.class);
					}

					throw pe;
				}
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				_addMultipleFileEntries(
					portletConfig, actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				_addTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteFileEntry(actionRequest, false);
			}
			else if (cmd.equals(Constants.DELETE_TEMP)) {
				_deleteTempFileEntry(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
				_cancelFileEntriesCheckOut(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKIN)) {
				_checkInFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.CHECKOUT)) {
				_checkOutFileEntries(actionRequest);
			}
			else if (cmd.equals(Constants.MOVE_TO_TRASH)) {
				_deleteFileEntry(actionRequest, true);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				_restoreTrashEntries(actionRequest);
			}
			else if (cmd.equals(Constants.REVERT)) {
				_revertFileEntry(actionRequest);
			}

			if (cmd.equals(Constants.ADD_TEMP) ||
				cmd.equals(Constants.DELETE_TEMP)) {

				actionResponse.setRenderParameter("mvcPath", "/null.jsp");
			}
			else if (cmd.equals(Constants.PREVIEW)) {
				SessionMessages.add(
					actionRequest,
					_portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT);

				hideDefaultSuccessMessage(actionRequest);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/document_library/edit_file_entry");
			}
			else {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");
				int workflowAction = ParamUtil.getInteger(
					actionRequest, "workflowAction",
					WorkflowConstants.ACTION_SAVE_DRAFT);

				if ((fileEntry != null) &&
					(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

					redirect = _getSaveAndContinueRedirect(
						portletConfig, actionRequest, fileEntry, redirect);

					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					redirect = _portal.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						if (cmd.equals(Constants.ADD) && (fileEntry != null)) {
							String portletResource = _http.getParameter(
								redirect, "portletResource", false);

							String namespace = _portal.getPortletNamespace(
								portletResource);

							if (Validator.isNotNull(portletResource)) {
								redirect = _http.addParameter(
									redirect, namespace + "className",
									DLFileEntry.class.getName());
								redirect = _http.addParameter(
									redirect, namespace + "classPK",
									fileEntry.getFileEntryId());
							}
						}

						actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
					}
				}
			}
		}
		catch (Exception e) {
			_handleUploadException(actionRequest, actionResponse, cmd, e);
		}
	}

	private void _addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException, PortalException {

		List<KeyValuePair> validFileNameKVPs = new ArrayList<>();
		List<KeyValuePair> invalidFileNameKVPs = new ArrayList<>();

		String[] selectedFileNames = ParamUtil.getParameterValues(
			actionRequest, "selectedFileName", new String[0], false);

		for (String selectedFileName : selectedFileNames) {
			_addMultipleFileEntries(
				portletConfig, actionRequest, selectedFileName,
				validFileNameKVPs, invalidFileNameKVPs);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (KeyValuePair validFileNameKVP : validFileNameKVPs) {
			String fileName = validFileNameKVP.getKey();
			String originalFileName = validFileNameKVP.getValue();

			JSONObject jsonObject = JSONUtil.put(
				"added", Boolean.TRUE
			).put(
				"fileName", fileName
			).put(
				"originalFileName", originalFileName
			);

			jsonArray.put(jsonObject);
		}

		for (KeyValuePair invalidFileNameKVP : invalidFileNameKVPs) {
			String fileName = invalidFileNameKVP.getKey();
			String errorMessage = invalidFileNameKVP.getValue();

			JSONObject jsonObject = JSONUtil.put(
				"added", Boolean.FALSE
			).put(
				"errorMessage", errorMessage
			).put(
				"fileName", fileName
			).put(
				"originalFileName", fileName
			);

			jsonArray.put(jsonObject);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonArray);
	}

	private void _addMultipleFileEntries(
			PortletConfig portletConfig, ActionRequest actionRequest,
			String selectedFileName, List<KeyValuePair> validFileNameKVPs,
			List<KeyValuePair> invalidFileNameKVPs)
		throws PortalException {

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
				TEMP_FOLDER_NAME, selectedFileName);

			String originalSelectedFileName =
				TempFileEntryUtil.getOriginalTempFileName(
					tempFileEntry.getFileName());

			String uniqueFileName = DLUtil.getUniqueFileName(
				tempFileEntry.getGroupId(), folderId, originalSelectedFileName);

			String mimeType = tempFileEntry.getMimeType();
			InputStream inputStream = tempFileEntry.getContentStream();
			long size = tempFileEntry.getSize();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), actionRequest);

			FileEntry fileEntry = _dlAppService.addFileEntry(
				repositoryId, folderId, uniqueFileName, mimeType,
				uniqueFileName, description, changeLog, inputStream, size,
				serviceContext);

			_assetDisplayPageEntryFormProcessor.process(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
				actionRequest);

			validFileNameKVPs.add(
				new KeyValuePair(uniqueFileName, selectedFileName));
		}
		catch (Exception e) {
			String errorMessage = _getAddMultipleFileEntriesErrorMessage(
				portletConfig, actionRequest, e);

			invalidFileNameKVPs.add(
				new KeyValuePair(selectedFileName, errorMessage));
		}
		finally {
			if (tempFileEntry != null) {
				TempFileEntryUtil.deleteTempFileEntry(
					tempFileEntry.getFileEntryId());
			}
		}
	}

	private void _addTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			String tempFileName = TempFileEntryUtil.getTempFileName(
				sourceFileName);

			String mimeType = uploadPortletRequest.getContentType("file");

			FileEntry fileEntry = _dlAppService.addTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, TEMP_FOLDER_NAME,
				tempFileName, inputStream, mimeType);

			JSONObject jsonObject = _multipleUploadResponseHandler.onSuccess(
				uploadPortletRequest, fileEntry);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private void _cancelFileEntriesCheckOut(ActionRequest actionRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
		else {
			long[] fileEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIdsFileEntry");

			for (long curFileEntryId : fileEntryIds) {
				_dlAppService.cancelCheckOut(curFileEntryId);
			}
		}
	}

	private void _checkInFileEntries(ActionRequest actionRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				actionRequest.getParameter("versionIncrease"),
				DLVersionNumberIncrease.AUTOMATIC);

		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fileEntryId > 0) {
			_dlAppService.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);
		}
		else {
			long[] fileEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIdsFileEntry");

			for (long curFileEntryId : fileEntryIds) {
				_dlAppService.checkInFileEntry(
					curFileEntryId, dlVersionNumberIncrease, changeLog,
					serviceContext);
			}
		}
	}

	private void _checkOutFileEntries(ActionRequest actionRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fileEntryId > 0) {
			_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);
		}
		else {
			long[] fileEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIdsFileEntry");

			for (long curFileEntryId : fileEntryIds) {
				_dlAppService.checkOutFileEntry(curFileEntryId, serviceContext);
			}
		}
	}

	private ServiceContext _createServiceContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (!cmd.equals(Constants.ADD_DYNAMIC)) {
			return ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), httpServletRequest);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		Group group = themeDisplay.getScopeGroup();

		if (layout.isPublicLayout() ||
			(layout.isTypeControlPanel() && !group.hasPrivateLayouts())) {

			return ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), httpServletRequest);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), httpServletRequest);

		ModelPermissions modelPermissions =
			serviceContext.getModelPermissions();

		serviceContext.setModelPermissions(
			ModelPermissionsFactory.create(
				modelPermissions.getActionIds(
					RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE),
				_RESTRICTED_GUEST_PERMISSIONS, DLFileEntry.class.getName()));

		return serviceContext;
	}

	private void _deleteFileEntry(
			ActionRequest actionRequest, boolean moveToTrash)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId == 0) {
			return;
		}

		String version = ParamUtil.getString(actionRequest, "version");

		if (Validator.isNotNull(version)) {
			_dlAppService.deleteFileVersion(fileEntryId, version);

			return;
		}

		if (!moveToTrash) {
			_dlAppService.deleteFileEntry(fileEntryId);

			return;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		if (!fileEntry.isRepositoryCapabilityProvided(TrashCapability.class)) {
			hideDefaultSuccessMessage(actionRequest);

			return;
		}

		fileEntry = _dlTrashService.moveFileEntryToTrash(fileEntryId);

		List<TrashedModel> trashedModels = new ArrayList<>();

		trashedModels.add((TrashedModel)fileEntry.getModel());

		Map<String, Object> data = new HashMap<>();

		data.put("trashedModels", trashedModels);

		addDeleteSuccessData(actionRequest, data);
	}

	private void _deleteTempFileEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(actionRequest, "folderId");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			_dlAppService.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), folderId, TEMP_FOLDER_NAME,
				fileName);

			jsonObject.put("deleted", Boolean.TRUE);
		}
		catch (Exception e) {
			String errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-deleting-the-file");

			jsonObject.put(
				"deleted", Boolean.FALSE
			).put(
				"errorMessage", errorMessage
			);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getAddMultipleFileEntriesErrorMessage(
			PortletConfig portletConfig, ActionRequest actionRequest,
			Exception e)
		throws PortalException {

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
		else if (e instanceof DuplicateFileEntryException) {
			errorMessage = themeDisplay.translate(
				"the-folder-you-selected-already-has-an-entry-with-this-" +
					"name.-please-select-a-different-folder");
		}
		else if (e instanceof FileExtensionException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-extension-x",
				StringUtil.merge(
					_getAllowedFileExtensions(portletConfig, actionRequest)));
		}
		else if (e instanceof FileNameException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-name");
		}
		else if (e instanceof FileSizeException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
				TextFormatter.formatStorageSize(
					_dlValidator.getMaxAllowableSize(),
					themeDisplay.getLocale()));
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

	private String[] _getAllowedFileExtensions(
			PortletConfig portletConfig, PortletRequest portletRequest)
		throws PortalException {

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
			return _dlConfiguration.fileExtensions();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				themeDisplay.getLayout(), portletDisplay.getId());

		Set<String> extensions = new HashSet<>();

		String[] mimeTypes = dlPortletInstanceSettings.getMimeTypes();

		for (String mimeType : mimeTypes) {
			extensions.addAll(MimeTypesUtil.getExtensions(mimeType));
		}

		return extensions.toArray(new String[0]);
	}

	private String _getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			FileEntry fileEntry, String redirect)
		throws PortletException {

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, portletConfig.getPortletName(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(fileEntry.getGroupId()), false);
		portletURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()), false);
		portletURL.setParameter(
			"version", String.valueOf(fileEntry.getVersion()), false);
		portletURL.setWindowState(actionRequest.getWindowState());

		return portletURL.toString();
	}

	private void _handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd, Exception e)
		throws Exception {

		if (e instanceof AssetCategoryException ||
			e instanceof AssetTagException) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		else if (e instanceof AntivirusScannerException ||
				 e instanceof DuplicateFileEntryException ||
				 e instanceof DuplicateFolderNameException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileMimeTypeException ||
				 e instanceof FileNameException ||
				 e instanceof FileSizeException ||
				 e instanceof LiferayFileItemException ||
				 e instanceof NoSuchFolderException ||
				 e instanceof SourceFileNameException ||
				 e instanceof StorageFieldRequiredException ||
				 e instanceof UploadRequestSizeException) {

			if (!cmd.equals(Constants.ADD_DYNAMIC) &&
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
				e instanceof DuplicateFileEntryException ||
				e instanceof FileExtensionException ||
				e instanceof FileNameException ||
				e instanceof FileSizeException ||
				e instanceof UploadRequestSizeException) {

				JSONObject jsonObject =
					_multipleUploadResponseHandler.onFailure(
						actionRequest, (PortalException)e);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}

			if (e instanceof AntivirusScannerException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}
		}
		else if (e instanceof DuplicateLockException ||
				 e instanceof FileEntryLockException.MustOwnLock ||
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

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
		else {
			Throwable cause = e.getCause();

			if (cause instanceof DuplicateFileEntryException) {
				SessionErrors.add(
					actionRequest, DuplicateFileEntryException.class);
			}
			else {
				throw e;
			}
		}
	}

	private void _restoreTrashEntries(ActionRequest actionRequest)
		throws Exception {

		long[] restoreTrashEntryIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "restoreTrashEntryIds"), 0L);

		for (long restoreTrashEntryId : restoreTrashEntryIds) {
			_trashEntryService.restoreEntry(restoreTrashEntryId);
		}
	}

	private void _revertFileEntry(ActionRequest actionRequest)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String version = ParamUtil.getString(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), actionRequest);

		_dlAppService.revertFileEntry(fileEntryId, version, serviceContext);
	}

	private FileEntry _updateFileEntry(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse,
			UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

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

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				uploadPortletRequest.getParameter("versionIncrease"),
				DLVersionNumberIncrease.AUTOMATIC);

		boolean updateVersionDetails = ParamUtil.getBoolean(
			uploadPortletRequest, "updateVersionDetails");

		if (!updateVersionDetails) {
			dlVersionNumberIncrease = DLVersionNumberIncrease.AUTOMATIC;
		}

		if (cmd.equals(Constants.ADD_DYNAMIC)) {
			title = uploadPortletRequest.getFileName("file");
		}

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

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

				if (portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
					PortletDisplay portletDisplay =
						themeDisplay.getPortletDisplay();

					DLPortletInstanceSettings dlPortletInstanceSettings =
						DLPortletInstanceSettings.getInstance(
							themeDisplay.getLayout(), portletDisplay.getId());

					int count = Arrays.binarySearch(
						dlPortletInstanceSettings.getMimeTypes(), contentType);

					if (count < 0) {
						throw new FileMimeTypeException(contentType);
					}
				}
			}

			ServiceContext serviceContext = _createServiceContext(
				uploadPortletRequest);

			FileEntry fileEntry = null;

			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_DYNAMIC)) {

				// Add file entry

				fileEntry = _dlAppService.addFileEntry(
					repositoryId, folderId, sourceFileName, contentType, title,
					description, changeLog, inputStream, size, serviceContext);

				if (cmd.equals(Constants.ADD_DYNAMIC)) {
					JSONObject jsonObject = JSONUtil.put(
						"fileEntryId", fileEntry.getFileEntryId());

					JSONPortletResponseUtil.writeJSON(
						actionRequest, actionResponse, jsonObject);
				}
			}
			else if (cmd.equals(Constants.UPDATE_AND_CHECKIN)) {

				// Update file entry and checkin

				fileEntry = _dlAppService.updateFileEntryAndCheckIn(
					fileEntryId, sourceFileName, contentType, title,
					description, changeLog, dlVersionNumberIncrease,
					inputStream, size, serviceContext);
			}
			else {

				// Update file entry

				fileEntry = _dlAppService.updateFileEntry(
					fileEntryId, sourceFileName, contentType, title,
					description, changeLog, dlVersionNumberIncrease,
					inputStream, size, serviceContext);
			}

			_assetDisplayPageEntryFormProcessor.process(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
				actionRequest);

			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			if (Validator.isNotNull(portletResource)) {
				MultiSessionMessages.add(
					actionRequest, portletResource + "requestProcessed");
			}

			return fileEntry;
		}
	}

	private static final String[] _RESTRICTED_GUEST_PERMISSIONS = {};

	private static final Log _log = LogFactoryUtil.getLog(
		EditFileEntryMVCActionCommand.class);

	@Reference
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Reference
	private DLAppService _dlAppService;

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private DLTrashService _dlTrashService;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private Http _http;

	@Reference(target = "(upload.response.handler=multiple)")
	private UploadResponseHandler _multipleUploadResponseHandler;

	@Reference
	private Portal _portal;

	@Reference
	private TrashEntryService _trashEntryService;

}