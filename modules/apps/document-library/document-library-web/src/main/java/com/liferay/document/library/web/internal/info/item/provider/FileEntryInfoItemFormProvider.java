/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.util.DLFileEntryTypeUtil;
import com.liferay.document.library.web.internal.info.item.FileEntryInfoItemFields;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					DLFileEntryConstants.getClassName()),
				0,
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					FileEntry.class.getName(), StringPool.BLANK,
					FileEntry.class.getSimpleName(), 0),
				0);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
	}

	@Override
	public InfoForm getInfoForm(FileEntry fileEntry) {
		long ddmStructureId = 0;
		long fileEntryTypeId = 0;

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

			DDMStructure ddmStructure = _fetchDDMStructure(fileEntryTypeId);

			if (ddmStructure != null) {
				ddmStructureId = ddmStructure.getStructureId();
			}
		}

		try {
			return _getInfoForm(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId())),
				ddmStructureId,
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					FileEntry.class.getName(), String.valueOf(fileEntryTypeId),
					FileEntry.class.getSimpleName(), 0),
				fileEntryTypeId);
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
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				DLFileEntryConstants.getClassName(),
				GetterUtil.getLong(formVariationKey), groupId),
			ddmStructureId,
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				FileEntry.class.getName(), String.valueOf(ddmStructureId),
				FileEntry.class.getSimpleName(), groupId),
			GetterUtil.getLong(formVariationKey));
	}

	private DDMStructure _fetchDDMStructure(long fileEntryTypeId) {
		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(fileEntryTypeId);

		if ((dlFileEntryType == null) ||
			(dlFileEntryType.getDataDefinitionId() == 0)) {

			return null;
		}

		return _ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getDataDefinitionId());
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
			FileEntryInfoItemFields.previewImageInfoField
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

	private InfoFieldSet _getFileEntryTypeInfoFieldSet(
			long ddmStructureId, long fileEntryTypeId)
		throws NoSuchStructureException {

		InfoFieldSet infoFieldSet =
			_ddmStructureInfoItemFieldSetProvider.getInfoItemFieldSet(
				ddmStructureId,
				_getStructureFieldSetNameInfoLocalizedValue(ddmStructureId));

		InfoFieldSet.Builder builder = InfoFieldSet.builder(
		).name(
			infoFieldSet.getName()
		).labelInfoLocalizedValue(
			infoFieldSet.getLabelInfoLocalizedValue()
		).infoFieldSetEntries(
			infoFieldSet.getInfoFieldSetEntries()
		);

		List<InfoFieldSet> infoFieldSets = _getMetadataInfoFieldSets(
			ddmStructureId, fileEntryTypeId);

		infoFieldSets.forEach(builder::infoFieldSetEntry);

		return builder.build();
	}

	private InfoFieldSet _getFileInformationFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			FileEntryInfoItemFields.fileNameInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.downloadURLInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.fileURLInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.mimeTypeInfoField
		).infoFieldSetEntry(
			FileEntryInfoItemFields.sizeInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "file-information")
		).name(
			"file-information"
		).build();
	}

	private InfoForm _getInfoForm(
			InfoFieldSet assetEntryInfoFieldSet, long ddmStructureId,
			InfoFieldSet displayPageInfoFieldSet, long fileEntryTypeId)
		throws NoSuchFormVariationException {

		try {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getBasicInformationFieldSet()
			).infoFieldSetEntry(
				_getFileInformationFieldSet()
			).<NoSuchStructureException>infoFieldSetEntry(
				unsafeConsumer -> {
					if (ddmStructureId != 0) {
						unsafeConsumer.accept(
							_getFileEntryTypeInfoFieldSet(
								ddmStructureId, fileEntryTypeId));
					}
				}
			).infoFieldSetEntry(
				unsafeConsumer -> {
					if (!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
						unsafeConsumer.accept(_getDisplayPageInfoFieldSet());
					}
				}
			).infoFieldSetEntry(
				unsafeConsumer -> {
					if (FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
						unsafeConsumer.accept(displayPageInfoFieldSet);
					}
				}
			).infoFieldSetEntry(
				_expandoInfoItemFieldSetProvider.getInfoFieldSet(
					DLFileEntryConstants.getClassName())
			).infoFieldSetEntry(
				_templateInfoItemFieldSetProvider.getInfoFieldSet(
					FileEntry.class.getName(), String.valueOf(fileEntryTypeId))
			).infoFieldSetEntry(
				assetEntryInfoFieldSet
			).infoFieldSetEntry(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
					FileEntry.class.getName())
			).labelInfoLocalizedValue(
				new ModelResourceLocalizedValue(FileEntry.class.getName())
			).name(
				FileEntry.class.getName()
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchFormVariationException(
				String.valueOf(ddmStructureId), noSuchStructureException);
		}
	}

	private List<InfoFieldSet> _getMetadataInfoFieldSets(
		long ddmStructureId, long fileEntryTypeId) {

		try {
			List<DDMStructure> ddmStructures =
				DLFileEntryTypeUtil.getDDMStructures(
					_dlFileEntryTypeService.getFileEntryType(fileEntryTypeId));

			List<InfoFieldSet> infoFieldSets = new ArrayList<>(
				ddmStructures.size());

			for (DDMStructure ddmStructure : ddmStructures) {
				if (ddmStructure.getStructureId() == ddmStructureId) {
					continue;
				}

				infoFieldSets.add(
					_ddmStructureInfoItemFieldSetProvider.getInfoItemFieldSet(
						ddmStructure.getStructureId()));
			}

			return infoFieldSets;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
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
			).defaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId())
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

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryInfoItemFormProvider.class);

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
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}