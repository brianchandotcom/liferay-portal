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

package com.liferay.commerce.product.definitions.web.internal.upload;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryNameException;
import com.liferay.commerce.product.exception.CPAttachmentFileEntrySizeException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = AttachmentsUploadFileEntryHandler.class
)
public class AttachmentsUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long cpDefinitionId = ParamUtil.getLong(
			uploadPortletRequest, "cpDefinitionId");

		CPDefinition cpDefinition = cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		_validateFile(fileName, size);

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			return addFileEntry(
				cpDefinition.getCPDefinitionId(), fileName, contentType,
				inputStream, themeDisplay);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_attachmentsConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentsConfiguration.class, properties);
	}

	protected FileEntry addFileEntry(
			long cpDefinitionId, String fileName, String contentType,
			InputStream inputStream, ThemeDisplay themeDisplay)
		throws PortalException {

		Folder folder = cpAttachmentFileEntryLocalService.getAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			CPDefinition.class.getName(), cpDefinitionId);

		String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
			themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			CPDefinition.class.getName(), cpDefinitionId,
			CPConstants.SERVICE_NAME, folder.getFolderId(), inputStream,
			uniqueFileName, contentType, true);
	}

	@Reference
	protected CPAttachmentFileEntryLocalService
		cpAttachmentFileEntryLocalService;

	@Reference
	protected CPDefinitionService cpDefinitionService;

	private void _validateFile(String fileName, long size)
		throws PortalException {

		if ((_attachmentsConfiguration.imageMaxSize() > 0) &&
			(size > _attachmentsConfiguration.imageMaxSize())) {

			throw new CPAttachmentFileEntrySizeException();
		}

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions = _attachmentsConfiguration.imageExtensions();

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new CPAttachmentFileEntryNameException(
			"Invalid image for file name " + fileName);
	}

	private static final String _PARAMETER_NAME = "imageSelectorFileName";

	private volatile AttachmentsConfiguration _attachmentsConfiguration;

}