/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.web.internal.upload;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryNameException;
import com.liferay.commerce.product.exception.CPAttachmentFileEntrySizeException;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = AssetCategoryAttachmentsUploadFileEntryHandler.class
)
public class AssetCategoryAttachmentsUploadFileEntryHandler
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
		long size = uploadPortletRequest.getSize(_PARAMETER_NAME);

		_validateFile(fileName, size);

		String contentType = uploadPortletRequest.getContentType(
			_PARAMETER_NAME);

		try (InputStream inputStream =
				uploadPortletRequest.getFileAsStream(_PARAMETER_NAME)) {

			return addFileEntry(
				assetCategory.getCategoryId(), fileName, contentType,
				inputStream, themeDisplay);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_attachmentsConfiguration = ConfigurableUtil.createConfigurable(
			AttachmentsConfiguration.class, properties);
	}

	protected FileEntry addFileEntry(
			long categoryId, String fileName, String contentType,
			InputStream inputStream, ThemeDisplay themeDisplay)
		throws PortalException {

		Folder folder = cpAttachmentFileEntryLocalService.getAttachmentsFolder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			AssetCategory.class.getName(), categoryId);

		String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
			themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			AssetCategory.class.getName(), categoryId, CPConstants.SERVICE_NAME,
			folder.getFolderId(), inputStream, uniqueFileName, contentType,
			true);
	}

	@Reference
	protected AssetCategoryService assetCategoryService;

	@Reference
	protected CPAttachmentFileEntryLocalService
		cpAttachmentFileEntryLocalService;

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