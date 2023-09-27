/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormFieldValue implements Serializable {

	public void addNestedDDMFormFieldValue(
		DDMFormFieldValue nestedDDMFormFieldValue) {

		nestedDDMFormFieldValue.setDDMFormValues(_ddmFormValues);

		_nestedDDMFormFieldValues.add(nestedDDMFormFieldValue);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormFieldValue)) {
			return false;
		}

		DDMFormFieldValue ddmFormFieldValue = (DDMFormFieldValue)object;

		if (Objects.equals(_instanceId, ddmFormFieldValue._instanceId) &&
			Objects.equals(_name, ddmFormFieldValue._name) &&
			Objects.equals(_value, ddmFormFieldValue._value) &&
			Objects.equals(
				_nestedDDMFormFieldValues,
				ddmFormFieldValue._nestedDDMFormFieldValues)) {

			return true;
		}

		return false;
	}

	public DDMFormField getDDMFormField() {
		DDMForm ddmForm = _ddmFormValues.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		return ddmFormFieldsMap.get(_name);
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public String getFieldReference() {
		if (Validator.isNotNull(_fieldReference)) {
			return _fieldReference;
		}

		DDMFormField ddmFormField = getDDMFormField();

		if (ddmFormField == null) {
			return _name;
		}

		return ddmFormField.getFieldReference();
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _name;
	}

	public List<DDMFormFieldValue> getNestedDDMFormFieldValues() {
		return _nestedDDMFormFieldValues;
	}

	public Map<String, List<DDMFormFieldValue>>
		getNestedDDMFormFieldValuesMap() {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldValuesMap =
			new HashMap<>();

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesMap.get(
					nestedDDMFormFieldValue.getName());

			if (nestedDDMFormFieldValues == null) {
				nestedDDMFormFieldValues = new ArrayList<>();

				nestedDDMFormFieldValuesMap.put(
					nestedDDMFormFieldValue.getName(),
					nestedDDMFormFieldValues);
			}

			nestedDDMFormFieldValues.add(nestedDDMFormFieldValue);
		}

		return nestedDDMFormFieldValuesMap;
	}

	public String getType() {
		DDMFormField ddmFormField = getDDMFormField();

		return ddmFormField.getType();
	}

	public Value getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _instanceId);

		hash = HashUtil.hash(hash, _name);
		hash = HashUtil.hash(hash, _nestedDDMFormFieldValues);

		return HashUtil.hash(hash, _value);
	}

	public void populateNestedDDMFormFieldValuesReferencesMap(
		Map<String, List<DDMFormFieldValue>>
			nestedDDMFormFieldValuesReferencesMap) {

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			List<DDMFormFieldValue> nestedDDMFormFieldValues =
				nestedDDMFormFieldValuesReferencesMap.get(
					nestedDDMFormFieldValue.getFieldReference());

			if (nestedDDMFormFieldValues == null) {
				nestedDDMFormFieldValues = new ArrayList<>();

				nestedDDMFormFieldValuesReferencesMap.put(
					nestedDDMFormFieldValue.getFieldReference(),
					nestedDDMFormFieldValues);
			}

			nestedDDMFormFieldValues.add(nestedDDMFormFieldValue);

			nestedDDMFormFieldValue.
				populateNestedDDMFormFieldValuesReferencesMap(
					nestedDDMFormFieldValuesReferencesMap);
		}
	}

	public void setDDMFormValues(DDMFormValues ddmFormValues) {
		for (DDMFormFieldValue nestedDDMFormFieldValue :
				_nestedDDMFormFieldValues) {

			nestedDDMFormFieldValue.setDDMFormValues(ddmFormValues);
		}

		_ddmFormValues = ddmFormValues;
	}

	public void setFieldReference(String fieldReference) {
		_fieldReference = fieldReference;
	}

	public void setInstanceId(String instanceId) {
		_instanceId = instanceId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNestedDDMFormFields(
		List<DDMFormFieldValue> nestedDDMFormFieldValues) {

		_nestedDDMFormFieldValues = nestedDDMFormFieldValues;
	}

	public void setValue(Value value) {
		_value = value;
	}

	private DDMFormValues _ddmFormValues;
	private String _fieldReference;
	private String _instanceId = StringUtil.randomString();
	private String _name;
	private List<DDMFormFieldValue> _nestedDDMFormFieldValues =
		new ArrayList<>();
	private Value _value;

}