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
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

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
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

		String originalFilename = uploadPortletRequest.getFileName(
			_PARAMETER_NAME);
		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		DLFolderPermission.check(
			themeDisplay.getPermissionChecker(), groupId, folderId,
			ActionKeys.ADD_DOCUMENT);

		_dlValidator.validateFileSize(originalFilename, size);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			String uniqueFileName = _uniqueFileNameProvider.provide(
				originalFilename,
				fileName -> _fileExists(fileName, groupId, folderId));

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);

			return _dlAppService.addFileEntry(
				groupId, folderId, uniqueFileName, contentType, uniqueFileName,
				StringPool.BLANK, StringPool.BLANK, inputStream, size,
				serviceContext);
		}
	}

	private boolean _fileExists(String fileName, long groupId, long folderId) {
		try {
			if (_dlAppService.getFileEntry(groupId, folderId, fileName) !=
					null) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return false;
		}
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadFileEntryHandler.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}