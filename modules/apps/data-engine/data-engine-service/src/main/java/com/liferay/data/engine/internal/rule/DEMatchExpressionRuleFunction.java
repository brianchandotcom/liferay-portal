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

package com.liferay.data.engine.internal.rule;

import com.liferay.data.engine.constants.DEDataDefinitionRuleConstants;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunction;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunctionApplyRequest;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunctionApplyResponse;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * It validates if a value matches a regular expression.
 *
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"de.data.definition.rule.function.name=" + DEDataDefinitionRuleConstants.MATCH_EXPRESSION_RULE,
		"de.data.definition.rule.function.type=" + DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE
	},
	service = DEDataDefinitionRuleFunction.class
)
public class DEMatchExpressionRuleFunction
	implements DEDataDefinitionRuleFunction {

	@Override
	/**
	 * @see DEDataDefinitionRuleFunction
	 */
	public DEDataDefinitionRuleFunctionApplyResponse apply(
		DEDataDefinitionRuleFunctionApplyRequest
			deDataDefinitionRuleFunctionApplyRequest) {

		DEDataDefinitionRuleFunctionApplyResponse
			deDataDefinitionRuleFunctionApplyResponse =
				new DEDataDefinitionRuleFunctionApplyResponse();

		DEDataDefinitionField deDataDefinitionField =
			deDataDefinitionRuleFunctionApplyRequest.getDEDataDefinitionField();

		deDataDefinitionRuleFunctionApplyResponse.setDEDataDefinitionField(
			deDataDefinitionField);

		deDataDefinitionRuleFunctionApplyResponse.setValid(false);
		deDataDefinitionRuleFunctionApplyResponse.setErrorCode(
			DEDataDefinitionRuleConstants.VALUE_MUST_MATCH_EXPRESSION_ERROR);

		Object value = deDataDefinitionRuleFunctionApplyRequest.getValue();

		if (value == null) {
			return deDataDefinitionRuleFunctionApplyResponse;
		}

		boolean result = false;

		Map<String, Object> parameters =
			deDataDefinitionRuleFunctionApplyRequest.getParameters();

		try {
			Pattern pattern = Pattern.compile(
				MapUtil.getString(parameters, "expression"));

			Matcher matcher = pattern.matcher(value.toString());

			result = matcher.matches();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		deDataDefinitionRuleFunctionApplyResponse.setValid(result);

		if (result) {
			deDataDefinitionRuleFunctionApplyResponse.setErrorCode(null);
		}

		return deDataDefinitionRuleFunctionApplyResponse;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DEMatchExpressionRuleFunction.class);

}