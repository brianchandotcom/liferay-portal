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
public final class DEDataDefinitionField implements Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataDefinitionField)) {
			return false;
		}

		DEDataDefinitionField deDataDefinitionField =
			(DEDataDefinitionField)obj;

		if (Objects.equals(
				_defaultValue, deDataDefinitionField._defaultValue) &&
			Objects.equals(_indexable, deDataDefinitionField._indexable) &&
			Objects.equals(_label, deDataDefinitionField._label) &&
			Objects.equals(_localizable, deDataDefinitionField._localizable) &&
			Objects.equals(_name, deDataDefinitionField._name) &&
			Objects.equals(_repeatable, deDataDefinitionField._repeatable) &&
			Objects.equals(_required, deDataDefinitionField._required) &&
			Objects.equals(_tip, deDataDefinitionField._tip) &&
			Objects.equals(_type, deDataDefinitionField._type)) {

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

	public String getType() {
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

		public static Builder newBuilder(String name, String type) {
			return new Builder(name, type);
		}

		public DEDataDefinitionField build() {
			return _deDataDefinitionField;
		}

		public Builder defaultValue(Object defaultValue) {
			_deDataDefinitionField._defaultValue = defaultValue;

			return this;
		}

		public Builder indexable(boolean indexable) {
			_deDataDefinitionField._indexable = indexable;

			return this;
		}

		public Builder label(Map<String, String> label) {
			_deDataDefinitionField._label.putAll(label);

			return this;
		}

		public Builder localizable(boolean localizable) {
			_deDataDefinitionField._localizable = localizable;

			return this;
		}

		public Builder repeatable(boolean repeatable) {
			_deDataDefinitionField._repeatable = repeatable;

			return this;
		}

		public Builder required(boolean required) {
			_deDataDefinitionField._required = required;

			return this;
		}

		public Builder tip(Map<String, String> tip) {
			_deDataDefinitionField._tip.putAll(tip);

			return this;
		}

		private Builder(String name, String type) {
			_deDataDefinitionField._name = name;
			_deDataDefinitionField._type = type;
		}

		private final DEDataDefinitionField _deDataDefinitionField =
			new DEDataDefinitionField();

	}

	private DEDataDefinitionField() {
	}

	private Object _defaultValue;
	private boolean _indexable = true;
	private final Map<String, String> _label = new HashMap<>();
	private boolean _localizable;
	private String _name;
	private boolean _repeatable;
	private boolean _required;
	private final Map<String, String> _tip = new HashMap<>();
	private String _type;

}