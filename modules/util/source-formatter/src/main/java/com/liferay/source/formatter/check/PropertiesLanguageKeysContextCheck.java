/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
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

		List<String> allowedSingleWordLanguageKeys =
			_getAllowedSingleWordLanguageKeys();

		int contextDepth = GetterUtil.getInteger(
			getAttributeValue(_CONTEXT_DEPTH_KEY, absolutePath));

		List<String> forbiddenContextNames = getAttributeValues(
			_FORBIDDEN_CONTEXT_NAMES_KEY, absolutePath);

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (key.matches("\\w+") &&
				StringUtil.equalsIgnoreCase(key, properties.getProperty(key)) &&
				!allowedSingleWordLanguageKeys.contains(key)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"The single-word key '", key,
						"' should include a word of context at the end, ",
						"within a [], to indicate specific meaning"));

				continue;
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
						"The key '", matcher.group(1), "' should include a ",
						"word of context at the end, within a [], to indicate ",
						"specific meaning"));
			}

			String bracketsContent = matcher.group(2);

			if ((bracketsContent.length() == 0) ||
				((bracketsContent.length() == 1) &&
				 !bracketsContent.equals("n") &&
				 !bracketsContent.equals("v")) ||
				(bracketsContent.matches("\\d+") && !key.contains("code") &&
				 !key.contains("status")) ||
				forbiddenContextNames.contains(bracketsContent)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"The context '", bracketsContent,
						"' is invalid in the key '", key, "'"));
			}
		}

		return content;
	}

	private synchronized List<String> _getAllowedSingleWordLanguageKeys()
		throws IOException {

		if (_allowedSingleWordLanguageKeys != null) {
			return _allowedSingleWordLanguageKeys;
		}

		_allowedSingleWordLanguageKeys = new ArrayList<>();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"dependencies/allowed-single-word-language-keys.txt");

		if (inputStream == null) {
			return Collections.emptyList();
		}

		_allowedSingleWordLanguageKeys = ListUtil.fromString(
			StringUtil.read(inputStream));

		return _allowedSingleWordLanguageKeys;
	}

	private static final String _CONTEXT_DEPTH_KEY = "contextDepth";

	private static final String _FORBIDDEN_CONTEXT_NAMES_KEY =
		"forbiddenContextNames";

	private static final Pattern _languageKeyPattern = Pattern.compile(
		"([\\s\\S]+)\\[([\\s\\S]*)\\]");

	private List<String> _allowedSingleWordLanguageKeys;

}