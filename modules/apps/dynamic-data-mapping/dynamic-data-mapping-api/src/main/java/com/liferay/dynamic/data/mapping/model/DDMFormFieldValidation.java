/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormFieldValidation)) {
			return false;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			(DDMFormFieldValidation)object;

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