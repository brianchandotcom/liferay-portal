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
import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public class GitBisectToolJob extends PortalRepositoryJob {

	public GitBisectToolJob(String jobName, String portalBranchName) {
		super(jobName);

		try {
			_jenkinsGitWorkingDirectory =
				JenkinsResultsParserUtil.getJenkinsGitWorkingDirectory();

			_portalGitWorkingDirectory =
				JenkinsResultsParserUtil.getPortalGitWorkingDirectory(
					portalBranchName);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to create a Git working directory", ioe);
		}

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(
					_jenkinsGitWorkingDirectory.getWorkingDirectory(),
					"commands/build.properties")));

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(
					_jenkinsGitWorkingDirectory.getWorkingDirectory(),
					"commands/dependencies/git-bisect-tool.properties")));
	}

	public GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;

}