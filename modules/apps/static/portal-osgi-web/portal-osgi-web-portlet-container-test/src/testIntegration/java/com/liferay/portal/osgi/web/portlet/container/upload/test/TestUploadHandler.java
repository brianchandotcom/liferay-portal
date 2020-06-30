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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Manuel de la Peña
 */
public class TestUploadHandler extends BaseUploadHandler {

	public TestUploadHandler(
		TestUploadPortlet testUploadPortlet,
		UniqueFileNameProvider uniqueFileNameProvider) {

		_testUploadPortlet = testUploadPortlet;
		_uniqueFileNameProvider = uniqueFileNameProvider;
	}

	@Override
	protected FileEntry addFileEntry(
		long userId, long groupId, long folderId, String fileName,
		String contentType, InputStream inputStream, long size,
		ServiceContext serviceContext) {

		TestFileEntry testFileEntry = new TestFileEntry(
			fileName, folderId, groupId, inputStream);

		_testUploadPortlet.put(testFileEntry);

		return testFileEntry;
	}

	@Override
	protected void checkPermission(
		long groupId, long folderId, PermissionChecker permissionChecker) {
	}

	@Override
	protected void doHandleUploadException(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortalException pe, JSONObject jsonObject) {
	}

	@Override
	protected FileEntry fetchFileEntry(
		long userId, long groupId, long folderId, String fileName) {

		FileEntry fileEntry = new TestFileEntry(
			fileName, folderId, groupId, null);

		return _testUploadPortlet.get(fileEntry.toString());
	}

	@Override
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

			String parameterName = getParameterName();

			validateFile(
				uploadPortletRequest.getFileName(parameterName),
				uploadPortletRequest.getContentType(parameterName),
				uploadPortletRequest.getSize(parameterName));

			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					parameterName)) {

				TestFileEntry fileEntry = new TestFileEntry(
					_uniqueFileNameProvider.provide(
						uploadPortletRequest.getFileName(parameterName),
						curFileName -> _hasFileEntry(
							themeDisplay.getScopeGroupId(), curFileName)),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					themeDisplay.getScopeGroupId(), inputStream);

				_testUploadPortlet.put(fileEntry);

				imageJSONObject.put(
					"fileEntryId", fileEntry.getFileEntryId()
				).put(
					"groupId", fileEntry.getGroupId()
				).put(
					"title", fileEntry.getTitle()
				).put(
					"type", "document"
				).put(
					"url", getURL(fileEntry, themeDisplay)
				).put(
					"uuid", fileEntry.getUuid()
				);

				return imageJSONObject;
			}
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	protected String getParameterName() {
		return TestUploadPortlet.PARAMETER_NAME;
	}

	@Override
	protected ServiceContext getServiceContext(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return ServiceContextFactory.getInstance(
			TestFileEntry.class.getName(), uploadPortletRequest);
	}

	@Override
	protected void validateFile(
		String fileName, String contentType, long size) {
	}

	private boolean _hasFileEntry(long groupId, String fileName) {
		FileEntry fileEntry = new TestFileEntry(
			fileName, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, groupId,
			null);

		TestFileEntry testFileEntry = _testUploadPortlet.get(
			fileEntry.toString());

		if (testFileEntry == null) {
			return false;
		}

		return true;
	}

	private final TestUploadPortlet _testUploadPortlet;
	private final UniqueFileNameProvider _uniqueFileNameProvider;

}