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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.BNDImportsFormatter;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		_checkWildcardImports(
			fileName, absolutePath, content, _CONDITIONAL_PACKAGE_PATTERN);
		_checkWildcardImports(
			fileName, absolutePath, content, _EXPORT_CONTENTS_PATTERN);
		_checkWildcardImports(
			fileName, absolutePath, content, _EXPORTS_PATTERN);

		ImportsFormatter importsFormatter = new BNDImportsFormatter();

		content = importsFormatter.format(
			content, _CONDITIONAL_PACKAGE_PATTERN);
		content = importsFormatter.format(content, _EXPORT_CONTENTS_PATTERN);
		content = importsFormatter.format(content, _EXPORTS_PATTERN);
		content = importsFormatter.format(content, _IMPORTS_PATTERN);
		content = importsFormatter.format(content, _PRIVATE_PACKAGES_PATTERN);

		if (!absolutePath.contains("-test/")) {
			content = _removeInternalPrivatePackages(content);
		}

		return content;
	}

	private void _checkWildcardImports(
		String fileName, String absolutePath, String content, Pattern pattern) {

		if (absolutePath.contains("/portal-kernel/") ||
			absolutePath.contains("/third-party/") ||
			absolutePath.contains("/util-bridges/") ||
			absolutePath.contains("/util-java/") ||
			absolutePath.contains("/util-taglib/") ||
			fileName.endsWith("/system.packages.extra.bnd")) {

			return;
		}

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String imports = matcher.group(3);

		matcher = _WILDCARD_IMPORT_PATTERN.matcher(imports);

		while (matcher.find()) {
			String wildcardImport = matcher.group(1);

			if (wildcardImport.matches("^!?com\\.liferay\\..+")) {
				addMessage(
					fileName,
					"Do not use wildcard in Export-Package '" + wildcardImport +
						"'");
			}
		}
	}

	private String _removeInternalPrivatePackages(String content) {
		Matcher matcher = _PRIVATE_PACKAGES_PATTERN.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();

		matcher = _INTERNAL_PRIVATE_PACKAGE_PATTERN.matcher(match);

		if (!matcher.find()) {
			return content;
		}

		String replacement = StringUtil.removeSubstring(
			match, matcher.group(2));

		return StringUtil.replace(content, match, replacement);
	}

	private static final Pattern _CONDITIONAL_PACKAGE_PATTERN =
		RegexUtil.getPattern(
			"\n-conditionalpackage:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
			Pattern.DOTALL | Pattern.MULTILINE);

	private static final Pattern _EXPORT_CONTENTS_PATTERN =
		RegexUtil.getPattern(
			"\n-exportcontents:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
			Pattern.DOTALL | Pattern.MULTILINE);

	private static final Pattern _EXPORTS_PATTERN = RegexUtil.getPattern(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);

	private static final Pattern _IMPORTS_PATTERN = RegexUtil.getPattern(
		"\nImport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);

	private static final Pattern _INTERNAL_PRIVATE_PACKAGE_PATTERN =
		RegexUtil.getPattern("(,\\\\\n\t|: )(.*\\.internal.*)(\n|\\Z)");

	private static final Pattern _PRIVATE_PACKAGES_PATTERN =
		RegexUtil.getPattern(
			"\nPrivate-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
			Pattern.DOTALL | Pattern.MULTILINE);

	private static final Pattern _WILDCARD_IMPORT_PATTERN =
		RegexUtil.getPattern("(\\S+\\*)(,\\\\\n|\n|\\Z)");

}