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

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalReleaseJob
	extends BaseJob
	implements BatchDependentJob, PortalTestClassJob, TestSuiteJob {

	public BasePortalReleaseJob(
		String jobName, String portalBranchName, BuildProfile buildProfile,
		String testSuiteName) {

		super(jobName);

		_portalBranchName = portalBranchName;
		this.buildProfile = buildProfile;

		_testSuiteName = testSuiteName;

		if (buildProfile == null) {
			this.buildProfile = BuildProfile.PORTAL;
		}

		_jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				portalBranchName);

		jobPropertiesFiles.add(
			new File(
				_portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		readJobProperties();
	}

	@Override
	public Set<String> getBatchNames() {
		Set<String> batchNames = new TreeSet<>();

		Properties jobProperties = getJobProperties();

		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names", false, _portalBranchName,
					getTestSuiteName())));
		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names", false, _portalBranchName,
					buildProfile.toString(), getTestSuiteName())));

		return batchNames;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		Set<String> batchNames = new TreeSet<>();

		Properties jobProperties = getJobProperties();

		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names.smoke", false,
					_portalBranchName, getTestSuiteName())));
		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names.smoke", false,
					_portalBranchName, buildProfile.toString(),
					getTestSuiteName())));

		return batchNames;
	}

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	public static enum BuildProfile {

		DXP {

			private static final String _TEXT = "dxp";

			@Override
			public String toString() {
				return _TEXT;
			}

		},
		PORTAL {

			private static final String _TEXT = "portal";

			@Override
			public String toString() {
				return _TEXT;
			}

		}

	}

	protected GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	protected BuildProfile buildProfile;

	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final String _portalBranchName;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _testSuiteName;

}