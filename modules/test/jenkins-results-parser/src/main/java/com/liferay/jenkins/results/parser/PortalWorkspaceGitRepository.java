/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		List<String> filePaths = new ArrayList<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			filePaths.add(
				JenkinsResultsParserUtil.getCanonicalPath(modifiedFile));
		}

		for (File deletedFile : gitWorkingDirectory.getDeletedFilesList()) {
			filePaths.add(
				JenkinsResultsParserUtil.getCanonicalPath(deletedFile));
		}

		if (!multiPattern.matchesAll(filePaths.toArray(new String[0]))) {
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

		Retryable<Object> setupProfileDXPRetryable = new Retryable<Object>(
			true, _SETUP_PROFILE_DXP_RETRY_COUNT,
			_SETUP_PROFILE_DXP_RETRY_DELAY, true) {

			@Override
			public Object execute() {
				try {
					AntUtil.callTarget(
						getDirectory(), "build.xml", "setup-profile-dxp");
				}
				catch (AntException antException) {
					throw new RuntimeException(antException);
				}

				return null;
			}

		};

		setupProfileDXPRetryable.executeWithRetries();
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
		Properties testProperties = getProperties("portal.test.properties");

		String companyDefaultLocale = System.getenv(
			"TEST_COMPANY_DEFAULT_LOCALE");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(companyDefaultLocale)) {
			testProperties.setProperty(
				"test.company.default.locale", companyDefaultLocale);
		}

		return testProperties;
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

	private static final int _SETUP_PROFILE_DXP_RETRY_COUNT = 2;

	private static final int _SETUP_PROFILE_DXP_RETRY_DELAY = 5;

}