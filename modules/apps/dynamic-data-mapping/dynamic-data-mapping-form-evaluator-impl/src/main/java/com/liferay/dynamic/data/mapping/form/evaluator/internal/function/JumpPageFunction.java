/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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