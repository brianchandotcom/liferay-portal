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

import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.BNDImportsFormatter;

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
			fileName, absolutePath, content, _conditionalPackagePattern);
		_checkWildcardImports(
			fileName, absolutePath, content, _exportContentsPattern);
		_checkWildcardImports(fileName, absolutePath, content, _exportsPattern);

		ImportsFormatter importsFormatter = new BNDImportsFormatter();

		content = importsFormatter.format(content, _conditionalPackagePattern);
		content = importsFormatter.format(content, _exportContentsPattern);
		content = importsFormatter.format(content, _exportsPattern);
		content = importsFormatter.format(content, _importsPattern);
		content = importsFormatter.format(content, _privatePackagesPattern);

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

		matcher = _wilcardImportPattern.matcher(imports);

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

	private final Pattern _conditionalPackagePattern = Pattern.compile(
		"\n-conditionalpackage:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _exportContentsPattern = Pattern.compile(
		"\n-exportcontents:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _exportsPattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _importsPattern = Pattern.compile(
		"\nImport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _privatePackagesPattern = Pattern.compile(
		"\nPrivate-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _wilcardImportPattern = Pattern.compile(
		"(\\S+\\*)(,\\\\\n|\n|\\Z)");

}