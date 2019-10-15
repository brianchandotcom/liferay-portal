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

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderTestUtil {

	public static String executeCSSBuilder(
			String separator, Path docrootDirPath, String dirName,
			String[] excludes, boolean generateSourceMap, Path portalCommonPath,
			String outputDirName, int precision,
			String[] rtlExcludedPathRegexps, String sassCompilerClassName)
		throws Exception {

		List<String> args = new ArrayList<>();

		args.add("--base-dir" + separator + docrootDirPath.toAbsolutePath());
		args.add("--compiler" + separator + sassCompilerClassName);
		args.add("--dir-names" + separator + dirName);
		args.add("--excludes" + separator + StringTestUtil.merge(excludes));
		args.add("--generate-source-map" + separator + generateSourceMap);
		args.add(
			"--import-paths" + separator + portalCommonPath.toAbsolutePath());
		args.add("--output-dir" + separator + outputDirName);
		args.add("--precision" + separator + precision);
		args.add(
			"--rtl-excluded-path-regexps" + separator +
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

}