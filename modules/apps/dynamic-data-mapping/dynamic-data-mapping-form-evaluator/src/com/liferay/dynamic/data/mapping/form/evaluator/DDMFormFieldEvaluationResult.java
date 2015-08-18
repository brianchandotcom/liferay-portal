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

	public String getValidationMessage() {
		return _validationMessage;
	}

	public boolean isValid() {
		return _valid;
	}

	public boolean isVisible() {
		return _visible;
	}

	public void setNestedDDMFormFieldEvaluationResults(
		List<DDMFormFieldEvaluationResult>
			nestedDDMFormFieldEvaluationResults) {

		_nestedDDMFormFieldEvaluationResults =
			nestedDDMFormFieldEvaluationResults;
	}

	public void setValid(boolean valid) {
		_valid = valid;
	}

	public void setValidationMessage(String validationMessage) {
		_validationMessage = validationMessage;
	}

	public void setVisible(boolean visible) {
		_visible = visible;
	}

	private final String _instanceId;
	private final String _name;
	private List<DDMFormFieldEvaluationResult>
		_nestedDDMFormFieldEvaluationResults = new ArrayList<>();
	private boolean _valid;
	private String _validationMessage = StringPool.BLANK;
	private boolean _visible;

}