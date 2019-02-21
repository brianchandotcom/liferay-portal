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

import org.apache.commons.lang.math.NumberUtils;

import org.osgi.service.component.annotations.Component;

/**
 * It validates if a value is an integer number.
 *
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"de.data.definition.rule.function.name=" + DEDataDefinitionRuleConstants.INTEGER_LITERAL_RULE,
		"de.data.definition.rule.function.type=" + DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE
	},
	service = DEDataDefinitionRuleFunction.class
)
public class DEIntegerLiteralRuleFunction
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
			DEDataDefinitionRuleConstants.VALUE_MUST_BE_INTEGER_ERROR);

		Object value = deDataDefinitionRuleFunctionApplyRequest.getValue();

		if (value == null) {
			return deDataDefinitionRuleFunctionApplyResponse;
		}

		Integer valueInteger = NumberUtils.toInt(
			value.toString(), Integer.MIN_VALUE);

		boolean result = false;

		if (valueInteger != Integer.MIN_VALUE) {
			result = true;
		}

		deDataDefinitionRuleFunctionApplyResponse.setValid(result);

		if (result) {
			deDataDefinitionRuleFunctionApplyResponse.setErrorCode(null);
		}

		return deDataDefinitionRuleFunctionApplyResponse;
	}

}