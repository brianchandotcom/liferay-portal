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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderInvoker {

	public static void invoke(File baseDir, CSSBuilderArgs cssBuilderArgs)
		throws Exception {

		List<String> args = new ArrayList<>();

		String[] dirNames = cssBuilderArgs.getDirNames();

		if (dirNames.length == 1) {
			args.add("sass.dir=" + dirNames[0]);
		}
		else {
			for (int i = 0; i < dirNames.length; i++) {
				args.add("sass.dir." + i + "=" + dirNames[i]);
			}
		}

		args.add(
			"sass.docroot.dir=" +
				_getAbsolutePath(baseDir, cssBuilderArgs.getDocrootDirName()));
		args.add(
			"sass.generate.source.map=" + cssBuilderArgs.isGenerateSourceMap());
		args.add(
			"sass.portal.common.dir=" +
				_getAbsolutePath(
					baseDir, cssBuilderArgs.getPortalCommonDirName()));
		args.add("sass.precision=" + cssBuilderArgs.getPrecision());

		String[] rtlExcludedPathRegexps =
			cssBuilderArgs.getRtlExcludedPathRegexps();

		if ((rtlExcludedPathRegexps != null) &&
			(rtlExcludedPathRegexps.length > 0)) {

			args.add(
				"sass.rtl.excluded.path.regexps=" +
					StringUtil.merge(rtlExcludedPathRegexps));
		}

		args.add(
			"sass.compiler.class.name=" +
				cssBuilderArgs.getSassCompilerClassName());

		CSSBuilder.main(args.toArray(new String[args.size()]));
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}