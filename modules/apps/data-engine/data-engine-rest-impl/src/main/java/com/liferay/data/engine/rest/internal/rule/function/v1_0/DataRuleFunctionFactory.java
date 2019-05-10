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

import com.liferay.data.engine.spi.rule.function.DataRuleFunction;

import java.util.Objects;

/**
 * @author Jeyvison Nascimento
 */
public class DataRuleFunctionFactory {

	public static DataRuleFunction getDataRuleFunction(String name) {
		if (Objects.equals(name, "decimalLiteral")) {
			return new DecimalLiteralDataRuleFunction();
		}
		else if (Objects.equals(name, "emailAddress")) {
			return new EmailAddressDataRuleFunction();
		}
		else if (Objects.equals(name, "empty")) {
			return new EmptyDataRuleFunction();
		}
		else if (Objects.equals(name, "integerLiteral")) {
			return new EmailAddressDataRuleFunction();
		}
		else if (Objects.equals(name, "matchExpression")) {
			return new MatchExpressionDataRuleFunction();
		}
		else if (Objects.equals(name, "url")) {
			return new EmailAddressDataRuleFunction();
		}

		return null;
	}

}