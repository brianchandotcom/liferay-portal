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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.exception.WikiAttachmentMimeTypeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageService;

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

		_wikiNodeModelResourcePermission.check(
			themeDisplay.getPermissionChecker(), page.getNodeId(),
			ActionKeys.ADD_ATTACHMENT);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);
		String[] mimeTypes = ParamUtil.getParameterValues(
			uploadPortletRequest, "mimeTypes");

		_validateFile(fileName, contentType, mimeTypes);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			return _wikiPageService.addPageAttachment(
				page.getNodeId(), page.getTitle(), fileName, inputStream,
				contentType);
		}
	}

	private void _validateFile(
			String fileName, String contentType, String[] mimeTypes)
		throws PortalException {

		if (ArrayUtil.isEmpty(mimeTypes)) {
			return;
		}

		for (String mimeType : mimeTypes) {
			if (mimeType.equals(contentType)) {
				return;
			}
		}

		throw new WikiAttachmentMimeTypeException(
			StringBundler.concat(
				"Invalid MIME type ", contentType, " for file name ",
				fileName));
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	@Reference
	private WikiPageService _wikiPageService;

}