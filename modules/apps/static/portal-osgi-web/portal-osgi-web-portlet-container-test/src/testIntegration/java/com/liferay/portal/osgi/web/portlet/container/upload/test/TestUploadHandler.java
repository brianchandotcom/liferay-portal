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

package com.liferay.portal.osgi.web.portlet.container.upload.test;

import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Manuel de la Peña
 */
public class TestUploadHandler {

	public TestUploadHandler(TestUploadPortlet testUploadPortlet) {
		_testUploadPortlet = testUploadPortlet;
	}

	public void upload(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
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

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}

			JSONObject imageJSONObject = _getImageJSONObject(portletRequest);

			String randomId = ParamUtil.getString(
				uploadPortletRequest, "randomId");

			imageJSONObject.put("randomId", randomId);

			jsonObject.put(
				"file", imageJSONObject
			).put(
				"success", Boolean.TRUE
			);

			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		catch (PortalException portalException) {
			_handleUploadException(
				portletRequest, portletResponse, portalException, jsonObject);
		}
	}

	private FileEntry _fetchFileEntry(
		long groupId, long folderId, String fileName) {

		FileEntry fileEntry = new TestFileEntry(
			fileName, folderId, groupId, null);

		return _testUploadPortlet.get(fileEntry.toString());
	}

	private JSONObject _getImageJSONObject(PortletRequest portletRequest)
		throws PortalException {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject imageJSONObject = JSONFactoryUtil.createJSONObject();

		try {
			imageJSONObject.put(
				"attributeDataImageId",
				EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);

			String parameterName = TestUploadPortlet.PARAMETER_NAME;

			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					parameterName)) {

				TestFileEntry testFileEntry = new TestFileEntry(
					_getUniqueFileName(
						themeDisplay,
						uploadPortletRequest.getFileName(parameterName), 0),
					0, themeDisplay.getScopeGroupId(), inputStream);

				_testUploadPortlet.put(testFileEntry);

				imageJSONObject.put(
					"fileEntryId", testFileEntry.getFileEntryId()
				).put(
					"groupId", testFileEntry.getGroupId()
				).put(
					"title", testFileEntry.getTitle()
				).put(
					"type", "document"
				).put(
					"url",
					PortletFileRepositoryUtil.getPortletFileEntryURL(
						themeDisplay, testFileEntry, StringPool.BLANK)
				).put(
					"uuid", testFileEntry.getUuid()
				);

				return imageJSONObject;
			}
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private String _getUniqueFileName(
			ThemeDisplay themeDisplay, String fileName, long folderId)
		throws PortalException {

		FileEntry fileEntry = _fetchFileEntry(
			themeDisplay.getScopeGroupId(), folderId, fileName);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0;
			 i < UploadServletRequestConfigurationHelperUtil.getMaxTries();
			 i++) {

			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = _fetchFileEntry(
				themeDisplay.getScopeGroupId(), folderId, curFileName);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName);
	}

	private void _handleUploadException(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortalException portalException, JSONObject jsonObject) {

		jsonObject.put("success", Boolean.FALSE);

		if (portalException instanceof AntivirusScannerException ||
			portalException instanceof FileNameException ||
			portalException instanceof FileSizeException ||
			portalException instanceof UploadRequestSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			if (portalException instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException antivirusScannerException =
					(AntivirusScannerException)portalException;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)portletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				errorMessage = themeDisplay.translate(
					antivirusScannerException.getMessageKey());
			}
			else if (portalException instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}
			else if (portalException instanceof FileSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (portalException instanceof UploadRequestSizeException) {
				errorType =
					ServletResponseConstants.SC_UPLOAD_REQUEST_SIZE_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONUtil.put(
				"errorType", errorType
			).put(
				"message", errorMessage
			);

			jsonObject.put("error", errorJSONObject);
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				portletRequest, portletResponse, jsonObject);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private final TestUploadPortlet _testUploadPortlet;

}