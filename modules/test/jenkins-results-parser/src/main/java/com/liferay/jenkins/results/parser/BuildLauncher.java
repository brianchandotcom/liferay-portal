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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class BuildLauncher {

	public static void main(String[] args) {
		String buildCommand = _getBuildCommand(args);

		System.out.println("##");
		System.out.println("## " + buildCommand);
		System.out.println("##");

		BuildData buildData = _getBuildData(args);

		BuildRunner buildRunner = BuildRunnerFactory.newBuildRunner(buildData);

		if (buildCommand.equals(_COMMAND_RUN)) {
			buildRunner.run();
		}
		else if (buildCommand.equals(_COMMAND_TEARDOWN)) {
			buildRunner.tearDown();
		}
	}

	private static String _getBuildCommand(String[] args) {
		String buildCommand = null;

		for (String arg : args) {
			if (!arg.equals(_COMMAND_RUN) && !arg.equals(_COMMAND_TEARDOWN)) {
				continue;
			}

			if (buildCommand != null) {
				throw new RuntimeException("Too many build commands");
			}

			buildCommand = arg;
		}

		if (buildCommand == null) {
			throw new RuntimeException("No available build command");
		}

		return buildCommand;
	}

	private static BuildData _getBuildData(String[] args) {
		Map<String, String> buildProperties = new HashMap<>();

		buildProperties.putAll(_getEnvironmentVariables());

		buildProperties.putAll(_getJenkinsBuildParameters(buildProperties));

		buildProperties.putAll(_getBuildOptions(args));

		BuildData buildData = BuildDataFactory.newBuildData(
			buildProperties.get("RUN_ID"), buildProperties.get("JOB_NAME"),
			buildProperties.get("BUILD_URL"));

		String jenkinsGitHubURL = buildProperties.get("JENKINS_GITHUB_URL");

		if ((jenkinsGitHubURL != null) && !jenkinsGitHubURL.isEmpty()) {
			buildData.setJenkinsGitHubURL(jenkinsGitHubURL);
		}

		String workspace = buildProperties.get("WORKSPACE");

		if ((workspace != null) && !workspace.isEmpty()) {
			buildData.setWorkspaceDir(new File(workspace));
		}

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			String portalGitHubURL = buildProperties.get("PORTAL_GITHUB_URL");

			if ((portalGitHubURL != null) && !portalGitHubURL.isEmpty()) {
				portalBuildData.setPortalGitHubURL(portalGitHubURL);
			}

			String portalUpstreamBranchName = buildProperties.get(
				"PORTAL_UPSTREAM_BRANCH_NAME");

			if ((portalUpstreamBranchName != null) &&
				!portalUpstreamBranchName.isEmpty()) {

				portalBuildData.setPortalUpstreamBranchName(
					portalUpstreamBranchName);
			}
		}

		return buildData;
	}

	private static Map<String, String> _getBuildOptions(String[] args) {
		Map<String, String> buildOptions = new HashMap<>();

		for (String arg : args) {
			Matcher matcher = _buildOptionPattern.matcher(arg);

			if (!matcher.find()) {
				continue;
			}

			buildOptions.put(matcher.group("name"), matcher.group("value"));
		}

		return buildOptions;
	}

	private static Map<String, String> _getEnvironmentVariables() {
		Map<String, String> environmentVariables = new HashMap<>();

		environmentVariables.put("BUILD_URL", System.getenv("BUILD_URL"));
		environmentVariables.put("JOB_NAME", System.getenv("JOB_NAME"));
		environmentVariables.put(
			"PORTAL_GITHUB_URL", System.getenv("PORTAL_GITHUB_URL"));
		environmentVariables.put(
			"PORTAL_UPSTREAM_BRANCH_NAME",
			System.getenv("PORTAL_UPSTREAM_BRANCH_NAME"));
		environmentVariables.put("RUN_ID", System.getenv("RUN_ID"));

		String workspace = System.getenv("WORKSPACE");

		if (workspace != null) {
			environmentVariables.put("WORKSPACE", workspace);
		}

		return environmentVariables;
	}

	private static Map<String, String> _getJenkinsBuildParameters(
		Map<String, String> buildProperties) {

		String buildURL = buildProperties.get("BUILD_URL");

		if (buildURL == null) {
			return new HashMap<>();
		}

		return JenkinsResultsParserUtil.getBuildParameters(buildURL);
	}

	private static final String _COMMAND_RUN = "run";

	private static final String _COMMAND_TEARDOWN = "teardown";

	private static final Pattern _buildOptionPattern = Pattern.compile(
		"-D(?<name>[^=\\s]+)=(?<value>.+)");

}