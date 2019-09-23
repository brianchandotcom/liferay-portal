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

package com.liferay.document.library.google.docs.internal.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GoogleDocsMetadataHelper {

	public static DDMStructure getGoogleDocsDDMStructure(
		DLFileEntryType dlFileEntryType) {

		if (dlFileEntryType == null) {
			return null;
		}

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

	public GoogleDocsMetadataHelper(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntry dlFileEntry,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
		StorageEngine storageEngine) {

		try {
			_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
			_ddmStructureLocalService = ddmStructureLocalService;
			_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
			_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
			_storageEngine = storageEngine;

			_dlFileVersion = dlFileEntry.getFileVersion();
			_ddmStructure = getGoogleDocsDDMStructure(
				dlFileEntry.getDLFileEntryType());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public GoogleDocsMetadataHelper(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileVersion dlFileVersion,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
		StorageEngine storageEngine) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileVersion = dlFileVersion;
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
		_storageEngine = storageEngine;

		try {
			_ddmStructure = getGoogleDocsDDMStructure(
				dlFileVersion.getDLFileEntryType());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean containsField(String fieldName) {
		_initDLFileEntryMetadataAndFields();

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

	private void _addGoogleDocsDLFileEntryMetadata() {
		try {
			DLFileEntry dlFileEntry = _dlFileVersion.getFileEntry();

			_dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.createDLFileEntryMetadata(
					CounterLocalServiceUtil.increment());

			long ddmStructureId = _ddmStructure.getStructureId();

			Fields fields = new Fields();

			fields.put(
				new Field(
					ddmStructureId,
					GoogleDocsConstants.DDM_FIELD_NAME_DESCRIPTION, ""));
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
			fields.put(
				new Field(
					ddmStructureId, GoogleDocsConstants.DDM_FIELD_NAME_URL,
					""));

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);
			serviceContext.setScopeGroupId(_dlFileVersion.getGroupId());
			serviceContext.setUserId(_dlFileVersion.getUserId());

			DDMFormValues ddmFormValues = _toDDMFormValues(fields);

			long ddmStorageId = _storageEngine.create(
				_dlFileVersion.getCompanyId(), ddmStructureId, ddmFormValues,
				serviceContext);

			_dlFileEntryMetadata.setDDMStorageId(ddmStorageId);

			_dlFileEntryMetadata.setDDMStructureId(ddmStructureId);
			_dlFileEntryMetadata.setFileEntryId(dlFileEntry.getFileEntryId());
			_dlFileEntryMetadata.setFileVersionId(
				_dlFileVersion.getFileVersionId());

			_dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.addDLFileEntryMetadata(
					_dlFileEntryMetadata);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to add DDM fields for file version " +
					_dlFileVersion.getFileVersionId(),
				pe);
		}
	}

	private Field _getField(String fieldName) {
		_initDLFileEntryMetadataAndFields();

		Field field = _fieldsMap.get(fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Unknown field " + fieldName);
		}

		return field;
	}

	private void _initDLFileEntryMetadataAndFields() {
		if (_fieldsMap != null) {
			return;
		}

		if (_dlFileVersion == null) {
			return;
		}

		_fieldsMap = new HashMap<>();

		_dlFileEntryMetadata =
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				_ddmStructure.getStructureId(),
				_dlFileVersion.getFileVersionId());

		if (_dlFileEntryMetadata == null) {
			_addGoogleDocsDLFileEntryMetadata();
		}

		try {
			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				_dlFileEntryMetadata.getDDMStorageId());

			_fields = _ddmFormValuesToFieldsConverter.convert(
				_ddmStructureLocalService.getDDMStructure(
					_ddmStructure.getStructureId()),
				ddmFormValues);

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

	private DDMFormValues _toDDMFormValues(Fields fields)
		throws PortalException {

		return _fieldsToDDMFormValuesConverter.convert(
			_ddmStructureLocalService.getDDMStructure(
				_ddmStructure.getStructureId()),
			fields);
	}

	private final DDMFormValuesToFieldsConverter
		_ddmFormValuesToFieldsConverter;
	private final DDMStructure _ddmStructure;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryMetadata _dlFileEntryMetadata;
	private final DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private DLFileVersion _dlFileVersion;
	private Fields _fields;
	private Map<String, Field> _fieldsMap;
	private final FieldsToDDMFormValuesConverter
		_fieldsToDDMFormValuesConverter;
	private final StorageEngine _storageEngine;

}