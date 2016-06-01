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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;

import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Manuel de la Peña
 */
public class TestUploadHandler extends BaseUploadHandler {

	public TestUploadHandler(TestUploadPortlet testUploadPortlet) {
		_testUploadPortlet = testUploadPortlet;
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

	private final TestUploadPortlet _testUploadPortlet;

}