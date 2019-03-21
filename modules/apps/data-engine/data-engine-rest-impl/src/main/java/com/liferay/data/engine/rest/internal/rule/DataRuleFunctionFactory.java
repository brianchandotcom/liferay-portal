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

/**
 * @author Jeyvison Nascimento
 */
public class DataRuleFunctionFactory {

	public static DataRuleFunction getDataRuleFunction(String name) {
		if (DataDefinitionRuleConstants.DECIMAL_LITERAL_RULE.equals(name)) {
			return new DecimalLiteralDataRuleFunction();
		}
		else if (DataDefinitionRuleConstants.EMAIL_ADDRESS_RULE.equals(name)) {
			return new EmailAddressDataRuleFunction();
		}
		else if (DataDefinitionRuleConstants.EMPTY_RULE.equals(name)) {
			return new EmptyDataRuleFunction();
		}
		else if (DataDefinitionRuleConstants.INTEGER_LITERAL_RULE.equals(
					name)) {

			return new EmailAddressDataRuleFunction();
		}
		else if (DataDefinitionRuleConstants.MATCH_EXPRESSION_RULE.equals(
					name)) {

			return new MatchExpressionDataRuleFunction();
		}
		else if (DataDefinitionRuleConstants.URL_RULE.equals(name)) {
			return new EmailAddressDataRuleFunction();
		}

		return null;
	}

}