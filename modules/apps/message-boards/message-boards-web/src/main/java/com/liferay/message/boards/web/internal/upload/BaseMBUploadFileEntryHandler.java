/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.upload;

import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseMBUploadFileEntryHandler
	implements UploadFileEntryHandler {

	public BaseMBUploadFileEntryHandler(
		DLValidator dlValidator, MBMessageService mbMessageService) {

		_dlValidator = dlValidator;
		_mbMessageService = mbMessageService;
	}

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String fileName = _getFileName(uploadPortletRequest);

		_dlValidator.validateFileExtension(fileName);

		String contentType = _getContentType(uploadPortletRequest);

		_dlValidator.validateFileSize(
			themeDisplay.getScopeGroupId(), fileName, contentType,
			uploadPortletRequest.getSize(getParameterName()));

		long categoryId = ParamUtil.getLong(uploadPortletRequest, "categoryId");

		try (InputStream inputStream = _getFileAsInputStream(
				uploadPortletRequest)) {

			return _mbMessageService.addTempAttachment(
				themeDisplay.getScopeGroupId(), categoryId,
				MBMessageConstants.TEMP_FOLDER_NAME,
				TempFileEntryUtil.getTempFileName(fileName), inputStream,
				contentType);
		}
	}

	protected abstract String getParameterName();

	private String _getContentType(UploadPortletRequest uploadPortletRequest) {
		return uploadPortletRequest.getContentType(getParameterName());
	}

	private InputStream _getFileAsInputStream(
			UploadPortletRequest uploadPortletRequest)
		throws IOException {

		return uploadPortletRequest.getFileAsStream(getParameterName());
	}

	private String _getFileName(UploadPortletRequest uploadPortletRequest) {
		return uploadPortletRequest.getFileName(getParameterName());
	}

	private final DLValidator _dlValidator;
	private final MBMessageService _mbMessageService;

}