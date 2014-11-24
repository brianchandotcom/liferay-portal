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

package com.liferay.document.library.google.docs.migration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
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
public class LegacyGoogleDocsMetadataHelper {

	public static DDMStructure getGoogleDocsDDMStructure(
		DLFileEntryType dlFileEntryType) {

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			if (ddmStructure.getType() == DDMStructureConstants.TYPE_AUTO) {
				return ddmStructure;
			}
		}

		return null;
	}

	public LegacyGoogleDocsMetadataHelper(DLFileEntry dlFileEntry) {
		try {
			_dlFileVersion = dlFileEntry.getFileVersion();

			_ddmStructure = getGoogleDocsDDMStructure(
				dlFileEntry.getDLFileEntryType());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public void delete() {
		try {
			DLFileEntryMetadataLocalServiceUtil.deleteDLFileEntryMetadata(
				_dlFileEntryMetadata.getFileEntryMetadataId());

			StorageEngineUtil.deleteByClass(
				_dlFileEntryMetadata.getDDMStorageId());
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to delete DDM fields for file version " +
					_dlFileVersion.getFileVersionId(),
				pe);
		}
	}

	public String getFieldValue(String fieldName) {
		initDLFileEntryMetadataAndFields();

		Field field = _fields.get(fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Unknown field " + fieldName);
		}

		Serializable value = field.getValue();

		if (value == null) {
			return null;
		}

		return value.toString();
	}

	protected void initDLFileEntryMetadataAndFields() {
		if (_fields == null) {
			_fields = new HashMap<>();

			if (_dlFileVersion == null) {
				return;
			}

			try {
				_dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						_ddmStructure.getStructureId(),
						_dlFileVersion.getFileVersionId());

				Fields fields = StorageEngineUtil.getFields(
					_dlFileEntryMetadata.getDDMStorageId());

				for (Field field : fields) {
					_fields.put(field.getName(), field);
				}
			}
			catch (PortalException pe) {
				throw new SystemException(
					"Unable to load DDM fields for file version " +
						_dlFileVersion.getFileVersionId(),
					pe);
			}
		}
	}

	private final DDMStructure _ddmStructure;
	private DLFileEntryMetadata _dlFileEntryMetadata;
	private DLFileVersion _dlFileVersion;
	private Map<String, Field> _fields;

}