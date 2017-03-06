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

import com.liferay.portal.kernel.util.StringPool;
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
public class BNDImportsFormatter extends BaseImportsFormatter {

	public static String getImports(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(3);
		}

		return null;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		if (line.endsWith(StringPool.BACK_SLASH)) {
			line = line.substring(0, line.length() - 1);
		}

		if (line.endsWith(StringPool.COMMA)) {
			line = line.substring(0, line.length() - 1);
		}

		String importString = StringUtil.trim(line);

		if (Validator.isNull(importString)) {
			return null;
		}

		int pos = importString.indexOf(StringPool.SEMICOLON);

		if (pos != -1) {
			importString = importString.substring(0, pos);
		}

		return new ImportPackage(importString, false, line, true);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packageDir,
			String className)
		throws IOException {

		String imports = getImports(content, importPattern);

		if (Validator.isNull(imports)) {
			return content;
		}

		String newImports = sortAndGroupImports(imports);

		newImports = newImports.substring(0, newImports.length() - 1);

		newImports = StringUtil.replace(
			newImports, new String[] {"\n", "\n,\\"},
			new String[] {",\\\n", "\n\t\\"});

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		return content;
	}

}