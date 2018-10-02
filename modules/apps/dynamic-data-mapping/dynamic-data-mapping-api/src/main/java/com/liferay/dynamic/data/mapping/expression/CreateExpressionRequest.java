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
	private DDMExpressionFieldAccessor _ddmExpressionFieldAccessor;
	private DDMExpressionObserver _ddmExpressionObserver;
	private DDMExpressionParameterAccessor _ddmExpressionParameterAccessor;
	private String _expression;

}