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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPVariableOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Matcher matcher = _variableDefinitionPattern.matcher(content);

		while (matcher.find()) {
			int endLineNumber = getLineNumber(content, matcher.end(1) - 1);
			int startLineNumber = getLineNumber(content, matcher.start() + 3);

			String previousLine = null;
			String previousVariableName = null;

			for (int i = startLineNumber; i <= endLineNumber; i++) {
				String line = getLine(content, i);

				int x = line.indexOf(" =");

				int y = line.lastIndexOf(" ", x - 1);

				String variableName = line.substring(y, x);

				if ((previousVariableName != null) &&
					(variableName.compareToIgnoreCase(previousVariableName) <
						0)) {

					content = StringUtil.replaceFirst(
						content, line, previousLine, matcher.start());
					content = StringUtil.replaceFirst(
						content, previousLine, line, matcher.start());

					return content;
				}

				previousLine = line;
				previousVariableName = variableName;
			}
		}

		return content;
	}

	private static final Pattern _variableDefinitionPattern = Pattern.compile(
		"<%\n(\t*[\\w<>\\[\\],\\? ]+ \\w+ = .*\n){2,}\t*%>(\n|\\Z)");

}