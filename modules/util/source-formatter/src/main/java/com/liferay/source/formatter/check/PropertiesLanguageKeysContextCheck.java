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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

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

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		int contextDepth = GetterUtil.getInteger(
			getAttributeValue(_CONTEXT_DEPTH_KEY, absolutePath));

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = properties.getProperty(key);

			if (key.matches("\\w+") &&
				StringUtil.equalsIgnoreCase(key, value)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"One word '", key,
						"' for both key and value might have the ",
						"disambiguation"));
			}

			if ((contextDepth != 0) &&
				((StringUtil.count(key, StringPool.DASH) + 1) !=
					contextDepth)) {

				continue;
			}

			Matcher matcher = _languageKeyPattern.matcher(key);

			if (!matcher.matches()) {
				continue;
			}

			if (properties.containsKey(matcher.group(1))) {
				addMessage(
					fileName,
					StringBundler.concat(
						"The key for '", matcher.group(1),
						"' should have the context in [] to indicate the ",
						"specific meaning"));
			}

			String bracketsContent = matcher.group(2);

			if ((bracketsContent.length() == 0) ||
				((bracketsContent.length() == 1) &&
				 !bracketsContent.equals("n") &&
				 !bracketsContent.equals("v")) ||
				(bracketsContent.matches("\\d+") && !key.contains("code") &&
				 !key.contains("status")) ||
				getAttributeValues(
					_FORBIDDEN_CONTEXT_NAMES_KEY, absolutePath
				).contains(
					bracketsContent
				)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"The context '", bracketsContent,
						"' is invalid in the key '", key, "'"));
			}
		}

		return content;
	}

	private static final String _CONTEXT_DEPTH_KEY = "contextDepth";

	private static final String _FORBIDDEN_CONTEXT_NAMES_KEY =
		"forbiddenContextNames";

	private static final Pattern _languageKeyPattern = Pattern.compile(
		"([\\s\\S]+)\\[([\\s\\S]*)\\]");

}