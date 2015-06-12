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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 * @author Raymond Augé
 */
public class JavaImportsFormatter extends ImportsFormatter {

	public static String getImports(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	public static String stripJavaImports(
			String content, String packageDir, String className)
		throws IOException {

		String imports = getImports(content);

		if (Validator.isNull(imports)) {
			return content;
		}

		Set<String> classes = ClassUtil.getClasses(
			new UnsyncStringReader(content), className);

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			int x = line.indexOf("import ");

			if (x == -1) {
				continue;
			}

			int y = line.lastIndexOf(CharPool.PERIOD);

			String importPackage = line.substring(x + 7, y);

			if (importPackage.equals(packageDir) ||
				importPackage.equals("java.lang")) {

				continue;
			}

			String importClass = line.substring(y + 1, line.length() - 1);

			if (importClass.equals("*") || classes.contains(importClass)) {
				sb.append(line);
				sb.append("\n");
			}
		}

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		String newImports = importsFormatter.format(sb.toString());

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		// Ensure a blank line exists between the package and the first import

		content = content.replaceFirst(
			"(?m)^[ \t]*(package .*;)\\s*^[ \t]*import", "$1\n\nimport");

		// Ensure a blank line exists between the last import (or package if
		// there are no imports) and the class comment

		content = content.replaceFirst(
			"(?m)^[ \t]*((?:package|import) .*;)\\s*^[ \t]*/\\*\\*",
			"$1\n\n/**");

		return content;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		Matcher matcher = _javaImportPattern.matcher(line);

		if (!matcher.find()) {
			return null;
		}

		boolean isStatic = false;

		if (Validator.isNotNull(matcher.group(1))) {
			isStatic = true;
		}

		String importString = matcher.group(2);

		return new ImportPackage(importString, isStatic, line);
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*;\n+)+", Pattern.MULTILINE);
	private static final Pattern _javaImportPattern = Pattern.compile(
		"import( static)? ([^;]+);");

}