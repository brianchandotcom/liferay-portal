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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.exception.WikiAttachmentMimeTypeException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Alejandro Tardín
 */
@Component(service = PageAttachmentWikiUploadFileEntryHandler.class)
public class PageAttachmentWikiUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long resourcePrimKey = ParamUtil.getLong(
			uploadPortletRequest, "resourcePrimKey");

		WikiPage page = _wikiPageService.getPage(resourcePrimKey);

		WikiNodePermissionChecker.check(
			themeDisplay.getPermissionChecker(), page.getNodeId(),
			ActionKeys.ADD_ATTACHMENT);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		_validateFile(uploadPortletRequest, contentType);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			return _wikiPageService.addPageAttachment(
				page.getNodeId(), page.getTitle(), fileName, inputStream,
				contentType);
		}
	}

	private void _validateFile(
			UploadPortletRequest uploadPortletRequest, String contentType)
		throws PortalException {

		String[] mimeTypes = ParamUtil.getParameterValues(
			uploadPortletRequest, "mimeTypes");

		if (ArrayUtil.isEmpty(mimeTypes)) {
			return;
		}

		for (String mimeType : mimeTypes) {
			if (mimeType.equals(contentType)) {
				return;
			}
		}

		throw new WikiAttachmentMimeTypeException(
			contentType + " is not a valid document type");
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	@Reference
	private WikiPageService _wikiPageService;

}