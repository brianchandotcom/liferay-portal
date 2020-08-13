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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.web.internal.info.item.FileEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class FileEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<FileEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(FileEntry fileEntry) {
		try {
			return InfoItemFieldValues.builder(
			).infoFieldValue(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.descriptionInfoField,
					fileEntry.getDescription())
			).infoFieldValue(
				new InfoFieldValue<>(
					FileEntryInfoItemFields.titleInfoField,
					fileEntry.getTitle())
			).infoFieldValues(
				_getAssetEntryInfoFieldValues(fileEntry)
			).infoFieldValues(
				_getDDMStructureInfoFieldValues(fileEntry)
			).infoFieldValues(
				_getExpandoInfoFieldValues(fileEntry)
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					FileEntry.class.getName(), fileEntry)
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
			//TODO

			return Collections.emptyList();
		}

		return Collections.emptyList();
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

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}