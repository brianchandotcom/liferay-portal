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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaInnerClassImportsCheck extends BaseFileCheck {

	public void setUpperCasePackageNames(String upperCasePackageNames) {
		Collections.addAll(
			_upperCasePackageNames, StringUtil.split(upperCasePackageNames));
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String className = null;
		List<String> imports = null;
		String packageName = null;

		Matcher matcher = _innerClassImportPattern.matcher(content);

		while (matcher.find()) {
			String outerClassFullyQualifiedName = matcher.group(2);

			if (_upperCasePackageNames.contains(outerClassFullyQualifiedName)) {
				continue;
			}

			String innerClassName = matcher.group(4);
			String innerClassFullyQualifiedName = matcher.group(1);
			String outerClassName = matcher.group(3);

			// Skip inner classes with long names, because it causes a lot of
			// cases where we get long lines that are hard to resolve

			if (imports == null) {
				imports = _getImports(content);
			}

			if ((innerClassName.length() + outerClassName.length()) > 40) {
				content = _stripRedundantOuterClass(
					content, innerClassName, innerClassFullyQualifiedName,
					imports);

				continue;
			}

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

			if (outerClassName.equals(className)) {
				continue;
			}

			if (_isRedundantImport(
					content, innerClassName, outerClassName,
					outerClassFullyQualifiedName, imports)) {

				return _formatInnerClassImport(
					content, innerClassName, innerClassFullyQualifiedName,
					outerClassName, outerClassFullyQualifiedName);
			}
			else {
				content = _stripRedundantOuterClass(
					content, innerClassName, innerClassFullyQualifiedName,
					imports);
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
		String content, String innerClassName, String outerClassName,
		String outerClassFullyQualifiedName, List<String> imports) {

		if (content.matches(
				"(?s).*\\.\\s*new\\s+" + innerClassName + "\\(.*")) {

			return false;
		}

		String fullyQualifiedName = _getFullyQualifiedName(
			outerClassName, null, imports);

		if ((fullyQualifiedName != null) &&
			!fullyQualifiedName.equals(outerClassFullyQualifiedName)) {

			return false;
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

	private String _stripRedundantOuterClass(
		String content, String innerClassName, String outerAndInnerClassName) {

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"\n(.*[^\\w\n.])", outerAndInnerClassName, "\\W"));

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String lineStart = StringUtil.trimLeading(matcher.group(1));

			if (lineStart.contains("//") ||
				ToolsUtil.isInsideQuotes(content, matcher.end(1))) {

				continue;
			}

			return StringUtil.replaceFirst(
				content, outerAndInnerClassName, innerClassName,
				matcher.end(1));
		}

		return content;
	}

	private String _stripRedundantOuterClass(
		String content, String innerClassName,
		String innerClassFullyQualifiedName, List<String> imports) {

		Matcher matcher = _outerClassPattern.matcher(
			innerClassFullyQualifiedName);

		while (matcher.find()) {
			int x = matcher.end();

			if (x == innerClassFullyQualifiedName.length()) {
				return content;
			}

			String outerClassFullyQualifiedName =
				innerClassFullyQualifiedName.substring(0, x);

			if (imports.contains(outerClassFullyQualifiedName)) {
				content = _stripRedundantOuterClass(
					content, innerClassName,
					innerClassFullyQualifiedName.substring(
						matcher.start() + 1));
			}
		}

		return content;
	}

	private final Pattern _innerClassImportPattern = Pattern.compile(
		"\nimport (([\\w.]+\\.([A-Z]\\w+))\\.([A-Z]\\w+));");
	private final Pattern _outerClassPattern = Pattern.compile("\\.[A-Z]\\w+");
	private final List<String> _upperCasePackageNames = new ArrayList<>();

}