/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalHotfixReleasePortalTopLevelBuild
	extends PortalTopLevelBuild
	implements PortalHotfixReleaseBuild, PortalWorkspaceBuild {

	public PortalHotfixReleasePortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String branchName = getBranchName();

		if (branchName.equals("master")) {
			return "liferay-portal";
		}

		return "liferay-portal-ee";
	}

	@Override
	public String getBranchName() {
		String testBuildHotfixZipURL = getParameterValue(
			"TEST_BUILD_HOTFIX_ZIP_URL");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testBuildHotfixZipURL)) {
			throw new RuntimeException(
				"Please set 'TEST_BUILD_HOTFIX_ZIP_URL'");
		}

		Matcher matcher = _hotfixZipURLPattern.matcher(testBuildHotfixZipURL);

		if (!matcher.find()) {
			throw new RuntimeException(
				"Please set a valid 'TEST_BUILD_HOTFIX_ZIP_URL'");
		}

		String majorVersion = matcher.group("majorVersion");
		String minorVersion = matcher.group("minorVersion");

		if (majorVersion.equals("7") && minorVersion.equals("4")) {
			return "master";
		}

		String branchName = JenkinsResultsParserUtil.combine(
			majorVersion, ".", minorVersion, ".x");

		if (branchName.startsWith("6")) {
			return "ee-" + branchName;
		}

		return branchName;
	}

	@Override
	public PortalFixpackRelease getPortalFixpackRelease() {
		if (_portalFixpackRelease != null) {
			return _portalFixpackRelease;
		}

		String patcherPortalVersion = getParameterValue(
			"PATCHER_BUILD_PATCHER_PORTAL_VERSION");

		if (JenkinsResultsParserUtil.isNullOrEmpty(patcherPortalVersion)) {
			return null;
		}

		if (patcherPortalVersion.contains("7310")) {
			Matcher matcher = _patcherPortalVersion73Pattern.matcher(
				patcherPortalVersion);

			if (!matcher.find()) {
				return null;
			}

			String fixpackVersion = matcher.group("fixpackVersion");

			if (!fixpackVersion.equals("1") && !fixpackVersion.equals("2")) {
				return null;
			}

			try {
				URL portalFixpackURL = new URL(
					JenkinsResultsParserUtil.combine(
						"https://files.liferay.com/private/ee/fix-packs/7.3.10",
						"/dxp/liferay-fix-pack-dxp-", fixpackVersion,
						"-7310.zip"));

				_portalFixpackRelease = new PortalFixpackRelease(
					portalFixpackURL);
			}
			catch (MalformedURLException malformedURLException) {
				throw new RuntimeException(malformedURLException);
			}
		}

		Matcher matcher = _patcherPortalVersionDXPPattern.matcher(
			patcherPortalVersion);

		if (!matcher.find()) {
			return null;
		}

		try {
			URL portalFixpackURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://files.liferay.com/private/ee/fix-packs/",
					matcher.group("majorVersion"), ".",
					matcher.group("minorVersion"), ".",
					matcher.group("fixVersion"), "/",
					matcher.group("fixpackType"), "/liferay-",
					patcherPortalVersion, ".zip"));

			_portalFixpackRelease = new PortalFixpackRelease(portalFixpackURL);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}

		return _portalFixpackRelease;
	}

	@Override
	public PortalHotfixRelease getPortalHotfixRelease() {
		if (_portalHotfixRelease != null) {
			return _portalHotfixRelease;
		}

		try {
			_portalHotfixRelease = new PortalHotfixRelease(
				new URL(getParameterValue("TEST_BUILD_HOTFIX_ZIP_URL")),
				getPortalFixpackRelease(), getPortalRelease());
		}
		catch (MalformedURLException malformedURLException) {
			return null;
		}

		return _portalHotfixRelease;
	}

	@Override
	public PortalRelease getPortalRelease() {
		if (_portalRelease != null) {
			return _portalRelease;
		}

		PortalFixpackRelease portalFixpackRelease = getPortalFixpackRelease();

		if (portalFixpackRelease != null) {
			_portalRelease = portalFixpackRelease.getPortalRelease();

			return _portalRelease;
		}

		String patcherPortalVersion = getParameterValue(
			"PATCHER_BUILD_PATCHER_PORTAL_VERSION");

		if (patcherPortalVersion.contains("7310")) {
			Matcher matcher = _patcherPortalVersion73Pattern.matcher(
				patcherPortalVersion);

			String portalReleaseVersion = "7.3.10";

			if (matcher.find()) {
				String fixpackVersion = matcher.group("fixpackVersion");

				if (fixpackVersion.equals("1") || fixpackVersion.equals("2")) {
					portalReleaseVersion = "7.3.10.1";
				}
				else if (fixpackVersion.equals("3")) {
					portalReleaseVersion = "7.3.10.3";
				}
				else {
					portalReleaseVersion = "7.3.10.u" + fixpackVersion;
				}
			}

			_portalRelease = new PortalRelease(portalReleaseVersion);

			return _portalRelease;
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(patcherPortalVersion)) {
			Matcher patcherPortalVersion62Matcher =
				_patcherPortalVersion62Pattern.matcher(patcherPortalVersion);

			if (patcherPortalVersion62Matcher.find()) {
				StringBuilder sb = new StringBuilder();

				sb.append(patcherPortalVersion62Matcher.group("majorVersion"));
				sb.append(".");
				sb.append(patcherPortalVersion62Matcher.group("minorVersion"));
				sb.append(".");
				sb.append(patcherPortalVersion62Matcher.group("fixVersion"));

				String servicePackVersion = patcherPortalVersion62Matcher.group(
					"servicePackVersion");

				if (!JenkinsResultsParserUtil.isNullOrEmpty(
						servicePackVersion)) {

					sb.append(".");
					sb.append(Integer.parseInt(servicePackVersion) + 1);
				}

				_portalRelease = new PortalRelease(sb.toString());

				return _portalRelease;
			}

			Matcher patcherPortalVersion74Matcher =
				_patcherPortalVersion74Pattern.matcher(patcherPortalVersion);

			if (patcherPortalVersion74Matcher.find()) {
				StringBuilder sb = new StringBuilder();

				sb.append(patcherPortalVersion74Matcher.group("majorVersion"));
				sb.append(".");
				sb.append(patcherPortalVersion74Matcher.group("minorVersion"));
				sb.append(".");
				sb.append(patcherPortalVersion74Matcher.group("fixVersion"));

				String updateVersion = patcherPortalVersion74Matcher.group(
					"updateVersion");

				if (!JenkinsResultsParserUtil.isNullOrEmpty(updateVersion)) {
					sb.append(updateVersion);
				}

				_portalRelease = new PortalRelease(sb.toString());

				return _portalRelease;
			}
		}

		Matcher hotfixZipURLMatcher = _hotfixZipURLPattern.matcher(
			getParameterValue("TEST_BUILD_HOTFIX_ZIP_URL"));

		if (!hotfixZipURLMatcher.find()) {
			return null;
		}

		_portalRelease = new PortalRelease(
			JenkinsResultsParserUtil.combine(
				hotfixZipURLMatcher.group("majorVersion"), ".",
				hotfixZipURLMatcher.group("minorVersion"), ".",
				hotfixZipURLMatcher.group("fixVersion")));

		return _portalRelease;
	}

	@Override
	public PortalWorkspace getPortalWorkspace() {
		Workspace workspace = getWorkspace();

		if (!(workspace instanceof PortalWorkspace)) {
			return null;
		}

		return (PortalWorkspace)workspace;
	}

	@Override
	public Workspace getWorkspace() {
		Workspace workspace = WorkspaceFactory.newWorkspace(
			getBaseGitRepositoryName(), getBranchName(), getJobName());

		if (workspace instanceof PortalWorkspace) {
			PortalWorkspace portalWorkspace = (PortalWorkspace)workspace;

			portalWorkspace.setBuildProfile(getBuildProfile());
		}

		String portalGitHubURL = _getPortalGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalGitHubURL)) {
			WorkspaceGitRepository workspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			workspaceGitRepository.setGitHubURL(portalGitHubURL);
		}

		return workspace;
	}

	private String _getPortalGitHubURL() {
		String portalBranchName = getParameterValue(
			"TEST_PORTAL_USER_BRANCH_NAME");
		String portalBranchUsername = getParameterValue(
			"TEST_PORTAL_USER_NAME");

		String branchName = getBranchName();

		String portalRepositoryName = "liferay-portal";

		if (!branchName.equals("master")) {
			portalRepositoryName = "liferay-portal-ee";
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBranchName) ||
			JenkinsResultsParserUtil.isNullOrEmpty(portalBranchUsername)) {

			String patcherPortalVersion = getParameterValue(
				"PATCHER_BUILD_PATCHER_PORTAL_VERSION");

			if (JenkinsResultsParserUtil.isNullOrEmpty(patcherPortalVersion)) {
				return null;
			}

			Matcher patcherPortalVersion74Matcher =
				_patcherPortalVersion74Pattern.matcher(patcherPortalVersion);

			if (!patcherPortalVersion74Matcher.find()) {
				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(patcherPortalVersion74Matcher.group("majorVersion"));
			sb.append(".");
			sb.append(patcherPortalVersion74Matcher.group("minorVersion"));
			sb.append(".");
			sb.append(patcherPortalVersion74Matcher.group("fixVersion"));

			String updateVersion = patcherPortalVersion74Matcher.group(
				"updateVersion");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(updateVersion)) {
				sb.append(updateVersion);
			}

			portalBranchUsername = "liferay";
			portalBranchName = sb.toString();
			portalRepositoryName = "liferay-portal-ee";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/");
		sb.append(portalBranchUsername);
		sb.append("/");
		sb.append(portalRepositoryName);
		sb.append("/tree/");
		sb.append(portalBranchName);

		return sb.toString();
	}

	private static final Pattern _hotfixZipURLPattern = Pattern.compile(
		"https?://.*(?<majorVersion>\\d)(?<minorVersion>\\d)" +
			"(?<fixVersion>\\d{2})\\.(lpkg|zip)");
	private static final Pattern _patcherPortalVersion62Pattern =
		Pattern.compile(
			"(?<majorVersion>6)\\.(?<minorVersion>2)\\." +
				"(?<fixVersion>\\d{2})( SP(?<servicePackVersion>\\d+))?");
	private static final Pattern _patcherPortalVersion73Pattern =
		Pattern.compile("fix-pack-dxp-(?<fixpackVersion>\\d+)-7310");
	private static final Pattern _patcherPortalVersion74Pattern =
		Pattern.compile(
			"(?<majorVersion>7)\\.(?<minorVersion>4)\\." +
				"(?<fixVersion>\\d{2})(?<updateVersion>-(ep|u)\\d+)?");
	private static final Pattern _patcherPortalVersionDXPPattern =
		Pattern.compile(
			"fix-pack-(?<fixpackType>de|dxp)-(?<fixpackVersion>\\d+)-" +
				"(?<majorVersion>\\d)(?<minorVersion>\\d)" +
					"(?<fixVersion>\\d{2})");

	private PortalFixpackRelease _portalFixpackRelease;
	private PortalHotfixRelease _portalHotfixRelease;
	private PortalRelease _portalRelease;

}