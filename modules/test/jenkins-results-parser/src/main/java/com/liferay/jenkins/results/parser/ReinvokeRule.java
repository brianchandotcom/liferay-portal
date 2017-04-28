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
 * @author Peter Yoo
 */
public class ReinvokeRule {

	public static List<ReinvokeRule> getReinvokeRules() {
		Properties buildProperties = null;

		List<ReinvokeRule> reinvokeRules = new ArrayList<>();

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to load reinvoke rules", ioe);
		}

		for (Object propertyNameObject : buildProperties.keySet()) {
			String propertyName = propertyNameObject.toString();

			if (propertyName.startsWith("reinvoke.rule[")) {
				String ruleName = propertyName.substring(
					"reinvoke.rule[".length(), propertyName.lastIndexOf("]"));

				reinvokeRules.add(
					new ReinvokeRule(
						buildProperties.getProperty(propertyName), ruleName));
			}
		}

		return reinvokeRules;
	}

	public String getName() {
		return name;
	}

	public String getNotificationList() {
		return notificationList;
	}

	public boolean matches(Build build) {
		Matcher matcher = null;

		if (axisVariablePattern != null) {
			if (!(build instanceof AxisBuild)) {
				return false;
			}

			AxisBuild axisBuild = (AxisBuild)build;

			matcher = axisVariablePattern.matcher(axisBuild.getAxisVariable());

			if (!matcher.find()) {
				return false;
			}
		}

		if (jobVariantPattern != null) {
			matcher = jobVariantPattern.matcher(build.getJobVariant());

			if (!matcher.find()) {
				return false;
			}
		}

		if (topLevelBuildJobNamePattern != null) {
			TopLevelBuild topLevelBuild = build.getTopLevelBuild();

			if (topLevelBuild != null) {
				matcher = topLevelBuildJobNamePattern.matcher(
					topLevelBuild.getJobName());

				if (!matcher.find()) {
					return false;
				}
			}
		}

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

		sb.append("name=");
		sb.append(name);

		if (axisVariablePattern != null) {
			sb.append("\naxisVariable=");
			sb.append(axisVariablePattern.pattern());
		}

		if (consolePattern != null) {
			sb.append("\nconsole=");
			sb.append(consolePattern.pattern());
		}

		if (jobVariantPattern != null) {
			sb.append("\njobVariant=");
			sb.append(jobVariantPattern.pattern());
		}

		if (notificationList != null) {
			sb.append("\nnotificationList=");
			sb.append(notificationList);
		}

		if (topLevelBuildJobNamePattern != null) {
			sb.append("\ntopLevelJobName=");
			sb.append(topLevelBuildJobNamePattern.pattern());
		}

		sb.append("\n");

		return sb.toString();
	}

	protected Pattern axisVariablePattern;
	protected Pattern consolePattern;
	protected Pattern jobVariantPattern;
	protected String name;
	protected String notificationList;
	protected Pattern topLevelBuildJobNamePattern;

	private ReinvokeRule(String configurations, String ruleName) {
		name = ruleName;

		for (String configuration : configurations.split("\n")) {
			int x = configuration.indexOf("=");

			String name = configuration.substring(0, x);

			if (name.equals("notificationList")) {
				notificationList = configuration.substring(x + 1);

				continue;
			}

			Pattern pattern = Pattern.compile(configuration.substring(x + 1));

			if (name.equals("axisVariable")) {
				axisVariablePattern = pattern;

				continue;
			}

			if (name.equals("console")) {
				consolePattern = pattern;

				continue;
			}

			if (name.equals("jobVariant")) {
				jobVariantPattern = pattern;

				continue;
			}

			if (name.equals("topLevelJobName")) {
				topLevelBuildJobNamePattern = pattern;

				continue;
			}
		}
	}

}