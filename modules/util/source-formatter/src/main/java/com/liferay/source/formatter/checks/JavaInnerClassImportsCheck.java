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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaInnerClassImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String className = null;
		List<String> imports = null;
		String packageName = null;

		Matcher matcher = _innerClassImportPattern.matcher(content);

		while (matcher.find()) {
			String innerClassName = matcher.group(4);
			String outerClassName = matcher.group(3);

			// Skip inner classes with long names, because it causes a lot of
			// cases where we get long lines that are hard to resolve

			if ((innerClassName.length() + outerClassName.length()) > 40) {
				continue;
			}

			String innerClassFullyQualifiedName = matcher.group(1);
			String outerClassFullyQualifiedName = matcher.group(2);

			if (className == null) {
				className = JavaSourceUtil.getClassName(fileName);
				packageName = JavaSourceUtil.getPackageName(content);
			}

			if (outerClassFullyQualifiedName.equals(
					packageName + "." + className)) {

				return _removeInnerClassImport(
					content, innerClassFullyQualifiedName,
					outerClassFullyQualifiedName);
			}

			if (imports == null) {
				imports = _getImports(content);
			}

			if (_isRedundantImport(
					content, innerClassName, innerClassFullyQualifiedName,
					imports)) {

				return _formatInnerClassImport(
					content, innerClassName, innerClassFullyQualifiedName,
					outerClassName, outerClassFullyQualifiedName);
			}
		}

		return content;
	}

	private String _formatInnerClassImport(
		String content, String innerClassName,
		String innerClassFullyQualifiedName, String outerClassName,
		String outerClassFullyQualifiedName) {

		content = _removeInnerClassImport(
			content, innerClassFullyQualifiedName,
			outerClassFullyQualifiedName);

		Pattern pattern = Pattern.compile("[^.\\w]" + innerClassName + "\\W");

		while (true) {
			Matcher matcher = pattern.matcher(content);

			if (!matcher.find()) {
				return content;
			}

			content = StringUtil.insert(
				content, outerClassName + StringPool.PERIOD,
				matcher.start() + 1);
		}
	}

	private String _getFullyQualifiedName(
		String s1, String s2, List<String> imports) {

		for (String importLine : imports) {
			if (!importLine.endsWith(StringPool.PERIOD + s1)) {
				continue;
			}

			if (s2 == null) {
				return importLine;
			}

			s2 = StringUtil.replaceLast(
				s2.replaceAll("\\s", StringPool.BLANK), CharPool.PERIOD,
				StringPool.BLANK);

			return StringBundler.concat(importLine, StringPool.PERIOD, s2);
		}

		return null;
	}

	private List<String> _getImports(String content) {
		List<String> imports = new ArrayList<>();

		String[] importLines = StringUtil.splitLines(
			JavaImportsFormatter.getImports(content));

		for (String importLine : importLines) {
			if (Validator.isNotNull(importLine)) {
				imports.add(importLine.substring(7, importLine.length() - 1));
			}
		}

		return imports;
	}

	private boolean _isRedundantImport(
		String content, String innerClassName,
		String innerClassFullyQualifiedName, List<String> imports) {

		Pattern pattern = Pattern.compile(
			"(\\w+)\\.\\s*(\\w+\\.\\s*)*" + innerClassName + "\\W");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			String match = matcher.group();

			if (match.startsWith(innerClassFullyQualifiedName)) {
				continue;
			}

			String fullyQualifiedName = _getFullyQualifiedName(
				matcher.group(1), matcher.group(2), imports);

			if (fullyQualifiedName == null) {
				continue;
			}

			if (!innerClassFullyQualifiedName.startsWith(fullyQualifiedName)) {
				return false;
			}
		}

		return true;
	}

	private String _removeInnerClassImport(
		String content, String innerClassFullyQualifiedName,
		String outerClassFullyQualifiedName) {

		String replacement = StringPool.BLANK;

		if (!content.contains("import " + outerClassFullyQualifiedName + ";")) {
			replacement = "\nimport " + outerClassFullyQualifiedName + ";";
		}

		return StringUtil.replaceFirst(
			content, "\nimport " + innerClassFullyQualifiedName + ";",
			replacement);
	}

	private final Pattern _innerClassImportPattern = Pattern.compile(
		"\nimport (([\\w.]+\\.([A-Z]\\w+))\\.([A-Z]\\w+));");

}