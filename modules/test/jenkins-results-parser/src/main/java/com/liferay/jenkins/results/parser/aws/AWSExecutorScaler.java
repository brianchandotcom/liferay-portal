/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSExecutorScaler {

	public AWSFleetCloud getAWSFleetCloud() {
		return _awsFleetCloud;
	}

	public String getClassName() {
		return _jsonObject.getString("className");
	}

	public int getMemoryGiBPerExecutor() {
		return _jsonObject.optInt("memoryGiBPerExecutor");
	}

	public int getVCpuPerExecutor() {
		return _jsonObject.optInt("vCpuPerExecutor");
	}

	@Override
	public String toString() {
		return String.valueOf(_jsonObject);
	}

	protected AWSExecutorScaler(
		AWSFleetCloud awsFleetCloud, JSONObject jsonObject) {

		_awsFleetCloud = awsFleetCloud;
		_jsonObject = jsonObject;
	}

	private final AWSFleetCloud _awsFleetCloud;
	private final JSONObject _jsonObject;

}