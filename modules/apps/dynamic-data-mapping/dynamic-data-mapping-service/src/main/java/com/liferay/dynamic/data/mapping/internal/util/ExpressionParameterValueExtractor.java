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