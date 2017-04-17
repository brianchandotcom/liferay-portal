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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.PropertiesDefinitionKeysCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	protected void checkMaxLineLength(
		String line, String fileName, int lineCount) {

		String trimmedLine = StringUtil.trimLeading(line);

		if (!trimmedLine.startsWith("# ")) {
			return;
		}

		int lineLength = getLineLength(line);

		if (lineLength <= _maxLineLength) {
			return;
		}

		int x = line.indexOf("# ");
		int y = line.lastIndexOf(StringPool.SPACE, _maxLineLength);

		if ((x + 1) == y) {
			return;
		}

		int z = line.indexOf(StringPool.SPACE, _maxLineLength + 1);

		if (z == -1) {
			z = lineLength;
		}

		if ((z - y + x + 2) <= _maxLineLength) {
			processMessage(fileName, "> " + _maxLineLength, lineCount);
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		if (fileName.endsWith("/dependencies.properties")) {
			newContent = formatDependenciesProperties(content);
		}
		else if (fileName.endsWith("/liferay-plugin-package.properties")) {
			newContent = formatPluginPackageProperties(
				fileName, absolutePath, content);
		}
		else if (fileName.endsWith("/portlet.properties")) {
			newContent = formatPortletProperties(fileName, content);
		}
		else if (fileName.endsWith("/source-formatter.properties")) {
			formatSourceFormatterProperties(fileName, content);
		}
		else if ((!portalSource && !subrepository) ||
				 !fileName.endsWith("/portal.properties")) {

			formatPortalProperties(fileName, content);
		}

		return formatProperties(fileName, newContent);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		if (portalSource) {
			return new String[] {
				"**/lib/*/dependencies.properties", "**/Language.properties",
				"**/liferay-plugin-package.properties", "**/portal.properties",
				"**/portal-ext.properties", "**/portal-legacy-*.properties",
				"**/portlet.properties", "**/source-formatter.properties",
				"**/test.properties"
			};
		}

		return new String[] {
			"**/liferay-plugin-package.properties", "**/portal.properties",
			"**/portal-ext.properties", "**/portlet.properties",
			"**/source-formatter.properties"
		};
	}

	protected String fixIncorrectLicenses(String absolutePath, String content) {
		if (!isModulesApp(absolutePath, false)) {
			return content;
		}

		Matcher matcher = _licensesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String licenses = matcher.group(1);

		String expectedLicenses = "LGPL";

		if (isModulesApp(absolutePath, true)) {
			expectedLicenses = "DXP";
		}

		if (licenses.equals(expectedLicenses)) {
			return content;
		}

		return StringUtil.replace(
			content, "licenses=" + licenses, "licenses=" + expectedLicenses,
			matcher.start());
	}

	protected String formatDependenciesProperties(String content) {
		List<String> lines = ListUtil.fromString(content);

		lines = ListUtil.sort(lines);

		StringBundler sb = new StringBundler(content.length() * 2);

		for (String line : lines) {
			line = StringUtil.removeChar(line, CharPool.SPACE);

			if (Validator.isNotNull(line) &&
				(line.charAt(0) != CharPool.POUND)) {

				sb.append(line);
				sb.append(CharPool.NEW_LINE);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	protected String formatPluginPackageProperties(
		String fileName, String absolutePath, String content) {

		content = StringUtil.replace(content, "\n\n", "\n");

		content = StringUtil.replace(
			content, StringPool.TAB, StringPool.FOUR_SPACES);

		Matcher matcher = _singleValueOnMultipleLinesPattern.matcher(content);

		if (matcher.find()) {
			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());
		}

		return fixIncorrectLicenses(absolutePath, content);
	}

	protected void formatPortalProperties(String fileName, String content)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			int previousPos = -1;

			String portalPortalPropertiesContent = getPortalPortalProperties();

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos + 1));

				pos = portalPortalPropertiesContent.indexOf(
					StringPool.FOUR_SPACES + property);

				if (pos == -1) {
					continue;
				}

				if (pos < previousPos) {
					processMessage(
						fileName,
						"Follow order as in portal-impl/src/portal.properties",
						lineCount);
				}

				previousPos = pos;
			}
		}
	}

	protected String formatPortletProperties(String fileName, String content)
		throws Exception {

		if (!content.contains("include-and-override=portlet-ext.properties")) {
			content =
				"include-and-override=portlet-ext.properties\n\n" + content;
		}

		if (!portalSource && !subrepository) {
			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			String previousProperty = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (lineCount == 1) {
					continue;
				}

				if (line.startsWith(StringPool.POUND) ||
					line.startsWith(StringPool.SPACE) ||
					line.startsWith(StringPool.TAB)) {

					continue;
				}

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos));

				pos = property.indexOf(CharPool.OPEN_BRACKET);

				if (pos != -1) {
					property = property.substring(0, pos);
				}

				if (Validator.isNotNull(previousProperty) &&
					(previousProperty.compareToIgnoreCase(property) > 0)) {

					processMessage(
						fileName, "Unsorted property '" + property + "'",
						lineCount);
				}

				previousProperty = property;
			}
		}

		return content;
	}

	protected String formatProperties(String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				checkMaxLineLength(line, fileName, lineCount);

				if (line.startsWith(StringPool.TAB)) {
					line = line.replace(StringPool.TAB, StringPool.FOUR_SPACES);
				}

				if (line.contains(" \t")) {
					line = line.replace(" \t", StringPool.FOUR_SPACES);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		return newContent;
	}

	protected void formatSourceFormatterProperties(
			String fileName, String content)
		throws Exception {

		boolean hasPrivateAppsDir = false;

		int level = PLUGINS_MAX_DIR_LEVEL;

		if (portalSource) {
			File privateAppsDir = getFile(
				"modules/private/apps", PORTAL_MAX_DIR_LEVEL);

			if (privateAppsDir != null) {
				hasPrivateAppsDir = true;
			}

			level = PORTAL_MAX_DIR_LEVEL;
		}

		Properties properties = new Properties();

		InputStream inputStream = new FileInputStream(fileName);

		properties.load(inputStream);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (!key.endsWith("excludes")) {
				continue;
			}

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			List<String> propertyFileNames = ListUtil.fromString(
				value, StringPool.COMMA);

			for (String propertyFileName : propertyFileNames) {
				if (propertyFileName.contains(StringPool.STAR) ||
					propertyFileName.endsWith("-ext.properties") ||
					(portalSource && !hasPrivateAppsDir &&
					 isModulesApp(propertyFileName, true))) {

					continue;
				}

				int pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				File file = getFile(propertyFileName, level);

				if (file == null) {
					processMessage(
						fileName,
						"Property value '" + propertyFileName +
							"' points to file that does not exist");
				}
			}
		}
	}

	protected String getPortalPortalProperties() throws Exception {
		if (_portalPortalPropertiesContent != null) {
			return _portalPortalPropertiesContent;
		}

		String portalPortalPropertiesContent = null;

		if (portalSource) {
			File file = getFile(
				"portal-impl/src/portal.properties", PORTAL_MAX_DIR_LEVEL);

			portalPortalPropertiesContent = FileUtil.read(file);

			_portalPortalPropertiesContent = portalPortalPropertiesContent;

			return _portalPortalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			portalPortalPropertiesContent = StringPool.BLANK;
		}

		_portalPortalPropertiesContent = portalPortalPropertiesContent;

		return _portalPortalPropertiesContent;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateSourceChecks() {
		_sourceChecks.add(new WhitespaceCheck(true));

		_sourceChecks.add(new PropertiesDefinitionKeysCheck());
	}

	@Override
	protected void preFormat() throws Exception {
		_maxLineLength = sourceFormatterArgs.getMaxLineLength();
	}

	private final Pattern _licensesPattern = Pattern.compile(
		"\nlicenses=(\\w+)\n");
	private int _maxLineLength;
	private String _portalPortalPropertiesContent;
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*=(\\\\\n *).*(\n[^ ]|\\Z)");
	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}