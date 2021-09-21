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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.web.internal.info.item.FileEntryInfoItemFields;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMFormValuesInfoFieldValuesProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class FileEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<FileEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(FileEntry fileEntry) {
		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getFileEntryInfoFieldValues(fileEntry)
			).infoFieldValues(
				_getAssetEntryInfoFieldValues(fileEntry)
			).infoFieldValues(
				_getDDMStructureInfoFieldValues(fileEntry)
			).infoFieldValues(
				_getExpandoInfoFieldValues(fileEntry)
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					FileEntry.class.getName(), fileEntry)
			).infoFieldValues(
				_templateInfoItemFieldSetProvider.getInfoFieldValues(
					FileEntry.class.getName(),
					_getInfoItemFormVariationKey(fileEntry), fileEntry)
			).infoItemReference(
				new InfoItemReference(
					FileEntry.class.getName(), fileEntry.getFileEntryId())
			).build();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Caught unexpected exception", portalException);
		}
	}

	private List<InfoFieldValue<Object>> _getAssetEntryInfoFieldValues(
			FileEntry fileEntry)
		throws NoSuchInfoItemException {

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			return _assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());
		}

		return Collections.emptyList();
	}

	private List<InfoFieldValue<Object>> _getDDMStructureInfoFieldValues(
		FileEntry fileEntry) {

		if (fileEntry.getModel() instanceof DLFileEntry) {
			try {
				List<InfoFieldValue<Object>> infoFieldValues =
					new ArrayList<>();

				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				DLFileEntryType dlFileEntryType =
					_dlFileEntryTypeLocalService.getFileEntryType(
						dlFileEntry.getFileEntryTypeId());

				List<DDMStructure> ddmStructures =
					dlFileEntryType.getDDMStructures();

				for (DDMStructure ddmStructure : ddmStructures) {
					FileVersion fileVersion = fileEntry.getFileVersion();

					DLFileEntryMetadata dlFileEntryMetadata =
						_dlFileEntryMetadataLocalService.getFileEntryMetadata(
							ddmStructure.getStructureId(),
							fileVersion.getFileVersionId());

					infoFieldValues.addAll(
						_ddmFormValuesInfoFieldValuesProvider.
							getInfoFieldValues(
								fileEntry,
								StorageEngineManagerUtil.getDDMFormValues(
									dlFileEntryMetadata.getDDMStorageId())));
				}

				return infoFieldValues;
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		return Collections.emptyList();
	}

	private String _getDisplayPageURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			FileEntry.class.getName(), fileEntry.getFileEntryId(),
			themeDisplay);
	}

	private List<InfoFieldValue<Object>> _getExpandoInfoFieldValues(
			FileEntry fileEntry)
		throws PortalException {

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(
				true);

			return _expandoInfoItemFieldSetProvider.getInfoFieldValues(
				DLFileEntryConstants.getClassName(), dlFileVersion);
		}

		return Collections.emptyList();
	}

	private List<InfoFieldValue<Object>> _getFileEntryInfoFieldValues(
		FileEntry fileEntry) {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			List<InfoFieldValue<Object>> fileEntryFieldValues =
				new ArrayList<>();

			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.fileName, fileEntry.getFileName()));

			String mimeType = fileEntry.getMimeType();

			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.mimeType, mimeType));

			if (mimeType.startsWith("image")) {
				WebImage fileURLWebImage = new WebImage(
					_dlURLHelper.getDownloadURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK),
					new InfoItemReference(
						FileEntry.class.getName(),
						new ClassPKInfoItemIdentifier(
							fileEntry.getFileEntryId())));

				fileURLWebImage.setAlt(fileEntry.getTitle());

				fileEntryFieldValues.add(
					new InfoFieldValue<>(
						FileEntryInfoItemFields.fileURL, fileURLWebImage));
			}

			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.titleInfoField,
					fileEntry.getTitle()));
			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.descriptionInfoField,
					fileEntry.getDescription()));
			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.versionInfoField,
					fileEntry.getVersion()));
			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.size, fileEntry.getSize()));
			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.createDateInfoField,
					fileEntry.getCreateDate()));
			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.modifiedDateInfoField,
					fileEntry.getModifiedDate()));

			User user = _userLocalService.fetchUser(fileEntry.getUserId());

			if (user != null) {
				fileEntryFieldValues.add(
					new InfoFieldValue<>(
						FileEntryInfoItemFields.authorNameInfoField,
						user.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						user.getPortraitURL(themeDisplay));

					webImage.setAlt(user.getFullName());

					fileEntryFieldValues.add(
						new InfoFieldValue<>(
							FileEntryInfoItemFields.authorProfileImageInfoField,
							webImage));
				}
			}

			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.publishDateInfoField,
					fileEntry.getModifiedDate()));

			String downloadURL = _dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);

			if (Validator.isNotNull(downloadURL)) {
				fileEntryFieldValues.add(
					new InfoFieldValue<>(
						FileEntryInfoItemFields.downloadURL, downloadURL));
			}

			WebImage imagePreviewURLWebImage = new WebImage(
				_dlURLHelper.getImagePreviewURL(fileEntry, null),
				new InfoItemReference(
					FileEntry.class.getName(),
					new ClassPKInfoItemIdentifier(fileEntry.getFileEntryId())));

			imagePreviewURLWebImage.setAlt(fileEntry.getTitle());

			fileEntryFieldValues.add(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.previewImage,
					imagePreviewURLWebImage));

			if (themeDisplay != null) {
				fileEntryFieldValues.add(
					new InfoFieldValue<>(
						FileEntryInfoItemFields.displayPageURLInfoField,
						_getDisplayPageURL(fileEntry, themeDisplay)));
			}

			return fileEntryFieldValues;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _getInfoItemFormVariationKey(FileEntry fileEntry) {
		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			return String.valueOf(dlFileEntry.getFileEntryTypeId());
		}

		return StringPool.BLANK;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private DDMFormValuesInfoFieldValuesProvider
		_ddmFormValuesInfoFieldValuesProvider;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

	@Reference
	private UserLocalService _userLocalService;

}