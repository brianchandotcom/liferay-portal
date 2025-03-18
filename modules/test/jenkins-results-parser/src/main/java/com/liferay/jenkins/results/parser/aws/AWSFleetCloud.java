/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.aws;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSFleetCloud {

	public String getAWSCredentialsId() {
		return _jsonObject.getString("aws.credentials.id");
	}

	public int getCloudStatusIntervalSec() {
		return _jsonObject.getInt("cloud.status.interval.sec");
	}

	public String getComputerConnectorClassName() {
		return _jsonObject.getString("computer.connector.class.name");
	}

	public String getComputerConnectorCredentialsId() {
		return _jsonObject.getString("computer.connector.credentials.id");
	}

	public String getComputerConnectorJavaPath() {
		return _jsonObject.getString("computer.connector.java.path");
	}

	public String getComputerConnectorJVMOptions() {
		return _jsonObject.getString("computer.connector.jvm.options");
	}

	public int getComputerConnectorLaunchTimeoutSeconds() {
		return _jsonObject.getInt("computer.connector.launch.timeout.seconds");
	}

	public int getComputerConnectorMaxNumRetries() {
		return _jsonObject.getInt("computer.connector.max.num.retries");
	}

	public int getComputerConnectorPort() {
		return _jsonObject.getInt("computer.connector.port");
	}

	public int getComputerConnectorRetryWaitTime() {
		return _jsonObject.getInt("computer.connector.retry.wait.time");
	}

	public String getEndpoint() {
		return _jsonObject.getString("endpoint");
	}

	public String getExecutorScalerClassName() {
		return _jsonObject.optString("executor.scaler.class.name");
	}

	public int getExecutorScalerMemoryGiBPerExecutor() {
		return _jsonObject.optInt("executor.scaler.memory.gib.per.executor");
	}

	public int getExecutorScalerNumExecutor() {
		return _jsonObject.getInt("executor.scaler.num.executors");
	}

	public int getExecutorScalerVCpuPerExecutor() {
		return _jsonObject.optInt("executor.scaler.vcpu.per.executor");
	}

	public String getFleet() {
		return _jsonObject.getString("fleet");
	}

	public String getFsRoot() {
		return _jsonObject.getString("fs.root");
	}

	public int getInitOnlineCheckIntervalSec() {
		return _jsonObject.getInt("init.online.check.interval.sec");
	}

	public int getInitOnlineTimeoutSec() {
		return _jsonObject.getInt("init.online.timeout.sec");
	}

	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	public List<String> getLabels() {
		List<String> labels = new ArrayList<>();

		String labelString = getLabelString();

		if (JenkinsResultsParserUtil.isNullOrEmpty(labelString)) {
			return labels;
		}

		for (String label : labelString.split(" ")) {
			labels.add(label.trim());
		}

		return labels;
	}

	public String getLabelString() {
		return _jsonObject.getString("label.string");
	}

	public int getMaxSize() {
		return _jsonObject.getInt("max.size");
	}

	public int getMaxTotalUses() {
		return _jsonObject.getInt("max.total.uses");
	}

	public int getMinSize() {
		return _jsonObject.getInt("min.size");
	}

	public int getMinSpareSize() {
		return _jsonObject.getInt("min.spare.size");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public int getNumExecutors() {
		return _jsonObject.getInt("num.executors");
	}

	public String getRegion() {
		return _jsonObject.getString("region");
	}

	public boolean isAddNodeOnlyIfRunning() {
		return _jsonObject.getBoolean("add.node.only.if.running");
	}

	public boolean isAlwaysReconnect() {
		return _jsonObject.getBoolean("always.reconnect");
	}

	public boolean isDisableTaskResubmit() {
		return _jsonObject.getBoolean("disable.task.resubmit");
	}

	public boolean isNoDelayProvision() {
		return _jsonObject.getBoolean("no.delay.provision");
	}

	public boolean isPrivateIpUsed() {
		return _jsonObject.getBoolean("private.ip.used");
	}

	public boolean isRestrictUsage() {
		return _jsonObject.getBoolean("restrict.usage");
	}

	public boolean isScaleExecutorsByWeight() {
		return _jsonObject.getBoolean("scale.executors.by.weight");
	}

	@Override
	public String toString() {
		return String.valueOf(_jsonObject);
	}

	protected AWSFleetCloud(
		JenkinsMaster jenkinsMaster, JSONObject jsonObject) {

		_jenkinsMaster = jenkinsMaster;
		_jsonObject = jsonObject;
	}

	private final JenkinsMaster _jenkinsMaster;
	private final JSONObject _jsonObject;

}