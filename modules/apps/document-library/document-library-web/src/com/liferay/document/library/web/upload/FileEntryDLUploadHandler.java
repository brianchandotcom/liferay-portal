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

package com.liferay.document.library.web.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.ImageNameException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Roberto Díaz
 */
public class FileEntryDLUploadHandler extends BaseUploadHandler {

	@Override
	protected FileEntry addFileEntry(
			ThemeDisplay themeDisplay, long classPK, String fileName,
			InputStream inputStream, String contentType)
		throws PortalException {

		return null;
	}

	protected void checkPermission(
			long groupId, long classPK, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, DLPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_DOCUMENT);

		if (!containsResourcePermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_DOCUMENT);
		}
	}

	@Override
	protected FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, long classPK, String fileName)
		throws PortalException {

		return null;
	}

	@Override
	protected JSONObject getImageJSONObject(PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		InputStream inputStream = null;

		try {
			long repositoryId = ParamUtil.getLong(
				uploadPortletRequest, "repositoryId");
			long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

			String fileName = uploadPortletRequest.getFileName(
				"imageSelectorFileName");

			if (folderId > 0) {
				Folder folder = DLAppServiceUtil.getFolder(folderId);

				if (folder.getGroupId() != themeDisplay.getScopeGroupId()) {
					throw new NoSuchFolderException(
						"{folderId=" + folderId + "}");
				}
			}

			String contentType = uploadPortletRequest.getContentType(
				"imageSelectorFileName");

			long size = uploadPortletRequest.getSize("imageSelectorFileName");

			if (size == 0) {
				contentType = MimeTypesUtil.getContentType(fileName);
			}

			validateFile(fileName, contentType, size);

			inputStream = uploadPortletRequest.getFileAsStream(
				"imageSelectorFileName");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				repositoryId, folderId, fileName, contentType, fileName,
				StringPool.BLANK, StringPool.BLANK, inputStream, size,
				serviceContext);

			imageJSONObject.put(
				"url",
				DLUtil.getPreviewURL(
					fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
					StringPool.BLANK));

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

			return imageJSONObject;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	protected String getParameterName() {
		return null;
	}

	@Override
	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof ImageNameException ||
			pe instanceof FileNameException ||
			pe instanceof FileSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof ImageNameException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (pe instanceof FileSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);

			try {
				JSONPortletResponseUtil.writeJSON(
					portletRequest, portletResponse, jsonObject);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
		else {
			throw pe;
		}
	}

	@Override
	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		long maxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DL_IMAGE_EXTENSIONS, StringPool.COMMA);

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new ImageNameException("Invalid image for file name " + fileName);
	}

}