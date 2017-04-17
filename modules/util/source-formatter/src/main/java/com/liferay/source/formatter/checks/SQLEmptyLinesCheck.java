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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Hugo Huijser
 */
public class SQLEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String[] lines = StringUtil.splitLines(content);

		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];
			String previousLine = lines[i - 1];

			if (Validator.isNull(line) || line.startsWith("\t") ||
				Validator.isNull(previousLine) ||
				previousLine.startsWith("\t")) {

				continue;
			}

			String previousSQLCommand = _getSQLCommand(previousLine);
			String sqlCommand = _getSQLCommand(line);

			if (!previousSQLCommand.equals(sqlCommand)) {
				return StringUtil.replace(
					content, previousLine + "\n" + line,
					previousLine + "\n\n" + line);
			}
		}

		return content;
	}

	private String _getSQLCommand(String line) {
		String[] words = StringUtil.split(line, CharPool.SPACE);

		return words[0];
	}

}