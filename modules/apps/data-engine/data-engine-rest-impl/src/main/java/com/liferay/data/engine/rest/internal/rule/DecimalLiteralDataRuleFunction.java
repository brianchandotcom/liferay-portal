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

package com.liferay.data.engine.rest.internal.rule;

import com.liferay.data.engine.constants.DataDefinitionRuleConstants;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRuleParameter;

import java.math.BigDecimal;

/**
 * @author Jeyvison Nascimento
 */
public class DecimalLiteralDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		DataDefinitionField dataDefinitionField,
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters,
		Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			new DataRuleFunctionResult();

		dataRuleFunctionResult.setDataDefinitionField(dataDefinitionField);
		dataRuleFunctionResult.setErrorCode(
			DataDefinitionRuleConstants.VALUE_MUST_BE_DECIMAL_ERROR);
		dataRuleFunctionResult.setValid(false);

		if (value == null) {
			return dataRuleFunctionResult;
		}

		boolean result;

		try {
			new BigDecimal(value.toString());

			result = true;
		}
		catch (NumberFormatException nfe) {
			result = false;
		}

		dataRuleFunctionResult.setValid(true);

		if (result) {
			dataRuleFunctionResult.setErrorCode(null);
		}

		return dataRuleFunctionResult;
	}

}