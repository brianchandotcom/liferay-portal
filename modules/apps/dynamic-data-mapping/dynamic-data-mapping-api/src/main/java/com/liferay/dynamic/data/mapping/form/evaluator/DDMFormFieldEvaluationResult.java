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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author     Marcellus Tavares
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class DDMFormFieldEvaluationResult {

	public DDMFormFieldEvaluationResult(String name, String instanceId) {
		_name = name;
		_instanceId = instanceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormFieldEvaluationResult)) {
			return false;
		}

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)obj;

		if (Objects.equals(
				_instanceId, ddmFormFieldEvaluationResult._instanceId) &&
			Objects.equals(_name, ddmFormFieldEvaluationResult._name)) {

			return true;
		}

		return false;
	}

	public String getErrorMessage() {
		return MapUtil.getString(_properties, "errorMessage");
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _name;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@JSON(name = "nestedFields")
	public List<DDMFormFieldEvaluationResult>
		getNestedDDMFormFieldEvaluationResults() {

		return _nestedDDMFormFieldEvaluationResults;
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public <T> T getValue() {
		return (T)_properties.get("value");
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _instanceId);
	}

	public boolean isReadOnly() {
		return MapUtil.getBoolean(_properties, "readOnly");
	}

	public boolean isRequired() {
		return MapUtil.getBoolean(_properties, "required");
	}

	public boolean isValid() {
		return MapUtil.getBoolean(_properties, "valid", true);
	}

	public boolean isVisible() {
		return MapUtil.getBoolean(_properties, "visible", true);
	}

	public void setErrorMessage(String errorMessage) {
		_properties.put("errorMessage", errorMessage);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setNestedDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult>
			nestedDDMFormFieldEvaluationResults) {

		_nestedDDMFormFieldEvaluationResults =
			nestedDDMFormFieldEvaluationResults;
	}

	public void setProperty(String name, Object value) {
		_properties.put(name, value);
	}

	public void setReadOnly(boolean readOnly) {
		_properties.put("readOnly", readOnly);
	}

	public void setRequired(boolean required) {
		_properties.put("required", required);
	}

	public void setValid(boolean valid) {
		_properties.put("valid", valid);
	}

	public void setValue(Object value) {
		_properties.put("value", value);
	}

	public void setVisible(boolean visible) {
		_properties.put("visible", visible);
	}

	private final String _instanceId;
	private final String _name;
	private List<DDMFormFieldEvaluationResult>
		_nestedDDMFormFieldEvaluationResults = new ArrayList<>();
	private final Map<String, Object> _properties = new HashMap<>();

}