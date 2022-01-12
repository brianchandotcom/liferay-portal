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

package com.liferay.jenkins.results.parser.job.property;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGlobJobProperty
	extends BaseTestDirJobProperty implements GlobJobProperty {

	@Override
	public List<PathMatcher> getPathMatchers() {
		List<PathMatcher> pathMatchers =
			JenkinsResultsParserUtil.toPathMatchers(
				_getWorkingDirectory() + "/", getRelativeGlobs());

		pathMatchers.removeAll(Collections.singleton(null));

		return pathMatchers;
	}

	@Override
	public List<String> getRelativeGlobs() {
		List<String> relativeGlobs = new ArrayList<>();

		String value = getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(value)) {
			return relativeGlobs;
		}

		String relativePath = getRelativePath();

		for (String glob : value.split(",")) {
			String relativeGlob = relativePath + "/" + glob;

			relativeGlob = relativeGlob.replaceAll("/+", "/");

			if (relativeGlob.startsWith("/")) {
				relativeGlob = relativeGlob.substring(1);
			}

			relativeGlobs.add(relativeGlob);
		}

		return relativeGlobs;
	}

	@Override
	public File getTestBaseDir() {
		File testBaseDir = super.getTestBaseDir();

		if (testBaseDir != null) {
			return testBaseDir;
		}

		Job job = getJob();

		if (job instanceof PortalTestClassJob) {
			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			PortalGitWorkingDirectory portalGitWorkingDirectory =
				portalTestClassJob.getPortalGitWorkingDirectory();

			return portalGitWorkingDirectory.getWorkingDirectory();
		}

		return null;
	}

	protected BaseGlobJobProperty(
		Job job, Type type, File testBaseDir, String basePropertyName,
		boolean useBasePropertyName, String testSuiteName,
		String testBatchName) {

		super(
			job, type, testBaseDir, basePropertyName, useBasePropertyName,
			testSuiteName, testBatchName);
	}

	protected String getRelativePath() {
		File gitWorkingDirectory = _getWorkingDirectory();
		File testBaseDir = getTestBaseDir();

		if (Objects.equals(gitWorkingDirectory, testBaseDir)) {
			return "";
		}

		return JenkinsResultsParserUtil.getPathRelativeTo(
			testBaseDir, gitWorkingDirectory);
	}

	private File _getWorkingDirectory() {
		File testBaseDir = getTestBaseDir();

		if (testBaseDir != null) {
			return getWorkingDirectory(testBaseDir);
		}

		Job job = getJob();

		if (!(job instanceof PortalTestClassJob)) {
			return new File(".");
		}

		PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		return portalGitWorkingDirectory.getWorkingDirectory();
	}

}