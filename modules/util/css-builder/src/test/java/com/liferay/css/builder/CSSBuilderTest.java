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

import com.liferay.css.builder.util.StringTestUtil;

import java.io.PrintStream;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class CSSBuilderTest extends BaseCSSBuilderTestCase {

	@Parameterized.Parameters(name = "{0}")
	public static String[] getSeparators() {
		return new String[] {"=", " "};
	}

	public CSSBuilderTest(String separator) {
		_separator = separator;
	}

	@Override
	protected String executeCSSBuilder(
			Path docrootDirPath, String dirName, String[] excludes,
			boolean generateSourceMap, Path portalCommonPath,
			String outputDirName, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		List<String> args = new ArrayList<>();

		args.add("--base-dir" + _separator + docrootDirPath.toAbsolutePath());
		args.add("--compiler" + _separator + sassCompilerClassName);
		args.add("--dir-names" + _separator + dirName);
		args.add("--excludes" + _separator + StringTestUtil.merge(excludes));
		args.add("--generate-source-map" + _separator + generateSourceMap);
		args.add(
			"--import-paths" + _separator + portalCommonPath.toAbsolutePath());
		args.add("--output-dir" + _separator + outputDirName);
		args.add("--precision" + _separator + precision);
		args.add(
			"--rtl-excluded-path-regexps" + _separator +
				StringTestUtil.merge(rtlExcludedPathRegexps));

		StringPrintStream output = new StringPrintStream();

		PrintStream systemOut = System.out;

		System.setOut(output);

		try {
			CSSBuilder.main(args.toArray(new String[0]));
		}
		finally {
			System.setOut(systemOut);
		}

		return output.toString();
	}

	private final String _separator;

}