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

package com.liferay.source.formatter;

import com.liferay.source.formatter.checks.util.XMLSourceUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = {
			"**/.bnd/**", "**/.idea/**", "**/.ivy/**", "**/bin/**",
			"**/javadocs-*.xml", "**/logs/**", "**/portal-impl/**/*.action",
			"**/portal-impl/**/*.function", "**/portal-impl/**/*.macro",
			"**/portal-impl/**/*.testcase", "**/src/test/**",
			"**/test-classes/unit/**", "**/test-results/**", "**/test/unit/**",
			"**/tools/node**"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected boolean hasGeneratedTag(String content) {
		if (!content.contains("@generated")) {
			return false;
		}

		int pos = -1;

		while (true) {
			pos = content.indexOf("@generated", pos + 1);

			if (pos == -1) {
				return false;
			}

			if (!XMLSourceUtil.isInsideCDATAMarkup(content, pos)) {
				return true;
			}
		}
	}

	private static final String[] _INCLUDES = {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro", "**/*.pom",
		"**/*.testcase", "**/*.toggle", "**/*.xml",
		"**/definitions/liferay-*.xsd"
	};

}