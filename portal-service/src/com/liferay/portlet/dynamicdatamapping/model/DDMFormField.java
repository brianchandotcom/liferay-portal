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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

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

	public DDMFormField(DDMFormField ddmFormField) {
		_optionalProperties = new LinkedHashMap<>(
			ddmFormField._optionalProperties);
		_requiredProperties = new LinkedHashMap<>(
			ddmFormField._requiredProperties);

		if (ddmFormField.getDDMFormFieldOptions() != null) {
			setDDMFormFieldOptions(
				new DDMFormFieldOptions(ddmFormField.getDDMFormFieldOptions()));
		}

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
		_optionalProperties = new LinkedHashMap<>();
		_requiredProperties = new LinkedHashMap<>();

		setName(name);
		setType(type);

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
		return MapUtil.getString(_requiredProperties, "dataType");
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public DDMFormFieldOptions getDDMFormFieldOptions() {
		return (DDMFormFieldOptions)_optionalProperties.get("options");
	}

	public String getIndexType() {
		return MapUtil.getString(_requiredProperties, "indexType");
	}

	public LocalizedValue getLabel() {
		return (LocalizedValue)_requiredProperties.get("label");
	}

	public String getName() {
		return MapUtil.getString(_requiredProperties, "name");
	}

	public String getNamespace() {
		return MapUtil.getString(_requiredProperties, "namespace");
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
		return (LocalizedValue)_requiredProperties.get("predefinedValue");
	}

	public Object getProperty(String name) {
		if (_requiredProperties.containsKey(name)) {
			return _requiredProperties.get(name);
		}

		return _optionalProperties.get(name);
	}

	public LocalizedValue getStyle() {
		return (LocalizedValue)_requiredProperties.get("style");
	}

	public LocalizedValue getTip() {
		return (LocalizedValue)_requiredProperties.get("tip");
	}

	public String getType() {
		return MapUtil.getString(_requiredProperties, "type");
	}

	public boolean isLocalizable() {
		return MapUtil.getBoolean(_requiredProperties, "localizable", true);
	}

	public boolean isMultiple() {
		return MapUtil.getBoolean(_optionalProperties, "localizable");
	}

	public boolean isReadOnly() {
		return MapUtil.getBoolean(_requiredProperties, "readOnly");
	}

	public boolean isRepeatable() {
		return MapUtil.getBoolean(_requiredProperties, "repeatable");
	}

	public boolean isRequired() {
		return MapUtil.getBoolean(_requiredProperties, "required");
	}

	public boolean isShowLabel() {
		return MapUtil.getBoolean(_requiredProperties, "showLabel", true);
	}

	public boolean isTransient() {
		if (Validator.isNull(getDataType())) {
			return true;
		}

		return false;
	}

	public void setDataType(String dataType) {
		_requiredProperties.put("dataType", dataType);
	}

	public void setDDMForm(DDMForm ddmForm) {
		for (DDMFormField nestedDDMFormField : _nestedDDMFormFields) {
			nestedDDMFormField.setDDMForm(ddmForm);
		}

		_ddmForm = ddmForm;
	}

	public void setDDMFormFieldOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		_optionalProperties.put("options", ddmFormFieldOptions);
	}

	public void setIndexType(String indexType) {
		_requiredProperties.put("indexType", indexType);
	}

	public void setLabel(LocalizedValue label) {
		_requiredProperties.put("label", label);
	}

	public void setLocalizable(boolean localizable) {
		_requiredProperties.put("localizable", localizable);
	}

	public void setMultiple(boolean multiple) {
		_optionalProperties.put("multiple", multiple);
	}

	public void setName(String name) {
		_requiredProperties.put("name", name);
	}

	public void setNamespace(String namespace) {
		_requiredProperties.put("namespace", namespace);
	}

	public void setNestedDDMFormFields(List<DDMFormField> nestedDDMFormFields) {
		_nestedDDMFormFields = nestedDDMFormFields;
	}

	public void setOptionalProperty(String name, Object value) {
		_optionalProperties.put(name, value);
	}

	public void setPredefinedValue(LocalizedValue predefinedValue) {
		_requiredProperties.put("predefinedValue", predefinedValue);
	}

	public void setReadOnly(boolean readOnly) {
		_requiredProperties.put("readOnly", readOnly);
	}

	public void setRepeatable(boolean repeatable) {
		_requiredProperties.put("repeatable", repeatable);
	}

	public void setRequired(boolean required) {
		_requiredProperties.put("required", required);
	}

	public void setRequiredProperty(String name, Object value) {
		_requiredProperties.put(name, value);
	}

	public void setShowLabel(boolean showLabel) {
		_requiredProperties.put("showLabel", showLabel);
	}

	public void setStyle(LocalizedValue style) {
		_requiredProperties.put("style", style);
	}

	public void setTip(LocalizedValue tip) {
		_requiredProperties.put("tip", tip);
	}

	public void setType(String type) {
		_requiredProperties.put("type", type);
	}

	private DDMForm _ddmForm;
	private List<DDMFormField> _nestedDDMFormFields = new ArrayList<>();
	private final Map<String, Object> _optionalProperties;
	private final Map<String, Object> _requiredProperties;

}