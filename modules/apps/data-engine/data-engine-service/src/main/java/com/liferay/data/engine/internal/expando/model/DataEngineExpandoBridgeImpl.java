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

package com.liferay.data.engine.internal.expando.model;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.data.engine.rest.strategy.util.DataRecordValueKeyUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldParameterNameUtil;
import com.liferay.dynamic.data.mapping.util.SettingsDDMFormFieldsUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataEngineExpandoBridgeImpl implements ExpandoBridge {

	public DataEngineExpandoBridgeImpl(
			String className, long classPK, long companyId,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			GroupLocalService groupLocalService)
		throws Exception {

		_className = className;
		_classPK = classPK;
		_companyId = companyId;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;

		Group group = groupLocalService.fetchCompanyGroup(companyId);

		if (group == null) {
			throw new IllegalArgumentException("Invalid company " + companyId);
		}

		_companyGroupId = group.getGroupId();

		_dataDefinitionResource = DataDefinitionResource.builder(
		).checkPermissions(
			false
		).user(
			GuestOrUserUtil.getGuestOrUser(_companyId)
		).build();

		_dataRecordResource = DataRecordResource.builder(
		).checkPermissions(
			false
		).user(
			GuestOrUserUtil.getGuestOrUser(_companyId)
		).build();

		DataDefinition dataDefinition = null;

		try {
			dataDefinition = _getDataDefinition();
		}
		catch (Exception exception) {
			if ((exception instanceof NoSuchStructureException) ||
				(exception.getCause() instanceof NoSuchStructureException)) {

				dataDefinition = new DataDefinition();

				dataDefinition.setAvailableLanguageIds(new String[] {"en_US"});
				dataDefinition.setDataDefinitionKey(_className);
				dataDefinition.setName(
					HashMapBuilder.<String, Object>put(
						"en_US", _className
					).build());
				dataDefinition.setStorageType(StorageType.DEFAULT.getValue());
			}
			else {
				throw exception;
			}
		}

		if (Validator.isNull(dataDefinition.getId())) {
			_dataDefinitionResource.postSiteDataDefinitionByContentType(
				_companyGroupId, "native-object", dataDefinition);
		}
	}

	@Override
	public void addAttribute(String name) throws PortalException {
		addAttribute(name, false);
	}

	@Override
	public void addAttribute(String name, boolean secure)
		throws PortalException {

		addAttribute(name, "text", secure);
	}

	@Override
	public void addAttribute(String name, int type) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAttribute(String name, int type, boolean secure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addAttribute(
			String name, int type, Serializable defaultValue, boolean secure)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addAttribute(String name, String type) throws PortalException {
		addAttribute(name, "text", false);
	}

	@Override
	public void addAttribute(String name, String type, boolean secure)
		throws PortalException {

		addAttribute(name, type, null, secure);
	}

	@Override
	public void addAttribute(
			String name, String type, Serializable defaultValue)
		throws PortalException {

		addAttribute(name, type, defaultValue, false);
	}

	@Override
	public void addAttribute(
			String name, String fieldType, Serializable defaultValue,
			boolean secure)
		throws PortalException {

		try {
			DataDefinition dataDefinition = _getDataDefinition();

			DataDefinitionField[] dataDefinitionFields =
				dataDefinition.getDataDefinitionFields();

			Optional<DataDefinitionField> dataDefinitionFieldsOptional =
				Stream.of(
					dataDefinitionFields
				).filter(
					dataDefinitionField -> StringUtil.equals(
						dataDefinitionField.getName(), name)
				).findAny();

			if (dataDefinitionFieldsOptional.isPresent()) {
				return;
			}

			dataDefinition.setDataDefinitionFields(
				ArrayUtil.append(
					dataDefinitionFields,
					_createDataDefinitionField(defaultValue, fieldType, name)));

			_dataDefinitionResource.putDataDefinition(
				dataDefinition.getId(), dataDefinition);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	@Override
	public Serializable getAttribute(String name) {
		return getAttribute(name, false);
	}

	@Override
	public Serializable getAttribute(String name, boolean secure) {
		if (_classPK <= 0) {
			throw new UnsupportedOperationException(
				"Class primary key is less than or equal to 0");
		}

		try {
			DDLRecord ddlRecord = DDLRecordLocalServiceUtil.fetchFirstRecord(
				_className, _classPK);

			if (ddlRecord == null) {
				return getAttributeDefault(name);
			}

			DataRecord dataRecord = _dataRecordResource.getDataRecord(
				ddlRecord.getRecordId());

			Map<String, Object> dataRecordValues =
				dataRecord.getDataRecordValues();

			return (Serializable)dataRecordValues.get(
				_findDataRecordValueKey(dataRecordValues, name));
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Serializable getAttributeDefault(String name) {
		try {
			DataDefinition dataDefinition = _getDataDefinition();

			Optional<Serializable> serializableOptional = Stream.of(
				dataDefinition.getDataDefinitionFields()
			).filter(
				dataDefinitionField -> StringUtil.equals(
					dataDefinitionField.getName(), name)
			).map(
				DataDefinitionField::getDefaultValue
			).filter(
				MapUtil::isNotEmpty
			).map(
				defaultValueMap -> (Serializable)defaultValueMap.get("en_US")
			).findFirst();

			return serializableOptional.get();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		try {
			DataDefinition dataDefinition = _getDataDefinition();

			return Collections.enumeration(
				Stream.of(
					dataDefinition.getDataDefinitionFields()
				).map(
					DataDefinitionField::getName
				).collect(
					Collectors.toList()
				));
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public UnicodeProperties getAttributeProperties(String name) {
		try {
			UnicodeProperties unicodeProperties = new UnicodeProperties();

			DataDefinition dataDefinition = _getDataDefinition();

			Stream.of(
				dataDefinition.getDataDefinitionFields()
			).filter(
				dataDefinitionField -> StringUtil.equals(
					name, dataDefinitionField.getName())
			).forEach(
				dataDefinitionField -> unicodeProperties.put(
					"fieldType", dataDefinitionField.getFieldType())
			);

			return unicodeProperties;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return getAttributes(false);
	}

	@Override
	public Map<String, Serializable> getAttributes(boolean secure) {
		List<String> attributeNames = ListUtil.fromEnumeration(
			getAttributeNames());

		Stream<String> stream = attributeNames.stream();

		return stream.collect(
			Collectors.toMap(Function.identity(), this::getAttribute));
	}

	@Override
	public Map<String, Serializable> getAttributes(Collection<String> names) {
		return getAttributes(names, false);
	}

	@Override
	public Map<String, Serializable> getAttributes(
		Collection<String> names, boolean secure) {

		List<String> attributeNames = ListUtil.fromEnumeration(
			getAttributeNames());

		Stream<String> stream = attributeNames.stream();

		return stream.filter(
			names::contains
		).collect(
			Collectors.toMap(Function.identity(), this::getAttribute)
		);
	}

	@Override
	public int getAttributeType(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public boolean hasAttribute(String name) {
		try {
			DataDefinition dataDefinition = _getDataDefinition();

			Optional<DataDefinitionField> dataDefinitionFieldsOptional =
				Stream.of(
					dataDefinition.getDataDefinitionFields()
				).filter(
					dataDefinitionField -> StringUtil.equals(
						dataDefinitionField.getName(), name)
				).findAny();

			return dataDefinitionFieldsOptional.isPresent();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public boolean isIndexEnabled() {
		return false;
	}

	@Override
	public void setAttribute(String name, Serializable value) {
		setAttribute(name, value, false);
	}

	@Override
	public void setAttribute(String name, Serializable value, boolean secure) {
		if (_classPK <= 0) {
			throw new UnsupportedOperationException(
				"Class primary key is less than or equal to 0");
		}

		try {
			DDLRecord ddlRecord = DDLRecordLocalServiceUtil.fetchFirstRecord(
				_className, _classPK);

			if (ddlRecord == null) {
				DataDefinition dataDefinition = _getDataDefinition();

				DataRecord dataRecord = new DataRecord();

				dataRecord.setDataRecordValues(
					HashMapBuilder.<String, Object>put(
						DataRecordValueKeyUtil.createDataRecordValueKey(
							name, StringUtil.randomString(), StringPool.BLANK,
							0),
						value
					).build());

				dataRecord = _dataRecordResource.postDataDefinitionDataRecord(
					dataDefinition.getId(), dataRecord);

				ddlRecord = DDLRecordLocalServiceUtil.getDDLRecord(
					dataRecord.getId());

				ddlRecord.setClassName(_className);
				ddlRecord.setClassPK(_classPK);

				DDLRecordLocalServiceUtil.updateDDLRecord(ddlRecord);
			}
			else {
				DataRecord dataRecord = _dataRecordResource.getDataRecord(
					ddlRecord.getRecordId());

				Map<String, Object> dataRecordValues =
					dataRecord.getDataRecordValues();

				dataRecordValues.put(
					_findDataRecordValueKey(dataRecordValues, name), value);

				_dataRecordResource.putDataRecord(
					dataRecord.getId(), dataRecord);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setAttributeDefault(String name, Serializable defaultValue) {
		try {
			DataDefinition dataDefinition = _getDataDefinition();

			Stream.of(
				dataDefinition.getDataDefinitionFields()
			).filter(
				dataDefinitionField -> StringUtil.equals(
					dataDefinitionField.getName(), name)
			).map(
				DataDefinitionField::getDefaultValue
			).forEach(
				map -> map.put("en_US", defaultValue)
			);

			_updateDataDefinition(dataDefinition);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setAttributeProperties(
		String name, UnicodeProperties unicodeProperties) {

		setAttributeProperties(name, unicodeProperties, false);
	}

	@Override
	public void setAttributeProperties(
		String name, UnicodeProperties unicodeProperties, boolean secure) {

		try {
			DataDefinition dataDefinition = _getDataDefinition();

			Stream.of(
				dataDefinition.getDataDefinitionFields()
			).filter(
				dataDefinitionField -> StringUtil.equals(
					dataDefinitionField.getName(), name)
			).forEach(
				dataDefinitionField -> dataDefinitionField.setFieldType(
					unicodeProperties.getProperty("fieldType"))
			);

			_updateDataDefinition(dataDefinition);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setAttributes(Map<String, Serializable> attributes) {
		setAttributes(attributes, false);
	}

	@Override
	public void setAttributes(
		Map<String, Serializable> attributes, boolean secure) {

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			setAttribute(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void setAttributes(ServiceContext serviceContext) {
		setAttributes(serviceContext, false);
	}

	@Override
	public void setAttributes(ServiceContext serviceContext, boolean secure) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setClassName(String className) {
		_className = className;
	}

	@Override
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setIndexEnabled(boolean indexEnabled) {
	}

	private DataDefinitionField _createDataDefinitionField(
		Serializable defaultValue, String fieldType, String name) {

		DataDefinitionField dataDefinitionField = new DataDefinitionField();

		dataDefinitionField.setCustomProperties(
			HashMapBuilder.<String, Object>put(
				"dataType",
				() -> {
					Map<String, DDMFormField> settingsDDMFormFields =
						SettingsDDMFormFieldsUtil.getSettingsDDMFormFields(
							_ddmFormFieldTypeServicesTracker, fieldType);

					DDMFormField settingsDDMFormField =
						settingsDDMFormFields.get("dataType");

					LocalizedValue localizedValue =
						settingsDDMFormField.getPredefinedValue();

					return localizedValue.getString(
						localizedValue.getDefaultLocale());
				}
			).build());

		dataDefinitionField.setDefaultValue(
			HashMapBuilder.<String, Object>put(
				"en_US", defaultValue
			).build());
		dataDefinitionField.setFieldType(fieldType);
		dataDefinitionField.setName(name);

		return dataDefinitionField;
	}

	private String _findDataRecordValueKey(
		Map<String, Object> dataRecordValues, String name) {

		for (Map.Entry<String, Object> entry : dataRecordValues.entrySet()) {
			String[] dataRecordValueKeyParts =
				DDMFormFieldParameterNameUtil.
					getLastDDMFormFieldParameterNameParts(entry.getKey());

			if (name.contains(
					dataRecordValueKeyParts
						[DDMFormFieldParameterNameUtil.
							DDM_FORM_FIELD_NAME_INDEX])) {

				name = entry.getKey();

				break;
			}
		}

		return name;
	}

	private DataDefinition _getDataDefinition() throws Exception {
		return _dataDefinitionResource.
			getSiteDataDefinitionByContentTypeByDataDefinitionKey(
				_companyGroupId, "native-object", _className);
	}

	private void _updateDataDefinition(DataDefinition dataDefinition)
		throws Exception {

		_dataDefinitionResource.putDataDefinition(
			dataDefinition.getId(), dataDefinition);
	}

	private String _className;
	private long _classPK;
	private final long _companyGroupId;
	private long _companyId;
	private final DataDefinitionResource _dataDefinitionResource;
	private final DataRecordResource _dataRecordResource;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}