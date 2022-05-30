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

package com.liferay.upload;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alicia García
 */
public abstract class BaseImageEditorUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		checkPermissions(uploadPortletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);

		if (Validator.isNotNull(fileName)) {
			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					_PARAMETER_NAME)) {

				return _addTempFileEntry(
					fileName, inputStream, _PARAMETER_NAME,
					uploadPortletRequest, themeDisplay);
			}
		}

		return _editImageFileEntry(uploadPortletRequest, themeDisplay);
	}

	protected abstract void checkPermissions(
			UploadPortletRequest uploadPortletRequest)
		throws PortalException;

	protected abstract DLAppService getDLAppService();

	protected abstract String getFolderName();

	protected abstract UniqueFileNameProvider getUniqueFileNameProvider();

	private FileEntry _addTempFileEntry(
			String fileName, InputStream inputStream, String parameterName,
			UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		UniqueFileNameProvider uniqueFileNameProvider =
			getUniqueFileNameProvider();

		String uniqueFileName = uniqueFileNameProvider.provide(
			fileName, curFileName -> _exists(themeDisplay, curFileName));

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			getFolderName(), uniqueFileName, inputStream,
			uploadPortletRequest.getContentType(parameterName));
	}

	private FileEntry _editImageFileEntry(
			UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay)
		throws IOException, PortalException {

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		DLAppService dlAppService = getDLAppService();

		FileEntry fileEntry = dlAppService.getFileEntry(fileEntryId);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"imageBlob")) {

			return _addTempFileEntry(
				fileEntry.getFileName(), inputStream, "imageBlob",
				uploadPortletRequest, themeDisplay);
		}
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			FileEntry tempFileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderName(), curFileName);

			if (tempFileEntry != null) {
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

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseImageEditorUploadFileEntryHandler.class);

}