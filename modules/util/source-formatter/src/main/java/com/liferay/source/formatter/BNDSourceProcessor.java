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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportPackage;
import com.liferay.portal.tools.ImportsFormatter;

import java.io.File;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	protected void checkDirectoryAndBundleName(
		String fileName, String absolutePath, String content) {

		if (!portalSource || !isModulesFile(absolutePath) ||
			!fileName.endsWith("/bnd.bnd") ||
			absolutePath.contains("/testIntegration/") ||
			absolutePath.contains("/third-party/")) {

			return;
		}

		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		String dirName = absolutePath.substring(y + 1, x);

		if (dirName.endsWith("-taglib-web")) {
			String newDirName = dirName.substring(0, dirName.length() - 4);

			processErrorMessage(
				fileName,
				"Rename module '" + dirName + "' to '" + newDirName + "'");
		}

		Matcher matcher = _bundleNamePattern.matcher(content);

		if (matcher.find()) {
			String strippedBundleName = StringUtil.removeChars(
				matcher.group(1), CharPool.DASH, CharPool.SPACE);

			strippedBundleName = strippedBundleName.replaceAll(
				"Implementation$", "Impl");
			strippedBundleName = strippedBundleName.replaceAll(
				"Utilities$", "Util");

			String expectedBundleName =
				"liferay" + StringUtil.removeChars(dirName, CharPool.DASH);

			if (!strippedBundleName.equalsIgnoreCase(expectedBundleName)) {
				processErrorMessage(fileName, "Bundle-Name: " + fileName);
			}
		}

		matcher = _bundleSymbolicNamePattern.matcher(content);

		if (matcher.find()) {
			String bundleSymbolicName = matcher.group(1);

			String expectedBundleSymbolicName =
				"com.liferay." +
					StringUtil.replace(
						dirName, StringPool.DASH, StringPool.PERIOD);

			if (!expectedBundleSymbolicName.contains(".import.") &&
				!expectedBundleSymbolicName.contains(".private.") &&
				!bundleSymbolicName.equalsIgnoreCase(
					expectedBundleSymbolicName)) {

				processErrorMessage(
					fileName, "Bundle-SymbolicName: " + fileName);
			}
		}
	}

	protected void checkWildcardImports(
		String fileName, String absolutePath, String content, Pattern pattern) {

		if (absolutePath.contains("/portal-kernel/") ||
			absolutePath.contains("/util-bridges/") ||
			absolutePath.contains("/util-java/") ||
			absolutePath.contains("/util-taglib/")) {

			return;
		}

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String imports = matcher.group(2);

		matcher = _wilcardImportPattern.matcher(imports);

		while (matcher.find()) {
			String wildcardImport = matcher.group(1);

			if (wildcardImport.matches("^!?com\\.liferay\\..+")) {
				processErrorMessage(
					fileName,
					"Don't use wildcard in Export-Package '" + wildcardImport +
						"': " + fileName);
			}
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = trimContent(content, false);

		content = StringUtil.replace(
			content, new String[] {"/\n", "/,\\\n"},
			new String[] {"\n", ",\\\n"});

		// LPS-61288

		if (fileName.endsWith("-web/bnd.bnd") &&
			content.contains("Liferay-Require-SchemaVersion: 1.0.0")) {

			processErrorMessage(
				fileName,
				"Do not include the header Liferay-Require-SchemaVersion in " +
					"web modules: " + fileName);
		}

		content = StringUtil.replace(content, " \\\n", "\\\n");

		Matcher matcher = _incorrectTabPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.TAB, matcher.start());
		}

		matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.SPACE, matcher.start());
		}

		checkWildcardImports(fileName, absolutePath, content, _exportsPattern);

		ImportsFormatter importsFormatter = new BNDImportsFormatter();

		content = importsFormatter.format(content, _exportsPattern);
		content = importsFormatter.format(content, _importsPattern);

		checkDirectoryAndBundleName(fileName, absolutePath, content);

		content = formatBundleClassPath(content);

		if (portalSource && isModulesFile(absolutePath) &&
			!fileName.endsWith("test-bnd.bnd")) {

			content = formatIncludeResource(content);
		}

		return sortDefinitions(content, new DefinitionComparator());
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected String formatBundleClassPath(String content) {
		Matcher matcher = _bundleClassPathPattern.matcher(content);

		if (matcher.find()) {
			return sortDefinitionProperties(
				content, matcher.group(), new NaturalOrderStringComparator());
		}

		return content;
	}

	protected String formatIncludeResource(String content) {
		Matcher matcher = _includeResourcePattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String includeResources = matcher.group();

		for (String includeResourceDir : _INCLUDE_RESOURCE_DIRS_BLACKLIST) {
			Pattern includeResourceDirPattern = Pattern.compile(
				"(\t|: )" + includeResourceDir + "(,\\\\\n|\n||\\Z)");

			Matcher matcher2 = includeResourceDirPattern.matcher(
				includeResources);

			if (!matcher2.find()) {
				continue;
			}

			String beforeIncludeResourceDir = matcher2.group(1);

			if (!beforeIncludeResourceDir.equals("\t")) {
				return StringUtil.replace(
					content, includeResources, StringPool.BLANK);
			}

			String afterIncludeResourceDir = matcher2.group(2);

			int x = includeResources.lastIndexOf("\\", matcher2.start());
			int y = matcher2.end();

			String replacement = null;

			if (afterIncludeResourceDir.equals(",\\\n")) {
				replacement =
					includeResources.substring(0, x + 1) +
						includeResources.substring(y - 1);
			}
			else {
				replacement = includeResources.substring(0, x - 1);

				if (afterIncludeResourceDir.equals("\n")) {
					replacement += "\n";
				}
			}

			return StringUtil.replace(content, includeResources, replacement);
		}

		return sortDefinitionProperties(
			content, includeResources, new IncludeResourceComparator());
	}

	protected String sortDefinitionProperties(
		String content, String properties, Comparator<String> comparator) {

		String[] lines = StringUtil.splitLines(properties);

		if (lines.length == 1) {
			return content;
		}

		String previousProperty = null;

		for (int i = 1; i < lines.length; i++) {
			String property = StringUtil.trim(lines[i]);

			if (property.endsWith(",\\")) {
				property = property.substring(0, property.length() - 2);
			}

			if (previousProperty != null) {
				int value = comparator.compare(previousProperty, property);

				if (value > 0) {
					String replacement = StringUtil.replaceFirst(
						properties, previousProperty, property);

					replacement = StringUtil.replaceLast(
						replacement, property, previousProperty);

					return StringUtil.replace(content, properties, replacement);
				}
			}

			previousProperty = property;
		}

		return content;
	}

	private static final String[] _INCLUDE_RESOURCE_DIRS_BLACKLIST =
		new String[] {
			"classes",
			"META-INF/resources=src/main/resources/META-INF/resources",
			"META-INF/resources/content=src/main/resources/content",
			"WEB-INF=src/main/resources/WEB-INF"
		};

	private static final String[] _INCLUDES = new String[] {"**/*.bnd"};

	private final Pattern _bundleClassPathPattern = Pattern.compile(
		"^Bundle-ClassPath:[\\s\\S]*?([^\\\\]\n|\\Z)", Pattern.MULTILINE);
	private final Pattern _bundleNamePattern = Pattern.compile(
		"^Bundle-Name: (.*)\n", Pattern.MULTILINE);
	private final Pattern _bundleSymbolicNamePattern = Pattern.compile(
		"^Bundle-SymbolicName: (.*)\n", Pattern.MULTILINE);
	private final Pattern _exportsPattern = Pattern.compile(
		"\nExport-Package:(\\\\\n| )(.*?\n|\\Z)[^\t]",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _importsPattern = Pattern.compile(
		"\nImport-Package:(\\\\\n| )(.*?\n|\\Z)[^\t]",
		Pattern.DOTALL | Pattern.MULTILINE);
	private final Pattern _includeResourcePattern = Pattern.compile(
		"^(-includeresource|Include-Resource):[\\s\\S]*?([^\\\\]\n|\\Z)",
		Pattern.MULTILINE);
	private final Pattern _incorrectTabPattern = Pattern.compile(
		"\n[^\t].*:\\\\\n(\t{2,})[^\t]");
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*:(\\\\\n\t).*(\n[^\t]|\\Z)");
	private final Pattern _wilcardImportPattern = Pattern.compile(
		"(\\S+\\*)(,\\\\\n|\n|\\Z)");

	private static class DefinitionComparator implements Comparator<String> {

		@Override
		public int compare(String definition1, String definition2) {
			if (definition1.startsWith(StringPool.DASH) ^
				definition2.startsWith(StringPool.DASH)) {

				return -definition1.compareTo(definition2);
			}

			return definition1.compareTo(definition2);
		}

	}

	private static class IncludeResourceComparator
		implements Comparator<String> {

		@Override
		public int compare(String includeResource1, String includeResource2) {
			if (includeResource1.startsWith(StringPool.AT) ^
				includeResource2.startsWith(StringPool.AT)) {

				if (includeResource1.startsWith(StringPool.AT)) {
					return 1;
				}

				return -1;
			}

			int pos1 = includeResource1.indexOf(".jar!/");
			int pos2 = includeResource2.indexOf(".jar!/");

			if ((pos1 == -1) || (pos2 == -1)) {
				return includeResource1.compareToIgnoreCase(includeResource2);
			}

			String jarFileName1 = includeResource1.substring(0, pos1);
			String jarFileName2 = includeResource1.substring(0, pos2);

			if (!jarFileName1.equals(jarFileName2)) {
				return includeResource1.compareToIgnoreCase(includeResource2);
			}

			ImportPackage importPackage1 = new ImportPackage(
				includeResource1.substring(pos1 + 6), false, includeResource1);
			ImportPackage importPackage2 = new ImportPackage(
				includeResource2.substring(pos2 + 6), false, includeResource2);

			return importPackage1.compareTo(importPackage2);
		}

	}

}