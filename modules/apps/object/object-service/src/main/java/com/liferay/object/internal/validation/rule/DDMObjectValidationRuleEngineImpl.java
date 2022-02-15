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

package com.liferay.object.internal.validation.rule;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ObjectValidationRuleEngine.class)
public class DDMObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	public boolean evaluate(String expression, Map<String, Object> variables) {
		try {
			return _evaluateExpression(expression, variables);
		}
		catch (Exception exception) {
			//TODO log exception
		}

		return false;
	}

	@Override
	public String getName() {
		return "ddm";
	}

	private <T> DDMExpression<T> _createExpression(String expression)
		throws DDMExpressionException {

		return _ddmExpressionFactory.createExpression(
			CreateExpressionRequest.Builder.newBuilder(
				expression
			).build());
	}

	private boolean _evaluateExpression(
			String expression, Map<String, Object> variables)
		throws DDMExpressionException {

		DDMExpression<Boolean> ddmExpression = _createExpression(expression);

		ddmExpression.setVariables(variables);

		return ddmExpression.evaluate();
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

}