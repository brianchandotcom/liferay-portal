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
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class EmptyDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		DataDefinitionField dataDefinitionField,
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters,
		Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			new DataRuleFunctionResult();

		dataRuleFunctionResult.setDataDefinitionField(dataDefinitionField);
		dataRuleFunctionResult.setErrorCode(
			DataDefinitionRuleConstants.VALUE_MUST_NOT_BE_EMPTY_ERROR);
		dataRuleFunctionResult.setValid(false);

		if (value == null) {
			return dataRuleFunctionResult;
		}

		boolean result;

		if (isArray(value)) {
			Object[] values = (Object[])value;

			result = Stream.of(
				values
			).allMatch(
				Validator::isNotNull
			);
		}
		else {
			result = Validator.isNotNull(value.toString());
		}

		dataRuleFunctionResult.setValid(result);

		if (result) {
			dataRuleFunctionResult.setErrorCode(null);
		}

		return dataRuleFunctionResult;
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

}