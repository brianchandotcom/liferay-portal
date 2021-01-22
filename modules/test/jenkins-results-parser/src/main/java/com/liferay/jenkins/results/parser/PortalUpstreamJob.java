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
import java.util.List;
import java.util.Set;

/**
 * @author Leslie Wong
 */
public class PortalUpstreamJob
	extends PortalGitRepositoryJob implements BatchDependentJob {

	public PortalUpstreamJob(String jobName, BuildProfile buildProfile) {
		super(jobName, buildProfile);

		GitWorkingDirectory jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/dependencies/test-upstream-batch.properties"));

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
	protected Set<String> getRawBatchNames() {
		return getSetFromString(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(),
				"test.batch.names[portal-upstream(" + getBranchName() + ")]"));
	}

	protected Set<String> getRawDependentBatchNames() {
		return getSetFromString(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(),
				"test.batch.names.smoke[" + getBranchName() + "]"));
	}

}