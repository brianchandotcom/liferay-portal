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
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSFactory {

	public static List<AWSFleetCloud> getAWSFleetClouds(
		JenkinsMaster jenkinsMaster) {

		List<AWSFleetCloud> awsFleetClouds = new ArrayList<>();

		JSONArray fleetCloudsJSONArray =
			_getBuildPropertiesFleetCloudsJSONArray(jenkinsMaster);

		if (fleetCloudsJSONArray == null) {
			fleetCloudsJSONArray = _getJenkinsFleetCloudsJSONArray(
				jenkinsMaster);
		}

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

	public static AWSFleetCloud newAWSFleetCloud(
		JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

		return new AWSFleetCloud(jenkinsMaster, jsonObject);
	}

	private static JSONArray _getBuildPropertiesFleetCloudsJSONArray(
		JenkinsMaster jenkinsMaster) {

		if (jenkinsMaster == null) {
			return null;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String fleetNames = JenkinsResultsParserUtil.getProperty(
				buildProperties,
				"master.cloud.fleets(" + jenkinsMaster.getName() + ")");

			if (JenkinsResultsParserUtil.isNullOrEmpty(fleetNames)) {
				return null;
			}

			String cloudFleetPropertyNames =
				JenkinsResultsParserUtil.getProperty(
					buildProperties, "cloud.fleet.property.names");

			JSONArray fleetCloudsJSONArray = new JSONArray();

			for (String fleetName : fleetNames.split(",")) {
				JSONObject fleetCloudJSONObject = new JSONObject();

				fleetCloudJSONObject.put("name", fleetName);

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						cloudFleetPropertyNames)) {

					fleetCloudsJSONArray.put(fleetCloudJSONObject);

					continue;
				}

				for (String cloudFleetPropertyName :
						cloudFleetPropertyNames.split(",")) {

					String cloudFleetPropertyValue =
						JenkinsResultsParserUtil.getProperty(
							buildProperties,
							"cloud.fleet.property(" + fleetName + "/" +
								cloudFleetPropertyName + ")");

					if (JenkinsResultsParserUtil.isNullOrEmpty(
							cloudFleetPropertyValue)) {

						cloudFleetPropertyValue =
							JenkinsResultsParserUtil.getProperty(
								buildProperties,
								"cloud.fleet.property(default/" +
									cloudFleetPropertyName + ")");
					}

					fleetCloudJSONObject.put(
						cloudFleetPropertyName, cloudFleetPropertyValue);
				}

				fleetCloudsJSONArray.put(fleetCloudJSONObject);
			}

			return fleetCloudsJSONArray;
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private static JSONArray _getJenkinsFleetCloudsJSONArray(
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
				"Unable to load Groovy script", ioException);
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