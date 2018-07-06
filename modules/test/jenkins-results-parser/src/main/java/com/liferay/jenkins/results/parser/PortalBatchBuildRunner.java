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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalBatchBuildRunner extends BatchBuildRunner {

	@Override
	public void setup() {
		primaryLocalRepository.setup();

		JenkinsResultsParserUtil.writePropertiesFile(
			_portalAppServerPropertiesFile, portalAppServerProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_portalBuildPropertiesFile, portalBuildProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_portalSQLPropertiesFile, portalSQLProperties, true);
		JenkinsResultsParserUtil.writePropertiesFile(
			_portalTestPropertiesFile, portalTestProperties, true);
	}

	protected PortalBatchBuildRunner(Job job, String batchName) {
		super(job, batchName);

		if (!(job instanceof PortalTestClassJob)) {
			throw new RuntimeException("Invalid job type");
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		primaryLocalRepository = RepositoryFactory.getLocalRepository(
			portalGitWorkingDirectory.getRepositoryName(),
			portalGitWorkingDirectory.getUpstreamBranchName());

		if (!(primaryLocalRepository instanceof PortalLocalRepository)) {
			throw new RuntimeException("Invalid workspace");
		}

		String hostname = System.getenv("HOSTNAME");

		_portalAppServerPropertiesFile = new File(
			primaryLocalRepository.getDirectory(),
			JenkinsResultsParserUtil.combine(
				"app.server.", hostname, ".properties"));

		_portalBuildPropertiesFile = new File(
			primaryLocalRepository.getDirectory(),
			JenkinsResultsParserUtil.combine(
				"build.", hostname, ".properties"));

		_portalSQLPropertiesFile = new File(
			primaryLocalRepository.getDirectory(),
			JenkinsResultsParserUtil.combine(
				"sql/sql.", hostname, ".properties"));

		_portalTestPropertiesFile = new File(
			primaryLocalRepository.getDirectory(),
			JenkinsResultsParserUtil.combine("test.", hostname, ".properties"));

		_setPortalAppServerProperties();
		_setPortalBuildProperties();
	}

	protected final Properties portalAppServerProperties = new Properties();
	protected final Properties portalBuildProperties = new Properties();
	protected final Properties portalSQLProperties = new Properties();
	protected final Properties portalTestProperties = new Properties();

	private void _setPortalAppServerProperties() {
		portalAppServerProperties.put(
			"app.server.parent.dir",
			primaryLocalRepository.getDirectory() + "/bundles");
	}

	private void _setPortalBuildProperties() {
		Properties jobProperties = job.getJobProperties();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			Matcher matcher = _pattern.matcher(jobPropertyName);

			if (matcher.find()) {
				String portalBuildPropertyName = matcher.group(
					"portalBuildPropertyName");

				portalBuildProperties.put(
					portalBuildPropertyName,
					JenkinsResultsParserUtil.getProperty(
						jobProperties, jobPropertyName));
			}
		}

		portalBuildProperties.put("jsp.precompile", "off");
		portalBuildProperties.put("jsp.precompile.parallel", "off");

		portalBuildProperties.put(
			"liferay.home", primaryLocalRepository.getDirectory() + "/bundles");
	}

	private static final Pattern _pattern = Pattern.compile(
		"portal.build.properties\\[(?<portalBuildPropertyName>[^\\]]+)\\]");

	private final File _portalAppServerPropertiesFile;
	private final File _portalBuildPropertiesFile;
	private final File _portalSQLPropertiesFile;
	private final File _portalTestPropertiesFile;

}