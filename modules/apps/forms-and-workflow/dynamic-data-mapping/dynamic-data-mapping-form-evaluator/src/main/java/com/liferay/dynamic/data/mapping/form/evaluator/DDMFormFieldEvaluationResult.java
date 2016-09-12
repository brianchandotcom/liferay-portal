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

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldEvaluationResult {

	public DDMFormFieldEvaluationResult(String name, String instanceId) {
		_name = name;
		_instanceId = instanceId;
	}

	public void addOption(String key, String value) {
		_options.add(new KeyValuePair(key, value));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			(DDMFormFieldEvaluationResult)obj;

		if (_instanceId == null) {
			if (ddmFormFieldEvaluationResult._instanceId != null) {
				return false;
			}
		}
		else if (!_instanceId.equals(
					ddmFormFieldEvaluationResult._instanceId)) {

			return false;
		}

		if (_name == null) {
			if (ddmFormFieldEvaluationResult._name != null) {
				return false;
			}
		}
		else if (!_name.equals(ddmFormFieldEvaluationResult._name)) {
			return false;
		}

		return true;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getName() {
		return _name;
	}

	@JSON(name = "nestedFields")
	public List<DDMFormFieldEvaluationResult>
		getNestedDDMFormFieldEvaluationResults() {

		return _nestedDDMFormFieldEvaluationResults;
	}

	public List<KeyValuePair> getOptions() {
		return _options;
	}

	public Object getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _instanceId);
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isValid() {
		return _valid;
	}

	public boolean isVisible() {
		return _visible;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setNestedDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult>
			nestedDDMFormFieldEvaluationResults) {

		_nestedDDMFormFieldEvaluationResults =
			nestedDDMFormFieldEvaluationResults;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setValid(boolean valid) {
		_valid = valid;
	}

	public void setValue(Object value) {
		_value = value;
	}

	public void setVisible(boolean visible) {
		_visible = visible;
	}

	private String _errorMessage = StringPool.BLANK;
	private final String _instanceId;
	private final String _name;
	private List<DDMFormFieldEvaluationResult>
		_nestedDDMFormFieldEvaluationResults = new ArrayList<>();
	private final List<KeyValuePair> _options = new ArrayList<>();
	private boolean _readOnly;
	private boolean _valid = true;
	private Object _value;
	private boolean _visible = true;

}