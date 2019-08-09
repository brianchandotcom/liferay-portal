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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class YMLEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatEmptyLines(content);
	}

	private String _formatEmptyLines(String content) {
		String[] contentBlocks = new String[0];

		Matcher matcher = _styleBlockPattern.matcher(content);

		int lastEndPos = 0;

		while (matcher.find()) {
			contentBlocks = ArrayUtil.append(
				contentBlocks, content.substring(lastEndPos, matcher.start()));
			contentBlocks = ArrayUtil.append(
				contentBlocks,
				content.substring(matcher.start(), matcher.end()));

			lastEndPos = matcher.end();
		}

		if (contentBlocks.length == 0) {
			contentBlocks = ArrayUtil.append(contentBlocks, content);
		}

		StringBundler sb = new StringBundler(contentBlocks.length);

		for (int i = 0; i < contentBlocks.length; i++) {
			String s = contentBlocks[i];

			if ((i % 2) != 0) {
				sb.append(s);

				continue;
			}

			s = s.replaceAll("(?<!\n)(\n---\n)", "\n$1");
			s = s.replaceAll("(\n---\n)(?!\n)", "$1\n");
			s = s.replaceAll("(?<!---)\n\n(?!---)", "\n");

			sb.append(s);
		}

		return sb.toString();
	}

	private static final Pattern _styleBlockPattern = Pattern.compile(
		"(?<=\\|-\n)( +)(.*?(\n|\\Z))(\\1(.*?(\n|\\Z)))*", Pattern.DOTALL);

}