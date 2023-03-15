/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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