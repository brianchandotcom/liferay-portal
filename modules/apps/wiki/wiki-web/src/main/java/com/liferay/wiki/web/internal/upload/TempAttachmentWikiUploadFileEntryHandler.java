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

package com.liferay.wiki.web.internal.upload;

import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.service.WikiPageService;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = TempAttachmentWikiUploadFileEntryHandler.class
)
public class TempAttachmentWikiUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		_dlValidator.validateFileSize(
			uploadPortletRequest.getFileName(_PARAMETER_NAME),
			uploadPortletRequest.getContentType(_PARAMETER_NAME),
			uploadPortletRequest.getSize(_PARAMETER_NAME));

		long nodeId = ParamUtil.getLong(
			uploadPortletRequest.getPortletRequest(), "nodeId");

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			String tempFileName = TempFileEntryUtil.getTempFileName(
				uploadPortletRequest.getFileName(_PARAMETER_NAME));

			return _wikiPageService.addTempFileEntry(
				nodeId, WikiConstants.TEMP_FOLDER_NAME, tempFileName,
				inputStream,
				uploadPortletRequest.getContentType(_PARAMETER_NAME));
		}
	}

	private static final String _PARAMETER_NAME = "file";

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private WikiPageService _wikiPageService;

}