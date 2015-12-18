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
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValidation implements Serializable {

	public DDMFormFieldValidation() {
	}

	public DDMFormFieldValidation(
		DDMFormFieldValidation ddmFormFieldValidation) {

		_expression = ddmFormFieldValidation._expression;
		_errorMessage = ddmFormFieldValidation._errorMessage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormFieldValidation)) {
			return false;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			(DDMFormFieldValidation)obj;

		if (Validator.equals(
				_errorMessage, ddmFormFieldValidation._errorMessage) &&
			Validator.equals(_expression, ddmFormFieldValidation._expression)) {

			return true;
		}

		return false;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getExpression() {
		return _expression;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _errorMessage);

		return HashUtil.hash(hash, _expression);
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setExpression(String expression) {
		_expression = expression;
	}

	private String _errorMessage;
	private String _expression;

}