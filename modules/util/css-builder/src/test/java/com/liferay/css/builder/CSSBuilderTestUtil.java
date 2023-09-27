/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		PrintStream printStream = System.out;

		StringPrintStream stringPrintStream = new StringPrintStream();

		System.setOut(stringPrintStream);

		try {
			CSSBuilder.main(args.toArray(new String[0]));
		}
		finally {
			System.setOut(printStream);
		}

		return stringPrintStream.toString();
	}

}