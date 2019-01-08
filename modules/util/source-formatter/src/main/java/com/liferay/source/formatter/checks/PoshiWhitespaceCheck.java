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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatWhitespace(content);

		return super.doProcess(fileName, absolutePath, content);
	}

	private String _formatWhitespace(String content) {
		Matcher matcher = _incorrectWhitespacePattern.matcher(content);

		while (matcher.find()) {
			int x = matcher.start(1);

			if (!ToolsUtil.isInsideQuotes(content, x)) {
				return StringUtil.replaceFirst(
					content, matcher.group(1), StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _incorrectWhitespacePattern = Pattern.compile(
		"\\)(\\s+);");

}