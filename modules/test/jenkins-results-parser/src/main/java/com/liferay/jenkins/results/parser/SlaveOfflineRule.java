/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class SlaveOfflineRule {

	public static List<SlaveOfflineRule> getSlaveOfflineRules() {
		if (_slaveOfflineRules != null) {
			return _slaveOfflineRules;
		}

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load slave offline rules", ioException);
		}

		_slaveOfflineRules = new ArrayList<>();

		for (Object propertyNameObject : buildProperties.keySet()) {
			String propertyName = propertyNameObject.toString();

			if (propertyName.startsWith("slave.offline.rule[")) {
				String ruleName = propertyName.substring(
					"slave.offline.rule[".length(),
					propertyName.lastIndexOf("]"));

				_slaveOfflineRules.add(
					new SlaveOfflineRule(
						buildProperties.getProperty(propertyName), ruleName));
			}
		}

		return _slaveOfflineRules;
	}

	public String getName() {
		return name;
	}

	public String getNotificationRecipients() {
		return notificationRecipients;
	}

	public boolean matches(Build build) {
		if (consolePattern != null) {
			String consoleText = build.getConsoleText();

			if (JenkinsResultsParserUtil.isNullOrEmpty(consoleText)) {
				return false;
			}

			for (String line : consoleText.split("\n")) {
				Matcher matcher = consolePattern.matcher(line);

				if (matcher.find()) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Build ", build.getBuildURL(), " matched with ",
							"slave offline rule ", getName(),
							".\nMatching console log line:\n", line));

					return true;
				}
			}

			return false;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Slave offline rule ", getName(),
				" has a null console pattern"));

		return false;
	}

	public boolean shutdown() {
		return shutdown;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (consolePattern != null) {
			sb.append("console=");
			sb.append(consolePattern.pattern());
			sb.append("\n");
		}

		sb.append("name=");
		sb.append(name);
		sb.append("\n");

		if (notificationRecipients != null) {
			sb.append("notificationRecipients=");
			sb.append(notificationRecipients);
			sb.append("\n");
		}

		return sb.toString();
	}

	protected Map<String, String> parseConfigurations(String configurations) {
		String[] configurationsArray = configurations.split("\\s*\n\\s*");

		Map<String, String> configurationsMap = new HashMap<>(
			configurationsArray.length);

		for (String configuration : configurationsArray) {
			Matcher matcher = _configurationsPattern.matcher(configuration);

			if (!matcher.matches()) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to parse configuration in slave offline ",
						"rule \"", getName(), "\"\n", configuration));
			}

			String value = matcher.group(2);

			if ((value == null) || value.isEmpty()) {
				continue;
			}

			configurationsMap.put(matcher.group(1), value);
		}

		return configurationsMap;
	}

	protected void validateRequiredConfigurationParameter(
		Map<String, String> configurationsMap, String parameterName) {

		if (!configurationsMap.containsKey(parameterName)) {
			throw new IllegalStateException(
				JenkinsResultsParserUtil.combine(
					"Unable to detect required configuration \"", parameterName,
					" in slave offline rule \"", getName(), "\""));
		}
	}

	protected Pattern consolePattern;
	protected String name;
	protected String notificationRecipients;
	protected boolean shutdown;

	private SlaveOfflineRule(String configurations, String ruleName) {
		name = ruleName;

		Map<String, String> configurationsMap = parseConfigurations(
			configurations);

		validateRequiredConfigurationParameter(configurationsMap, "console");

		consolePattern = Pattern.compile(configurationsMap.get("console"));

		validateRequiredConfigurationParameter(
			configurationsMap, "notificationRecipients");

		notificationRecipients = configurationsMap.get(
			"notificationRecipients");

		if (configurationsMap.containsKey("shutdown")) {
			shutdown = Boolean.parseBoolean(configurationsMap.get("shutdown"));
		}
		else {
			shutdown = false;
		}
	}

	private static final Pattern _configurationsPattern = Pattern.compile(
		"([^=]+)=(.*)");
	private static List<SlaveOfflineRule> _slaveOfflineRules;

}