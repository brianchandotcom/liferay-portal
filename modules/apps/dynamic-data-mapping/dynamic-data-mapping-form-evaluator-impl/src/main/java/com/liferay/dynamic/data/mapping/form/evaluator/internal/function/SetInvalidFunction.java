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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserverAware;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;

/**
 * @author Leonardo Barros
 */
public class SetInvalidFunction
	implements DDMExpressionFunction.Function2<String, String, Boolean>,
			   DDMExpressionObserverAware {

	public static final String NAME = "setInvalid";

	@Override
	public Boolean apply(String field, String errorMessage) {
		if (_ddmExpressionObserver == null) {
			return false;
		}

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				field, "valid", false);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			builder.withParameter(
				"errorMessage", errorMessage
			).build();

		_ddmExpressionObserver.updateFieldProperty(updateFieldPropertyRequest);

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionObserver(
		DDMExpressionObserver ddmExpressionObserver) {

		_ddmExpressionObserver = ddmExpressionObserver;
	}

	private DDMExpressionObserver _ddmExpressionObserver;

}