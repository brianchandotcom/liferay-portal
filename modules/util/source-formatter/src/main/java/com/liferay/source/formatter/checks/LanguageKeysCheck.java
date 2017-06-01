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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class LanguageKeysCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_portalLanguageProperties = getPortalLanguageProperties();
	}

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!isSubrepository() &&
			!fileName.endsWith("JSPLanguageUtilCheck.java")) {

			_checkLanguageKeys(fileName, absolutePath, content, getPatterns());
		}

		return content;
	}

	protected List<Pattern> getPatterns() {
		return Arrays.asList(languageKeyPattern);
	}

	protected final Pattern languageKeyPattern = Pattern.compile(
		"LanguageUtil.(?:get|format)\\([^;%]+|Liferay.Language.get\\('([^']+)");

	private void _checkLanguageKeys(
			String fileName, String absolutePath, String content,
			List<Pattern> patterns)
		throws Exception {

		if (fileName.endsWith(".vm")) {
			return;
		}

		for (Pattern pattern : patterns) {
			_checkLanguageKeys(fileName, absolutePath, content, pattern);
		}
	}

	private void _checkLanguageKeys(
			String fileName, String absolutePath, String content,
			Pattern pattern)
		throws Exception {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String[] languageKeys = _getLanguageKeys(matcher);

			for (String languageKey : languageKeys) {
				if (Validator.isNumber(languageKey) ||
					languageKey.endsWith(StringPool.CLOSE_CURLY_BRACE) ||
					languageKey.endsWith(StringPool.DASH) ||
					languageKey.endsWith(StringPool.OPEN_BRACKET) ||
					languageKey.endsWith(StringPool.PERIOD) ||
					languageKey.endsWith(StringPool.UNDERLINE) ||
					languageKey.startsWith(StringPool.DASH) ||
					languageKey.startsWith(StringPool.DOLLAR) ||
					languageKey.startsWith(StringPool.OPEN_BRACKET) ||
					languageKey.startsWith(StringPool.OPEN_CURLY_BRACE) ||
					languageKey.startsWith(StringPool.PERIOD) ||
					languageKey.startsWith(StringPool.UNDERLINE) ||
					_portalLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				Properties moduleLanguageProperties =
					_getModuleLanguageProperties(fileName);

				if ((moduleLanguageProperties != null) &&
					moduleLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				Properties moduleLangLanguageProperties =
					_getModuleLangLanguageProperties(absolutePath);

				if ((moduleLangLanguageProperties != null) &&
					moduleLangLanguageProperties.containsKey(languageKey)) {

					continue;
				}

				BNDSettings bndSettings = getBNDSettings(fileName);

				if (bndSettings == null) {
					addMessage(
						fileName, "Missing language key '" + languageKey + "'");

					continue;
				}

				Properties bndFileLanguageProperties =
					bndSettings.getLanguageProperties();

				if ((bndFileLanguageProperties != null) &&
					!bndFileLanguageProperties.containsKey(languageKey)) {

					addMessage(
						fileName, "Missing language key '" + languageKey + "'");
				}

				putBNDSettings(bndSettings);
			}
		}
	}

	private String[] _getLanguageKeys(Matcher matcher) {
		int groupCount = matcher.groupCount();

		if (groupCount == 1) {
			String languageKey = matcher.group(1);

			if (Validator.isNotNull(languageKey)) {
				return new String[] {languageKey};
			}
		}
		else if (groupCount == 2) {
			String languageKey = matcher.group(2);

			languageKey = TextFormatter.format(languageKey, TextFormatter.K);

			return new String[] {languageKey};
		}

		StringBundler sb = new StringBundler();

		String match = matcher.group();

		int count = 0;

		for (int i = 0; i < match.length(); i++) {
			char c = match.charAt(i);

			switch (c) {
				case CharPool.CLOSE_PARENTHESIS:
					if (count <= 1) {
						return new String[0];
					}

					count--;

					break;

				case CharPool.OPEN_PARENTHESIS:
					count++;

					break;

				case CharPool.QUOTE:
					if (count > 1) {
						break;
					}

					while (i < match.length()) {
						i++;

						if (match.charAt(i) == CharPool.QUOTE) {
							String languageKey = sb.toString();

							if (match.startsWith("names")) {
								return StringUtil.split(languageKey);
							}
							else {
								return new String[] {languageKey};
							}
						}

						sb.append(match.charAt(i));
					}
			}
		}

		return new String[0];
	}

	private List<String> _getModuleLangDirNames(
		String moduleLocation, String buildGradleContent) {

		List<String> moduleLangDirNames = new ArrayList<>();

		Matcher matcher = _mergeLangPattern.matcher(buildGradleContent);

		if (matcher.find()) {
			String[] sourceDirs = StringUtil.split(matcher.group(1));

			for (String sourceDir : sourceDirs) {
				sourceDir = StringUtil.trim(sourceDir);

				moduleLangDirNames.add(
					moduleLocation + StringPool.SLASH +
						sourceDir.substring(1, sourceDir.length() - 1));
			}

			return moduleLangDirNames;
		}

		int x = moduleLocation.lastIndexOf(StringPool.SLASH);

		String baseModuleName = moduleLocation.substring(0, x);

		int y = baseModuleName.lastIndexOf(StringPool.SLASH);

		baseModuleName = baseModuleName.substring(
			y + 1, baseModuleName.length());

		String moduleLangDirName =
			moduleLocation.substring(0, x + 1) + baseModuleName +
				"-lang/src/main/resources/content";

		File moduleLangDir = new File(moduleLangDirName);

		if (!moduleLangDir.exists() &&
			moduleLangDirName.contains("/modules/ee/")) {

			moduleLangDirName = StringUtil.replaceFirst(
				moduleLangDirName, "/modules/ee/", "/modules/");
		}

		moduleLangDirNames.add(moduleLangDirName);

		return moduleLangDirNames;
	}

	private Properties _getModuleLangLanguageProperties(String absolutePath)
		throws Exception {

		Properties properties = _moduleLangLanguagePropertiesMap.get(
			absolutePath);

		if (properties != null) {
			return properties;
		}

		String buildGradleContent = null;
		String buildGradleFileLocation = absolutePath;

		while (true) {
			int pos = buildGradleFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			buildGradleFileLocation = buildGradleFileLocation.substring(
				0, pos + 1);

			File file = new File(buildGradleFileLocation + "build.gradle");

			if (file.exists()) {
				buildGradleContent = FileUtil.read(file);

				break;
			}

			buildGradleFileLocation = StringUtil.replaceLast(
				buildGradleFileLocation, CharPool.SLASH, StringPool.BLANK);
		}

		Matcher matcher = _applyLangMergerPluginPattern.matcher(
			buildGradleContent);

		if (!matcher.find()) {
			return null;
		}

		String moduleLocation = StringUtil.replaceLast(
			buildGradleFileLocation, CharPool.SLASH, StringPool.BLANK);

		List<String> moduleLangDirNames = _getModuleLangDirNames(
			moduleLocation, buildGradleContent);

		properties = new Properties();

		for (String moduleLangDirName : moduleLangDirNames) {
			String moduleLangLanguagePropertiesFileName =
				moduleLangDirName + "/Language.properties";

			File file = new File(moduleLangLanguagePropertiesFileName);

			if (!file.exists()) {
				continue;
			}

			InputStream inputStream = new FileInputStream(file);

			properties.load(inputStream);
		}

		_moduleLangLanguagePropertiesMap.put(absolutePath, properties);

		return properties;
	}

	private Properties _getModuleLanguageProperties(String fileName) {
		Properties properties = _moduleLanguagePropertiesMap.get(fileName);

		if (properties != null) {
			return properties;
		}

		StringBundler sb = new StringBundler(3);

		int pos = fileName.indexOf("/docroot/");

		if (pos != -1) {
			sb.append(fileName.substring(0, pos + 9));
			sb.append("WEB-INF/src/");
		}
		else {
			pos = fileName.indexOf("src/");

			if (pos == -1) {
				return null;
			}

			sb.append(fileName.substring(0, pos + 4));

			if (fileName.contains("src/main/")) {
				sb.append("main/resources/");
			}
		}

		sb.append("content/Language.properties");

		try {
			properties = new Properties();

			InputStream inputStream = new FileInputStream(sb.toString());

			properties.load(inputStream);

			_moduleLanguagePropertiesMap.put(fileName, properties);

			return properties;
		}
		catch (Exception e) {
		}

		return null;
	}

	private final Pattern _applyLangMergerPluginPattern = Pattern.compile(
		"^apply[ \t]+plugin[ \t]*:[ \t]+\"com.liferay.lang.merger\"$",
		Pattern.MULTILINE);
	private final Pattern _mergeLangPattern = Pattern.compile(
		"mergeLang \\{\\s*sourceDirs = \\[(.*?)\\]", Pattern.DOTALL);
	private final Map<String, Properties> _moduleLangLanguagePropertiesMap =
		new HashMap<>();
	private final Map<String, Properties> _moduleLanguagePropertiesMap =
		new HashMap<>();
	private Properties _portalLanguageProperties;

}