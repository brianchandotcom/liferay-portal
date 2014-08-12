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

package com.liferay.portal.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marcellus Tavares
 */
public class VariableNamesExtractor {

	public List<String> extract(String expressionString) {
		if (expressionString == null) {
			return Collections.emptyList();
		}

		List<String> variableNames = new ArrayList<String>();

		Matcher matcher = _pattern.matcher(expressionString);

		while (matcher.find()) {
			variableNames.add(matcher.group(1));
		}

		return variableNames;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\b([a-zA-Z]+[\\w\\._]*)(?!\\()\\b", Pattern.MULTILINE);

}