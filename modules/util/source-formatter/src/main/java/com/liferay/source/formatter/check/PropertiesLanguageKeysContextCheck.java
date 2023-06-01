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

import com.liferay.petra.string.StringBundler;

import java.io.IOException;
import java.io.StringReader;

import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class PropertiesLanguageKeysContextCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/content/Language.properties")) {
			return content;
		}

		_checkLanguageKeysContext(fileName, absolutePath, content);

		return content;
	}

	private void _checkLanguageKeysContext(
			String fileName, String absolutePath, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			Matcher matcher = _languageKeyPattern.matcher(key);

			if (!matcher.matches()) {
				continue;
			}

			if (properties.containsKey(matcher.group(1))) {
				addMessage(
					fileName,
					StringBundler.concat(
						"The Key '", matcher.group(1),
						"' should with [context] to indicate specific ",
						"meaning"));
			}

			String bracketsContent = matcher.group(3);

			if ((bracketsContent.length() == 0) ||
				((bracketsContent.length() == 1) &&
				 !bracketsContent.equals("n") &&
				 !bracketsContent.equals("v")) ||
				bracketsContent.matches("(\\d+)") ||
				getAttributeValues(
					_FORBIDDEN_CONTEXT_NAMES_KEY, absolutePath
				).contains(
					bracketsContent
				)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"The Key '", key, "' contain unqualified context for '",
						bracketsContent, "'"));
			}
		}
	}

	private static final String _FORBIDDEN_CONTEXT_NAMES_KEY =
		"forbiddenContextNames";

	private static final Pattern _languageKeyPattern = Pattern.compile(
		"([\\s\\S]+)(\\[([\\s\\S]*)\\])");

}