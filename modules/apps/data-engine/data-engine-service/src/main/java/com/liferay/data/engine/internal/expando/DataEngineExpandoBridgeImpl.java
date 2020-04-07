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

package com.liferay.data.engine.internal.expando;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataEngineExpandoBridgeImpl implements ExpandoBridge {

	public DataEngineExpandoBridgeImpl(
		String className, long classPK, long companyId) {

		_className = className;
		_classPK = classPK;
		_companyId = companyId;

		Group group = GroupLocalServiceUtil.fetchCompanyGroup(companyId);

		if (group == null) {
			throw new IllegalArgumentException("Invalid company: " + companyId);
		}

		_companyGroupId = group.getGroupId();

		DataDefinition dataDefinition = null;

		DataDefinitionResource dataDefinitionResource = null;

		try {
			dataDefinitionResource = _getDataDefinitionResource();

			dataDefinition = _getDataDefinition();
		}
		catch (Exception exception) {
			if ((exception instanceof NoSuchStructureException) ||
				(exception.getCause() instanceof NoSuchStructureException)) {

				dataDefinition = new DataDefinition();

				dataDefinition.setDataDefinitionKey(_className);
				dataDefinition.setName(
					HashMapBuilder.<String, Object>put(
						"en_US", _className
					).build());
				dataDefinition.setStorageType("json");
			}
			else {
				throw new RuntimeException(exception);
			}
		}

		try {
			if (Validator.isNull(dataDefinition.getId())) {
				dataDefinitionResource.postSiteDataDefinitionByContentType(
					_companyGroupId, "native-object", dataDefinition);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
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
			String name, String type, Serializable defaultValue, boolean secure)
		throws PortalException {

		DataDefinition dataDefinition = _getDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		Stream<DataDefinitionField> stream = Arrays.stream(
			dataDefinitionFields);

		Optional<DataDefinitionField> dataDefinitionFieldOptional =
			stream.filter(
				dataDefinitionField -> StringUtil.equals(
					dataDefinitionField.getName(), name)
			).findAny();

		if (dataDefinitionFieldOptional.isPresent()) {
			return;
		}

		List<DataDefinitionField> dataDefinitionFieldsList = new ArrayList<>();

		Collections.addAll(dataDefinitionFieldsList, dataDefinitionFields);

		DataDefinitionField dataDefinitionField = createDataDefinitionField(
			name, type, defaultValue);

		dataDefinitionFieldsList.add(dataDefinitionField);

		dataDefinition.setDataDefinitionFields(
			dataDefinitionFieldsList.toArray(new DataDefinitionField[0]));

		try {
			DataDefinitionResource dataDefinitionResource =
				_getDataDefinitionResource();

			dataDefinitionResource.putDataDefinition(
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
				"Class primary key is less or equal to 0");
		}

		try {
			DDLRecord ddlRecord =
				DDLRecordLocalServiceUtil.fetchFirstRecordByClassNameAndClassPK(
					_className, _classPK);

			if (ddlRecord == null) {
				return getAttributeDefault(name);
			}

			DataRecordResource dataRecordResource = _getDataRecordResource();

			DataRecord dataRecord = dataRecordResource.getDataRecord(
				ddlRecord.getRecordId());

			Map<String, Object> dataRecordValues =
				dataRecord.getDataRecordValues();

			return (Serializable)dataRecordValues.get(name);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Serializable getAttributeDefault(String name) {
		try {
			DataDefinition dataDefinition = _getDataDefinition();

			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (StringUtil.equals(dataDefinitionField.getName(), name)) {
					Map<String, Object> defaultValueMap =
						dataDefinitionField.getDefaultValue();

					if (!MapUtil.isEmpty(defaultValueMap)) {
						return (Serializable)defaultValueMap.get("en_US");
					}
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		List<String> attributeNames = new ArrayList<>();

		try {
			DataDefinition dataDefinition = _getDataDefinition();

			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				attributeNames.add(dataDefinitionField.getName());
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return Collections.enumeration(attributeNames);
	}

	@Override
	public UnicodeProperties getAttributeProperties(String name) {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		DataDefinition dataDefinition = _getDataDefinition();

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			if (StringUtil.equals(name, dataDefinitionField.getName())) {
				unicodeProperties.put(
					"fieldType", dataDefinitionField.getFieldType());

				break;
			}
		}

		return unicodeProperties;
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return getAttributes(false);
	}

	@Override
	public Map<String, Serializable> getAttributes(boolean secure) {
		Map<String, Serializable> attributes = new HashMap<>();

		Enumeration<String> attributeNames = getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			attributes.put(attributeName, getAttribute(attributeName));
		}

		return attributes;
	}

	@Override
	public Map<String, Serializable> getAttributes(Collection<String> names) {
		return getAttributes(names, false);
	}

	@Override
	public Map<String, Serializable> getAttributes(
		Collection<String> names, boolean secure) {

		Map<String, Serializable> attributes = new HashMap<>();

		List<String> namesList = new ArrayList<>(names);

		List<String> attributeNames = ListUtil.fromEnumeration(
			getAttributeNames());

		Stream<String> stream = attributeNames.stream();

		stream.filter(
			attributeName -> ListUtil.exists(namesList, attributeName::equals)
		).forEach(
			attributeName -> attributes.put(
				attributeName, getAttribute(attributeName))
		);

		return attributes;
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

			Stream<DataDefinitionField> stream = Arrays.stream(
				dataDefinition.getDataDefinitionFields());

			Optional<DataDefinitionField> dataDefinitionFieldOptional =
				stream.filter(
					dataDefinitionField -> StringUtil.equals(
						dataDefinitionField.getName(), name)
				).findAny();

			return dataDefinitionFieldOptional.isPresent();
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
				"Class primary key is less or equal to 0");
		}

		try {
			DataRecordResource dataRecordResource = _getDataRecordResource();

			DataRecord dataRecord = null;

			DDLRecord ddlRecord =
				DDLRecordLocalServiceUtil.fetchFirstRecordByClassNameAndClassPK(
					_className, _classPK);

			if (ddlRecord == null) {
				dataRecord = new DataRecord();

				dataRecord.setDataRecordValues(
					HashMapBuilder.<String, Object>put(
						name, value
					).build());

				DataDefinition dataDefinition = _getDataDefinition();

				dataRecord = dataRecordResource.postDataDefinitionDataRecord(
					dataDefinition.getId(), dataRecord);

				ddlRecord = DDLRecordLocalServiceUtil.getDDLRecord(
					dataRecord.getId());

				ddlRecord.setClassName(_className);
				ddlRecord.setClassPK(_classPK);

				DDLRecordLocalServiceUtil.updateDDLRecord(ddlRecord);

				return;
			}

			dataRecord = dataRecordResource.getDataRecord(
				ddlRecord.getRecordId());

			Map<String, Object> dataRecordValues =
				dataRecord.getDataRecordValues();

			dataRecordValues.put(name, value);

			dataRecordResource.putDataRecord(dataRecord.getId(), dataRecord);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void setAttributeDefault(String name, Serializable defaultValue) {
		try {
			DataDefinitionResource dataDefinitionResource =
				_getDataDefinitionResource();

			DataDefinition dataDefinition = _getDataDefinition();

			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (StringUtil.equals(dataDefinitionField.getName(), name)) {
					Map<String, Object> defaultValueMap =
						dataDefinitionField.getDefaultValue();

					defaultValueMap.put("en_US", defaultValue);

					dataDefinitionResource.putDataDefinition(
						dataDefinition.getId(), dataDefinition);
				}
			}
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
			DataDefinitionResource dataDefinitionResource =
				_getDataDefinitionResource();

			DataDefinition dataDefinition = _getDataDefinition();

			for (DataDefinitionField dataDefinitionField :
					dataDefinition.getDataDefinitionFields()) {

				if (StringUtil.equals(dataDefinitionField.getName(), name)) {
					dataDefinitionField.setFieldType(
						unicodeProperties.getProperty("fieldType"));

					dataDefinitionResource.putDataDefinition(
						dataDefinition.getId(), dataDefinition);

					break;
				}
			}
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
		throw new UnsupportedOperationException("Operation not supported");
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

	protected DataDefinitionField createDataDefinitionField(
		String name, String type, Serializable defaultValue) {

		DataDefinitionField dataDefinitionField = new DataDefinitionField();

		dataDefinitionField.setDefaultValue(
			HashMapBuilder.<String, Object>put(
				"en_US", defaultValue
			).build());
		dataDefinitionField.setFieldType(type);
		dataDefinitionField.setIndexable(false);
		dataDefinitionField.setName(name);
		dataDefinitionField.setReadOnly(false);
		dataDefinitionField.setRequired(false);
		dataDefinitionField.setVisible(true);

		return dataDefinitionField;
	}

	private DataDefinition _getDataDefinition() {
		try {
			DataDefinitionResource dataDefinitionResource =
				_getDataDefinitionResource();

			return dataDefinitionResource.
				getSiteDataDefinitionByContentTypeByDataDefinitionKey(
					_companyGroupId, "native-object", _className);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private DataDefinitionResource _getDataDefinitionResource()
		throws PortalException {

		return DataDefinitionResource.builder(
		).user(
			UserLocalServiceUtil.getUser(_getUserId())
		).checkPermissions(
			false
		).build();
	}

	private DataRecordResource _getDataRecordResource() throws PortalException {
		return DataRecordResource.builder(
		).user(
			UserLocalServiceUtil.getUser(_getUserId())
		).checkPermissions(
			false
		).build();
	}

	private long _getUserId() throws PortalException {
		long userId = PrincipalThreadLocal.getUserId();

		if (userId == 0) {
			Company company = CompanyLocalServiceUtil.getCompany(_companyId);

			User user = company.getDefaultUser();

			userId = user.getUserId();
		}

		return userId;
	}

	private String _className;
	private long _classPK;
	private final long _companyGroupId;
	private long _companyId;

}