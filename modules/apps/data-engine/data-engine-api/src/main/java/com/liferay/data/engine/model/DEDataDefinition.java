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

package com.liferay.data.engine.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinition implements ClassedModel, Serializable {

	public DEDataDefinition(
		List<DEDataDefinitionField> deDataDefinitionFields) {

		_fields.addAll(deDataDefinitionFields);
	}

	public void addDescription(Locale locale, String description) {
		_description.put(LocaleUtil.toLanguageId(locale), description);
	}

	public void addDescriptions(Map<Locale, String> descriptions) {
		for (Map.Entry<Locale, String> entry : descriptions.entrySet()) {
			_description.put(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}
	}

	public void addField(DEDataDefinitionField field) {
		_fields.add(field);
	}

	public void addFields(List<DEDataDefinitionField> fields) {
		_fields.addAll(fields);
	}

	public void addName(Locale locale, String name) {
		_name.put(LocaleUtil.toLanguageId(locale), name);
	}

	public void addNames(Map<Locale, String> names) {
		for (Map.Entry<Locale, String> entry : names.entrySet()) {
			_name.put(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataDefinition)) {
			return false;
		}

		DEDataDefinition deDataDefinition = (DEDataDefinition)obj;

		if (Objects.equals(_name, deDataDefinition._name) &&
			Objects.equals(_description, deDataDefinition._description) &&
			Objects.equals(
				_dataDefinitionId, deDataDefinition._dataDefinitionId) &&
			Objects.equals(_storageType, deDataDefinition._storageType) &&
			Objects.equals(_fields, deDataDefinition._fields)) {

			return true;
		}

		return false;
	}

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public Map<String, String> getDescription() {
		return _description;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public List<DEDataDefinitionField> getFields() {
		return _fields;
	}

	@Override
	public Class<?> getModelClass() {
		return DEDataDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return DEDataDefinition.class.getName();
	}

	public Map<String, String> getName() {
		return _name;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dataDefinitionId;
	}

	public String getStorageType() {
		return _storageType;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name.hashCode());

		hash = HashUtil.hash(hash, _description.hashCode());

		hash = HashUtil.hash(hash, _dataDefinitionId);

		hash = HashUtil.hash(hash, _storageType.hashCode());

		return HashUtil.hash(hash, _fields.hashCode());
	}

	public void setDataDefinitionId(long dataDefinitionId) {
		_dataDefinitionId = dataDefinitionId;
	}

	public void setDescription(Map<String, String> description) {
		_description = description;

		if (_description == null) {
			_description = new HashMap<>();
		}
	}

	public void setFields(List<DEDataDefinitionField> fields) {
		_fields = fields;

		if (_fields == null) {
			_fields = new ArrayList<>();
		}
	}

	public void setName(Map<String, String> name) {
		_name = name;

		if (_name == null) {
			_name = new HashMap<>();
		}
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dataDefinitionId = ((Long)primaryKeyObj).longValue();
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	private long _dataDefinitionId;
	private Map<String, String> _description = new HashMap<>();
	private List<DEDataDefinitionField> _fields = new ArrayList<>();
	private Map<String, String> _name = new HashMap<>();
	private String _storageType = "json";

}