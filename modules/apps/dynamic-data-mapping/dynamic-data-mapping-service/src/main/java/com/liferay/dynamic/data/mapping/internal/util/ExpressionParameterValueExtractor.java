/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marcos Martins
 * @review
 */
public class ExpressionParameterValueExtractor {

	/**
	 * @param  expression Ex: equals('Country', "US")
	 * @return a list with the given expression parameters Ex: ['Country', "US"]
	 */
	public static List<String> extractParameterValues(String expression) {
		return ListUtil.filter(
			Arrays.asList(expression.split(_FUNCTION_STRUCTURE_REGEX)),
			Validator::isNotNull);
	}

	private static final String _FUNCTION_STRUCTURE_REGEX =
		"\\(+|[aA-zZ]+\\(|,\\s*|\\)+|(\\|\\||&&)";

}