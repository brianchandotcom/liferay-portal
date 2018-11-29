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

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DataDefinitionField implements Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DataDefinitionField)) {
			return false;
		}

		DataDefinitionField dataDefinitionField = (DataDefinitionField)obj;

		if (Objects.equals(_defaultValue, dataDefinitionField._defaultValue) &&
			Objects.equals(_indexable, dataDefinitionField._indexable) &&
			Objects.equals(_label, dataDefinitionField._label) &&
			Objects.equals(_localizable, dataDefinitionField._localizable) &&
			Objects.equals(_name, dataDefinitionField._name) &&
			Objects.equals(_repeatable, dataDefinitionField._repeatable) &&
			Objects.equals(_required, dataDefinitionField._required) &&
			Objects.equals(_tip, dataDefinitionField._tip) &&
			Objects.equals(_type, dataDefinitionField._type)) {

			return true;
		}

		return false;
	}

	public Object getDefaultValue() {
		return _defaultValue;
	}

	public Map<String, String> getLabel() {
		return Collections.unmodifiableMap(_label);
	}

	public String getName() {
		return _name;
	}

	public Map<String, String> getTip() {
		return Collections.unmodifiableMap(_tip);
	}

	public DataDefinitionColumnType getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		int hash = 0;

		if (_defaultValue != null) {
			hash = HashUtil.hash(hash, _defaultValue.hashCode());
		}

		hash = HashUtil.hash(hash, _indexable);

		hash = HashUtil.hash(hash, _label.hashCode());

		hash = HashUtil.hash(hash, _localizable);

		hash = HashUtil.hash(hash, _name.hashCode());

		hash = HashUtil.hash(hash, _repeatable);

		hash = HashUtil.hash(hash, _required);

		hash = HashUtil.hash(hash, _tip.hashCode());

		return HashUtil.hash(hash, _type.hashCode());
	}

	public boolean isIndexable() {
		return _indexable;
	}

	public boolean isLocalizable() {
		return _localizable;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	public boolean isRequired() {
		return _required;
	}

	public static final class Builder {

		public static Builder newBuilder(
			String name, DataDefinitionColumnType type) {

			return new Builder(name, type);
		}

		public DataDefinitionField build() {
			return _dataDefinitionField;
		}

		public Builder defaultValue(Object defaultValue) {
			_dataDefinitionField._defaultValue = defaultValue;

			return this;
		}

		public Builder indexable(boolean indexable) {
			_dataDefinitionField._indexable = indexable;

			return this;
		}

		public Builder label(Map<String, String> label) {
			_dataDefinitionField._label.putAll(label);

			return this;
		}

		public Builder localizable(boolean localizable) {
			_dataDefinitionField._localizable = localizable;

			return this;
		}

		public Builder repeatable(boolean repeatable) {
			_dataDefinitionField._repeatable = repeatable;

			return this;
		}

		public Builder required(boolean required) {
			_dataDefinitionField._required = required;

			return this;
		}

		public Builder tip(Map<String, String> tip) {
			_dataDefinitionField._tip.putAll(tip);

			return this;
		}

		private Builder(String name, DataDefinitionColumnType type) {
			_dataDefinitionField._name = name;
			_dataDefinitionField._type = type;
		}

		private final DataDefinitionField _dataDefinitionField =
			new DataDefinitionField();

	}

	private DataDefinitionField() {
	}

	private Object _defaultValue;
	private boolean _indexable = true;
	private final Map<String, String> _label = new HashMap<>();
	private boolean _localizable;
	private String _name;
	private boolean _repeatable;
	private boolean _required;
	private final Map<String, String> _tip = new HashMap<>();
	private DataDefinitionColumnType _type;

}