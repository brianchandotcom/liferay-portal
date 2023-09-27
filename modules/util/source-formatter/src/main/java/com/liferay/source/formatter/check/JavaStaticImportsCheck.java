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
public class JavaStaticImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _importMethodPattern.matcher(content);

		while (matcher.find()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Do not import method '");
			sb.append(matcher.group(1));
			sb.append("', import class '");
			sb.append(matcher.group(2));
			sb.append("' instead");

			addMessage(
				fileName, sb.toString(), getLineNumber(content, matcher.end()));
		}

		matcher = _importConstantPattern.matcher(content);

		while (matcher.find()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Do not import constant '");
			sb.append(matcher.group(1));
			sb.append("', import class '");
			sb.append(matcher.group(2));
			sb.append("' instead or use Fully Qualified Name");

			addMessage(
				fileName, sb.toString(), getLineNumber(content, matcher.end()));
		}

		return content;
	}

	private static final Pattern _importConstantPattern = Pattern.compile(
		"\nimport static ((.*)\\.[A-Z_]*);");
	private static final Pattern _importMethodPattern = Pattern.compile(
		"\nimport static ((.*\\..*)\\.[a-z]\\w*);");

}