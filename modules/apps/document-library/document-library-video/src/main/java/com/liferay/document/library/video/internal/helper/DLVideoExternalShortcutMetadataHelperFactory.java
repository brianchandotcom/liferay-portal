/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.internal.helper;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoExternalShortcutMetadataHelperFactory.class)
public class DLVideoExternalShortcutMetadataHelperFactory {

	public DLVideoExternalShortcutMetadataHelper
		getDLVideoExternalShortcutMetadataHelper(FileEntry fileEntry) {

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DLVideoExternalShortcutMetadataHelper
				dlVideoExternalShortcutMetadataHelper =
					new DLVideoExternalShortcutMetadataHelper(
						_ddmFormValuesToFieldsConverter,
						_ddmStorageEngineManager, _ddmStructureLocalService,
						dlFileEntry, _dlFileEntryMetadataLocalService,
						_fieldsToDDMFormValuesConverter);

			if (dlVideoExternalShortcutMetadataHelper.isExternalShortcut()) {
				return dlVideoExternalShortcutMetadataHelper;
			}
		}

		return null;
	}

	public DLVideoExternalShortcutMetadataHelper
		getDLVideoExternalShortcutMetadataHelper(FileVersion fileVersion) {

		if (fileVersion.getModel() instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			DLVideoExternalShortcutMetadataHelper
				dlVideoExternalShortcutMetadataHelper =
					new DLVideoExternalShortcutMetadataHelper(
						_ddmFormValuesToFieldsConverter,
						_ddmStorageEngineManager, _ddmStructureLocalService,
						dlFileVersion, _dlFileEntryMetadataLocalService,
						_fieldsToDDMFormValuesConverter);

			if (dlVideoExternalShortcutMetadataHelper.isExternalShortcut()) {
				return dlVideoExternalShortcutMetadataHelper;
			}
		}

		return null;
	}

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

}