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
public class PortalBatchBuildRunner extends BatchBuildRunner {

	protected PortalBatchBuildRunner(Job job, String batchName) {
		super(job, batchName);

		if (!(job instanceof PortalTestClassJob)) {
			throw new RuntimeException("Invalid job type");
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

		portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		primaryWorkspace = WorkspaceFactory.newWorkspace(
			portalGitWorkingDirectory.getRepositoryType(),
			portalGitWorkingDirectory.getUpstreamBranchName());

		if (!(primaryWorkspace instanceof PortalWorkspace)) {
			throw new RuntimeException("Invalid workspace");
		}

		portalWorkspace = (PortalWorkspace)primaryWorkspace;

		_setPortalAppServerProperties();
		_setPortalBuildProperties();
	}

	protected PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected PortalWorkspace portalWorkspace;

	private void _setPortalAppServerProperties() {
		Properties properties = new Properties();

		properties.put(
			"app.server.parent.dir",
			portalWorkspace.getRepositoryDir() + "/bundles");

		portalWorkspace.setAppServerProperties(properties);
	}

	private void _setPortalBuildProperties() {
		Properties properties = new Properties();

		Properties jobProperties = job.getJobProperties();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			Matcher matcher = _pattern.matcher(jobPropertyName);

			if (matcher.find()) {
				String portalBuildPropertyName = matcher.group(
					"portalBuildPropertyName");

				properties.put(
					portalBuildPropertyName,
					jobProperties.getProperty(jobPropertyName));
			}
		}

		properties.put("jsp.precompile", "off");
		properties.put("jsp.precompile.parallel", "off");

		properties.put(
			"liferay.home", portalWorkspace.getRepositoryDir() + "/bundles");

		portalWorkspace.setBuildProperties(properties);
	}

	private static final Pattern _pattern = Pattern.compile(
		"portal.build.properties\\[(?<portalBuildPropertyName>[^\\]]+)\\]");

}