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
public abstract class BaseBuildRunner {

	public Job getJob() {
		return job;
	}

	public void setup() {
		primaryLocalRepository.setup();
	}

	protected BaseBuildRunner(Job job) {
		this.job = job;
	}

	protected Properties getPortalJobBuildProperties() {
		Properties properties = new Properties();

		Properties jobProperties = job.getJobProperties();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			Matcher matcher = _pattern.matcher(jobPropertyName);

			if (matcher.find()) {
				String portalBuildPropertyName = matcher.group(
					"portalBuildPropertyName");

				properties.put(
					portalBuildPropertyName,
					JenkinsResultsParserUtil.getProperty(
						jobProperties, jobPropertyName));
			}
		}

		return properties;
	}

	protected final Job job;
	protected LocalRepository primaryLocalRepository;

	private static final Pattern _pattern = Pattern.compile(
		"portal.build.properties\\[(?<portalBuildPropertyName>[^\\]]+)\\]");

}