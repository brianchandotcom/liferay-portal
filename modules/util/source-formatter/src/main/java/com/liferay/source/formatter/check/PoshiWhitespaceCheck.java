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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alan Huang
 */
public class PoshiWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _removeRedundantWhitespaceInTaskDescription(fileName, content);
	}

	private String _removeRedundantWhitespaceInTaskDescription(
		String fileName, String content) {

		if (!fileName.endsWith("macro") && !fileName.endsWith("testcase")) {
			return content;
		}

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler(lines.length * 2);

		for (String line : lines) {
			String trimmedLine = StringUtil.trim(line);

			if (!trimmedLine.startsWith("task (")) {
				sb.append(line);
				sb.append(StringPool.NEW_LINE);

				continue;
			}

			String newLine = trimmedLine.replaceAll(" {2,}", " ");

			if (StringUtil.equals(trimmedLine, newLine)) {
				sb.append(line);
			}
			else {
				sb.append(StringUtil.replaceFirst(line, trimmedLine, newLine));
			}

			sb.append(StringPool.NEW_LINE);
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

}