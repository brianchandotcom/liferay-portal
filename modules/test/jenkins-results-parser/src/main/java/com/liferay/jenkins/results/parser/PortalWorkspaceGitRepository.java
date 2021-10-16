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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalWorkspaceGitRepository extends BaseWorkspaceGitRepository {

	public boolean bypassCITestRelevant() {
		setUp();

		String ciTestRelevantBypassFilePathPatterns =
			JenkinsResultsParserUtil.getCIProperty(
				getUpstreamBranchName(),
				"ci.test.relevant.bypass.file.path.patterns", getName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(
				ciTestRelevantBypassFilePathPatterns)) {

			return false;
		}

		MultiPattern multiPattern = new MultiPattern(
			ciTestRelevantBypassFilePathPatterns.split("\\s*,\\s*"));

		List<String> modifiedFilePaths = new ArrayList<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			modifiedFilePaths.add(
				JenkinsResultsParserUtil.getCanonicalPath(modifiedFile));
		}

		if (!multiPattern.matchesAll(
				modifiedFilePaths.toArray(new String[0]))) {

			return false;
		}

		return true;
	}

	public String getLiferayFacesAlloyURL() {
		return _getLiferayFacesURL(
			"liferay-faces-alloy", "liferay.faces.alloy.branch");
	}

	public String getLiferayFacesBridgeImplURL() {
		return _getLiferayFacesURL(
			"liferay-faces-bridge-impl", "liferay.faces.bridge.impl.branch");
	}

	public String getLiferayFacesPortalURL() {
		return _getLiferayFacesURL(
			"liferay-faces-portal", "liferay.faces.portal.branch");
	}

	public String getLiferayFacesShowcaseURL() {
		return _getLiferayFacesURL(
			"liferay-faces-showcase", "liferay.faces.showcase.branch");
	}

	public String getPluginsRepositoryDirName() {
		try {
			String lpPluginsDirString = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"portal.release.properties", "lp.plugins.dir",
				getUpstreamBranchName());

			if (JenkinsResultsParserUtil.isNullOrEmpty(lpPluginsDirString)) {
				return null;
			}

			return lpPluginsDirString.replaceAll(".*/([^/]+)", "$1");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public String getPortalPrivateRepositoryDirName() {
		return JenkinsResultsParserUtil.getGitDirectoryName(
			"liferay-portal-ee", getUpstreamBranchName() + "-private");
	}

	public void setUpPortalProfile() {
		String upstreamBranchName = getUpstreamBranchName();

		if (!upstreamBranchName.equals("master") &&
			!upstreamBranchName.matches("7\\.\\d+\\.x")) {

			return;
		}

		try {
			AntUtil.callTarget(
				getDirectory(), "build.xml", "setup-profile-dxp");
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}
	}

	public void setUpTCKHome() {
		Map<String, String> parameters = new HashMap<>();

		String tckHome = JenkinsResultsParserUtil.getProperty(
			_getPortalTestProperties(), "tck.home");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(tckHome)) {
			parameters.put("tck.home", tckHome);
		}

		try {
			AntUtil.callTarget(
				getDirectory(), "build-test-tck.xml", "prepare-tck",
				parameters);
		}
		catch (AntException antException) {
			throw new RuntimeException(antException);
		}
	}

	@Override
	public void writePropertiesFiles() {
		_writeAppServerPropertiesFile();
		_writeBuildPropertiesFile();
		_writeReleasePropertiesFile();
		_writeSQLPropertiesFile();
		_writeTestPropertiesFile();
	}

	protected PortalWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected PortalWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(pullRequest, upstreamBranchName);
	}

	protected PortalWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef, upstreamBranchName);
	}

	@Override
	protected Set<String> getPropertyOptions() {
		Set<String> propertyOptions = new HashSet<>(super.getPropertyOptions());

		propertyOptions.add(getUpstreamBranchName());

		return propertyOptions;
	}

	private String _getLiferayFacesURL(
		String repositoryName, String propertyName) {

		try {
			String branchName = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"portal.test.properties", propertyName,
				getUpstreamBranchName());

			if (JenkinsResultsParserUtil.isNullOrEmpty(branchName)) {
				branchName = "master";
			}

			return JenkinsResultsParserUtil.combine(
				"https://github.com/liferay/", repositoryName, "/tree/",
				branchName);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Properties _getPortalTestProperties() {
		return getProperties("portal.test.properties");
	}

	private void _writeAppServerPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"app.server.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("portal.app.server.properties"), true);
	}

	private void _writeBuildPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"build.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("portal.build.properties"), true);
	}

	private void _writeReleasePropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"release.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("portal.release.properties"), true);
	}

	private void _writeSQLPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"sql/sql.", System.getenv("HOSTNAME"), ".properties")),
			getProperties("portal.sql.properties"), true);
	}

	private void _writeTestPropertiesFile() {
		JenkinsResultsParserUtil.writePropertiesFile(
			new File(
				getDirectory(),
				JenkinsResultsParserUtil.combine(
					"test.", System.getenv("HOSTNAME"), ".properties")),
			_getPortalTestProperties(), true);
	}

}