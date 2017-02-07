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
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	public static final String AUTOMATIC_COPY =
		com.liferay.portal.tools.LangBuilder.AUTOMATIC_COPY;

	public static final String AUTOMATIC_TRANSLATION =
		com.liferay.portal.tools.LangBuilder.AUTOMATIC_TRANSLATION;

	@Override
	public String[] getIncludes() {
		if (portalSource) {
			return new String[] {
				"**/Language.properties",
				"**/liferay-plugin-package.properties", "**/portal.properties",
				"**/portal-ext.properties", "**/portal-legacy-*.properties",
				"**/portlet.properties", "**/source-formatter.properties"
			};
		}

		return new String[] {
			"**/liferay-plugin-package.properties", "**/portal.properties",
			"**/portal-ext.properties", "**/portlet.properties",
			"**/source-formatter.properties"
		};
	}

	protected void addDuplicateLanguageKey(
		String fileName, String key, String value) {

		if (fileName.endsWith("portal-impl/src/content/Language.properties")) {
			return;
		}

		Map<String, String> duplicateLanguageKeysMap =
			_duplicateFileLanguageKeysMap.get(fileName);

		if (duplicateLanguageKeysMap == null) {
			duplicateLanguageKeysMap = new HashMap<>();
		}

		duplicateLanguageKeysMap.put(key, value);

		_duplicateFileLanguageKeysMap.put(fileName, duplicateLanguageKeysMap);
	}

	protected Map<String, Map<String, String>> addTranslations(
			String fileName, Map<String, String> duplicateLanguageKeysMap,
			Map<String, Map<String, String>> languagePropertiesTranslationsMap)
		throws Exception {

		String dirName = StringUtil.replaceLast(
			fileName, "Language.properties", StringPool.BLANK);
		String[] includes = new String[] {"**/Language_*.properties"};

		List<String> translationFileNames = getFileNames(
			dirName, null, new String[0], includes, true);

		for (String translationFileName : translationFileNames) {
			translationFileName = StringUtil.replace(
				translationFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File translationFile = new File(translationFileName);

			String content = FileUtil.read(translationFile);

			int pos = translationFileName.lastIndexOf(StringPool.SLASH);

			String shortTranslationFileName = translationFileName.substring(
				pos + 1);

			Map<String, String> translationMap =
				languagePropertiesTranslationsMap.get(shortTranslationFileName);

			if (translationMap == null) {
				translationMap = new HashMap<>();
			}

			for (Map.Entry<String, String> entry :
					duplicateLanguageKeysMap.entrySet()) {

				String key = entry.getKey();

				if (translationMap.containsKey(key)) {
					continue;
				}

				String value = getTranslatedKey(content, key);

				if (Validator.isNotNull(value) &&
					!value.endsWith(AUTOMATIC_COPY) &&
					!value.endsWith(AUTOMATIC_TRANSLATION)) {

					translationMap.put(key, value);
				}
			}

			languagePropertiesTranslationsMap.put(
				shortTranslationFileName, translationMap);
		}

		return languagePropertiesTranslationsMap;
	}

	protected void checkLanguageProperties(String fileName) throws Exception {
		if (_languagePropertiesMap == null) {
			populateLanguagePropertiesMap();
		}

		Properties languageProperties1 = _languagePropertiesMap.get(fileName);

		if (languageProperties1 == null) {
			return;
		}

		for (Map.Entry<String, Properties> propertiesEntry :
				_languagePropertiesMap.entrySet()) {

			String fileName2 = propertiesEntry.getKey();

			if (fileName.equals(fileName2)) {
				continue;
			}

			Properties languageProperties2 = propertiesEntry.getValue();

			for (Map.Entry<Object, Object> entry :
					languageProperties1.entrySet()) {

				String key = (String)entry.getKey();

				if (languageProperties2.containsKey(key)) {
					String value1 = (String)entry.getValue();
					String value2 = (String)languageProperties2.get(key);

					if (!value1.equals(value2)) {
						continue;
					}

					addDuplicateLanguageKey(fileName, key, value1);
					addDuplicateLanguageKey(fileName2, key, value1);
				}
			}
		}
	}

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

		if (portalSource && !fileName.contains("/samples/") &&
			fileName.endsWith("Language.properties") &&
			!isExcludedPath(LANGUAGE_KEYS_CHECK_EXCLUDES, absolutePath)) {

			checkLanguageProperties(fileName);
		}
		else if (fileName.endsWith("liferay-plugin-package.properties")) {
			newContent = formatPluginPackageProperties(
				fileName, absolutePath, content);
		}
		else if (fileName.endsWith("portlet.properties")) {
			newContent = formatPortletProperties(fileName, content);
		}
		else if (fileName.endsWith("source-formatter.properties")) {
			formatSourceFormatterProperties(fileName, content);
		}
		else if ((!portalSource && !subrepository) ||
				 !fileName.endsWith("portal.properties")) {

			formatPortalProperties(fileName, content);
		}

		return formatProperties(fileName, newContent);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected String fixIncorrectLicenses(String absolutePath, String content) {
		if (!absolutePath.contains("/modules/apps/") &&
			!absolutePath.contains("/modules/private/apps/")) {

			return content;
		}

		Matcher matcher = _licensesPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String licenses = matcher.group(1);

		String expectedLicenses = "LGPL";

		if (absolutePath.contains("/modules/private/apps/")) {
			expectedLicenses = "DXP";
		}

		if (licenses.equals(expectedLicenses)) {
			return content;
		}

		return StringUtil.replace(
			content, "licenses=" + licenses, "licenses=" + expectedLicenses,
			matcher.start());
	}

	protected void formatDuplicateLanguageKeys() throws Exception {
		if (_duplicateFileLanguageKeysMap.isEmpty()) {
			return;
		}

		Map<String, Map<String, String>> languagePropertiesTranslationsMap =
			new HashMap<>();

		Map<String, String> allDuplicateLanguageKeysMap = new HashMap<>();

		for (Map.Entry<String, Map<String, String>> entry :
				_duplicateFileLanguageKeysMap.entrySet()) {

			String fileName = entry.getKey();
			Map<String, String> duplicateLanguageKeysMap = entry.getValue();

			removeDuplicateKeys(fileName, duplicateLanguageKeysMap);

			languagePropertiesTranslationsMap = addTranslations(
				fileName, duplicateLanguageKeysMap,
				languagePropertiesTranslationsMap);

			allDuplicateLanguageKeysMap.putAll(duplicateLanguageKeysMap);
		}

		writeLanguageKeysToFile(
			"Language.properties", allDuplicateLanguageKeysMap, true);

		for (Map.Entry<String, Map<String, String>> entry :
				languagePropertiesTranslationsMap.entrySet()) {

			writeLanguageKeysToFile(entry.getKey(), entry.getValue(), false);
		}
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

		content = sortDefinitions(
			fileName, content, new NaturalOrderStringComparator());

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

				line = trimLine(line, true);

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
					 propertyFileName.contains("/private/apps/"))) {

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

	protected String getCategoryName(
		String line, String[][] categoryPrefixAndNameArray) {

		for (String[] categoryPrefixAndName : categoryPrefixAndNameArray) {
			String prefix = categoryPrefixAndName[0];

			if (line.startsWith(prefix)) {
				return categoryPrefixAndName[1];
			}
		}

		return "Messages";
	}

	protected String[][] getCategoryPrefixAndNameArray() {
		return new String[][] {
			new String[] {"action.", "Action names"},
			new String[] {"category.", "Category titles"},
			new String[] {"country.", "Country"},
			new String[] {"currency.", "Currency"},
			new String[] {"javax.portlet.", "Portlet descriptions and titles"},
			new String[] {"lang.", "Language settings"},
			new String[] {"language.", "Language"},
			new String[] {"model.resource.", "Model resources"}
		};
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

	protected String getTranslatedKey(String content, String key) {
		if (content.startsWith(key + "=")) {
			int x = content.indexOf("\n");

			if (x == -1) {
				return content.substring(key.length() + 1);
			}

			return content.substring(key.length() + 1, x);
		}

		int x = content.indexOf("\n" + key + "=");

		if (x == -1) {
			return null;
		}

		int y = x + key.length() + 2;

		int z = content.indexOf("\n", y);

		if (z == -1) {
			return content.substring(y);
		}

		return content.substring(y, z);
	}

	protected void populateLanguagePropertiesMap() throws Exception {
		Map<String, Properties> languagePropertiesMap =
			new ConcurrentHashMap<>();

		String[] includes = new String[] {"**/Language.properties"};

		List<String> modulesLanguagePropertiesNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null, new String[0], includes,
			true);

		for (String fileName : modulesLanguagePropertiesNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			if (isExcludedPath(LANGUAGE_KEYS_CHECK_EXCLUDES, fileName)) {
				continue;
			}

			Properties properties = new Properties();

			InputStream inputStream = new FileInputStream(fileName);

			properties.load(inputStream);

			languagePropertiesMap.put(fileName, properties);
		}

		_languagePropertiesMap = languagePropertiesMap;
	}

	@Override
	protected void postFormat() throws Exception {
		formatDuplicateLanguageKeys();
	}

	@Override
	protected void preFormat() throws Exception {
		_maxLineLength = sourceFormatterArgs.getMaxLineLength();
	}

	protected void removeDuplicateKeys(
			String fileName, Map<String, String> duplicateLanguageKeysMap)
		throws Exception {

		File file = new File(fileName);

		String content = FileUtil.read(file);

		String newContent = content;

		for (Map.Entry<String, String> entry :
				duplicateLanguageKeysMap.entrySet()) {

			String line = entry.getKey() + "=" + entry.getValue();

			if (newContent.startsWith(line)) {
				if (newContent.equals(line)) {
					newContent = StringPool.BLANK;
				}
				else {
					newContent = StringUtil.replace(
						newContent, line + "\n", StringPool.BLANK);
				}
			}
			else {
				newContent = StringUtil.replace(
					newContent, "\n" + line, StringPool.BLANK);
			}
		}

		processFormattedFile(file, fileName, content, newContent);
	}

	protected void writeLanguageKeysToFile(
			String fileName, Map<String, String> languageKeysMap,
			boolean processMessage)
		throws Exception {

		File languagePropertiesFile = new File(
			getFile("portal-impl", PORTAL_MAX_DIR_LEVEL),
			"src/content/" + fileName);

		if (!languagePropertiesFile.exists()) {
			return;
		}

		String languagePropertiesContent = FileUtil.read(
			languagePropertiesFile);

		String newLanguagePropertiesContent = languagePropertiesContent;

		String[][] categoryPrefixAndNameArray = getCategoryPrefixAndNameArray();

		StringBundler sb = new StringBundler();

		if (processMessage) {
			sb.append("The following language keys were used in multiple ");
			sb.append("modules and have been consolidated, or they already ");
			sb.append("existed in ");
			sb.append("portal-impl\\src\\content\\Language.properties:");
			sb.append("\n");
		}

		for (Map.Entry<String, String> entry : languageKeysMap.entrySet()) {
			String line = entry.getKey() + "=" + entry.getValue();

			if (processMessage) {
				sb.append(line);
				sb.append("\n");
			}

			String categoryName = getCategoryName(
				line, categoryPrefixAndNameArray);

			int pos = newLanguagePropertiesContent.indexOf(
				"## " + categoryName);

			for (int i = 0; i < 3; i++) {
				pos = newLanguagePropertiesContent.indexOf("\n", pos + 1);
			}

			if (!newLanguagePropertiesContent.contains("\n" + line + "\n")) {
				newLanguagePropertiesContent = StringUtil.insert(
					newLanguagePropertiesContent, line + "\n", pos + 1);
			}
		}

		if (processMessage) {
			processMessage(
				"portal-impl/src/content/" + fileName, sb.toString());
		}

		processFormattedFile(
			languagePropertiesFile, "portal-impl/src/content/" + fileName,
			languagePropertiesContent, newLanguagePropertiesContent);
	}

	private final Map<String, Map<String, String>>
		_duplicateFileLanguageKeysMap = new ConcurrentHashMap<>();
	private Map<String, Properties> _languagePropertiesMap;
	private final Pattern _licensesPattern = Pattern.compile(
		"\nlicenses=(\\w+)\n");
	private int _maxLineLength;
	private String _portalPortalPropertiesContent;
	private final Pattern _singleValueOnMultipleLinesPattern = Pattern.compile(
		"\n.*=(\\\\\n *).*(\n[^ ]|\\Z)");

}