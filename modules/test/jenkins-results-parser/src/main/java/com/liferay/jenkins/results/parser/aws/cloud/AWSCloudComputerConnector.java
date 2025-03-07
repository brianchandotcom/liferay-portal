/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws.cloud;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSCloudComputerConnector {

	public AWSCloud getAWSCloud() {
		return _awsCloud;
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

	protected AWSCloudComputerConnector(
		AWSCloud awsCloud, JSONObject jsonObject) {

		_awsCloud = awsCloud;
		_jsonObject = jsonObject;
	}

	private final AWSCloud _awsCloud;
	private final JSONObject _jsonObject;

}