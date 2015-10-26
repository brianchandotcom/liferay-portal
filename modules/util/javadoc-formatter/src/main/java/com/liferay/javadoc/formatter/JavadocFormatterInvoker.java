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

package com.liferay.javadoc.formatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterInvoker {

	public static JavadocFormatter invoke(
			File baseDir, JavadocFormatterArgs javadocFormatterArgs)
		throws Exception {

		Map<String, String> arguments = new HashMap<>();

		arguments.put("javadoc.author", javadocFormatterArgs.getAuthor());
		arguments.put(
			"javadoc.init",
			String.valueOf(javadocFormatterArgs.isInitializeMissingJavadocs()));
		arguments.put(
			"javadoc.input.dir",
			_getCanonicalPath(baseDir, javadocFormatterArgs.getInputDirName()));
		arguments.put(
			"javadoc.limit",
			StringUtil.merge(javadocFormatterArgs.getLimits()));
		arguments.put(
			"javadoc.lowest.supported.java.version",
			String.valueOf(
				javadocFormatterArgs.getLowestSupportedJavaVersion()));
		arguments.put(
			"javadoc.output.file.prefix",
			javadocFormatterArgs.getOutputFilePrefix());
		arguments.put(
			"javadoc.update",
			String.valueOf(javadocFormatterArgs.isUpdateJavadocs()));

		return new JavadocFormatter(arguments);
	}

	private static String _getCanonicalPath(File baseDir, String fileName)
		throws IOException {

		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getCanonicalPath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}