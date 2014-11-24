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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.StorageException;
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

	public static DDMStructure getGoogleDocsDDMStructure(
		DLFileEntryType dlFileEntryType) {

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

				return ddmStructure;
			}
		}

		return null;
	}

	public GoogleDocsMetadataHelper(DLFileEntry dlFileEntry) {
		try {
			_dlFileVersion = dlFileEntry.getFileVersion();

			_ddmStructure = getGoogleDocsDDMStructure(
				dlFileEntry.getDLFileEntryType());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public GoogleDocsMetadataHelper(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;

		try {
			_ddmStructure = getGoogleDocsDDMStructure(
				dlFileVersion.getDLFileEntryType());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean containsField(String fieldName) {
		initDLFileEntryMetadataAndFields();

		Field field = _fieldsMap.get(fieldName);

		if (field != null) {
			return true;
		}

		return false;
	}

	public String getFieldValue(String fieldName) {
		Field field = _getField(fieldName);

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

	public void setFieldValue(String fieldName, String value) {
		Field field = _getField(fieldName);

		field.setValue(value);
	}

	public void update() {
		try {
			StorageEngineUtil.update(
				_dlFileEntryMetadata.getDDMStorageId(), _fields,
				new ServiceContext());
		}
		catch (StorageException se) {
			throw new SystemException(
				"Unable to update DDM fields for file version " +
					_dlFileVersion.getFileVersionId(),
				se);
		}
	}

	protected void addGoogleDocsDLFileEntryMetadata() {
		try {
			DLFileEntry dlFileEntry = _dlFileVersion.getFileEntry();

			_dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.createDLFileEntryMetadata(
					CounterLocalServiceUtil.increment());

			long ddmStructureId = _ddmStructure.getStructureId();

			Fields fields = new Fields();
			fields.put(
				new Field(
					ddmStructureId,
					GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION, ""));
			fields.put(
				new Field(
					ddmStructureId, GoogleDocsConstants.DDM_FIELD_NAME_URL,
					""));
			fields.put(
				new Field(
					ddmStructureId,
					GoogleDocsConstants.DDM_FIELD_NAME_EMBEDDABLE_URL, ""));
			fields.put(
				new Field(
					ddmStructureId, GoogleDocsConstants.DDM_FIELD_NAME_ICON_URL,
					""));
			fields.put(
				new Field(
					ddmStructureId, GoogleDocsConstants.DDM_FIELD_NAME_ID, ""));
			fields.put(
				new Field(
					ddmStructureId, GoogleDocsConstants.DDM_FIELD_NAME_NAME,
					""));

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(_dlFileVersion.getUserId());
			serviceContext.setScopeGroupId(_dlFileVersion.getGroupId());

			long ddmStorageId = StorageEngineUtil.create(
				_dlFileVersion.getCompanyId(), ddmStructureId, fields,
				serviceContext);

			_dlFileEntryMetadata.setDDMStorageId(ddmStorageId);
			_dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
			_dlFileEntryMetadata.setFileEntryTypeId(
				dlFileEntry.getFileEntryTypeId());
			_dlFileEntryMetadata.setFileEntryId(dlFileEntry.getFileEntryId());
			_dlFileEntryMetadata.setFileVersionId(
				_dlFileVersion.getFileVersionId());

			_dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.addDLFileEntryMetadata(
					_dlFileEntryMetadata);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to add DDM fields for file version " +
					_dlFileVersion.getFileVersionId(),
				pe);
		}
	}

	protected void initDLFileEntryMetadataAndFields() {
		if (_fieldsMap == null) {
			_fieldsMap = new HashMap<>();

			if (_dlFileVersion == null) {
				return;
			}

			try {
				_dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
						_ddmStructure.getStructureId(),
						_dlFileVersion.getFileVersionId());
			}
			catch (NoSuchFileEntryMetadataException nsfeme) {
				addGoogleDocsDLFileEntryMetadata();
			}
			catch (PortalException pe) {
				throw new SystemException(
					"Unable to load file entry metadatafor file version " +
						_dlFileVersion.getFileVersionId(),
					pe);
			}

			try {
				_fields = StorageEngineUtil.getFields(
					_dlFileEntryMetadata.getDDMStorageId());

				for (Field field : _fields) {
					_fieldsMap.put(field.getName(), field);
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

	private Field _getField(String fieldName) {
		initDLFileEntryMetadataAndFields();

		Field field = _fieldsMap.get(fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Unknown field " + fieldName);
		}

		return field;
	}

	private final DDMStructure _ddmStructure;
	private DLFileEntryMetadata _dlFileEntryMetadata;
	private DLFileVersion _dlFileVersion;
	private Fields _fields;
	private Map<String, Field> _fieldsMap;

}