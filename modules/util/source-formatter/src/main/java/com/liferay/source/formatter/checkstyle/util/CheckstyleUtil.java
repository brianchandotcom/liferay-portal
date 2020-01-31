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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckstyleUtil {

	public static final String BASE_DIR_NAME_KEY = "baseDirName";

	public static final int BATCH_SIZE = 1000;

	public static final String MAX_LINE_LENGTH_KEY = "maxLineLength";

	public static final String SHOW_DEBUG_INFORMATION_KEY =
		"showDebugInformation";

	public static Configuration getConfiguration(
			String configurationFileName, Map<String, Properties> propertiesMap,
			SourceFormatterArgs sourceFormatterArgs)
		throws CheckstyleException {

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		Configuration configuration = ConfigurationLoader.loadConfiguration(
			new InputSource(
				classLoader.getResourceAsStream(configurationFileName)),
			new PropertiesExpander(System.getProperties()),
			ConfigurationLoader.IgnoredModulesOptions.EXECUTE);

		DefaultConfiguration treeWalkerConfiguration = _getChildConfiguration(
			configuration, "TreeWalker");

		Configuration[] checkConfigurations =
			treeWalkerConfiguration.getChildren();

		if (checkConfigurations == null) {
			return configuration;
		}

		JSONObject excludesJSONObject =
			SourceFormatterCheckUtil.getExcludesJSONObject(propertiesMap);

		List<String> checkNames = new ArrayList<>();

		String filterCheckName = sourceFormatterArgs.getCheckName();

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String checkName = checkConfiguration.getName();

			String checkSimpleName = SourceFormatterUtil.getSimpleName(
				checkName);

			if ((filterCheckName != null) &&
				!filterCheckName.equals(checkSimpleName)) {

				treeWalkerConfiguration.removeChild(checkConfiguration);

				continue;
			}

			DefaultConfiguration defaultConfiguration =
				new DefaultConfiguration(checkName);

			Map<String, String> messages = checkConfiguration.getMessages();

			for (Map.Entry<String, String> entry : messages.entrySet()) {
				defaultConfiguration.addMessage(
					entry.getKey(), entry.getValue());
			}

			if (checkName.startsWith("com.liferay.")) {
				checkNames.add(checkSimpleName);

				if (excludesJSONObject.length() != 0) {
					defaultConfiguration.addAttribute(
						_EXCLUDES_KEY, excludesJSONObject.toString());
				}

				JSONObject attributesJSONObject = _getAttributesJSONObject(
					propertiesMap, checkSimpleName, checkConfiguration,
					sourceFormatterArgs);

				defaultConfiguration.addAttribute(
					_ATTRIBUTES_KEY, attributesJSONObject.toString());
			}
			else {
				for (String attributeName :
						checkConfiguration.getAttributeNames()) {

					if (!attributeName.equals("description") &&
						!attributeName.equals("documentationLocation")) {

						defaultConfiguration.addAttribute(
							attributeName,
							checkConfiguration.getAttribute(attributeName));
					}
				}
			}

			treeWalkerConfiguration.removeChild(checkConfiguration);

			treeWalkerConfiguration.addChild(defaultConfiguration);
		}

		if (sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(CheckType.CHECKSTYLE, checkNames);
		}

		return configuration;
	}

	public static List<String> getLines(String s) throws IOException {
		List<String> lines = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(s))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

	private static JSONObject _addCustomAttributes(
		JSONObject jsonObject, String[][] attributesArray) {

		for (String[] values : attributesArray) {
			JSONArray jsonArray = new JSONArrayImpl();

			jsonArray.put(values[1]);

			jsonObject.put(values[0], jsonArray);
		}

		return jsonObject;
	}

	private static JSONObject _getAttributesJSONObject(
			Map<String, Properties> propertiesMap, String checkName,
			Configuration configuration,
			SourceFormatterArgs sourceFormatterArgs)
		throws CheckstyleException {

		JSONObject attributesJSONObject = new JSONObjectImpl();

		JSONObject configurationAttributesJSONObject =
			_getConfigurationAttributesJSONObject(configuration);

		configurationAttributesJSONObject = _addCustomAttributes(
			configurationAttributesJSONObject,
			new String[][] {
				{BASE_DIR_NAME_KEY, sourceFormatterArgs.getBaseDirName()},
				{
					MAX_LINE_LENGTH_KEY,
					String.valueOf(sourceFormatterArgs.getMaxLineLength())
				},
				{
					SHOW_DEBUG_INFORMATION_KEY,
					String.valueOf(sourceFormatterArgs.isShowDebugInformation())
				}
			});

		attributesJSONObject.put(
			SourceFormatterCheckUtil.CONFIGURATION_FILE_LOCATION,
			configurationAttributesJSONObject);

		attributesJSONObject = SourceFormatterCheckUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap,
			SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH);

		attributesJSONObject = SourceFormatterCheckUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap, CheckType.CHECKSTYLE,
			checkName);

		return attributesJSONObject;
	}

	private static DefaultConfiguration _getChildConfiguration(
		Configuration configuration, String name) {

		if (!(configuration instanceof DefaultConfiguration)) {
			return null;
		}

		DefaultConfiguration defaultConfiguration =
			(DefaultConfiguration)configuration;

		for (Configuration childConfiguration :
				defaultConfiguration.getChildren()) {

			String configurationName = childConfiguration.getName();

			if (configurationName.equals(name) &&
				(childConfiguration instanceof DefaultConfiguration)) {

				return (DefaultConfiguration)childConfiguration;
			}
		}

		return null;
	}

	private static JSONObject _getConfigurationAttributesJSONObject(
			Configuration configuration)
		throws CheckstyleException {

		JSONObject configurationAttributesJSONObject = new JSONObjectImpl();

		for (String attributeName : configuration.getAttributeNames()) {
			JSONArray jsonArray = new JSONArrayImpl();

			String[] values = StringUtil.split(
				configuration.getAttribute(attributeName), StringPool.COMMA);

			for (String value : values) {
				jsonArray.put(value);
			}

			configurationAttributesJSONObject.put(attributeName, jsonArray);
		}

		return configurationAttributesJSONObject;
	}

	private static final String _ATTRIBUTES_KEY = "attributes";

	private static final String _EXCLUDES_KEY = "excludes";

}