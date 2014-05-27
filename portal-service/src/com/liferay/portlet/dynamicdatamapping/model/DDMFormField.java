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

package com.liferay.portlet.dynamicdatamapping.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMFormField {

	public DDMFormField(String name, String type) {
		_name = name;
		_type = type;
	}

	public String getDataType() {
		return _dataType;
	}

	public String getIndexType() {
		return _indexType;
	}

	public LocalizedValue getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public List<DDMFormField> getNestedFields() {
		return _nestedFields;
	}

	public Map<String, DDMFormField> getNestedFieldsMap() {
		Map<String, DDMFormField> fieldsMap =
			new HashMap<String, DDMFormField>();

		for (DDMFormField nestedField : _nestedFields) {
			fieldsMap.put(nestedField.getName(), nestedField);

			fieldsMap.putAll(nestedField.getNestedFieldsMap());
		}

		return fieldsMap;
	}

	public DDMFormFieldOptions getOptions() {
		return _options;
	}

	public LocalizedValue getPredefinedValue() {
		return _predefinedValue;
	}

	public LocalizedValue getStyle() {
		return _style;
	}

	public LocalizedValue getTip() {
		return _tip;
	}

	public String getType() {
		return _type;
	}

	public boolean isMultiple() {
		return _multiple;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setDataType(String dataType) {
		_dataType = dataType;
	}

	public void setIndexType(String indexType) {
		_indexType = indexType;
	}

	public void setLabel(LocalizedValue label) {
		_label = label;
	}

	public void setMultiple(boolean multiple) {
		_multiple = multiple;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNestedFields(List<DDMFormField> nestedFields) {
		_nestedFields = nestedFields;
	}

	public void setOptions(DDMFormFieldOptions options) {
		_options = options;
	}

	public void setPredefinedValue(LocalizedValue predefinedValue) {
		_predefinedValue = predefinedValue;
	}

	public void setRepeatable(boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setStyle(LocalizedValue style) {
		_style = style;
	}

	public void setTip(LocalizedValue tip) {
		_tip = tip;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _dataType;
	private String _indexType;
	private LocalizedValue _label = new LocalizedValue();
	private boolean _multiple;
	private String _name;
	private List<DDMFormField> _nestedFields = new ArrayList<DDMFormField>();
	private DDMFormFieldOptions _options = new DDMFormFieldOptions();
	private LocalizedValue _predefinedValue = new LocalizedValue();
	private boolean _repeatable;
	private boolean _required;
	private LocalizedValue _style = new LocalizedValue();
	private LocalizedValue _tip = new LocalizedValue();
	private String _type;

}