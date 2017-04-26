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

import com.liferay.source.formatter.checks.JSStylingCheck;
import com.liferay.source.formatter.checks.JSWhitespaceCheck;
import com.liferay.source.formatter.checks.LanguageKeysCheck;
import com.liferay.source.formatter.checks.SourceCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JSSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = {
			"**/*.min.js", "**/*.nocsf.js", "**/*.soy.js", "**/aui/**",
			"**/jquery/**", "**/lodash/**", "**/misc/**", "**/r2.js",
			"**/tools/**"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() throws Exception {
		List<SourceCheck> sourceChecks = new ArrayList<>();

		sourceChecks.add(new JSWhitespaceCheck());

		sourceChecks.add(new JSStylingCheck());

		if (portalSource) {
			sourceChecks.add(
				new LanguageKeysCheck(getPortalLanguageProperties()));
		}

		return sourceChecks;
	}

	private static final String[] _INCLUDES = {"**/*.js"};

}