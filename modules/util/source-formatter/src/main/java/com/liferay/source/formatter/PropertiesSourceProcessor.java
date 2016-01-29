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
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		if (portalSource) {
			return new String[] {
				"**/Language.properties", "**/portal.properties",
				"**/portal-ext.properties", "**/portal-legacy-*.properties",
				"**/portlet.properties", "**/source-formatter.properties"
			};
		}

		return new String[] {
			"**/portal.properties", "**/portal-ext.properties",
			"**/portlet.properties", "**/source-formatter.properties"
		};
	}

	protected void addDuplicateLanguageKey(String fileName, String line) {
		Set<String> duplicateLines = _duplicateLanguageKeyLinesMap.get(
			fileName);

		if (duplicateLines == null) {
			duplicateLines = new HashSet<>();
		}

		duplicateLines.add(line);

		_duplicateLanguageKeyLinesMap.put(fileName, duplicateLines);
	}

	protected void checkLanguageProperties(String fileName) throws Exception {
		if (_languagePropertiesMap == null) {
			populateLanguagePropertiesMap();
		}

		Properties languageProperties1 = _languagePropertiesMap.get(fileName);

		_languagePropertiesMap.remove(fileName);

		if (languageProperties1 == null) {
			return;
		}

		for (Map.Entry<String, Properties> propertiesEntry :
				_languagePropertiesMap.entrySet()) {

			String fileName2 = propertiesEntry.getKey();
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

					String line = key + "=" + value1;

					addDuplicateLanguageKey(fileName, line);

					if (!fileName2.endsWith(
							"portal-impl/src/content/Language.properties")) {

						addDuplicateLanguageKey(fileName2, line);
					}
				}
			}
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		if (portalSource && !fileName.contains("/samples/") &&
			fileName.endsWith("Language.properties")) {

			checkLanguageProperties(fileName);
		}
		else if (fileName.endsWith("portlet.properties")) {
			newContent = formatPortletProperties(fileName, content);
		}
		else if (fileName.endsWith("source-formatter.properties")) {
			formatSourceFormatterProperties(fileName, content);
		}
		else if (!portalSource || !fileName.endsWith("portal.properties")) {
			formatPortalProperties(fileName, content);
		}

		return formatProperties(newContent);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected void formatDuplicateLanguageKeys() throws Exception {
		if (_duplicateLanguageKeyLinesMap.isEmpty()) {
			return;
		}

		Set<String> allDuplicateLines = new HashSet<>();

		for (Map.Entry<String, Set<String>> entry :
				_duplicateLanguageKeyLinesMap.entrySet()) {

			Set<String> duplicateLines = entry.getValue();

			removeDuplicateKeys(entry.getKey(), duplicateLines);

			allDuplicateLines.addAll(duplicateLines);
		}

		File coreLanguagePropertiesFile = new File(
			getFile("portal-impl", PORTAL_MAX_DIR_LEVEL),
			"src/content/Language.properties");

		String coreLanguagePropertiesContent = FileUtil.read(
			coreLanguagePropertiesFile);

		String newCoreLanguagePropertiesContent = coreLanguagePropertiesContent;

		String[][] categoryPrefixAndNameArray = getCategoryPrefixAndNameArray();

		for (String line : allDuplicateLines) {
			String categoryName = getCategoryName(
				line, categoryPrefixAndNameArray);

			int pos = newCoreLanguagePropertiesContent.indexOf(
				"## " + categoryName);

			for (int i = 0; i < 3; i++) {
				pos = newCoreLanguagePropertiesContent.indexOf("\n", pos + 1);
			}

			if (!newCoreLanguagePropertiesContent.contains(
					"\n" + line + "\n")) {

				newCoreLanguagePropertiesContent = StringUtil.insert(
					newCoreLanguagePropertiesContent, line + "\n", pos + 1);
			}
		}

		if (!coreLanguagePropertiesContent.equals(
				newCoreLanguagePropertiesContent)) {

			processFormattedFile(
				coreLanguagePropertiesFile,
				"portal-impl/src/content/Language.properties",
				coreLanguagePropertiesContent,
				newCoreLanguagePropertiesContent);
		}
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
					processErrorMessage(
						fileName, "sort " + fileName + " " + lineCount);
				}

				previousPos = pos;
			}
		}
	}

	protected String formatPortletProperties(String fileName, String content)
		throws Exception {

		if (!content.contains("include-and-override=portlet-ext.properties")) {
			content =
				"include-and-override=portlet-ext.properties" + "\n\n" +
					content;
		}

		if (!portalSource) {
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

					processErrorMessage(
						fileName, "sort: " + fileName + " " + lineCount);
				}

				previousProperty = property;
			}
		}

		return content;
	}

	protected String formatProperties(String content) throws Exception {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(line, true);

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

		String path = StringPool.BLANK;

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		if (pos != -1) {
			path = fileName.substring(0, pos + 1);
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
				if (propertyFileName.startsWith("**") ||
					propertyFileName.endsWith("**")) {

					continue;
				}

				pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				File file = new File(path + propertyFileName);

				if (!file.exists()) {
					processErrorMessage(
						fileName,
						"Incorrect property value: " + propertyFileName + " " +
							fileName);
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

		if (portalSource) {
			File file = getFile(
				"portal-impl/src/portal.properties",
				BaseSourceProcessor.PORTAL_MAX_DIR_LEVEL);

			_portalPortalPropertiesContent = FileUtil.read(file);

			return _portalPortalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			_portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			_portalPortalPropertiesContent = StringPool.BLANK;
		}

		return _portalPortalPropertiesContent;
	}

	protected void populateLanguagePropertiesMap() throws Exception {
		_languagePropertiesMap = new HashMap<>();

		String[] includes = new String[] {"**/Language.properties"};

		List<String> modulesLanguagePropertiesNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null, new String[0],
			includes);

		for (String fileName : modulesLanguagePropertiesNames) {
			Properties properties = new Properties();

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			InputStream inputStream = new FileInputStream(fileName);

			properties.load(inputStream);

			_languagePropertiesMap.put(fileName, properties);
		}
	}

	@Override
	protected void postFormat() throws Exception {
		formatDuplicateLanguageKeys();
	}

	protected void removeDuplicateKeys(String fileName, Set<String> lines)
		throws Exception {

		File file = new File(fileName);

		String content = FileUtil.read(file);

		String newContent = content;

		for (String line : lines) {
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

	private Map<String, Set<String>> _duplicateLanguageKeyLinesMap =
		new HashMap<>();
	private Map<String, Properties> _languagePropertiesMap;
	private String _portalPortalPropertiesContent;

}