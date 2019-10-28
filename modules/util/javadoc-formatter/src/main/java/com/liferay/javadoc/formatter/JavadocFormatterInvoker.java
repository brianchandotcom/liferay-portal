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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterInvoker {

	public static JavadocFormatter invoke(
			File baseDir, JavadocFormatterArgs javadocFormatterArgs)
		throws Exception {

		Map<String, String> arguments = HashMapBuilder.put(
			"javadoc.author", javadocFormatterArgs.getAuthor()
		).put(
			"javadoc.generate.xml",
			String.valueOf(javadocFormatterArgs.isGenerateXml())
		).put(
			"javadoc.init",
			String.valueOf(javadocFormatterArgs.isInitializeMissingJavadocs())
		).put(
			"javadoc.input.dir",
			_getCanonicalPath(baseDir, javadocFormatterArgs.getInputDirName())
		).put(
			"javadoc.limit", StringUtil.merge(javadocFormatterArgs.getLimits())
		).put(
			"javadoc.output.file.prefix",
			javadocFormatterArgs.getOutputFilePrefix()
		).put(
			"javadoc.update",
			String.valueOf(javadocFormatterArgs.isUpdateJavadocs())
		).build();

		return new JavadocFormatter(arguments);
	}

	private static String _getCanonicalPath(File baseDir, String fileName)
		throws IOException {

		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getCanonicalPath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}