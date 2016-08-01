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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

		for (DDMFormFieldRule ddmFormFieldRule :
				ddmFormField._ddmFormFieldRules) {

			addDDMFormFieldRule(new DDMFormFieldRule(ddmFormFieldRule));
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation != null) {
			setDDMFormFieldValidation(
				new DDMFormFieldValidation(ddmFormFieldValidation));
		}

		setLabel(new LocalizedValue(ddmFormField.getLabel()));
		setPredefinedValue(
			new LocalizedValue(ddmFormField.getPredefinedValue()));
		setStyle(new LocalizedValue(ddmFormField.getStyle()));
		setTip(new LocalizedValue(ddmFormField.getTip()));

		for (DDMFormField nestedDDMFormField :
				ddmFormField._nestedDDMFormFields) {

			addNestedDDMFormField(new DDMFormField(nestedDDMFormField));
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void addDDMFormFieldRule(DDMFormFieldRule ddmFormFieldRule) {
		_ddmFormFieldRules.add(ddmFormFieldRule);
	}

	public void addNestedDDMFormField(DDMFormField nestedDDMFormField) {
		nestedDDMFormField.setDDMForm(_ddmForm);

		_nestedDDMFormFields.add(nestedDDMFormField);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormField)) {
			return false;
		}

		DDMFormField ddmFormField = (DDMFormField)obj;

		if (Objects.equals(_properties, ddmFormField._properties) &&
			Objects.equals(
				_nestedDDMFormFields, ddmFormField._nestedDDMFormFields)) {

			return true;
		}

		return false;
	}

	public String getDataType() {
		return MapUtil.getString(_properties, "dataType");
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public DDMFormFieldOptions getDDMFormFieldOptions() {
		return (DDMFormFieldOptions)_properties.get("options");
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public List<DDMFormFieldRule> getDDMFormFieldRules() {
		return _ddmFormFieldRules;
	}

	public DDMFormFieldValidation getDDMFormFieldValidation() {
		Object value = _properties.get("validation");

		if (value instanceof DDMFormFieldValidation) {
			return (DDMFormFieldValidation)value;
		}

		return null;
	}

	public String getFieldNamespace() {
		return MapUtil.getString(_properties, "fieldNamespace");
	}

	public String getIndexType() {
		return MapUtil.getString(_properties, "indexType");
	}

	public LocalizedValue getLabel() {
		return (LocalizedValue)_properties.get("label");
	}

	public String getName() {
		return MapUtil.getString(_properties, "name");
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

	public Map<String, Object> getProperties() {
		return _properties;
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
		return MapUtil.getString(_properties, "type");
	}

	public String getVisibilityExpression() {
		return MapUtil.getString(_properties, "visibilityExpression");
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _properties);

		return HashUtil.hash(hash, _nestedDDMFormFields);
	}

	public boolean isLocalizable() {
		return MapUtil.getBoolean(_properties, "localizable");
	}

	public boolean isMultiple() {
		return MapUtil.getBoolean(_properties, "multiple");
	}

	public boolean isReadOnly() {
		return MapUtil.getBoolean(_properties, "readOnly");
	}

	public boolean isRepeatable() {
		return MapUtil.getBoolean(_properties, "repeatable");
	}

	public boolean isRequired() {
		return MapUtil.getBoolean(_properties, "required");
	}

	public boolean isShowLabel() {
		return MapUtil.getBoolean(_properties, "showLabel", true);
	}

	public boolean isTransient() {
		if (Validator.isNull(getDataType())) {
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setDDMFormFieldRules(List<DDMFormFieldRule> ddmFormFieldRules) {
		_ddmFormFieldRules = ddmFormFieldRules;
	}

	public void setDDMFormFieldValidation(
		DDMFormFieldValidation ddmFormFieldValidation) {

		_properties.put("validation", ddmFormFieldValidation);
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

	private DDMForm _ddmForm;
	private List<DDMFormFieldRule> _ddmFormFieldRules = new ArrayList<>();
	private List<DDMFormField> _nestedDDMFormFields = new ArrayList<>();
	private final Map<String, Object> _properties;

}