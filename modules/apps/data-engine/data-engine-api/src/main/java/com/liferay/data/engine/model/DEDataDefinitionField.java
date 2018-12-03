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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public final class DEDataDefinitionField implements Serializable {

	public DEDataDefinitionField(String name, String type) {
		_name = name;
		_type = type;
	}

	public void addLabel(String key, String label) {
		_label.put(key, label);
	}

	public void addLabels(Map<String, String> label) {
		_label.putAll(label);
	}

	public void addTip(String key, String tip) {
		_tip.put(key, tip);
	}

	public void addTips(Map<String, String> tip) {
		_tip.putAll(tip);
	}

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
		return _label;
	}

	public String getName() {
		return _name;
	}

	public Map<String, String> getTip() {
		return _tip;
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

	public void setDefaultValue(Object defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setIndexable(boolean indexable) {
		_indexable = indexable;
	}

	public void setLabel(Map<String, String> label) {
		_label = label;

		if (_label == null) {
			_label = new HashMap<>();
		}
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRepeatable(boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setTip(Map<String, String> tip) {
		_tip = tip;

		if (_tip == null) {
			_tip = new HashMap<>();
		}
	}

	public void setType(String type) {
		_type = type;
	}

	private Object _defaultValue;
	private boolean _indexable = true;
	private Map<String, String> _label = new HashMap<>();
	private boolean _localizable;
	private String _name;
	private boolean _repeatable;
	private boolean _required;
	private Map<String, String> _tip = new HashMap<>();
	private String _type;

}