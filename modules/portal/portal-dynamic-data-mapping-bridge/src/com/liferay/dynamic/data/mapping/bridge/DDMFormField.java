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

package com.liferay.dynamic.data.mapping.bridge;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo Carvalho
 * @author Marcellus Tavares
 */
public class DDMFormField implements Serializable {

	public DDMFormField() {
		_properties = new LinkedHashMap<>();
	}

	public DDMFormField(DDMFormField ddmFormField) {
		_properties = new LinkedHashMap<>(ddmFormField._properties);

		setDDMFormFieldOptions(
			new DDMFormFieldOptions(ddmFormField.getDDMFormFieldOptions()));
		setLabel(new LocalizedValue(ddmFormField.getLabel()));
		setPredefinedValue(
			new LocalizedValue(ddmFormField.getPredefinedValue()));
		setStyle(new LocalizedValue(ddmFormField.getStyle()));
		setTip(new LocalizedValue(ddmFormField.getTip()));

		for (DDMFormField nestedDDMFormField :
				ddmFormField._nestedDDMFormFields) {

			addNestedDDMFormField(nestedDDMFormField);
		}
	}

	public DDMFormField(String name, String type) {
		_properties = new LinkedHashMap<>();

		setName(name);
		setType(type);

		setDDMFormFieldOptions(new DDMFormFieldOptions());
		setLabel(new LocalizedValue());
		setPredefinedValue(new LocalizedValue());
		setStyle(new LocalizedValue());
		setTip(new LocalizedValue());
	}

	public void addNestedDDMFormField(DDMFormField nestedDDMFormField) {
		nestedDDMFormField.setDDMForm(_ddmForm);

		_nestedDDMFormFields.add(nestedDDMFormField);
	}

	public String getDataType() {
		return getStringProperty("dataType");
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public DDMFormFieldOptions getDDMFormFieldOptions() {
		return (DDMFormFieldOptions)_properties.get("options");
	}

	public String getFieldNamespace() {
		return getStringProperty("fieldNamespace");
	}

	public String getIndexType() {
		return getStringProperty("indexType");
	}

	public LocalizedValue getLabel() {
		return (LocalizedValue)_properties.get("label");
	}

	public String getName() {
		return getStringProperty("name");
	}

	public List<DDMFormField> getNestedDDMFormFields() {
		return _nestedDDMFormFields;
	}

	public Map<String, DDMFormField> getNestedDDMFormFieldsMap() {
		Map<String, DDMFormField> nestedDDMFormFieldsMap =
			new LinkedHashMap<>();

		for (DDMFormField nestedDDMFormField : _nestedDDMFormFields) {
			nestedDDMFormFieldsMap.put(
				nestedDDMFormField.getName(), nestedDDMFormField);

			nestedDDMFormFieldsMap.putAll(
				nestedDDMFormField.getNestedDDMFormFieldsMap());
		}

		return nestedDDMFormFieldsMap;
	}

	public LocalizedValue getPredefinedValue() {
		return (LocalizedValue)_properties.get("predefinedValue");
	}

	public Object getProperty(String name) {
		return _properties.get(name);
	}

	public LocalizedValue getStyle() {
		return (LocalizedValue)_properties.get("style");
	}

	public LocalizedValue getTip() {
		return (LocalizedValue)_properties.get("tip");
	}

	public String getType() {
		return getStringProperty("type");
	}

	public String getVisibilityExpression() {
		return getStringProperty("visibilityExpression");
	}

	public boolean isLocalizable() {
		return getBooleanProperty("localizable");
	}

	public boolean isMultiple() {
		return getBooleanProperty("multiple");
	}

	public boolean isReadOnly() {
		return getBooleanProperty("readOnly");
	}

	public boolean isRepeatable() {
		return getBooleanProperty("repeatable");
	}

	public boolean isRequired() {
		return getBooleanProperty("required");
	}

	public boolean isShowLabel() {
		return getBooleanProperty("showLabel");
	}

	public boolean isTransient() {
		if ((getDataType() == null) || (getDataType().trim().length() == 0)) {
			return true;
		}

		return false;
	}

	public void setDataType(String dataType) {
		_properties.put("dataType", dataType);
	}

	public void setDDMForm(DDMForm ddmForm) {
		for (DDMFormField nestedDDMFormField : _nestedDDMFormFields) {
			nestedDDMFormField.setDDMForm(ddmForm);
		}

		_ddmForm = ddmForm;
	}

	public void setDDMFormFieldOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		_properties.put("options", ddmFormFieldOptions);
	}

	public void setFieldNamespace(String fieldNamespace) {
		_properties.put("fieldNamespace", fieldNamespace);
	}

	public void setIndexType(String indexType) {
		_properties.put("indexType", indexType);
	}

	public void setLabel(LocalizedValue label) {
		_properties.put("label", label);
	}

	public void setLocalizable(boolean localizable) {
		_properties.put("localizable", localizable);
	}

	public void setMultiple(boolean multiple) {
		_properties.put("multiple", multiple);
	}

	public void setName(String name) {
		_properties.put("name", name);
	}

	public void setNestedDDMFormFields(List<DDMFormField> nestedDDMFormFields) {
		_nestedDDMFormFields = nestedDDMFormFields;
	}

	public void setPredefinedValue(LocalizedValue predefinedValue) {
		_properties.put("predefinedValue", predefinedValue);
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	public void setReadOnly(boolean readOnly) {
		_properties.put("readOnly", readOnly);
	}

	public void setRepeatable(boolean repeatable) {
		_properties.put("repeatable", repeatable);
	}

	public void setRequired(boolean required) {
		_properties.put("required", required);
	}

	public void setShowLabel(boolean showLabel) {
		_properties.put("showLabel", showLabel);
	}

	public void setStyle(LocalizedValue style) {
		_properties.put("style", style);
	}

	public void setTip(LocalizedValue tip) {
		_properties.put("tip", tip);
	}

	public void setType(String type) {
		_properties.put("type", type);
	}

	public void setVisibilityExpression(String visibilityExpression) {
		_properties.put("visibilityExpression", visibilityExpression);
	}

	protected boolean getBooleanProperty(String key) {
		Object value = _properties.get(key);

		if (value == null) {
			return false;
		}

		return (Boolean)_properties.get(key);
	}

	protected String getStringProperty(String key) {
		Object value = _properties.get(key);

		if (value == null) {
			return _BLANK;
		}

		return (String)_properties.get(key);
	}

	private static final String _BLANK = "";

	private DDMForm _ddmForm;
	private List<DDMFormField> _nestedDDMFormFields = new ArrayList<>();
	private final Map<String, Object> _properties;

}