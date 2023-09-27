/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

/**
 * @author Leonardo Barros
 */
public final class CreateExpressionRequest {

	public DDMExpressionActionHandler getDDMExpressionActionHandler() {
		return _ddmExpressionActionHandler;
	}

	public DDMExpressionFieldAccessor getDDMExpressionFieldAccessor() {
		return _ddmExpressionFieldAccessor;
	}

	public DDMExpressionObserver getDDMExpressionObserver() {
		return _ddmExpressionObserver;
	}

	public DDMExpressionParameterAccessor getDDMExpressionParameterAccessor() {
		return _ddmExpressionParameterAccessor;
	}

	public String getExpression() {
		return _expression;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public boolean isDDMExpressionDateValidation() {
		return _ddmExpressionDateValidation;
	}

	public static class Builder {

		public static Builder newBuilder(String expression) {
			return new Builder(expression);
		}

		public CreateExpressionRequest build() {
			return _createExpressionRequest;
		}

		public Builder withDDMExpressionActionHandler(
			DDMExpressionActionHandler ddmExpressionActionHandler) {

			_createExpressionRequest._ddmExpressionActionHandler =
				ddmExpressionActionHandler;

			return this;
		}

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		@Deprecated
		public Builder withDDMExpressionDateValidation(
			boolean ddmExpressionDateValidation) {

			_createExpressionRequest._ddmExpressionDateValidation =
				ddmExpressionDateValidation;

			return this;
		}

		public Builder withDDMExpressionFieldAccessor(
			DDMExpressionFieldAccessor ddmExpressionFieldAccessor) {

			_createExpressionRequest._ddmExpressionFieldAccessor =
				ddmExpressionFieldAccessor;

			return this;
		}

		public Builder withDDMExpressionObserver(
			DDMExpressionObserver ddmExpressionObserver) {

			_createExpressionRequest._ddmExpressionObserver =
				ddmExpressionObserver;

			return this;
		}

		public Builder withDDMExpressionParameterAccessor(
			DDMExpressionParameterAccessor ddmExpressionParameterAccessor) {

			_createExpressionRequest._ddmExpressionParameterAccessor =
				ddmExpressionParameterAccessor;

			return this;
		}

		private Builder(String expression) {
			_createExpressionRequest._expression = expression;
		}

		private final CreateExpressionRequest _createExpressionRequest =
			new CreateExpressionRequest();

	}

	private CreateExpressionRequest() {
	}

	private DDMExpressionActionHandler _ddmExpressionActionHandler;
	private boolean _ddmExpressionDateValidation;
	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionObserver _ddmExpressionObserver;
	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;
	private String _expression;

}