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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
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
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load slave offline rules", ioe);
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
		Matcher matcher = null;

		if (consolePattern != null) {
			String consoleText = build.getConsoleText();

			for (String line : consoleText.split("\n")) {
				matcher = consolePattern.matcher(line);

				if (matcher.find()) {
					return true;
				}
			}

			return false;
		}

		return true;
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

	protected Pattern consolePattern;
	protected String name;
	protected String notificationRecipients;

	private SlaveOfflineRule(String configurations, String ruleName) {
		name = ruleName;

		for (String configuration : configurations.split("\n")) {
			int x = configuration.indexOf("=");

			String name = configuration.substring(0, x);

			String value = configuration.substring(x + 1);

			value = value.trim();

			if (value.isEmpty()) {
				continue;
			}

			if (name.equals("console")) {
				consolePattern = Pattern.compile(value);
			}
			else if (name.equals("notificationRecipients")) {
				notificationRecipients = value;
			}
		}
	}

	private static List<SlaveOfflineRule> _slaveOfflineRules;

}