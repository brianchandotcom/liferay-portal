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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiParametersOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		return _sortPoshiParameters(fileName, content);
	}

	private String _sortPoshiParameters(String fileName, String content) {
		Matcher matcher1 = _methodCallPattern.matcher(content);

		while (matcher1.find()) {
			Map<String, String> parametersMap = new TreeMap<>(
				new NaturalOrderStringComparator());

			String parameters = matcher1.group(2);

			Matcher matcher2 = _parametersPattern.matcher(parameters);

			while (matcher2.find()) {
				String parameterName = matcher2.group(1);

				if (parametersMap.containsKey(parameterName)) {
					addMessage(
						fileName,
						"Parameter '" + parameterName + "' is already used",
						getLineNumber(content, matcher1.start(1)));

					parametersMap.clear();

					break;
				}

				parametersMap.put(parameterName, matcher2.group(3));
			}

			if (parametersMap.isEmpty()) {
				continue;
			}

			String indent = SourceUtil.getIndent(matcher1.group(1));

			StringBundler sb = new StringBundler();

			for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
				if (parametersMap.size() == 1) {
					sb.append(entry.getKey());
					sb.append(" = ");
					sb.append(entry.getValue());

					break;
				}

				sb.append(CharPool.NEW_LINE);
				sb.append(indent);
				sb.append(CharPool.TAB);
				sb.append(entry.getKey());
				sb.append(" = ");
				sb.append(entry.getValue());
				sb.append(CharPool.COMMA);
			}

			if (parametersMap.size() > 1) {
				sb.setIndex(sb.index() - 1);
			}

			content = StringUtil.replaceFirst(
				content, parameters, sb.toString(), matcher1.start(1));
		}

		return content;
	}

	private static final Pattern _methodCallPattern = Pattern.compile(
		StringBundler.concat(
			"(?:[^>])\n([ \t]*\\w+(?:\\.\\w+)?\\(",
			"((\\s*\\w+[ \t]*=[ \t]*(('''|\").*?\\5|\\w[^\n]*\\))\\s*)",
			"(,(\\s*\\w+[ \t]*=[ \t]*(('''|\").*?\\9|\\w[^\n]*\\))\\s*))*))",
			"(?=\\)\\s*;\n)"),
		Pattern.DOTALL);
	private static final Pattern _parametersPattern = Pattern.compile(
		"(\\w+)([ \t]*=[ \t]*)((('''|\").*?\\5|\\w.*\\))|.+?\\))",
		Pattern.DOTALL);

}