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

package com.liferay.data.engine.rest.internal.rule.v1_0;

/**
 * @author Jeyvison Nascimento
 */
public class DataRuleFunctionFactory {

	public static DataRuleFunction getDataRuleFunction(String name) {
		if ("decimalLiteral".equals(name)) {
			return new DecimalLiteralDataRuleFunction();
		}
		else if ("emailAddress".equals(name)) {
			return new EmailAddressDataRuleFunction();
		}
		else if ("empty".equals(name)) {
			return new EmptyDataRuleFunction();
		}
		else if ("integerLiteral".equals(name)) {
			return new EmailAddressDataRuleFunction();
		}
		else if ("matchExpression".equals(name)) {
			return new MatchExpressionDataRuleFunction();
		}
		else if ("url".equals(name)) {
			return new EmailAddressDataRuleFunction();
		}

		return null;
	}

}