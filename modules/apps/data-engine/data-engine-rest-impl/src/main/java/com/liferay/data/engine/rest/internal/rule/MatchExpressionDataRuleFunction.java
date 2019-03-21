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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionRuleParameterUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jeyvison Nascimento
 */
public class MatchExpressionDataRuleFunction implements DataRuleFunction {

	@Override
	public DataRuleFunctionResult validate(
		DataDefinitionField dataDefinitionField,
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters,
		Object value) {

		DataRuleFunctionResult dataRuleFunctionResult =
			new DataRuleFunctionResult();

		dataRuleFunctionResult.setDataDefinitionField(dataDefinitionField);
		dataRuleFunctionResult.setErrorCode(
			DataDefinitionRuleConstants.VALUE_MUST_MATCH_EXPRESSION_ERROR);
		dataRuleFunctionResult.setValid(false);

		if (value == null) {
			return dataRuleFunctionResult;
		}

		boolean result = false;

		try {
			Pattern pattern = Pattern.compile(
				MapUtil.getString(
					DataDefinitionRuleParameterUtil.
						toDataDefinitionRuleParametersMap(
							dataDefinitionRuleParameters),
					"expression"));

			Matcher matcher = pattern.matcher(value.toString());

			result = matcher.matches();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		dataRuleFunctionResult.setValid(result);

		if (result) {
			dataRuleFunctionResult.setErrorCode(null);
		}

		return dataRuleFunctionResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MatchExpressionDataRuleFunction.class);

}