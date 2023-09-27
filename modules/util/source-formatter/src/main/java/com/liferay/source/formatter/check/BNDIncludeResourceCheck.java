/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ImportPackage;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDIncludeResourceCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("test-bnd.bnd")) {
			content = _formatIncludeResource(fileName, content);
		}

		if (fileName.endsWith("-test/bnd.bnd") &&
			isAttributeValue(_CHECK_TEST_INCLUDE_RESOURCE_KEY, absolutePath)) {

			_checkIncludeResource(fileName, content);
		}

		return content;
	}

	private void _checkIncludeResource(String fileName, String content) {
		String includeResource = BNDSourceUtil.getDefinitionValue(
			content, "-includeresource");

		if ((includeResource != null) &&
			includeResource.contains("test-classes/integration")) {

			addMessage(
				fileName,
				"Do not use 'test-classes/integration' in bnd.bnd in test " +
					"modules");
		}
	}

	private String _formatIncludeResource(String fileName, String content) {
		if (fileName.endsWith("/bnd.bnd") &&
			!fileName.endsWith("-test/bnd.bnd")) {

			Matcher matcher = _includeDashResourcePattern.matcher(content);

			if (matcher.find()) {
				String replacement = StringUtil.replace(
					matcher.group(), "Include-Resource:", "-includeresource:");

				return StringUtil.replace(
					content, matcher.group(), replacement);
			}
		}

		Matcher matcher1 = _includeResourcePattern.matcher(content);

		if (!matcher1.find()) {
			return content;
		}

		String includeResources = matcher1.group();

		matcher1 = _includeResourceJarPattern.matcher(includeResources);

		if (matcher1.find()) {
			String replacement = StringUtil.replace(
				includeResources, matcher1.group(), "-[0-9]*.jar");

			return StringUtil.replace(content, includeResources, replacement);
		}

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
				return StringUtil.removeSubstring(content, includeResources);
			}

			String afterIncludeResourceDir = matcher2.group(2);

			int x = includeResources.lastIndexOf("\\", matcher2.start());

			String replacement = null;

			if (afterIncludeResourceDir.equals(",\\\n")) {
				int y = matcher2.end();

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

		return _sortDefinitionProperties(
			content, includeResources, new IncludeResourceComparator());
	}

	private String _sortDefinitionProperties(
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

	private static final String _CHECK_TEST_INCLUDE_RESOURCE_KEY =
		"checkTestIncludeResource";

	private static final String[] _INCLUDE_RESOURCE_DIRS_BLACKLIST = {
		"classes", "META-INF/resources=src/main/resources/META-INF/resources",
		"META-INF/resources/content=src/main/resources/content",
		"WEB-INF=src/main/resources/WEB-INF"
	};

	private static final Pattern _includeDashResourcePattern = Pattern.compile(
		"^Include-Resource:.+", Pattern.MULTILINE);
	private static final Pattern _includeResourceJarPattern = Pattern.compile(
		"-[0-9\\.]+\\.jar");
	private static final Pattern _includeResourcePattern = Pattern.compile(
		"^(-includeresource|Include-Resource):[\\s\\S]*?([^\\\\]\n|\\Z)",
		Pattern.MULTILINE);

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

			String importString1 = includeResource1.substring(pos1 + 6);
			String importString2 = includeResource2.substring(pos2 + 6);

			if (importString1.endsWith(".class")) {
				importString1 = importString1.substring(
					0, importString1.length() - 6);
			}

			if (importString2.endsWith(".class")) {
				importString2 = importString2.substring(
					0, importString2.length() - 6);
			}

			ImportPackage importPackage1 = new ImportPackage(
				importString1, false, includeResource1);
			ImportPackage importPackage2 = new ImportPackage(
				importString2, false, includeResource2);

			return importPackage1.compareTo(importPackage2);
		}

	}

}