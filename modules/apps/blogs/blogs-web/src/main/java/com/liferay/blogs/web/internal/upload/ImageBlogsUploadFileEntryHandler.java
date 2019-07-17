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

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.exception.EntryImageNameException;
import com.liferay.blogs.exception.EntryImageSizeException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

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
	configurationPid = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	service = ImageBlogsUploadFileEntryHandler.class
)
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

		_validateFile(fileName, uploadPortletRequest.getSize(_PARAMETER_NAME));

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			return addFileEntry(
				fileName, contentType, inputStream, themeDisplay);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsFileUploadsConfiguration.class, properties);
	}

	protected FileEntry addFileEntry(
			String fileName, String contentType, InputStream inputStream,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Folder folder = blogsLocalService.addAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		String uniqueFileName = portletFileRepository.getUniqueFileName(
			themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);

		return portletFileRepository.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			BlogsEntry.class.getName(), 0, BlogsConstants.SERVICE_NAME,
			folder.getFolderId(), inputStream, uniqueFileName, contentType,
			true);
	}

	@Reference
	protected BlogsEntryLocalService blogsLocalService;

	@Reference
	protected PortletFileRepository portletFileRepository;

	@Reference(target = "(resource.name=" + BlogsConstants.RESOURCE_NAME + ")")
	protected PortletResourcePermission portletResourcePermission;

	private void _validateFile(String fileName, long size)
		throws PortalException {

		long blogsImageMaxSize = _blogsFileUploadsConfiguration.imageMaxSize();

		if ((blogsImageMaxSize > 0) && (size > blogsImageMaxSize)) {
			throw new EntryImageSizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		for (String imageExtension :
				_blogsFileUploadsConfiguration.imageExtensions()) {

			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new EntryImageNameException(
			"Invalid image for file name " + fileName);
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private BlogsFileUploadsConfiguration _blogsFileUploadsConfiguration;

}