/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemObjectVariationProvider.class)
public class FileEntryInfoItemObjectVariationProvider
	implements InfoItemObjectVariationProvider<FileEntry> {

	@Override
	public String getInfoItemFormVariationKey(FileEntry fileEntry) {
		if ((fileEntry == null) ||
			!(fileEntry.getModel() instanceof DLFileEntry)) {

			return null;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		return String.valueOf(dlFileEntry.getFileEntryTypeId());
	}

	@Override
	public String getInfoItemFormVariationLabel(
		FileEntry fileEntry, Locale locale) {

		if ((fileEntry == null) ||
			!(fileEntry.getModel() instanceof DLFileEntry)) {

			return null;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (dlFileEntry.getFileEntryTypeId() == 0) {
			return _language.get(locale, "basic-document");
		}

		DDMStructure ddmStructure = _fetchDDMStructure(
			dlFileEntry.getFileEntryTypeId());

		if (ddmStructure == null) {
			return null;
		}

		return ddmStructure.getName(locale);
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

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private Language _language;

}