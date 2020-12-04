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

package com.liferay.document.library.video.internal.helper;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
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
						_ddmStructureLocalService, dlFileEntry,
						_dlFileEntryMetadataLocalService,
						_fieldsToDDMFormValuesConverter, _storageEngine);

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
						_ddmStructureLocalService, dlFileVersion,
						_dlFileEntryMetadataLocalService,
						_fieldsToDDMFormValuesConverter, _storageEngine);

			if (dlVideoExternalShortcutMetadataHelper.isExternalShortcut()) {
				return dlVideoExternalShortcutMetadataHelper;
			}
		}

		return null;
	}

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private StorageEngine _storageEngine;

}