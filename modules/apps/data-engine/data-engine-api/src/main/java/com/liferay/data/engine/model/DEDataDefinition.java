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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinition implements ClassedModel, Serializable {

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
				_deDataDefinitionId, deDataDefinition._deDataDefinitionId) &&
			Objects.equals(_storageType, deDataDefinition._storageType) &&
			Objects.equals(_fields, deDataDefinition._fields)) {

			return true;
		}

		return false;
	}

	public long getDEDataDefinitionId() {
		return _deDataDefinitionId;
	}

	public Map<String, String> getDescription() {
		return Collections.unmodifiableMap(_description);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public List<DEDataDefinitionField> getFields() {
		return Collections.unmodifiableList(_fields);
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
		return Collections.unmodifiableMap(_name);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _deDataDefinitionId;
	}

	public String getStorageType() {
		return _storageType;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name.hashCode());

		hash = HashUtil.hash(hash, _description.hashCode());

		hash = HashUtil.hash(hash, _deDataDefinitionId);

		hash = HashUtil.hash(hash, _storageType.hashCode());

		return HashUtil.hash(hash, _fields.hashCode());
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_deDataDefinitionId = ((Long)primaryKeyObj).longValue();
	}

	public static final class Builder {

		public static Builder newBuilder(List<DEDataDefinitionField> fields) {
			return new Builder(fields);
		}

		public DEDataDefinition build() {
			return _deDataDefinition;
		}

		public Builder deDataDefinitionId(long deDataDefinitionId) {
			_deDataDefinition._deDataDefinitionId = deDataDefinitionId;

			return this;
		}

		public Builder description(Locale locale, String description) {
			_deDataDefinition._description.put(
				LocaleUtil.toLanguageId(locale), description);

			return this;
		}

		public Builder description(Map<Locale, String> descriptions) {
			for (Map.Entry<Locale, String> entry : descriptions.entrySet()) {
				_deDataDefinition._description.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}

			return this;
		}

		public Builder name(Locale locale, String name) {
			_deDataDefinition._name.put(LocaleUtil.toLanguageId(locale), name);

			return this;
		}

		public Builder name(Map<Locale, String> names) {
			for (Map.Entry<Locale, String> entry : names.entrySet()) {
				_deDataDefinition._name.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}

			return this;
		}

		public Builder storageType(String type) {
			_deDataDefinition._storageType = type;

			return this;
		}

		private Builder(List<DEDataDefinitionField> fields) {
			_deDataDefinition._fields.addAll(fields);
		}

		private final DEDataDefinition _deDataDefinition =
			new DEDataDefinition();

	}

	private DEDataDefinition() {
	}

	private long _deDataDefinitionId;
	private final Map<String, String> _description = new HashMap<>();
	private final List<DEDataDefinitionField> _fields = new ArrayList<>();
	private final Map<String, String> _name = new HashMap<>();
	private String _storageType = "json";

}