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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader.IgnoredModulesOptions;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckstyleUtil {

	public static final int BATCH_SIZE = 1000;

	public static Map<String, String> getAttributesMap(
			String checkName, Configuration configuration)
		throws CheckstyleException {

		if (Validator.isNull(checkName) || (configuration == null)) {
			return Collections.emptyMap();
		}

		Configuration[] checkConfigurations = _getCheckConfigurations(
			configuration);

		if (checkConfigurations == null) {
			return Collections.emptyMap();
		}

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String simpleName = SourceFormatterUtil.getSimpleName(
				checkConfiguration.getName());

			if (!Objects.equals(simpleName, checkName)) {
				continue;
			}

			DefaultConfiguration defaultConfiguration =
				(DefaultConfiguration)checkConfiguration;

			String[] attributeNames = defaultConfiguration.getAttributeNames();

			Map<String, String> attributesMap = new HashMap<>();

			for (String attributeName : attributeNames) {
				String attributeValue = defaultConfiguration.getAttribute(
					attributeName);

				attributesMap.put(attributeName, attributeValue);
			}

			return attributesMap;
		}

		return Collections.emptyMap();
	}

	public static String getAttributeValue(
			String checkName, String attributeName, Configuration configuration)
		throws CheckstyleException {

		Map<String, String> attributesMap = getAttributesMap(
			checkName, configuration);

		return GetterUtil.getString(attributesMap.get(attributeName));
	}

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
			IgnoredModulesOptions.EXECUTE);

		String checkName = sourceFormatterArgs.getCheckName();

		if (checkName != null) {
			configuration = _filterCheck(configuration, checkName);
		}

		configuration = _addAttribute(
			configuration, "maxLineLength",
			String.valueOf(sourceFormatterArgs.getMaxLineLength()),
			"com.liferay.source.formatter.checkstyle.checks.AppendCheck",
			"com.liferay.source.formatter.checkstyle.checks.ConcatCheck",
			"com.liferay.source.formatter.checkstyle.checks." +
				"PlusStatementCheck");
		configuration = _addAttribute(
			configuration, "runOutsidePortalExcludes",
			SourceFormatterUtil.getPropertyValue(
				"run.outside.portal.excludes", propertiesMap),
			"com.liferay.source.formatter.checkstyle.checks." +
				"ParsePrimitiveTypeCheck");
		configuration = _addAttribute(
			configuration, "showDebugInformation",
			String.valueOf(sourceFormatterArgs.isShowDebugInformation()),
			"com.liferay.*");

		configuration = _addPropertiesAttributes(configuration, propertiesMap);

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

}