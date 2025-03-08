/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSComputerConnector {

	public AWSFleetCloud getAWSFleetCloud() {
		return _awsFleetCloud;
	}

	public String getCredentialsId() {
		return _jsonObject.getString("credentialsId");
	}

	public String getJavaPath() {
		return _jsonObject.getString("javaPath");
	}

	public int getLaunchTimeoutSeconds() {
		return _jsonObject.getInt("launchTimeoutSeconds");
	}

	public int getMaxNumRetries() {
		return _jsonObject.getInt("maxNumRetries");
	}

	public int getPort() {
		return _jsonObject.getInt("port");
	}

	public int getRetryWaitTime() {
		return _jsonObject.getInt("retryWaitTime");
	}

	@Override
	public String toString() {
		return String.valueOf(_jsonObject);
	}

	protected AWSComputerConnector(
		AWSFleetCloud awsFleetCloud, JSONObject jsonObject) {

		_awsFleetCloud = awsFleetCloud;
		_jsonObject = jsonObject;
	}

	private final AWSFleetCloud _awsFleetCloud;
	private final JSONObject _jsonObject;

}