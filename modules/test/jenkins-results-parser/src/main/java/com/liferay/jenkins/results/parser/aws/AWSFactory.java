/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSFactory {

	public static List<AWSFleetCloud> getAWSFleetClouds(
		JenkinsMaster jenkinsMaster) {

		List<AWSFleetCloud> awsFleetClouds = new ArrayList<>();

		JSONArray fleetCloudsJSONArray = _getFleetCloudsJSONArray(
			jenkinsMaster);

		if (fleetCloudsJSONArray == null) {
			return awsFleetClouds;
		}

		for (int i = 0; i < fleetCloudsJSONArray.length(); i++) {
			awsFleetClouds.add(
				newAWSFleetCloud(
					jenkinsMaster, fleetCloudsJSONArray.getJSONObject(i)));
		}

		return awsFleetClouds;
	}

	public static AWSComputerConnector newAWSComputerConnector(
		AWSFleetCloud awsFleetCloud, JSONObject jsonObject) {

		return new AWSComputerConnector(awsFleetCloud, jsonObject);
	}

	public static AWSExecutorScaler newAWSExecutorScaler(
		AWSFleetCloud awsFleetCloud, JSONObject jsonObject) {

		return new AWSExecutorScaler(awsFleetCloud, jsonObject);
	}

	public static AWSFleetCloud newAWSFleetCloud(
		JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

		return new AWSFleetCloud(jenkinsMaster, jsonObject);
	}

	private static JSONArray _getFleetCloudsJSONArray(
		JenkinsMaster jenkinsMaster) {

		if (jenkinsMaster == null) {
			return null;
		}

		Class<?> clazz = AWSFactory.class;

		String script;

		try {
			script = JenkinsResultsParserUtil.readInputStream(
				clazz.getResourceAsStream(
					"dependencies/get-fleet-clouds.groovy"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load groovy script", ioException);
		}

		try {
			String response = JenkinsResultsParserUtil.executeJenkinsScript(
				jenkinsMaster.getName(), script, true);

			if (JenkinsResultsParserUtil.isNullOrEmpty(response)) {
				return null;
			}

			return new JSONArray(response.substring(response.indexOf("[")));
		}
		catch (Exception exception) {
			return null;
		}
	}

}