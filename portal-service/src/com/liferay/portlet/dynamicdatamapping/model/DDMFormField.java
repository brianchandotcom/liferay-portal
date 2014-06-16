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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMFormField implements Serializable {

	public DDMFormField(String name, String type) {
		_name = name;
		_type = type;
	}

	public String getDataType() {
		return _dataType;
	}

	public DDMFormFieldOptions getDDMFormFieldOptions() {
		return _ddmFormFieldOptions;
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

	public String getNamespace() {
		return _namespace;
	}

	public List<DDMFormField> getNestedDDMFormFields() {
		return _nestedDDMFormFields;
	}

	public Map<String, DDMFormField> getNestedDDMFormFieldsMap() {
		Map<String, DDMFormField> ddmFormfieldsMap =
			new HashMap<String, DDMFormField>();

		for (DDMFormField nestedDDMFormField : _nestedDDMFormFields) {
			ddmFormfieldsMap.put(
				nestedDDMFormField.getName(), nestedDDMFormField);

			ddmFormfieldsMap.putAll(
				nestedDDMFormField.getNestedDDMFormFieldsMap());
		}

		return ddmFormfieldsMap;
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

	public boolean isLocalizable() {
		return _localizable;
	}

	public boolean isMultiple() {
		return _multiple;
	}

	public boolean isReadOnly() {
		return _readOnly;
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

	public void setDDMFormFieldOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		_ddmFormFieldOptions = ddmFormFieldOptions;
	}

	public void setIndexType(String indexType) {
		_indexType = indexType;
	}

	public void setLabel(LocalizedValue label) {
		_label = label;
	}

	public void setLocalizable(boolean localizable) {
		_localizable = localizable;
	}

	public void setMultiple(boolean multiple) {
		_multiple = multiple;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setNestedDDMFormFields(List<DDMFormField> nestedDDMFormFields) {
		_nestedDDMFormFields = nestedDDMFormFields;
	}

	public void setPredefinedValue(LocalizedValue predefinedValue) {
		_predefinedValue = predefinedValue;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
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
	private DDMFormFieldOptions _ddmFormFieldOptions =
		new DDMFormFieldOptions();
	private String _indexType;
	private LocalizedValue _label = new LocalizedValue();
	private boolean _localizable;
	private boolean _multiple;
	private String _name;
	private String _namespace;
	private List<DDMFormField> _nestedDDMFormFields =
		new ArrayList<DDMFormField>();
	private LocalizedValue _predefinedValue = new LocalizedValue();
	private boolean _readOnly;
	private boolean _repeatable;
	private boolean _required;
	private LocalizedValue _style = new LocalizedValue();
	private LocalizedValue _tip = new LocalizedValue();
	private String _type;

}