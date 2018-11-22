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
public final class DataDefinition implements ClassedModel, Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataDefinition)) {
			return false;
		}

		DataDefinition dataDefinition = (DataDefinition)obj;

		if (Objects.equals(_name, dataDefinition._name) &&
			Objects.equals(_description, dataDefinition._description) &&
			Objects.equals(
				_dataDefinitionId, dataDefinition._dataDefinitionId) &&
			Objects.equals(_storageType, dataDefinition._storageType) &&
			Objects.equals(_columns, dataDefinition._columns)) {

			return true;
		}

		return false;
	}

	public List<DataDefinitionColumn> getColumns() {
		return Collections.unmodifiableList(_columns);
	}

	public long getDataDefinitionId() {
		return _dataDefinitionId;
	}

	public Map<String, String> getDescription() {
		return Collections.unmodifiableMap(_description);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		return DataDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return DataDefinition.class.getName();
	}

	public Map<String, String> getName() {
		return Collections.unmodifiableMap(_name);
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

		return HashUtil.hash(hash, _columns.hashCode());
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dataDefinitionId = ((Long)primaryKeyObj).longValue();
	}

	public static final class Builder {

		public static Builder newBuilder(List<DataDefinitionColumn> columns) {
			return new Builder(columns);
		}

		public DataDefinition build() {
			return _dataDefinition;
		}

		public Builder dataDefinitionId(long dataDefinitionId) {
			_dataDefinition._dataDefinitionId = dataDefinitionId;

			return this;
		}

		public Builder description(Locale locale, String description) {
			_dataDefinition._description.put(
				LocaleUtil.toLanguageId(locale), description);

			return this;
		}

		public Builder description(Map<Locale, String> descriptions) {
			for (Map.Entry<Locale, String> entry : descriptions.entrySet()) {
				_dataDefinition._description.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}

			return this;
		}

		public Builder name(Locale locale, String name) {
			_dataDefinition._name.put(LocaleUtil.toLanguageId(locale), name);

			return this;
		}

		public Builder name(Map<Locale, String> names) {
			for (Map.Entry<Locale, String> entry : names.entrySet()) {
				_dataDefinition._name.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}

			return this;
		}

		public Builder storageType(String type) {
			_dataDefinition._storageType = type;

			return this;
		}

		private Builder(List<DataDefinitionColumn> columns) {
			_dataDefinition._columns.addAll(columns);
		}

		private final DataDefinition _dataDefinition = new DataDefinition();

	}

	private DataDefinition() {
	}

	private final List<DataDefinitionColumn> _columns = new ArrayList<>();
	private long _dataDefinitionId;
	private final Map<String, String> _description = new HashMap<>();
	private final Map<String, String> _name = new HashMap<>();
	private String _storageType = "json";

}