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
public abstract class RepositoryJob extends BaseJob {

	public String getBranchName() {
		return branchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	protected RepositoryJob(String jobName) {
		super(jobName);
	}

	protected Properties getGitWorkingDirectoryProperties(
		String propertiesFilePath) {

		try {
			Properties properties = new Properties();

			List<File> propertiesFiles = _getPropertiesFiles(
				propertiesFilePath);

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

	protected String branchName;
	protected GitWorkingDirectory gitWorkingDirectory;

	private List<File> _getPropertiesFiles(String propertiesFilePath) {
		List<File> propertiesFiles = new ArrayList<>();

		File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

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