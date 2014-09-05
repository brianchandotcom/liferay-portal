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

package com.liferay.document.library.google.docs.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsMetadataHelper {

	public GoogleDocsMetadataHelper(FileVersion fileVersion)
		throws PortalException {

		_fileVersion = fileVersion;

		if (_fileVersion.getModel() instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			long fileEntryTypeId = dlFileVersion.getFileEntryTypeId();

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeServiceUtil.getFileEntryType(fileEntryTypeId);

			List<DDMStructure> ddmStructures =
				dlFileEntryType.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				String structureKey = ddmStructure.getStructureKey();

				if (structureKey.equals(
						GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

					_ddmStructure = ddmStructure;

					break;
				}
			}
		}
	}

	public String getFieldValue(String fieldName) {
		_loadFields();

		Field field = _fields.get(fieldName);

		Serializable value = field.getValue();

		if (value == null) {
			return null;
		}

		return value.toString();
	}

	public boolean isGoogleDocs() {
		if (_ddmStructure != null) {
			return true;
		}

		return false;
	}

	private void _loadFields() {
		if (_fields == null) {
			_fields = new HashMap<>();

			try {
				DLFileEntryMetadata dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
							_ddmStructure.getStructureId(),
							_fileVersion.getFileVersionId());

				Fields fields = StorageEngineUtil.getFields(
					dlFileEntryMetadata.getDDMStorageId());

				for (Field field : fields) {
					_fields.put(field.getName(), field);
				}
			}
			catch (PortalException pe) {
				throw new SystemException(
					"Unable to load DDM fields for file version " +
						_fileVersion.getFileVersionId(), pe);
			}
		}
	}

	private DDMStructure _ddmStructure;
	private Map<String, Field> _fields;
	private FileVersion _fileVersion;

}