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

package com.liferay.portlet.documentselector.action;

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileMimeTypeException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.action.ActionUtil;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentselector.util.DocumentSelectorUtil;
import com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException;

import java.io.InputStream;

import java.util.HashSet;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Eudaldo Alonso
 */
public class EditFileEntryAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

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
			else {
				fileEntry = updateFileEntry(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");
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
				redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					if (fileEntry != null) {
						String portletId = HttpUtil.getParameter(
							redirect, "p_p_id", false);

						String namespace = PortalUtil.getPortletNamespace(
							portletId);

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
		catch (Exception e) {
			handleUploadException(
				portletConfig, actionRequest, actionResponse, e);
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
					"portlet.document_selector.error");
			}
			else {
				throw e;
			}
		}

		String forward = "portlet.document_selector.edit_file_entry";

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

	protected String[] getAllowedFileExtensions(PortletRequest portletRequest)
		throws PortalException {

		Set<String> extensions = new HashSet<String>();

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		String[] mimeTypes = DocumentSelectorUtil.getMimeTypes(request);

		for (String mimeType : mimeTypes) {
			extensions.addAll(MimeTypesUtil.getExtensions(mimeType));
		}

		return extensions.toArray(new String[extensions.size()]);
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
			ActionResponse actionResponse, Exception e)
		throws Exception {

		if (e instanceof AssetCategoryException ||
			e instanceof AssetTagException) {

			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		else if (e instanceof AntivirusScannerException ||
				 e instanceof DuplicateFileException ||
				 e instanceof DuplicateFolderNameException ||
				 e instanceof LiferayFileItemException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileMimeTypeException ||
				 e instanceof FileNameException ||
				 e instanceof FileSizeException ||
				 e instanceof NoSuchFolderException ||
				 e instanceof SourceFileNameException ||
				 e instanceof StorageFieldRequiredException) {

			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				String uploadExceptionRedirect = ParamUtil.getString(
					actionRequest, "uploadExceptionRedirect");

				actionResponse.sendRedirect(uploadExceptionRedirect);

				SessionErrors.add(actionRequest, e.getClass());

				return;
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
							getAllowedFileExtensions(actionRequest)));
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

	protected FileEntry updateFileEntry(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(
			uploadPortletRequest, "repositoryId");
		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");
		String sourceFileName = uploadPortletRequest.getFileName("file");
		String title = ParamUtil.getString(uploadPortletRequest, "title");
		String description = ParamUtil.getString(
			uploadPortletRequest, "description");
		String changeLog = ParamUtil.getString(
			uploadPortletRequest, "changeLog");

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

			if (size == 0) {
				contentType = MimeTypesUtil.getContentType(title);
			}

			if (size > 0) {
				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					actionRequest);

				String[] mimeTypes = DocumentSelectorUtil.getMimeTypes(request);

				if (ArrayUtil.isNotEmpty(mimeTypes) &&
					!ArrayUtil.contains(mimeTypes, contentType)) {

					throw new FileMimeTypeException(contentType);
				}
			}

			inputStream = uploadPortletRequest.getFileAsStream("file");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, sourceFileName, contentType, title,
				description, changeLog, inputStream, size, serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), -1);

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

}