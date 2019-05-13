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

package com.liferay.data.engine.spi.definition;

import com.liferay.petra.lang.HashUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class SPIDataDefinitionField {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDataDefinitionField)) {
			return false;
		}

		SPIDataDefinitionField spiDataDefinitionField =
			(SPIDataDefinitionField)obj;

		if (Objects.equals(
				_customProperties, spiDataDefinitionField._customProperties) &&
			Objects.equals(
				_defaultValue, spiDataDefinitionField._defaultValue) &&
			Objects.equals(_fieldType, spiDataDefinitionField._fieldType) &&
			Objects.equals(_id, spiDataDefinitionField._id) &&
			Objects.equals(_indexable, spiDataDefinitionField._indexable) &&
			Objects.equals(_label, spiDataDefinitionField._label) &&
			Objects.equals(_localizable, spiDataDefinitionField._localizable) &&
			Objects.equals(_name, spiDataDefinitionField._name) &&
			Objects.equals(_repeatable, spiDataDefinitionField._repeatable) &&
			Objects.equals(_tip, spiDataDefinitionField._tip)) {

			return true;
		}

		return false;
	}

	public Map<String, Object> getCustomProperties() {
		return _customProperties;
	}

	public Map<String, Object> getDefaultValue() {
		return _defaultValue;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public long getId() {
		return _id;
	}

	public boolean getIndexable() {
		return _indexable;
	}

	public Map<String, Object> getLabel() {
		return _label;
	}

	public boolean getLocalizable() {
		return _localizable;
	}

	public String getName() {
		return _name;
	}

	public boolean getRepeatable() {
		return _repeatable;
	}

	public Map<String, Object> getTip() {
		return _tip;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _customProperties);

		hash = HashUtil.hash(hash, _defaultValue);
		hash = HashUtil.hash(hash, _fieldType);
		hash = HashUtil.hash(hash, _id);
		hash = HashUtil.hash(hash, _indexable);
		hash = HashUtil.hash(hash, _label);
		hash = HashUtil.hash(hash, _localizable);
		hash = HashUtil.hash(hash, _name);
		hash = HashUtil.hash(hash, _repeatable);

		return HashUtil.hash(hash, _tip);
	}

	public void setCustomProperties(Map<String, Object> customProperties) {
		_customProperties = customProperties;
	}

	public void setDefaultValue(Map<String, Object> defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setIndexable(boolean indexable) {
		_indexable = indexable;
	}

	public void setLabel(Map<String, Object> label) {
		_label = label;
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

	public void setTip(Map<String, Object> tip) {
		_tip = tip;
	}

	private Map<String, Object> _customProperties;
	private Map<String, Object> _defaultValue;
	private String _fieldType;
	private long _id;
	private boolean _indexable;
	private Map<String, Object> _label;
	private boolean _localizable;
	private String _name;
	private boolean _repeatable;
	private Map<String, Object> _tip;

}