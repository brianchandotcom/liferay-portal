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
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Stream;

/**
 * It validates if a value is a valid email address.
 *
 * @author Leonardo Barros
 */
public class EmailAddressDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		DataDefinitionField dataDefinitionField,
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters,
		Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			new DataRuleFunctionResult();

		dataRuleFunctionResult.setDataDefinitionField(dataDefinitionField);
		dataRuleFunctionResult.setErrorCode(
			DataDefinitionRuleConstants.INVALID_EMAIL_ADDRESS_ERROR);
		dataRuleFunctionResult.setValid(false);

		if (value == null) {
			return dataRuleFunctionResult;
		}

		boolean result = Stream.of(
			StringUtil.split(value.toString(), CharPool.COMMA)
		).map(
			String::trim
		).allMatch(
			Validator::isEmailAddress
		);

		dataRuleFunctionResult.setValid(result);

		if (result) {
			dataRuleFunctionResult.setErrorCode(null);
		}

		return dataRuleFunctionResult;
	}

}