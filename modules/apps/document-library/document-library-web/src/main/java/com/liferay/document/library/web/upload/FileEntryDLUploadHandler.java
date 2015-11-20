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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.BaseUploadHandler;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class FileEntryDLUploadHandler extends BaseUploadHandler {

	@Override
	protected FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return DLAppServiceUtil.addFileEntry(
			groupId, folderId, fileName, contentType, fileName,
			StringPool.BLANK, StringPool.BLANK, inputStream, size,
			serviceContext);
	}

	@Override
	protected void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		DLFolderPermission.check(
			permissionChecker, groupId, folderId, ActionKeys.ADD_DOCUMENT);
	}

	@Override
	protected FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			return DLAppServiceUtil.getFileEntry(groupId, folderId, fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	@Override
	protected long getFolderId(UploadPortletRequest uploadPortletRequest) {
		return ParamUtil.getLong(uploadPortletRequest, "folderId");
	}

	@Override
	protected String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	protected ServiceContext getServiceContext(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), uploadPortletRequest);
	}

	@Override
	protected String getURL(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		try {
			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
				StringPool.BLANK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get URL for file entry " +
						fileEntry.getFileEntryId());
			}
		}

		return StringPool.BLANK;
	}

	@Override
	protected void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		long maxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException(
				size + " exceeds its maximum permitted size of " + maxSize);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryDLUploadHandler.class);

}