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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJob implements Job {

	@Override
	public String getJobName() {
		return jobName;
	}

	protected BaseJob(String jobName) {
		this.jobName = jobName;
	}

	protected String getProperty(Properties properties, String name) {
		if (!properties.containsKey(name)) {
			return null;
		}

		String value = properties.getProperty(name);

		Matcher matcher = _propertiesPattern.matcher(value);

		String newValue = value;

		while (matcher.find()) {
			newValue = newValue.replace(
				matcher.group(0), getProperty(properties, matcher.group(1)));
		}

		return newValue;
	}

	protected String jobName;

	private static final Pattern _propertiesPattern = Pattern.compile(
		"\\$\\{([^\\}]+)\\}");

}