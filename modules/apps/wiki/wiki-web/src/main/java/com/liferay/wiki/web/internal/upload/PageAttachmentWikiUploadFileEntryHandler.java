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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;
import com.liferay.wiki.configuration.WikiFileUploadConfiguration;
import com.liferay.wiki.exception.WikiAttachmentMimeTypeException;
import com.liferay.wiki.exception.WikiAttachmentSizeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageService;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiFileUploadConfiguration",
	service = PageAttachmentWikiUploadFileEntryHandler.class
)
public class PageAttachmentWikiUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		dlValidator.validateFileSize(
			uploadPortletRequest.getFileName(_PARAMETER_NAME),
			uploadPortletRequest.getSize(_PARAMETER_NAME));

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

		_validateFile(
			fileName, contentType, mimeTypes,
			uploadPortletRequest.getSize(_PARAMETER_NAME));

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			return _wikiPageService.addPageAttachment(
				page.getNodeId(), page.getTitle(), fileName, inputStream,
				contentType);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiFileUploadConfiguration = ConfigurableUtil.createConfigurable(
			WikiFileUploadConfiguration.class, properties);
	}

	@Reference
	protected DLValidator dlValidator;

	private String[] _getValidMimeTypes(
		String[] mimeTypes, List<String> wikiAttachmentMimeTypes) {

		if (wikiAttachmentMimeTypes.contains(StringPool.STAR)) {
			return mimeTypes;
		}

		List<String> validMimeTypes = new ArrayList<>();

		for (String mimeType : mimeTypes) {
			if (wikiAttachmentMimeTypes.contains(mimeType)) {
				validMimeTypes.add(mimeType);
			}
		}

		return validMimeTypes.toArray(new String[0]);
	}

	private void _validateFile(
			String fileName, String contentType, String[] mimeTypes, long size)
		throws PortalException {

		long wikiAttachmentMaxSize =
			_wikiFileUploadConfiguration.attachmentMaxSize();

		if ((wikiAttachmentMaxSize > 0) && (size > wikiAttachmentMaxSize)) {
			throw new WikiAttachmentSizeException();
		}

		List<String> wikiAttachmentMimeTypes = ListUtil.toList(
			_wikiFileUploadConfiguration.attachmentMimeTypes());

		if (ArrayUtil.isEmpty(mimeTypes) &&
			ListUtil.isNull(wikiAttachmentMimeTypes)) {

			return;
		}

		for (String mimeType :
				_getValidMimeTypes(mimeTypes, wikiAttachmentMimeTypes)) {

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

	private WikiFileUploadConfiguration _wikiFileUploadConfiguration;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	@Reference
	private WikiPageService _wikiPageService;

}