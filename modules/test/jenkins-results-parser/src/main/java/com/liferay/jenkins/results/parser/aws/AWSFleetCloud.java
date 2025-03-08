/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws;

import com.liferay.jenkins.results.parser.JenkinsMaster;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSFleetCloud {

	public AWSComputerConnector getAWSComputerConnector() {
		return _awsComputerConnector;
	}

	public AWSExecutorScaler getAWSExecutorScaler() {
		return _awsExecutorScaler;
	}

	public int getCloudStatusIntervalSec() {
		return _jsonObject.getInt("cloudStatusIntervalSec");
	}

	public String getEndpoint() {
		return _jsonObject.getString("endpoint");
	}

	public String getFleet() {
		return _jsonObject.getString("fleet");
	}

	public String getFsRoot() {
		return _jsonObject.getString("fsRoot");
	}

	public int getInitOnlineCheckIntervalSec() {
		return _jsonObject.getInt("initOnlineCheckIntervalSec");
	}

	public int getInitOnlineTimeoutSec() {
		return _jsonObject.getInt("initOnlineTimeoutSec");
	}

	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	public String getLabelString() {
		return _jsonObject.getString("labelString");
	}

	public int getMaxSize() {
		return _jsonObject.getInt("maxSize");
	}

	public int getMaxTotalUses() {
		return _jsonObject.getInt("maxTotalUses");
	}

	public int getMinSize() {
		return _jsonObject.getInt("minSize");
	}

	public int getMinSpareSize() {
		return _jsonObject.getInt("minSpareSize");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public int getNumExecutors() {
		return _jsonObject.getInt("numExecutors");
	}

	public String getRegion() {
		return _jsonObject.getString("region");
	}

	public boolean isAlwaysReconnect() {
		return _jsonObject.getBoolean("alwaysReconnect");
	}

	public boolean isDisableTaskResubmit() {
		return _jsonObject.getBoolean("disableTaskResubmit");
	}

	public boolean isNoDelayProvision() {
		return _jsonObject.getBoolean("noDelayProvision");
	}

	public boolean isPrivateIpUsed() {
		return _jsonObject.getBoolean("privateIpUsed");
	}

	public boolean isRestrictUsage() {
		return _jsonObject.getBoolean("restrictUsage");
	}

	public boolean isScaleExecutorsByWeight() {
		return _jsonObject.getBoolean("scaleExecutorsByWeight");
	}

	@Override
	public String toString() {
		return String.valueOf(_jsonObject);
	}

	protected AWSFleetCloud(
		JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

		_jenkinsMaster = jenkinsMaster;
		_jsonObject = jsonObject;

		_awsComputerConnector = AWSFactory.newAWSComputerConnector(
			this, jsonObject.getJSONObject("computerConnector"));
		_awsExecutorScaler = AWSFactory.newAWSExecutorScaler(
			this, jsonObject.getJSONObject("executorScaler"));
	}

	private final AWSComputerConnector _awsComputerConnector;
	private final AWSExecutorScaler _awsExecutorScaler;
	private final JenkinsMaster _jenkinsMaster;
	private final JSONObject _jsonObject;

}