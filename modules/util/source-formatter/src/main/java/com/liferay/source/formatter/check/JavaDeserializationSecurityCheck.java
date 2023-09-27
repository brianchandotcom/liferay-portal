/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaDeserializationSecurityCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return content;
		}

		_checkDeserializationSecurity(fileName, content, absolutePath);

		return content;
	}

	private void _checkDeserializationSecurity(
		String fileName, String content, String absolutePath) {

		for (Pattern vulnerabilityPattern :
				_javaSerializationVulnerabilityPatterns) {

			Matcher matcher = vulnerabilityPattern.matcher(content);

			if (!matcher.matches()) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
				sb.append("Possible Java Serialization Remote Code Execution ");
				sb.append("vulnerability using ");
			}
			else {
				sb.append("Use ProtectedObjectInputStream instead of ");
			}

			sb.append(matcher.group(1));

			addMessage(fileName, sb.toString());
		}
	}

	private static final Pattern[] _javaSerializationVulnerabilityPatterns = {
		Pattern.compile(
			".*(new [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL),
		Pattern.compile(
			".*(extends [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL)
	};

}