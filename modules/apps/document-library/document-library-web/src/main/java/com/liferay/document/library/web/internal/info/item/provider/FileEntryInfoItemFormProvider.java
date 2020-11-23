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

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.info.item.FileEntryInfoItemFields;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class FileEntryInfoItemFormProvider
	implements InfoItemFormProvider<FileEntry> {

	@Override
	public InfoForm getInfoForm() {
		try {
			return _getInfoForm(
				0,
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					DLFileEntryConstants.getClassName()));
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
	}

	@Override
	public InfoForm getInfoForm(FileEntry fileEntry) {
		long ddmStructureId = 0;

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DDMStructure ddmStructure = _fetchDDMStructure(
				dlFileEntry.getFileEntryTypeId());

			if (ddmStructure != null) {
				ddmStructureId = ddmStructure.getStructureId();
			}
		}

		try {
			return _getInfoForm(
				ddmStructureId,
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId())));
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entry for file entry " +
					fileEntry.getFileEntryId(),
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		long ddmStructureId = 0;

		DDMStructure ddmStructure = _fetchDDMStructure(
			GetterUtil.getLong(formVariationKey));

		if (ddmStructure != null) {
			ddmStructureId = ddmStructure.getStructureId();
		}

		return _getInfoForm(
			ddmStructureId,
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				DLFileEntryConstants.getClassName(),
				GetterUtil.getLong(formVariationKey), groupId));
	}

	private DDMStructure _fetchDDMStructure(long fileEntryTypeId) {
		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(fileEntryTypeId);

		if (dlFileEntryType == null) {
			return null;
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			dlFileEntryType.getDataDefinitionId());

		if (ddmStructure == null) {
			ddmStructure = _ddmStructureLocalService.fetchStructure(
				dlFileEntryType.getGroupId(),
				_portal.getClassNameId(DLFileEntryMetadata.class),
				DLUtil.getDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure == null) {
			ddmStructure = _ddmStructureLocalService.fetchStructure(
				dlFileEntryType.getGroupId(),
				_portal.getClassNameId(DLFileEntryMetadata.class),
				DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure == null) {
			ddmStructure = _ddmStructureLocalService.fetchStructure(
				dlFileEntryType.getGroupId(),
				_portal.getClassNameId(DLFileEntryMetadata.class),
				dlFileEntryType.getFileEntryTypeKey());
		}

		return ddmStructure;
	}

	private InfoFieldSet _getBasicInformationFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			FileEntryInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.versionInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.publishDateInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.authorNameInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.authorProfileImageInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.previewImage
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			FileEntryInfoItemFields.displayPageURLInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"display-page"
		).build();
	}

	private InfoFieldSet _getFileInformationFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			FileEntryInfoItemFields.fileName
		).infoFieldSetEntry(
			FileEntryInfoItemFields.downloadURL
		).infoFieldSetEntry(
			FileEntryInfoItemFields.fileURL
		).infoFieldSetEntry(
			FileEntryInfoItemFields.mimeType
		).infoFieldSetEntry(
			FileEntryInfoItemFields.size
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "file-information")
		).name(
			"file-information"
		).build();
	}

	private InfoForm _getInfoForm(
			long ddmStructureId, InfoFieldSet assetEntryInfoFieldSet)
		throws NoSuchFormVariationException {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale, FileEntry.class.getName()));
		}

		try {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getBasicInformationFieldSet()
			).infoFieldSetEntry(
				_getFileInformationFieldSet()
			).<NoSuchStructureException>infoFieldSetEntry(
				consumer -> {
					if (ddmStructureId != 0) {
						consumer.accept(
							_ddmStructureInfoItemFieldSetProvider.
								getInfoItemFieldSet(
									ddmStructureId,
									_getStructureFieldSetNameInfoLocalizedValue(
										ddmStructureId)));

						consumer.accept(
							_ddmTemplateInfoItemFieldSetProvider.
								getInfoItemFieldSet(ddmStructureId));
					}
				}
			).infoFieldSetEntry(
				_getDisplayPageInfoFieldSet()
			).infoFieldSetEntry(
				_expandoInfoItemFieldSetProvider.getInfoFieldSet(
					FileEntry.class.getName())
			).infoFieldSetEntry(
				assetEntryInfoFieldSet
			).infoFieldSetEntry(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
					AssetCategory.class.getName())
			).labelInfoLocalizedValue(
				infoLocalizedValueBuilder.build()
			).name(
				FileEntry.class.getName()
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchFormVariationException(
				String.valueOf(ddmStructureId), noSuchStructureException);
		}
	}

	private InfoLocalizedValue<String>
			_getStructureFieldSetNameInfoLocalizedValue(long ddmStructureId)
		throws NoSuchStructureException {

		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			Map<Locale, String> nameMap = new HashMap<>(
				ddmStructure.getNameMap());

			return InfoLocalizedValue.<String>builder(
			).values(
				nameMap
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException("Unexpected exception", portalException);
		}
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMStructureInfoItemFieldSetProvider
		_ddmStructureInfoItemFieldSetProvider;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateInfoItemFieldSetProvider
		_ddmTemplateInfoItemFieldSetProvider;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

}