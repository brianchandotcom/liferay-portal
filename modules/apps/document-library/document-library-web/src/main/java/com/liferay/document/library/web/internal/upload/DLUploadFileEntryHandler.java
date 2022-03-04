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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
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

		long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

		ModelResourcePermissionUtil.check(
			_folderModelResourcePermission, themeDisplay.getPermissionChecker(),
			themeDisplay.getScopeGroupId(), folderId, ActionKeys.ADD_DOCUMENT);

		String fileName = uploadPortletRequest.getFileName(
			"imageSelectorFileName");

		if (Validator.isNotNull(fileName)) {
			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"imageSelectorFileName")) {

				return _addFileEntry(
					fileName, folderId, inputStream, "imageSelectorFileName",
					uploadPortletRequest, themeDisplay);
			}
		}

		return _editImageFileEntry(
			uploadPortletRequest, themeDisplay, folderId);
	}

	private FileEntry _addFileEntry(
			String fileName, long folderId, InputStream inputStream,
			String parameterName, UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		_dlValidator.validateFileSize(
			fileName, uploadPortletRequest.getContentType(parameterName),
			uploadPortletRequest.getSize(parameterName));

		String uniqueFileName = _uniqueFileNameProvider.provide(
			fileName,
			curFileName -> _exists(
				themeDisplay.getScopeGroupId(), folderId, curFileName));

		return _dlAppService.addFileEntry(
			null, themeDisplay.getScopeGroupId(), folderId, uniqueFileName,
			uploadPortletRequest.getContentType(parameterName), uniqueFileName,
			uniqueFileName, _getDescription(uploadPortletRequest),
			StringPool.BLANK, inputStream,
			uploadPortletRequest.getSize(parameterName), null, null,
			_getServiceContext(uploadPortletRequest));
	}

	private FileEntry _editImageFileEntry(
			UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay, long folderId)
		throws IOException, PortalException {

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"imageBlob")) {

			long fileEntryId = ParamUtil.getLong(
				uploadPortletRequest, "fileEntryId");

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			return _addFileEntry(
				fileEntry.getFileName(), folderId, inputStream, "imageBlob",
				uploadPortletRequest, themeDisplay);
		}
	}

	private boolean _exists(long groupId, long folderId, String fileName) {
		try {
			FileEntry fileEntry = _dlAppService.getFileEntryByFileName(
				groupId, folderId, fileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private FileEntry _fetchFileEntry(UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		try {
			long fileEntryId = GetterUtil.getLong(
				uploadPortletRequest.getParameter("fileEntryId"));

			if (fileEntryId == 0) {
				return null;
			}

			return _dlAppService.getFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException);
			}

			return null;
		}
	}

	private String _getDescription(UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		FileEntry fileEntry = _fetchFileEntry(uploadPortletRequest);

		if (fileEntry == null) {
			return StringPool.BLANK;
		}

		return fileEntry.getDescription();
	}

	private ServiceContext _getServiceContext(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		FileEntry fileEntry = _fetchFileEntry(uploadPortletRequest);

		if ((fileEntry == null) ||
			!(fileEntry.getModel() instanceof DLFileEntry)) {

			return ServiceContextFactory.getInstance(
				DLFileEntry.class.getName(), uploadPortletRequest);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntry.class.getName(), uploadPortletRequest);

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		serviceContext.setExpandoBridgeAttributes(
			expandoBridge.getAttributes());

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLUploadFileEntryHandler.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLValidator _dlValidator;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission<Folder> _folderModelResourcePermission;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}