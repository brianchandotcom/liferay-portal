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
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ObjectValidationRuleEngine.class)
public class DDMObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public boolean evaluate(Map<String, Object> inputObjects, String script) {
		try {
			return _evaluate(inputObjects, script);
		}
		catch (Exception exception) {
			_log.error(exception);

			return false;
		}
	}

	@Override
	public String getName() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_DDM;
	}

	@Override
	public boolean isValidScript(String script) {
		try {
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					script
				).build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException);
			}

			return false;
		}

		return true;
	}

	private boolean _evaluate(Map<String, Object> inputObjects, String script)
		throws Exception {

		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					script
				).build());

		ddmExpression.setVariables(inputObjects);

		return ddmExpression.evaluate();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMObjectValidationRuleEngineImpl.class);

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

}