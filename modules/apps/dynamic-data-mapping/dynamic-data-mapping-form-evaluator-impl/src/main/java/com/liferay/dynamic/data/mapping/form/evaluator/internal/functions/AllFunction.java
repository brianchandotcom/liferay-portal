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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "ddm.form.evaluator.function.name=all",
	service = DDMExpressionFunction.class
)
public class AllFunction
	implements DDMExpressionFunction.Function2<String, Object, Boolean> {

	@Override
	public Boolean apply(String expression, Object parameter) {
		if (!expression.contains("#value#")) {
			return false;
		}

		Object[] values = null;

		if (isArray(parameter)) {
			values = (Object[])parameter;

			if (values.length == 0) {
				return false;
			}
		}
		else {
			values = new Object[] {parameter};
		}

		return Stream.of(
			values
		).allMatch(
			value -> accept(expression, value)
		);
	}

	protected boolean accept(String expression, Object value) {
		expression = expression.replace("#value#", String.valueOf(value));

		try {
			CreateExpressionRequest createExpressionRequest =
				CreateExpressionRequest.Builder.newBuilder(
					expression
				).build();

			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createExpression(createExpressionRequest);

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee, ddmee);
			}
		}

		return false;
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	private static final Log _log = LogFactoryUtil.getLog(AllFunction.class);

}