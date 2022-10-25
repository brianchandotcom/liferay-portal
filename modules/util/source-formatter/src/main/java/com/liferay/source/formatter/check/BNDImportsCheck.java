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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.source.formatter.BNDImportsFormatter;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDImportsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_checkWildcardImports(
			fileName, absolutePath, content, "-conditionalpackage",
			_conditionalPackagePattern);
		_checkWildcardImports(
			fileName, absolutePath, content, "-exportcontents",
			_exportContentsPattern);
		_checkWildcardImports(
			fileName, absolutePath, content, "Export-Package", _exportsPattern);

		content = _checkImportPackageVersionRanges(content);

		ImportsFormatter importsFormatter = new BNDImportsFormatter();

		content = importsFormatter.format(content, _conditionalPackagePattern);
		content = importsFormatter.format(content, _exportContentsPattern);
		content = importsFormatter.format(content, _exportsPattern);
		content = importsFormatter.format(content, _importsPattern);
		content = importsFormatter.format(content, _privatePackagesPattern);

		if (!absolutePath.contains("-test/")) {
			content = _removeInternalPrivatePackages(content);
		}

		return content;
	}

	private String _checkImportPackageVersionRanges(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		if (matcher.find()) {
			String imports = matcher.group();

			String newImports = imports.replaceAll(
				"(com\\.liferay\\..*?version=\"[\\(\\[].*?,)(?!9999\\.0\\.0)." +
					"*?([\\)\\]]\")",
				"$19999.0.0$2");

			if (!imports.equals(newImports)) {
				return StringUtil.replaceFirst(content, imports, newImports);
			}
		}

		return content;
	}

	private void _checkWildcardImports(
		String fileName, String absolutePath, String content,
		String instruction, Pattern pattern) {

		if (absolutePath.contains("/portal-kernel/") ||
			absolutePath.contains("/support-tomcat/") ||
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
				String message = StringBundler.concat(
					"Do not use wildcard in ", instruction, ": '",
					wildcardImport, "'");

				addMessage(fileName, message);
			}
		}
	}

	private String _removeInternalPrivatePackages(String content) {
		Matcher matcher = _privatePackagesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();

		matcher = _internalPrivatePackagePattern.matcher(match);

		if (!matcher.find()) {
			return content;
		}

		String replacement = StringUtil.removeSubstring(
			match, matcher.group(2));

		return StringUtil.replace(content, match, replacement);
	}

	private static final Pattern _conditionalPackagePattern = Pattern.compile(
		"\n-conditionalpackage:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _exportContentsPattern = Pattern.compile(
		"\n-exportcontents:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _exportsPattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _importsPattern = Pattern.compile(
		"\nImport-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _internalPrivatePackagePattern =
		Pattern.compile("(,\\\\\n\t|: )(.*\\.internal.*)(\n|\\Z)");
	private static final Pattern _privatePackagesPattern = Pattern.compile(
		"\nPrivate-Package:(\\\\\n| )((.*?)(\n[^\t]|\\Z))",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _wilcardImportPattern = Pattern.compile(
		"(\\S+\\*)(,\\\\\n|\n|\\Z)");

}