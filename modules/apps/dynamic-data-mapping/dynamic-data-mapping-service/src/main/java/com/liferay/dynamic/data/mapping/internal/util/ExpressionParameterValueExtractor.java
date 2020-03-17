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

import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcos Martins
 */
public class ExpressionParameterValueExtractor {

	/**
	 * @param expression Ex: equals('Country', "US")
	 * @return a list with the given expression parameters Ex: ['Country', "US"]
	 */
	public static List<String> extractParameterValues(String expression) {
		List<String> parameterValues = Arrays.asList(
			expression.split(_FUNCTION_STRUCTURE_REGEX));

		Stream<String> parameterValueStream = parameterValues.stream();

		return parameterValueStream.filter(
			parameterValue -> Validator.isNotNull(parameterValue)
		).collect(
			Collectors.toList()
		);
	}

	private static final String _FUNCTION_STRUCTURE_REGEX =
		"([aA-zZ])*.([(])|,[ ]*|[)]";

}