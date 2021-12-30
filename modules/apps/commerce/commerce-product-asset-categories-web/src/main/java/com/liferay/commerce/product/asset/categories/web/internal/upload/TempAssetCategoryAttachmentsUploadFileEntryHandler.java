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

package com.liferay.commerce.product.asset.categories.web.internal.upload;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryNameException;
import com.liferay.commerce.product.exception.CPAttachmentFileEntrySizeException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UniqueFileNameProvider;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.product.configuration.AttachmentsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, enabled = false,
	service = TempAssetCategoryAttachmentsUploadFileEntryHandler.class
)
public class TempAssetCategoryAttachmentsUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(uploadPortletRequest, "categoryId");

		AssetCategory assetCategory = assetCategoryService.fetchCategory(
			categoryId);

		String fileName = uploadPortletRequest.getFileName(_PARAMETER_NAME);

		_validateFile(fileName, uploadPortletRequest.getSize(_PARAMETER_NAME));

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				_PARAMETER_NAME)) {

			return _addFileEntry(
				assetCategory.getCategoryId(), fileName, contentType,
				inputStream, themeDisplay);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_attachmentsConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentsConfiguration.class, properties);
	}

	@Reference
	protected AssetCategoryService assetCategoryService;

	private FileEntry _addFileEntry(
			long categoryId, String fileName, String contentType,
			InputStream inputStream, ThemeDisplay themeDisplay)
		throws PortalException {

		String uniqueFileName = _uniqueFileNameProvider.provide(
			fileName, curFileName -> _exists(themeDisplay, curFileName));

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			_TEMP_FOLDER_NAME, uniqueFileName, inputStream, contentType);
	}

	private boolean _exists(ThemeDisplay themeDisplay, String curFileName) {
		try {
			FileEntry fileEntry = TempFileEntryUtil.getTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				_TEMP_FOLDER_NAME, curFileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return false;
		}
	}

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

	private static final String _TEMP_FOLDER_NAME =
		TempAssetCategoryAttachmentsUploadFileEntryHandler.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		TempAssetCategoryAttachmentsUploadFileEntryHandler.class);

	private volatile AttachmentsConfiguration _attachmentsConfiguration;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}