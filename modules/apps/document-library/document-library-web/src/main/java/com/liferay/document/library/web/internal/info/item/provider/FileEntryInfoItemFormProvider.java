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
import com.liferay.document.library.web.internal.info.item.FileEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.repository.model.FileEntry;

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
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationFieldSet()
		).infoFieldSetEntry(
			_getFileInformationFieldSet()
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				FileEntry.class.getName())
		).infoFieldSetEntry(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				FileEntry.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName())
		).name(
			AssetCategory.class.getName()
		).build();
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

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}