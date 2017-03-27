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

package com.liferay.document.library.web.internal.upload;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Sergio González
 * @author Alejandro Tardín
 */
@Component(service = DLUploadFileEntryHandler.class)
public class DLUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry addFileEntry(
			long userId, long groupId, long folderId, String fileName,
			String contentType, InputStream inputStream, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return _dlAppService.addFileEntry(
			groupId, folderId, fileName, contentType, fileName,
			StringPool.BLANK, StringPool.BLANK, inputStream, size,
			serviceContext);
	}

	@Override
	public void checkPermission(
			long groupId, long folderId, PermissionChecker permissionChecker)
		throws PortalException {

		DLFolderPermission.check(
			permissionChecker, groupId, folderId, ActionKeys.ADD_DOCUMENT);
	}

	@Override
	public FileEntry fetchFileEntry(
			long userId, long groupId, long folderId, String fileName)
		throws PortalException {

		try {
			return _dlAppService.getFileEntry(groupId, folderId, fileName);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public long getFolderId(UploadPortletRequest uploadPortletRequest) {
		return ParamUtil.getLong(uploadPortletRequest, "folderId");
	}

	@Override
	public String getParameterName() {
		return "imageSelectorFileName";
	}

	@Override
	public ServiceContext getServiceContext(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), uploadPortletRequest);
	}

	@Override
	public String getURL(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		try {
			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
				StringPool.BLANK);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get URL for file entry " +
						fileEntry.getFileEntryId(),
					pe);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		_dlValidator.validateFileSize(fileName, size);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadFileEntryHandler.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLValidator _dlValidator;

}