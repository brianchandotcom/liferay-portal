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

package com.liferay.blogs.web.internal.upload;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.exception.EntryImageNameException;
import com.liferay.blogs.exception.EntryImageSizeException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ImageBlogsUploadFileEntryHandler.class)
public class ImageBlogsUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		portletResourcePermission.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
			ActionKeys.ADD_ENTRY);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		_validateFile(fileName, size);

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			return addFileEntry(
				fileName, contentType, inputStream, themeDisplay);
		}
	}

	protected FileEntry addFileEntry(
			String fileName, String contentType, InputStream inputStream,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Folder folder = blogsLocalService.addAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
			themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			BlogsEntry.class.getName(), 0, BlogsConstants.SERVICE_NAME,
			folder.getFolderId(), inputStream, uniqueFileName, contentType,
			true);
	}

	@Reference
	protected BlogsEntryLocalService blogsLocalService;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	protected PortletResourcePermission portletResourcePermission;

	private void _validateFile(String fileName, long size)
		throws PortalException {

		if ((PropsValues.BLOGS_IMAGE_MAX_SIZE > 0) &&
			(size > PropsValues.BLOGS_IMAGE_MAX_SIZE)) {

			throw new EntryImageSizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new EntryImageNameException(
			"Invalid image for file name " + fileName);
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

}