/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset;

import com.liferay.asset.kernel.model.BaseDDMFormValuesReader;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryDDMFormValuesReader extends BaseDDMFormValuesReader {

	public DLFileEntryDDMFormValuesReader(
		FileEntry fileEntry, FileVersion fileVersion) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
	}

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		DLFileEntryMetadata dlFileEntryMetadata = getDLFileEntryMetadata();

		if (dlFileEntryMetadata == null) {
			return new DDMFormValues(null);
		}

		return StorageEngineManagerUtil.getDDMFormValues(
			dlFileEntryMetadata.getDDMStorageId());
	}

	protected DLFileEntryMetadata getDLFileEntryMetadata() {
		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getClassStructures(
				_fileEntry.getCompanyId(),
				PortalUtil.getClassNameId(DLFileEntryMetadata.class));

		DLFileEntryMetadata dlFileEntryMetadata = null;

		for (DDMStructure ddmStructure : ddmStructures) {
			dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					_fileVersion.getFileVersionId());

			if (dlFileEntryMetadata != null) {
				break;
			}
		}

		return dlFileEntryMetadata;
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;

}