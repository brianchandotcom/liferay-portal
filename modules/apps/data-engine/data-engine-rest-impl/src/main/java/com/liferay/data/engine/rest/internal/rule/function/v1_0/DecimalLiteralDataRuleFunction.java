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

package com.liferay.data.engine.rest.internal.rule.function.v1_0;

import com.liferay.data.engine.rest.internal.constants.DataRuleFunctionConstants;
import com.liferay.data.engine.rule.function.DataRuleFunction;
import com.liferay.data.engine.rule.function.DataRuleFunctionResult;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.math.BigDecimal;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"data.engine.rule.function.name=decimalLiteral",
		"data.engine.rule.function.type=" + DataRuleFunctionConstants.VALIDATION_RULE_TYPE
	},
	service = DataRuleFunction.class
)
public class DecimalLiteralDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		Map<String, Object> dataDefinitionRuleParameters,
		SPIDataDefinitionField spiDataDefinitionField, Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionResult.of(
				spiDataDefinitionField, "value-must-be-a-decimal-value");

		if (value == null) {
			return dataRuleFunctionResult;
		}

		try {
			new BigDecimal(value.toString());

			dataRuleFunctionResult.setValid(true);
		}
		catch (NumberFormatException nfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nfe, nfe);
			}
		}

		return dataRuleFunctionResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DecimalLiteralDataRuleFunction.class);

}