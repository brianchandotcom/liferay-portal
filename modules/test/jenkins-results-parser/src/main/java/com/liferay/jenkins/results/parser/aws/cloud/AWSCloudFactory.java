/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws.cloud;

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
public class AWSCloudFactory {

	public static List<AWSCloud> getAWSClouds(JenkinsMaster jenkinsMaster) {
		List<AWSCloud> awsClouds = new ArrayList<>();

		JSONArray cloudsJSONArray = _getCloudsJSONArray(jenkinsMaster);

		if (cloudsJSONArray == null) {
			return awsClouds;
		}

		for (int i = 0; i < cloudsJSONArray.length(); i++) {
			awsClouds.add(
				newAWSCloud(jenkinsMaster, cloudsJSONArray.getJSONObject(i)));
		}

		return awsClouds;
	}

	public static AWSCloud newAWSCloud(
		JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

		return new AWSCloud(jenkinsMaster, jsonObject);
	}

	public static AWSCloudComputerConnector newAWSCloudComputerConnector(
		AWSCloud awsCloud, JSONObject jsonObject) {

		return new AWSCloudComputerConnector(awsCloud, jsonObject);
	}

	public static AWSCloudExecutorScaler newAWSCloudExecutorScaler(
		AWSCloud awsCloud, JSONObject jsonObject) {

		return new AWSCloudExecutorScaler(awsCloud, jsonObject);
	}

	private static JSONArray _getCloudsJSONArray(JenkinsMaster jenkinsMaster) {
		if (jenkinsMaster == null) {
			return null;
		}

		Class<?> clazz = AWSCloudFactory.class;

		String script;

		try {
			script = JenkinsResultsParserUtil.readInputStream(
				clazz.getResourceAsStream("dependencies/get-clouds.groovy"));
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