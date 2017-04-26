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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class JSPIncludeCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int x = -1;

		while (true) {
			x = content.indexOf("<%@ include file", x + 1);

			if (x == -1) {
				break;
			}

			int y = content.indexOf(CharPool.NEW_LINE, x);

			if (y == -1) {
				y = content.length();
			}

			String line = content.substring(x, y);

			y = line.indexOf(CharPool.QUOTE);

			int z = line.indexOf(CharPool.QUOTE, y + 1);

			if (z == -1) {
				continue;
			}

			String includeFileName = line.substring(y + 1, z);

			if (!includeFileName.startsWith("/")) {
				addMessage(
					fileName,
					"Include '" + includeFileName + "' should start with '/'",
					getLineCount(content, x));
			}
		}

		Matcher matcher = _includeFilePattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(),
				"@ include file=\"" + matcher.group(1) + "\"", matcher.start());
		}

		return content;
	}

	private final Pattern _includeFilePattern = Pattern.compile(
		"\\s*@\\s*include\\s*file=['\"](.*)['\"]");

}