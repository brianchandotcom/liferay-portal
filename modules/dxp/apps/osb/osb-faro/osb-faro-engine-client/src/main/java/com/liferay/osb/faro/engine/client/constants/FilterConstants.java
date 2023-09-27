/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

/**
 * @author Matthew Kong
 */
public class FilterConstants {

	public static final String COMPARISON_OPERATOR_EQUALS = " eq ";

	public static final String COMPARISON_OPERATOR_GREATER_THAN = " gt ";

	public static final String COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL =
		" ge ";

	public static final String COMPARISON_OPERATOR_LESS_THAN = " lt ";

	public static final String COMPARISON_OPERATOR_LESS_THAN_OR_EQUAL = " le ";

	public static final String COMPARISON_OPERATOR_NOT_EQUALS = " ne ";

	public static final String FIELD_NAME_CONTEXT_ACCOUNT =
		"organization/?/value";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL =
		"demographics/?/value";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT =
		"fields/?/value";

	public static final String LOGICAL_OPERATOR_AND = " and ";

	public static final String LOGICAL_OPERATOR_OR = " or ";

	public static final String STRING_FUNCTION_CONTAINS = "contains";

	public static final String STRING_FUNCTION_ENDS_WITH = "endswith";

	public static final String STRING_FUNCTION_NOT_CONTAINS = "not contains";

	public static final String STRING_FUNCTION_STARTS_WITH = "startswith";

	public static boolean isStringFunction(String operator) {
		if (operator.equals(FilterConstants.STRING_FUNCTION_CONTAINS) ||
			operator.equals(FilterConstants.STRING_FUNCTION_ENDS_WITH) ||
			operator.equals(FilterConstants.STRING_FUNCTION_NOT_CONTAINS) ||
			operator.equals(FilterConstants.STRING_FUNCTION_STARTS_WITH)) {

			return true;
		}

		return false;
	}

}