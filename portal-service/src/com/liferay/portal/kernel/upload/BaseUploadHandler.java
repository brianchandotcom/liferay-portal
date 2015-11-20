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

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public abstract class BaseUploadHandler implements UploadHandler {

	@Override
	public void upload(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			checkPermission(
				themeDisplay.getScopeGroupId(),
				getFolderId(uploadPortletRequest),
				themeDisplay.getPermissionChecker());

			UploadException uploadException =
				(UploadException)portletRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededRequestContentLengthLimit()) {
					throw new RequestContentLengthException(cause);
				}

				throw new PortalException(cause);
			}

			String randomId = ParamUtil.getString(
				uploadPortletRequest, "randomId");

			JSONObject imageJSONObject = getImageJSONObject(portletRequest);

			jsonObject.put("success", Boolean.TRUE);

			imageJSONObject.put("randomId", randomId);

			jsonObject.put("file", imageJSONObject);

			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortalException pe) {
			handleUploadException(
				portletRequest, portletResponse, pe, jsonObject);
		}
	}

	protected abstract FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException;

	protected abstract void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException;

	protected void doHandleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		throw pe;
	}

	protected abstract FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException;

	protected long getFolderId(UploadPortletRequest uploadPortletRequest) {
		return 0;
	}

	protected JSONObject getImageJSONObject(PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		InputStream inputStream = null;

		try {
			imageJSONObject.put(
				"attributeDataImageId",
				EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);

			String parameterName = getParameterName();

			String fileName = uploadPortletRequest.getFileName(parameterName);
			String contentType = uploadPortletRequest.getContentType(
				parameterName);
			long size = uploadPortletRequest.getSize(parameterName);

			validateFile(fileName, contentType, size);

			long folderId = getFolderId(uploadPortletRequest);

			String uniqueFileName = getUniqueFileName(
				themeDisplay, fileName, folderId);

			inputStream = uploadPortletRequest.getFileAsStream(parameterName);

			FileEntry fileEntry = addFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				folderId, uniqueFileName, contentType, inputStream, size,
				getServiceContext(uploadPortletRequest));

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
			imageJSONObject.put("groupId", fileEntry.getGroupId());
			imageJSONObject.put("title", fileEntry.getTitle());
			imageJSONObject.put("url", getURL(fileEntry, themeDisplay));
			imageJSONObject.put("uuid", fileEntry.getUuid());

			return imageJSONObject;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected abstract String getParameterName();

	/**
	 * @throws PortalException
	 */
	protected ServiceContext getServiceContext(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return null;
	}

	protected String getUniqueFileName(
			ThemeDisplay themeDisplay, String fileName, long folderId)
		throws PortalException {

		FileEntry fileEntry = fetchFileEntry(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), folderId,
			fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = fetchFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				folderId, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	protected String getURL(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		return PortletFileRepositoryUtil.getPortletFileEntryURL(
			themeDisplay, fileEntry, StringPool.BLANK);
	}

	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof FileNameException ||
			pe instanceof FileSizeException ||
			pe instanceof RequestContentLengthException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}
			else if (pe instanceof FileSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof RequestContentLengthException) {
				errorType =
					ServletResponseConstants.
						SC_UPLOAD_REQUEST_CONTENT_LENGTH_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);
		}
		else {
			doHandleUploadException(
				portletRequest, portletResponse, pe, jsonObject);
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected abstract void validateFile(
			String fileName, String contentType, long size)
		throws PortalException;

	protected static final String TEMP_FOLDER_NAME =
		BaseUploadHandler.class.getName();

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

}