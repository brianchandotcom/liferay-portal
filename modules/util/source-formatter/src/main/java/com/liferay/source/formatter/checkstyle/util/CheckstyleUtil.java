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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckstyleUtil {

	public static final int BATCH_SIZE = 1000;

	public static List<String> getCheckNames(Configuration configuration) {
		List<String> checkNames = new ArrayList<>();

		String name = configuration.getName();

		if (name.startsWith("com.liferay.")) {
			int pos = name.lastIndexOf(CharPool.PERIOD);

			checkNames.add(name.substring(pos + 1));
		}

		for (Configuration childConfiguration : configuration.getChildren()) {
			checkNames.addAll(getCheckNames(childConfiguration));
		}

		return checkNames;
	}

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

		List<String> checkNames = new ArrayList<>();

		DefaultConfiguration treeWalkerConfiguration = _getChildConfiguration(
			configuration, "TreeWalker");

		Configuration[] checkConfigurations =
			treeWalkerConfiguration.getChildren();

		if (checkConfigurations == null) {
			return configuration;
		}

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

			if (!checkName.startsWith("com.liferay.")) {
				continue;
			}

			checkNames.add(checkName);

			DefaultConfiguration defaultConfiguration =
				new DefaultConfiguration(checkName);

			Map<String, String> messages = checkConfiguration.getMessages();

			for (Map.Entry<String, String> entry : messages.entrySet()) {
				defaultConfiguration.addMessage(
					entry.getKey(), entry.getValue());
			}

			JSONObject attributesJSONObject = _getAttributesJSONObject(
				propertiesMap, checkSimpleName, checkConfiguration,
				sourceFormatterArgs);

			defaultConfiguration.addAttribute(
				_ATTRIBUTES_KEY, attributesJSONObject.toString());

			treeWalkerConfiguration.removeChild(checkConfiguration);

			treeWalkerConfiguration.addChild(defaultConfiguration);
		}

		if (sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(
				CheckType.CHECKSTYLE, getCheckNames(configuration));
		}

		return configuration;
	}

	private static Configuration _addAttribute(
		Configuration configuration, String key, String value,
		String... regexChecks) {

		Configuration[] checkConfigurations = _getCheckConfigurations(
			configuration);

		if (checkConfigurations == null) {
			return configuration;
		}

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String name = checkConfiguration.getName();

			for (String regexCheck : regexChecks) {
				if (name.matches(regexCheck)) {
					DefaultConfiguration defaultChildConfiguration =
						(DefaultConfiguration)checkConfiguration;

					defaultChildConfiguration.addAttribute(key, value);
				}
			}
		}

		return configuration;
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

	private static Configuration _addPropertiesAttributes(
			Configuration configuration, Map<String, Properties> propertiesMap)
		throws CheckstyleException {

		Configuration[] checkConfigurations = _getCheckConfigurations(
			configuration);

		if (checkConfigurations == null) {
			return configuration;
		}

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String checkName = SourceFormatterUtil.getSimpleName(
				checkConfiguration.getName());

			List<String> attributeNames = SourceFormatterUtil.getAttributeNames(
				CheckType.CHECKSTYLE, checkName, propertiesMap);

			for (String attributeName : attributeNames) {
				String value = SourceFormatterUtil.getPropertyValue(
					attributeName, CheckType.CHECKSTYLE, checkName,
					propertiesMap);

				if (Validator.isNull(value)) {
					continue;
				}

				DefaultConfiguration defaultChildConfiguration =
					(DefaultConfiguration)checkConfiguration;

				if (Validator.isBoolean(value) || Validator.isNumber(value)) {
					configuration = _overrideAttributeValue(
						configuration, defaultChildConfiguration, attributeName,
						value);
				}
				else {
					defaultChildConfiguration.addAttribute(
						attributeName, value);
				}
			}
		}

		return configuration;
	}

	private static Configuration _filterCheck(
		Configuration configuration, String checkName) {

		DefaultConfiguration treeWalkerConfiguration = _getChildConfiguration(
			configuration, "TreeWalker");

		Configuration[] checkConfigurations =
			treeWalkerConfiguration.getChildren();

		if (checkConfigurations == null) {
			return configuration;
		}

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			if (!checkName.equals(
					SourceFormatterUtil.getSimpleName(
						checkConfiguration.getName()))) {

				DefaultConfiguration defaultChildConfiguration =
					(DefaultConfiguration)checkConfiguration;

				treeWalkerConfiguration.removeChild(defaultChildConfiguration);
			}
		}

		return configuration;
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
				{"baseDirName", sourceFormatterArgs.getBaseDirName()},
				{
					"maxLineLength",
					String.valueOf(sourceFormatterArgs.getMaxLineLength())
				},
				{
					"showDebugInformation",
					String.valueOf(sourceFormatterArgs.isShowDebugInformation())
				}
			});

		attributesJSONObject.put(
			SourceFormatterUtil.CONFIGURATION_FILE_LOCATION,
			configurationAttributesJSONObject);

		attributesJSONObject = SourceFormatterUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap,
			SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH);

		attributesJSONObject = SourceFormatterUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap, CheckType.CHECKSTYLE,
			checkName);

		return attributesJSONObject;
	}

	private static Configuration[] _getCheckConfigurations(
		Configuration configuration) {

		DefaultConfiguration treeWalkerConfiguration = _getChildConfiguration(
			configuration, "TreeWalker");

		if (treeWalkerConfiguration == null) {
			return null;
		}

		return treeWalkerConfiguration.getChildren();
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

	private static Configuration _overrideAttributeValue(
			Configuration configuration,
			DefaultConfiguration defaultChildConfiguration,
			String attributeName, String value)
		throws CheckstyleException {

		DefaultConfiguration treeWalkerConfiguration = _getChildConfiguration(
			configuration, "TreeWalker");

		DefaultConfiguration copyConfiguration = new DefaultConfiguration(
			defaultChildConfiguration.getName());

		Map<String, String> messages = defaultChildConfiguration.getMessages();

		for (Map.Entry<String, String> entry : messages.entrySet()) {
			copyConfiguration.addMessage(entry.getKey(), entry.getValue());
		}

		String[] attributeNames = defaultChildConfiguration.getAttributeNames();

		for (String name : attributeNames) {
			if (name.equals(attributeName)) {
				copyConfiguration.addAttribute(name, value);
			}
			else {
				copyConfiguration.addAttribute(
					name, defaultChildConfiguration.getAttribute(name));
			}
		}

		if (!ArrayUtil.contains(attributeNames, attributeName)) {
			copyConfiguration.addAttribute(attributeName, value);
		}

		treeWalkerConfiguration.removeChild(defaultChildConfiguration);

		treeWalkerConfiguration.addChild(copyConfiguration);

		return configuration;
	}

	private static final String _ATTRIBUTES_KEY = "attributes";

}