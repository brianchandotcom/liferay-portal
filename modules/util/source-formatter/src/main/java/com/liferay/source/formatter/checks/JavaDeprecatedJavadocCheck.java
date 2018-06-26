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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaDeprecatedJavadocCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		return _formatDeprecatedJavadoc(content);
	}

	private String _formatDeprecatedJavadoc(String content) throws Exception {
		Matcher matcher = _deprecatedPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.group(2) == null) {
				return StringUtil.insert(
					content, " As of " + _getNextReleaseCodeName(),
					matcher.end(1));
			}

			String version = matcher.group(3);

			if (!ArrayUtil.contains(_OLD_RELEASE_CODE_NAMES, version)) {
				String nextReleaseCodeName = _getNextReleaseCodeName();

				if (!nextReleaseCodeName.equals(version)) {
					return StringUtil.replaceFirst(
						content, version, nextReleaseCodeName, matcher.start());
				}
			}

			String deprecatedInfo = matcher.group(4);

			if (Validator.isNull(deprecatedInfo)) {
				return content;
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA)) {
				return StringUtil.insert(
					content, StringPool.COMMA, matcher.end(3));
			}

			if (deprecatedInfo.endsWith(StringPool.PERIOD) &&
				!deprecatedInfo.matches("[\\S\\s]*\\.[ \n][\\S\\s]*")) {

				return StringUtil.replaceFirst(
					content, StringPool.PERIOD, StringPool.BLANK,
					matcher.end(4) - 1);
			}
		}

		return content;
	}

	private synchronized String _getNextReleaseCodeName() throws Exception {
		if (_nextReleaseCodeName != null) {
			return _nextReleaseCodeName;
		}

		Field codeNameField = ReleaseInfo.class.getDeclaredField("_CODE_NAME");

		codeNameField.setAccessible(true);

		_nextReleaseCodeName = String.valueOf(codeNameField.get(null));

		return _nextReleaseCodeName;
	}

	private static final String[] _OLD_RELEASE_CODE_NAMES =
		{"Bunyan", "Newton", "Paton", "Wilberforce"};

	private final Pattern _deprecatedPattern = Pattern.compile(
		"(\n\\s*\\* @deprecated)( As of ([^, \n]+)(.*?)\n\\s*\\*( @|/))?",
		Pattern.DOTALL);
	private String _nextReleaseCodeName;

}