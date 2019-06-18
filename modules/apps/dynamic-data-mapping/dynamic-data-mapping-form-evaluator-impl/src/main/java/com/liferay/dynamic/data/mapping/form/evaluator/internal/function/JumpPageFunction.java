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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandler;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionActionHandlerAware;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.expression.ExecuteActionRequest;

/**
 * @author Inácio Nery
 * @author Leonardo Barros
 */
public class JumpPageFunction
	implements DDMExpressionActionHandlerAware,
			   DDMExpressionFunction.Function2<Number, Number, Boolean> {

	public static final String NAME = "jumpPage";

	@Override
	public Boolean apply(Number fromPage, Number toPage) {
		if (_ddmExpressionActionHandler == null) {
			return false;
		}

		ExecuteActionRequest.Builder builder =
			ExecuteActionRequest.Builder.newBuilder("jumpPage");

		ExecuteActionRequest executeActionRequest = builder.withParameter(
			"from", fromPage.intValue()
		).withParameter(
			"to", toPage.intValue()
		).build();

		_ddmExpressionActionHandler.executeAction(executeActionRequest);

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setDDMExpressionActionHandler(
		DDMExpressionActionHandler ddmExpressionActionHandler) {

		_ddmExpressionActionHandler = ddmExpressionActionHandler;
	}

	private DDMExpressionActionHandler _ddmExpressionActionHandler;

}