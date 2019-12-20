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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
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
public class TestUploadHandler implements UploadHandler {

	public TestUploadHandler(TestUploadPortlet testUploadPortlet) {
		_testUploadPortlet = testUploadPortlet;
	}

	@Override
	public void upload(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

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

			JSONObject imageJSONObject = getImageJSONObject(portletRequest);

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
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortalException pe) {
			handleUploadException(
				portletRequest, portletResponse, pe, jsonObject);
		}
	}

	protected FileEntry addFileEntry(
		long userId, long groupId, long folderId, String fileName,
		String contentType, InputStream inputStream, long size,
		ServiceContext serviceContext) {

		TestFileEntry testFileEntry = new TestFileEntry(
			fileName, folderId, groupId, inputStream);

		_testUploadPortlet.put(testFileEntry);

		return testFileEntry;
	}

	protected FileEntry fetchFileEntry(
		long userId, long groupId, long folderId, String fileName) {

		FileEntry fileEntry = new TestFileEntry(
			fileName, folderId, groupId, null);

		return _testUploadPortlet.get(fileEntry.toString());
	}

	protected JSONObject getImageJSONObject(PortletRequest portletRequest)
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

			String fileName = uploadPortletRequest.getFileName(parameterName);
			String contentType = uploadPortletRequest.getContentType(
				parameterName);
			long size = uploadPortletRequest.getSize(parameterName);

			long folderId = 0;

			String uniqueFileName = getUniqueFileName(
				themeDisplay, fileName, folderId);

			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					parameterName)) {

				FileEntry fileEntry = addFileEntry(
					themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
					folderId, uniqueFileName, contentType, inputStream, size,
					ServiceContextFactory.getInstance(
						TestFileEntry.class.getName(), uploadPortletRequest));

				imageJSONObject.put(
					"fileEntryId", fileEntry.getFileEntryId()
				).put(
					"groupId", fileEntry.getGroupId()
				).put(
					"title", fileEntry.getTitle()
				).put(
					"type", "document"
				).put(
					"url",
					PortletFileRepositoryUtil.getPortletFileEntryURL(
						themeDisplay, fileEntry, StringPool.BLANK)
				).put(
					"uuid", fileEntry.getUuid()
				);

				return imageJSONObject;
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
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

	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof FileNameException ||
			pe instanceof FileSizeException ||
			pe instanceof UploadRequestSizeException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				ThemeDisplay themeDisplay =
					(ThemeDisplay)portletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}
			else if (pe instanceof FileSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof UploadRequestSizeException) {
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
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private final TestUploadPortlet _testUploadPortlet;

}