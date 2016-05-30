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

package com.liferay.portal.language.util;

import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class LanguagePropertyTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		List<String> coreFilePaths = _getPaths(_CORE_GLOB);
		List<String> moduleFilePaths = _getPaths(_MODULES_GLOB);

		_corePropertiesMap = _getPropertiesMap(coreFilePaths);
		_modulePropertiesMap = _getPropertiesMap(moduleFilePaths);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyDir() {
		_assertLanguageSettingNotPresent(LanguageConstants.KEY_DIR);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyLineBegin() {
		_assertLanguageSettingNotPresent(LanguageConstants.KEY_LINE_BEGIN);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyLineEnd() {
		_assertLanguageSettingNotPresent(LanguageConstants.KEY_LINE_END);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyUserNameFieldNames() {
		_assertLanguageSettingNotPresent(
			LanguageConstants.KEY_USER_NAME_FIELD_NAMES);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyUserNamePrefixValues() {
		_assertLanguageSettingNotPresent(
			LanguageConstants.KEY_USER_NAME_PREFIX_VALUES);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyUserNameRequiredFieldNames() {
		_assertLanguageSettingNotPresent(
			LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES);
	}

	@Test
	public void testModulesNoLanguageSettingsKeyUserNameSuffixValues() {
		_assertLanguageSettingNotPresent(
			LanguageConstants.KEY_USER_NAME_SUFFIX_VALUES);
	}

	@Test
	public void testUserNameRequiredFieldNamesSubsetOfUserNameFieldNames() {
		Set<String> paths = _corePropertiesMap.keySet();

		List<String> failureMessages = new ArrayList<>();

		for (String path : paths) {
			Properties properties = _corePropertiesMap.get(path);

			String userNameFieldNamesString = properties.getProperty(
				LanguageConstants.KEY_USER_NAME_FIELD_NAMES);
			String userNameRequiredFieldNamesString = properties.getProperty(
				LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES);

			if ((userNameFieldNamesString == null) ||
				(userNameRequiredFieldNamesString == null)) {

				continue;
			}

			String[] userNameFieldNames = StringUtil.split(
				userNameFieldNamesString);
			String[] userNameRequiredFieldNames = StringUtil.split(
				userNameRequiredFieldNamesString);

			for (String userNameRequiredFieldName :
					userNameRequiredFieldNames) {

				if (!ArrayUtil.contains(
						userNameFieldNames, userNameRequiredFieldName)) {

					failureMessages.add(path);
				}
			}
		}

		if (!failureMessages.isEmpty()) {
			Assert.fail(
				"Required field names are not a subset of user name field " +
					"names in " + failureMessages);
		}
	}

	@Test
	public void testValidLanguageSettingValueLangDir() {
		_assertValidLanguageSettingValue(LanguageConstants.KEY_DIR);
	}

	@Test
	public void testValidLanguageSettingValueLangLineBegin() {
		_assertValidLanguageSettingValue(LanguageConstants.KEY_LINE_BEGIN);
	}

	@Test
	public void testValidLanguageSettingValueLangLineEnd() {
		_assertValidLanguageSettingValue(LanguageConstants.KEY_LINE_END);
	}

	@Test
	public void testValidLanguageSettingValueLangUserNameFieldNames() {
		_assertValidLanguageSettingValue(
			LanguageConstants.KEY_USER_NAME_FIELD_NAMES);
	}

	@Test
	public void testValidLanguageSettingValueLangUserNameRequiredFieldNames() {
		_assertValidLanguageSettingValue(
			LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES);
	}

	private static List<String> _getPaths(String glob) throws IOException {
		final PathMatcher includePatternMatcher =
			FileSystems.getDefault().getPathMatcher("glob:" + glob);

		final List<String> paths = new ArrayList<>();

		FileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(
					Path path, BasicFileAttributes attrs)
				throws IOException {

				if (includePatternMatcher.matches(path)) {
					paths.add(path.toString());
				}

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {

				return FileVisitResult.CONTINUE;
			}

		};

		Files.walkFileTree(Paths.get(_BASE_DIR), simpleFileVisitor);

		return paths;
	}

	private static Map<String, Properties> _getPropertiesMap(List<String> paths)
		throws IOException {

		Map<String, Properties> propertiesMap = new HashMap<>();

		for (String path : paths) {
			Properties properties = new Properties();

			try (InputStream inputStream = new FileInputStream(path)) {
				properties.load(inputStream);
			}

			propertiesMap.put(path, properties);
		}

		return propertiesMap;
	}

	private void _assertLanguageSettingNotPresent(String key) {
		Set<String> paths = _modulePropertiesMap.keySet();

		List<String> failureMessages = new ArrayList<>();

		for (String path : paths) {
			Properties properties = _modulePropertiesMap.get(path);

			Set<String> propertyNames = properties.stringPropertyNames();

			if (propertyNames.contains(key)) {
				failureMessages.add(path);
			}
		}

		if (!failureMessages.isEmpty()) {
			Assert.fail(
				"Language setting \"" + key + "\" found in " + failureMessages);
		}
	}

	private void _assertValidLanguageSettingValue(String key) {
		Set<String> paths = _corePropertiesMap.keySet();

		List<String> failureMessages = new ArrayList<>();

		for (String path : paths) {
			Properties properties = _corePropertiesMap.get(path);

			String value = properties.getProperty(key);

			if ((value != null) && !LanguageValidator.isValid(key, value)) {
				failureMessages.add(path);
			}
		}

		if (!failureMessages.isEmpty()) {
			Assert.fail(
				"Invalid values for Language setting \"" + key +
					"\" found in " + failureMessages);
		}
	}

	private static final String _BASE_DIR = "./";

	private static final String _CORE_GLOB =
		"./portal-impl/src/**/Language*.properties";

	private static final String _MODULES_GLOB =
		"./modules/**/src/**/Language*.properties";

	private static Map<String, Properties> _corePropertiesMap;
	private static Map<String, Properties> _modulePropertiesMap;

}