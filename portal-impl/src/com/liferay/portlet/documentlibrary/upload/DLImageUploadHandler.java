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

package com.liferay.portlet.documentlibrary.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Roberto Díaz
 */
public class DLImageUploadHandler extends BaseUploadHandler {

	@Override
	public void upload(
		PortletRequest portletRequest,
		PortletResponse portletResponse) throws PortalException {
	}

	@Override
	protected FileEntry addFileEntry(
			ThemeDisplay themeDisplay, long classPK, String fileName,
			InputStream inputStream, String contentType)
		throws PortalException {

		return null;
	}

	@Override
	protected void checkPermission(
			long groupId, long classPK, PermissionChecker permissionChecker)
		throws PortalException {
	}

	@Override
	protected FileEntry fetchFileEntry(
			ThemeDisplay themeDisplay, long classPK, String fileName)
		throws PortalException {

		return null;
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
	}

	@Override
	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {
	}

}