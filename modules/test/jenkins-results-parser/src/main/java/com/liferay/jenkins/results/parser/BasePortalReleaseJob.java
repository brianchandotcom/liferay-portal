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

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		String jobName, BuildProfile buildProfile, String portalBranchName,
		String testSuiteName) {

		super(jobName, buildProfile);

		_portalBranchName = portalBranchName;

		_testSuiteName = testSuiteName;

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
	public List<AxisTestClassGroup> getDependentAxisTestClassGroups() {
		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getDependentBatchTestClassGroups()) {

			axisTestClassGroups.addAll(
				batchTestClassGroup.getAxisTestClassGroups());
		}

		return axisTestClassGroups;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		return getFilteredBatchNames(getRawDependentBatchNames());
	}

	@Override
	public List<BatchTestClassGroup> getDependentBatchTestClassGroups() {
		return getBatchTestClassGroups(getRawDependentBatchNames());
	}

	@Override
	public Set<String> getDependentSegmentNames() {
		return getFilteredSegmentNames(getRawDependentBatchNames());
	}

	@Override
	public List<SegmentTestClassGroup> getDependentSegmentTestClassGroups() {
		return getSegmentTestClassGroups(getRawDependentBatchNames());
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

	protected GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	@Override
	protected Set<String> getRawBatchNames() {
		Set<String> batchNames = new TreeSet<>();

		Properties jobProperties = getJobProperties();

		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names", false, _portalBranchName,
					getTestSuiteName())));

		BuildProfile buildProfile = getBuildProfile();

		batchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, "test.batch.names", false, _portalBranchName,
					buildProfile.toString(), getTestSuiteName())));

		return batchNames;
	}

	protected Set<String> getRawDependentBatchNames() {
		Set<String> dependentBatchNames = new TreeSet<>();

		dependentBatchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					getJobProperties(), "test.batch.names.smoke", false,
					_portalBranchName, getTestSuiteName())));

		BuildProfile buildProfile = getBuildProfile();

		dependentBatchNames.addAll(
			getSetFromString(
				JenkinsResultsParserUtil.getProperty(
					getJobProperties(), "test.batch.names.smoke", false,
					_portalBranchName, buildProfile.toString(),
					getTestSuiteName())));

		return dependentBatchNames;
	}

	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final String _portalBranchName;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _testSuiteName;

}