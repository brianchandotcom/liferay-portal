/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.javadoc.formatter;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterInvoker {

	public static JavadocFormatter invoke(
			File baseDir, JavadocFormatterArgs javadocFormatterArgs)
		throws Exception {

		return new JavadocFormatter(
			HashMapBuilder.put(
				"javadoc.author", javadocFormatterArgs.getAuthor()
			).put(
				"javadoc.generate.xml",
				String.valueOf(javadocFormatterArgs.isGenerateXml())
			).put(
				"javadoc.init",
				String.valueOf(
					javadocFormatterArgs.isInitializeMissingJavadocs())
			).put(
				"javadoc.input.dir",
				_getCanonicalPath(
					baseDir, javadocFormatterArgs.getInputDirName())
			).put(
				"javadoc.limit",
				StringUtil.merge(javadocFormatterArgs.getLimits())
			).put(
				"javadoc.output.file.prefix",
				javadocFormatterArgs.getOutputFilePrefix()
			).put(
				"javadoc.update",
				String.valueOf(javadocFormatterArgs.isUpdateJavadocs())
			).build());
	}

	private static String _getCanonicalPath(File baseDir, String fileName)
		throws Exception {

		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getCanonicalPath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}