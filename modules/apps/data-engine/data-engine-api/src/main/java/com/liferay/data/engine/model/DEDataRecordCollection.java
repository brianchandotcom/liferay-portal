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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollection implements ClassedModel, Serializable {

	public void addDescription(Locale locale, String description) {
		_description.put(LocaleUtil.toLanguageId(locale), description);
	}

	public void addDescriptions(Map<Locale, String> descriptions) {
		for (Map.Entry<Locale, String> entry : descriptions.entrySet()) {
			_description.put(
				LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
		}
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

		if (!(obj instanceof DEDataRecordCollection)) {
			return false;
		}

		DEDataRecordCollection deDataRecordCollection =
			(DEDataRecordCollection)obj;

		if (Objects.equals(
				_deDataRecordCollectionId,
				deDataRecordCollection._deDataRecordCollectionId) &&
			Objects.equals(_description, deDataRecordCollection._description) &&
			Objects.equals(_name, deDataRecordCollection._name) &&
			Objects.equals(
				_deDataDefinition, deDataRecordCollection._deDataDefinition)) {

			return true;
		}

		return false;
	}

	public DEDataDefinition getDEDataDefinition() {
		return _deDataDefinition;
	}

	public long getDEDataRecordCollectionId() {
		return _deDataRecordCollectionId;
	}

	public Map<String, String> getDescription() {
		return _description;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		return DEDataRecordCollection.class;
	}

	@Override
	public String getModelClassName() {
		return DEDataRecordCollection.class.getName();
	}

	public Map<String, String> getName() {
		return _name;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _deDataRecordCollectionId;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _deDataRecordCollectionId);

		hash = HashUtil.hash(hash, _description.hashCode());
		hash = HashUtil.hash(hash, _name.hashCode());

		return HashUtil.hash(hash, _deDataDefinition.hashCode());
	}

	public void setDEDataDefinition(DEDataDefinition deDataDefinition) {
		_deDataDefinition = deDataDefinition;
	}

	public void setDEDataRecordCollectionId(long deDataRecordCollectionId) {
		_deDataRecordCollectionId = deDataRecordCollectionId;
	}

	public void setDescription(Map<String, String> description) {
		_description = description;

		if (_description == null) {
			_description = new HashMap<>();
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
		_deDataRecordCollectionId = ((Long)primaryKeyObj).longValue();
	}

	private DEDataDefinition _deDataDefinition;
	private long _deDataRecordCollectionId;
	private Map<String, String> _description = new HashMap<>();
	private Map<String, String> _name = new HashMap<>();

}