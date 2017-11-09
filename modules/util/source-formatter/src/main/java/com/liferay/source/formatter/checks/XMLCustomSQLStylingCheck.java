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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLCustomSQLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("/custom-sql/")) {
			_checkScalability(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkScalability(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _whereNotInSQLPattern.matcher(content);

		while (matcher.find()) {
			int x = content.lastIndexOf("<sql id=", matcher.start());

			int y = content.indexOf(CharPool.QUOTE, x);

			int z = content.indexOf(CharPool.QUOTE, y + 1);

			String id = content.substring(y + 1, z);

			x = id.lastIndexOf(CharPool.PERIOD);

			y = id.lastIndexOf(CharPool.PERIOD, x - 1);

			String entityName = id.substring(y + 1, x);

			if (!isExcludedPath(
					_CUSTOM_FINDER_SCALABILITY_EXCLUDES, absolutePath,
					entityName)) {

				addMessage(
					fileName,
					"Avoid using WHERE ... NOT IN: " + id + ", see LPS-51315");
			}
		}
	}

	private static final String _CUSTOM_FINDER_SCALABILITY_EXCLUDES =
		"custom.finder.scalability.excludes";

	private final Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");

}