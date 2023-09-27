/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.upload;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.exception.EntryImageNameException;
import com.liferay.blogs.exception.EntryImageSizeException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
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

import java.util.Set;

/**
 * @author Roberto Díaz
 * @author Alejandro Tardín
 */
public class ImageBlogsUploadFileEntryHandler
	implements UploadFileEntryHandler {

	public ImageBlogsUploadFileEntryHandler(
		BlogsEntryLocalService blogsLocalService,
		BlogsFileUploadsConfiguration blogsFileUploadsConfiguration,
		PortletFileRepository portletFileRepository,
		PortletResourcePermission portletResourcePermission) {

		_blogsLocalService = blogsLocalService;
		_blogsFileUploadsConfiguration = blogsFileUploadsConfiguration;
		_portletFileRepository = portletFileRepository;
		_portletResourcePermission = portletResourcePermission;
	}

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_portletResourcePermission.check(
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

	protected FileEntry addFileEntry(
			String fileName, String contentType, InputStream inputStream,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Folder folder = _blogsLocalService.addAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		String uniqueFileName = _portletFileRepository.getUniqueFileName(
			themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);

		return _portletFileRepository.addPortletFileEntry(
			null, themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			BlogsEntry.class.getName(), 0, BlogsConstants.SERVICE_NAME,
			folder.getFolderId(), inputStream, uniqueFileName, contentType,
			true);
	}

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

		FileEntry fileEntry = _portletFileRepository.getPortletFileEntry(
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

		for (String extension :
				_blogsFileUploadsConfiguration.imageExtensions()) {

			if (extension.equals(StringPool.STAR) ||
				extensions.contains(extension)) {

				return;
			}
		}

		throw new EntryImageNameException(
			"Invalid image for file name " + fileName);
	}

	private final BlogsFileUploadsConfiguration _blogsFileUploadsConfiguration;
	private final BlogsEntryLocalService _blogsLocalService;
	private final PortletFileRepository _portletFileRepository;
	private final PortletResourcePermission _portletResourcePermission;

}