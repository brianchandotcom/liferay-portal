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

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJob implements Job {

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public Properties getJobProperties() {
		return _jobProperties;
	}

	@Override
	public void readJobProperties() {
		_jobProperties.clear();

		for (File jobPropertiesFile : jobPropertiesFiles) {
			_jobProperties.putAll(
				JenkinsResultsParserUtil.getProperties(jobPropertiesFile));
		}
	}

	protected BaseJob(String jobName) {
		_jobName = jobName;
	}

	protected Set<String> getSetFromString(String string) {
		Set<String> set = new TreeSet<>();

		if (string == null) {
			return set;
		}

		for (String item : StringUtils.split(string, ",")) {
			if (item.startsWith("#")) {
				continue;
			}

			set.add(item.trim());
		}

		return set;
	}

	protected final List<File> jobPropertiesFiles = new ArrayList<>();

	private final String _jobName;
	private final Properties _jobProperties = new Properties();

}