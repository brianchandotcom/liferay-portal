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
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.Set;

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

		String fileName = uploadPortletRequest.getFileName(
			"imageSelectorFileName");

		if (Validator.isNotNull(fileName)) {
			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"imageSelectorFileName")) {

				return _addFileEntry(
					fileName, inputStream, "imageSelectorFileName",
					uploadPortletRequest, themeDisplay);
			}
		}

		return _editImageFileEntry(uploadPortletRequest, themeDisplay);
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
			null, themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
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

	private FileEntry _addFileEntry(
			String fileName, InputStream inputStream, String parameterName,
			UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String contentType = uploadPortletRequest.getContentType(parameterName);

		_validateFile(
			fileName, contentType, uploadPortletRequest.getSize(parameterName));

		return addFileEntry(fileName, contentType, inputStream, themeDisplay);
	}

	private FileEntry _editImageFileEntry(
			UploadPortletRequest uploadPortletRequest,
			ThemeDisplay themeDisplay)
		throws IOException, PortalException {

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		FileEntry fileEntry = portletFileRepository.getPortletFileEntry(
			fileEntryId);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"imageBlob")) {

			return _addFileEntry(
				fileEntry.getFileName(), inputStream, "imageBlob",
				uploadPortletRequest, themeDisplay);
		}
	}

	private void _validateFile(String fileName, String contentType, long size)
		throws PortalException {

		long blogsImageMaxSize = _blogsFileUploadsConfiguration.imageMaxSize();

		if ((blogsImageMaxSize > 0) && (size > blogsImageMaxSize)) {
			throw new EntryImageSizeException();
		}

		Set<String> extensions = MimeTypesUtil.getExtensions(contentType);

		boolean validContentType = false;

		for (String extension :
				_blogsFileUploadsConfiguration.imageExtensions()) {

			if (extension.equals(StringPool.STAR) ||
				extensions.contains(extension)) {

				validContentType = true;

				break;
			}
		}

		if (!validContentType) {
			throw new EntryImageNameException(
				"Invalid image for file name " + fileName);
		}
	}

	private volatile BlogsFileUploadsConfiguration
		_blogsFileUploadsConfiguration;

}