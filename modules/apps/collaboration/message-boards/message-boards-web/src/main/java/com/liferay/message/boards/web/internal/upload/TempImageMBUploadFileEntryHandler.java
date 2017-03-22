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

package com.liferay.message.boards.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.InputStream;

/**
 * @author Ambrín Chaudhary
 */
public class TempImageMBUploadFileEntryHandler
	implements UploadFileEntryHandler {

	public TempImageMBUploadFileEntryHandler(long categoryId) {
		_categoryId = categoryId;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, TEMP_FOLDER_NAME, fileName, inputStream,
			contentType);
	}

	@Override
	public void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		MBCategoryPermission.check(
			permissionChecker, groupId, _categoryId, ActionKeys.ADD_FILE);
	}

	@Override
	public FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			return TempFileEntryUtil.getTempFileEntry(
				groupId, userId, TEMP_FOLDER_NAME, fileName);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TempImageMBUploadFileEntryHandler.class);

	private final long _categoryId;

}