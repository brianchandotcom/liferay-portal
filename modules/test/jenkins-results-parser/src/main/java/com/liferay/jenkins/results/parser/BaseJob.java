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
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJob implements Job {

	public String getJobName() {
		return jobName;
	}

	protected BaseJob(String jobName) {
		this.jobName = jobName;
	}

	protected Properties getGitWorkingDirectoryProperties(
		GitWorkingDirectory gitWorkingDirectory, String propertiesFilePath) {

		try {
			Properties properties = new Properties();

			File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

			List<File> propertiesFiles = _getPropertiesFiles(
				workingDirectory, propertiesFilePath);

			for (File propertiesFile : propertiesFiles) {
				if (!propertiesFile.exists()) {
					continue;
				}

				properties.load(new FileInputStream(propertiesFile));
			}

			return properties;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected String jobName;

	private List<File> _getPropertiesFiles(
		File workingDirectory, String propertiesFilePath) {

		List<File> propertiesFiles = new ArrayList<>();

		propertiesFiles.add(new File(workingDirectory, propertiesFilePath));

		String[] environments = {
			System.getenv("HOSTNAME"), System.getenv("HOST"),
			System.getenv("COMPUTERNAME"), System.getProperty("user.name")
		};

		for (String environment : environments) {
			if (environment == null) {
				continue;
			}

			propertiesFiles.add(
				new File(
					workingDirectory,
					propertiesFilePath.replace(
						".properties", "." + environment + ".properties")));
		}

		return propertiesFiles;
	}

}