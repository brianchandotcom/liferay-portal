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

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValidation implements Serializable {

	public DDMFormFieldValidation() {
	}

	public DDMFormFieldValidation(
		DDMFormFieldValidation ddmFormFieldValidation) {

		_ddmFormFieldValidationExpression =
			ddmFormFieldValidation._ddmFormFieldValidationExpression;
		_errorMessageLocalizedValue =
			ddmFormFieldValidation._errorMessageLocalizedValue;
		_parameterLocalizedValue =
			ddmFormFieldValidation._parameterLocalizedValue;
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

		if (Objects.equals(
				_ddmFormFieldValidationExpression,
				ddmFormFieldValidation._ddmFormFieldValidationExpression) &&
			Objects.equals(
				_errorMessageLocalizedValue,
				ddmFormFieldValidation._errorMessageLocalizedValue) &&
			Objects.equals(
				_parameterLocalizedValue,
				ddmFormFieldValidation._parameterLocalizedValue)) {

			return true;
		}

		return false;
	}

	public DDMFormFieldValidationExpression
		getDDMFormFieldValidationExpression() {

		return _ddmFormFieldValidationExpression;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getErrorMessageLocalizedValue()}
	 */
	@Deprecated
	public String getErrorMessage() {
		return _errorMessageLocalizedValue.getString(
			_errorMessageLocalizedValue.getDefaultLocale());
	}

	public LocalizedValue getErrorMessageLocalizedValue() {
		return _errorMessageLocalizedValue;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getDDMFormFieldValidationExpression()}
	 */
	@Deprecated
	public String getExpression() {
		return _expression;
	}

	public LocalizedValue getParameterLocalizedValue() {
		return _parameterLocalizedValue;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddmFormFieldValidationExpression);

		hash = hash + HashUtil.hash(hash, _errorMessageLocalizedValue);

		return HashUtil.hash(hash, _parameterLocalizedValue);
	}

	public void setDDMFormFieldValidationExpression(
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression) {

		_ddmFormFieldValidationExpression = ddmFormFieldValidationExpression;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setErrorMessageLocalizedValue(LocalizedValue)}
	 */
	@Deprecated
	public void setErrorMessage(String errorMessage) {
		LocalizedValue errorMessageLocalizedValue = new LocalizedValue();

		errorMessageLocalizedValue.addString(
			errorMessageLocalizedValue.getDefaultLocale(), errorMessage);

		setErrorMessageLocalizedValue(errorMessageLocalizedValue);
	}

	public void setErrorMessageLocalizedValue(
		LocalizedValue errorMessageLocalizedValue) {

		_errorMessageLocalizedValue = errorMessageLocalizedValue;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setDDMFormFieldValidationExpression(
	 *             DDMFormFieldValidationExpression)}
	 */
	@Deprecated
	public void setExpression(String expression) {
		_expression = expression;
	}

	public void setParameterLocalizedValue(
		LocalizedValue parameterLocalizedValue) {

		_parameterLocalizedValue = parameterLocalizedValue;
	}

	private DDMFormFieldValidationExpression _ddmFormFieldValidationExpression =
		new DDMFormFieldValidationExpression();
	private LocalizedValue _errorMessageLocalizedValue;
	private String _expression;
	private LocalizedValue _parameterLocalizedValue;

}