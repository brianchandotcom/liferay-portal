/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws.cloud;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSCloudExecutorScaler {

	public AWSCloud getAWSCloud() {
		return _awsCloud;
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

	protected AWSCloudExecutorScaler(AWSCloud awsCloud, JSONObject jsonObject) {
		_awsCloud = awsCloud;
		_jsonObject = jsonObject;
	}

	private final AWSCloud _awsCloud;
	private final JSONObject _jsonObject;

}