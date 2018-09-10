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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildLauncher {

	public static void main(String[] args) {
		String buildCommand = _getBuildCommand(args);

		System.out.println("##");
		System.out.println("## " + buildCommand);
		System.out.println("##");

		Map<String, String> buildRunnerParameters = new HashMap<>();

		buildRunnerParameters.putAll(_getEnvironmentVariables());

		String buildURL = buildRunnerParameters.get("BUILD_URL");

		buildRunnerParameters.putAll(_getJenkinsBuildParameters(buildURL));

		buildRunnerParameters.putAll(_getBuildOptions(args));

		_downloadJenkinsJSONObject(buildRunnerParameters);

		BuildData buildData = _getBuildData(buildRunnerParameters);

		BuildRunner buildRunner = BuildRunnerFactory.newBuildRunner(buildData);

		if (buildCommand.equals(_RUN_COMMAND)) {
			buildRunner.run();
		}
		else if (buildCommand.equals(_SETUP_COMMAND)) {
			buildRunner.setUp();
		}
		else if (buildCommand.equals(_TEARDOWN_COMMAND)) {
			buildRunner.tearDown();
		}
	}

	private static void _downloadJenkinsJSONObject(
		Map<String, String> buildRunnerParameters) {

		if (!buildRunnerParameters.containsKey("DIST_NODES") ||
			!buildRunnerParameters.containsKey("DIST_PATH") ||
			!buildRunnerParameters.containsKey("WORKSPACE")) {

			return;
		}

		String workspace = buildRunnerParameters.get("WORKSPACE");

		File jenkinsJSONObjectFile = new File(
			workspace, BuildData.JENKINS_BUILD_DATA_FILE_NAME);

		if (jenkinsJSONObjectFile.exists()) {
			return;
		}

		String distNodes = buildRunnerParameters.get("DIST_NODES");
		String distPath = buildRunnerParameters.get("DIST_PATH");

		Process process;

		int maxRetries = 3;
		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				String distNode = JenkinsResultsParserUtil.getRandomString(
					Arrays.asList(distNodes.split(",")));

				String command = JenkinsResultsParserUtil.combine(
					"time rsync -sv --timeout=1200 ", distNode, ":", distPath,
					"/", BuildData.JENKINS_BUILD_DATA_FILE_NAME, " ", workspace,
					"/", BuildData.JENKINS_BUILD_DATA_FILE_NAME);

				JenkinsResultsParserUtil.executeBashCommands(command);

				process = JenkinsResultsParserUtil.executeBashCommands(command);

				String standardOut = JenkinsResultsParserUtil.readInputStream(
					process.getInputStream());

				System.out.println(standardOut);

				String standardErr = JenkinsResultsParserUtil.readInputStream(
					process.getErrorStream());

				System.out.println(standardErr);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to get ",
							BuildData.JENKINS_BUILD_DATA_FILE_NAME, " file"),
						e);
				}

				System.out.println(
					"Unable to execute bash commands retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(3000);
			}
		}
	}

	private static String _getBuildCommand(String[] args) {
		String buildCommand = null;

		for (String arg : args) {
			if (!arg.equals(_RUN_COMMAND) && !arg.equals(_SETUP_COMMAND) &&
				!arg.equals(_TEARDOWN_COMMAND)) {

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

	private static BuildData _getBuildData(
		Map<String, String> buildRunnerParameters) {

		File jenkinsJSONObjectFile = new File(
			buildRunnerParameters.get("WORKSPACE"),
			BuildData.JENKINS_BUILD_DATA_FILE_NAME);

		try {
			if (!jenkinsJSONObjectFile.exists()) {
				JenkinsResultsParserUtil.write(jenkinsJSONObjectFile, "{}");
			}

			JenkinsJSONObject jenkinsJSONObject = new JenkinsJSONObject(
				JenkinsResultsParserUtil.read(jenkinsJSONObjectFile));

			return BuildDataFactory.newBuildData(
				buildRunnerParameters, jenkinsJSONObject,
				buildRunnerParameters.get("RUN_ID"));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
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

		String workspace = System.getenv("WORKSPACE");

		if (workspace != null) {
			environmentVariables.put("WORKSPACE", workspace);
		}

		return environmentVariables;
	}

	private static Map<String, String> _getJenkinsBuildParameters(
		String buildURL) {

		Map<String, String> jenkinsBuildParameters = new HashMap<>();

		if (buildURL == null) {
			return jenkinsBuildParameters;
		}

		String buildParametersURL = JenkinsResultsParserUtil.getLocalURL(
			buildURL + "api/json?tree=actions[parameters[name,value]]");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				buildParametersURL);

			JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(0);

			JSONArray parametersJSONArray = actionsJSONObject.getJSONArray(
				"parameters");

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				jenkinsBuildParameters.put(
					parameterJSONObject.getString("name"),
					parameterJSONObject.getString("value"));
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException();
		}

		return jenkinsBuildParameters;
	}

	private static final String _RUN_COMMAND = "run";

	private static final String _SETUP_COMMAND = "setup";

	private static final String _TEARDOWN_COMMAND = "teardown";

	private static final Pattern _buildOptionPattern = Pattern.compile(
		"-D(?<name>[^=\\s]+)=(?<value>.+)");

}