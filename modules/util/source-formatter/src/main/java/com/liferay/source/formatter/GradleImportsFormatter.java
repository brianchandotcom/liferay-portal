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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class GradleImportsFormatter extends BaseImportsFormatter {

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _gradleImportPattern.matcher(line);

		if (matcher.find()) {
			return new ImportPackage(matcher.group(1), false, line);
		}

		return null;
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packageDir,
			String className)
		throws IOException {

		String imports = _getImports(content);

		if (Validator.isNull(imports)) {
			return content;
		}

		String newImports = sortAndGroupImports(imports);

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		if (!content.startsWith(newImports)) {
			content = StringUtil.removeSubstring(content, newImports);

			content = newImports + "\n" + content;
		}

		return content;
	}

	private String _getImports(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		if (matcher.find()) {
			String imports = matcher.group();

			if (imports.endsWith("\n\n")) {
				imports = imports.substring(0, imports.length() - 1);
			}

			return imports;
		}

		return null;
	}

	private static final Pattern _gradleImportPattern = Pattern.compile(
		"import (.*)");
	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*\n+)+", Pattern.MULTILINE);

}