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

import com.liferay.data.engine.spi.field.type.SPIDataDefinitionField;
import com.liferay.data.engine.spi.rule.function.DataRuleFunction;
import com.liferay.data.engine.spi.rule.function.DataRuleFunctionResult;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Jeyvison Nascimento
 */
public class IntegerLiteralDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		SPIDataDefinitionField dataDefinitionField,
		Map<String, Object> dataDefinitionRuleParameters, Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			DataRuleFunctionResult.of(
				dataDefinitionField.getName(),
				"value-must-be-an-integer-value");

		if (value == null) {
			return dataRuleFunctionResult;
		}

		dataRuleFunctionResult.setValid(
			!Objects.equals(
				GetterUtil.getInteger(value.toString(), Integer.MIN_VALUE),
				Integer.MIN_VALUE));

		return dataRuleFunctionResult;
	}

}