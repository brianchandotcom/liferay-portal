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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
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

	public static String getCheckName(String name) {
		int pos = name.lastIndexOf(CharPool.PERIOD);

		if (pos != -1) {
			return name.substring(pos + 1);
		}

		return name;
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
			int maxLineLength, boolean showDebugInformation)
		throws Exception {

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		Configuration configuration = ConfigurationLoader.loadConfiguration(
			new InputSource(
				classLoader.getResourceAsStream(configurationFileName)),
			new PropertiesExpander(System.getProperties()), false);

		configuration = _addAttribute(
			configuration, "maxLineLength", String.valueOf(maxLineLength),
			"com.liferay.source.formatter.checkstyle.checks.AppendCheck");
		configuration = _addAttribute(
			configuration, "maxLineLength", String.valueOf(maxLineLength),
			"com.liferay.source.formatter.checkstyle.checks.ConcatCheck");
		configuration = _addAttribute(
			configuration, "maxLineLength", String.valueOf(maxLineLength),
			"com.liferay.source.formatter.checkstyle.checks." +
				"PlusStatementCheck");
		configuration = _addAttribute(
			configuration, "showDebugInformation",
			String.valueOf(showDebugInformation), "com.liferay.*");

		configuration = _addPropertiesAttributes(configuration, propertiesMap);

		if (showDebugInformation) {
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
		Configuration configuration, Map<String, Properties> propertiesMap) {

		Configuration[] checkConfigurations = _getCheckConfigurations(
			configuration);

		if (checkConfigurations == null) {
			return configuration;
		}

		for (Configuration checkConfiguration : checkConfigurations) {
			if (!(checkConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String checkName = getCheckName(checkConfiguration.getName());

			List<String> attributeNames = SourceFormatterUtil.getAttributeNames(
				checkName, propertiesMap);

			for (String attributeName : attributeNames) {
				String value = SourceFormatterUtil.getPropertyValue(
					attributeName, checkName, propertiesMap);

				if (Validator.isNotNull(value)) {
					DefaultConfiguration defaultChildConfiguration =
						(DefaultConfiguration)checkConfiguration;

					defaultChildConfiguration.addAttribute(
						attributeName, value);
				}
			}
		}

		return configuration;
	}

	private static Configuration[] _getCheckConfigurations(
		Configuration configuration) {

		if (!(configuration instanceof DefaultConfiguration)) {
			return null;
		}

		DefaultConfiguration defaultConfiguration =
			(DefaultConfiguration)configuration;

		DefaultConfiguration treeWalkerModule = null;

		for (Configuration childConfiguration :
				defaultConfiguration.getChildren()) {

			String name = childConfiguration.getName();

			if (name.equals("TreeWalker") &&
				(childConfiguration instanceof DefaultConfiguration)) {

				treeWalkerModule = (DefaultConfiguration)childConfiguration;

				break;
			}
		}

		if (treeWalkerModule != null) {
			return treeWalkerModule.getChildren();
		}

		return null;
	}

}