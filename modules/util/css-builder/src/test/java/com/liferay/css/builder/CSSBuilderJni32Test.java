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

package com.liferay.css.builder;

import java.nio.file.Path;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class CSSBuilderJni32Test extends BaseCSSBulderJni32TestCase {

	@Parameterized.Parameters(name = "{0}")
	public static String[] getSeparators() {
		return new String[] {"=", " "};
	}

	public CSSBuilderJni32Test(String separator) {
		_separator = separator;
	}

	@Override
	protected String executeCSSBuilder(
			Path docrootDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path portalCommonPath,
			String outputDirName, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		return CSSBuilderTestUtil.executeCSSBuilder(
			_separator, docrootDirPath, dirName, excludes, generateSourceMap,
			portalCommonPath, outputDirName, precision, rtlExcludedPathRegexps,
			sassCompilerClassName);
	}

	private final String _separator;

}