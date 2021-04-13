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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLImportsFormatter extends BaseImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		return createJavaImportPackage(line);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packagePath,
			String className)
		throws IOException {

		Matcher matcher = _importsPattern.matcher(content);

		while (matcher.find()) {
			String imports = matcher.group(1);

			if (imports.endsWith("\n\n")) {
				imports = imports.substring(0, imports.length() - 1);
			}

			String newImports = sortAndGroupImports(imports);

			if (!imports.equals(newImports)) {
				return StringUtil.replace(content, imports, newImports);
			}
		}

		return content;
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"<\\!\\[CDATA\\[\n((^[ \t]*import\\s+.*;\n+)+)", Pattern.MULTILINE);

}