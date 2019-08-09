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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

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
		String[] contentBlocks = YMLSourceUtil.getContentBlocks(
			content, _styleBlockPattern);

		StringBundler sb = new StringBundler(contentBlocks.length);

		for (int i = 0; i < contentBlocks.length; i++) {
			String s = contentBlocks[i];

			if ((i % 2) != 0) {
				sb.append(s);
				sb.append(StringPool.NEW_LINE);

				continue;
			}

			s = s.replaceAll("(?<!\n)(\n---\n)", "\n$1");
			s = s.replaceAll("(\n---\n)(?!\n)", "$1\n");
			s = s.replaceAll("(?<!---)\n\n(?!---)", "\n");

			sb.append(s);

			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final Pattern _styleBlockPattern = Pattern.compile(
		"(?<=\\|-\n)( +)(.*)(\n\\1.*)*");

}