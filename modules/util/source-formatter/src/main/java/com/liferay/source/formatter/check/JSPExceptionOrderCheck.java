/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPExceptionOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortExceptions(content);
	}

	private String _sortExceptions(String content) {
		Matcher matcher = _exceptionsPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String[] lines = StringUtil.splitLines(match);

			String previousException = null;
			String previousLine = null;

			for (String line : lines) {
				line = StringUtil.trim(line);

				int x = line.indexOf("<liferay-ui:error exception=\"<%=");

				int y = line.indexOf(".class %>", x);

				if (y == -1) {
					previousException = null;

					continue;
				}

				String currentException = line.substring(x, y);

				if (Validator.isNotNull(previousException) &&
					(previousException.compareToIgnoreCase(currentException) >
						0)) {

					String replacement = StringUtil.replaceFirst(
						match, previousLine, line);

					replacement = StringUtil.replaceLast(
						replacement, line, previousLine);

					return StringUtil.replaceFirst(
						content, match, replacement, matcher.start());
				}

				previousException = currentException;
				previousLine = line;
			}
		}

		return content;
	}

	private static final Pattern _exceptionsPattern = Pattern.compile(
		"(^[\t]*<liferay-ui:error exception=\"<%=.*\n)+", Pattern.MULTILINE);

}