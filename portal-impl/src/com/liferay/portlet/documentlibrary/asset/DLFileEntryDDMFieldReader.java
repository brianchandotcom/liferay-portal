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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portlet.asset.model.BaseDDMFieldReader;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryDDMFieldReader extends BaseDDMFieldReader {

	public DLFileEntryDDMFieldReader(
		FileEntry dlFileEntry, FileVersion fileVersion) {

		_fileEntry = dlFileEntry;
		_fileVersion = fileVersion;
	}

	@Override
	public Fields getFields() throws PortalException {
		Fields fields = new Fields();

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			DLFileEntryMetadata.class);

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getClassStructures(
				_fileEntry.getCompanyId(), classNameId);

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					_fileVersion.getFileVersionId());

			if (dlFileEntryMetadata == null) {
				continue;
			}

			Fields ddmStorageFields = StorageEngineUtil.getFields(
				dlFileEntryMetadata.getDDMStorageId());

			for (Field ddmStorageField : ddmStorageFields) {
				fields.put(ddmStorageField);
			}
		}

		return fields;
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;

}