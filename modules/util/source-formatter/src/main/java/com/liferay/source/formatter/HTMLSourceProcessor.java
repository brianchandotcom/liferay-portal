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

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class HTMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(
			new String[] {
				"**/jenkins-results-parser/src/test/resources/dependencies/**",
				"**/lib/versions.html"
			},
			getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	private static final String[] _INCLUDES = {"**/*.html", "**/*.path"};

}