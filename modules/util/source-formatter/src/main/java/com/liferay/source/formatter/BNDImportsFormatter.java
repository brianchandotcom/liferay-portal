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
import com.liferay.portal.tools.ImportPackage;
import com.liferay.portal.tools.ImportsFormatter;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDImportsFormatter extends ImportsFormatter {

	public static String getImports(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	public static String formatBNDImports(String content, Pattern pattern)
		throws IOException {

		String imports = getImports(content, pattern);

		if (Validator.isNull(imports)) {
			return content;
		}

		ImportsFormatter importsFormatter = new BNDImportsFormatter();

		String newImports = importsFormatter.format(imports);

		newImports = StringUtil.replace(newImports, "\n\n", "\n\t\\\n");

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		return content;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		String importString = StringUtil.trim(line);

		if (importString.equals(StringPool.BACK_SLASH)) {
			return null;
		}

		int pos = importString.indexOf(StringPool.SEMICOLON);

		if (pos != -1) {
			importString = importString.substring(0, pos);
		}
		else if (importString.endsWith(",\\")) {
			importString = importString.substring(0, importString.length() - 2);
		}

		return new ImportPackage(importString, false, line);
	}

}