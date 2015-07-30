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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;

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

		checkPermission(
			themeDisplay.getScopeGroupId(),
			themeDisplay.getPermissionChecker());

		UploadException uploadException =
			(UploadException)portletRequest.getAttribute(
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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String randomId = ParamUtil.getString(uploadPortletRequest, "randomId");

		try {
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
			ThemeDisplay themeDisplay, String fileName, InputStream inputStream,
			String contentType)
		throws PortalException;

	protected abstract void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException;

	protected abstract FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException;

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

			inputStream = uploadPortletRequest.getFileAsStream(parameterName);

			String uniqueFileName = getUniqueFileName(themeDisplay, fileName);

			FileEntry fileEntry = addFileEntry(
				themeDisplay, uniqueFileName, inputStream, contentType);

			imageJSONObject.put("fileEntryId", fileEntry.getFileEntryId());

			imageJSONObject.put(
				"url",
				PortletFileRepositoryUtil.getPortletFileEntryURL(
					themeDisplay, fileEntry, StringPool.BLANK));

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

	protected String getUniqueFileName(
			ThemeDisplay themeDisplay, String fileName)
		throws PortalException {

		FileEntry fileEntry = fetchFileEntry(themeDisplay, fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = fetchFileEntry(themeDisplay, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	protected abstract void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException;

	protected abstract void validateFile(
			String fileName, String contentType, long size)
		throws PortalException;

	protected static final String _TEMP_FOLDER_NAME =
		BaseUploadHandler.class.getName();

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

}