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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.StringReader;

import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Qi Zhang
 */
public class PropertiesLanguageKeysContextCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_languageKeyWordsCheck(fileName, content);

		return content;
	}

	private boolean _isContinuousString(String context) {
		if (context.length() == 1) {
			return false;
		}

		for (int i = 0; i < (context.length() - 1); i++) {
			if ((context.charAt(i) + 1) == context.charAt(i + 1)) {
				continue;
			}

			return false;
		}

		return true;
	}

	private void _languageKeyWordsCheck(String fileName, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<?> enumeration = properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();

			char lastChar = key.charAt(key.length() - 1);

			int pos = -1;

			if (lastChar == CharPool.CLOSE_BRACKET) {
				pos = key.lastIndexOf(CharPool.OPEN_BRACKET);
			}

			if (pos == -1) {
				continue;
			}

			String context = key.substring(pos);

			if (context.matches("\\[[\\.\\w+]*\\]")) {
				context = context.substring(1, context.length() - 1);
			}
			else {
				continue;
			}

			if (StringUtil.count(key.substring(0, pos), CharPool.PERIOD) > 0) {
				continue;
			}

			if (ArrayUtil.contains(_UNQUALIFIED_CONTEXT, context) ||
				((context.length() == 1) && !StringUtil.equals(context, "v")) ||
				_isContinuousString(context)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"The Key '", key, "' contain unqualified context for '",
						context, "'"));
			}

			key = key.substring(0, pos);

			if (Validator.isNotNull(properties.get(key))) {
				addMessage(
					fileName,
					StringBundler.concat(
						"The Key '", key,
						"' should with [context] or a context suffix to ",
						"indicate specific meaning"));
			}
		}
	}

	private static final String[] _UNQUALIFIED_CONTEXT = {
		"", "null", "#s", "..."
	};

}