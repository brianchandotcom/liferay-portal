import com.amazon.jenkins.ec2fleet.EC2FleetCloud;

import hudson.plugins.sshslaves.SSHConnector;

import org.json.JSONArray;
import org.json.JSONObject;

JSONArray cloudsJSONArray = new JSONArray();

Jenkins.instance.clouds.each { cloud ->
	if (cloud instanceof EC2FleetCloud) {
		JSONObject cloudJSONObject = new JSONObject();

		cloudJSONObject.put("alwaysReconnect", cloud.alwaysReconnect);
		cloudJSONObject.put("cloudStatusIntervalSec", cloud.cloudStatusIntervalSec);
		cloudJSONObject.put("disableTaskResubmit", cloud.disableTaskResubmit);
		cloudJSONObject.put("endpoint", cloud.endpoint);
		cloudJSONObject.put("fleet", cloud.fleet);
		cloudJSONObject.put("fsRoot", cloud.fsRoot);
		cloudJSONObject.put("initOnlineCheckIntervalSec", cloud.initOnlineCheckIntervalSec);
		cloudJSONObject.put("initOnlineTimeoutSec", cloud.initOnlineTimeoutSec);
		cloudJSONObject.put("labelString", cloud.labelString);
		cloudJSONObject.put("maxSize", cloud.maxSize);
		cloudJSONObject.put("maxTotalUses", cloud.maxTotalUses);
		cloudJSONObject.put("minSize", cloud.minSize);
		cloudJSONObject.put("minSpareSize", cloud.minSpareSize);
		cloudJSONObject.put("name", cloud.name);
		cloudJSONObject.put("noDelayProvision", cloud.noDelayProvision);
		cloudJSONObject.put("numExecutors", cloud.numExecutors);
		cloudJSONObject.put("privateIpUsed", cloud.privateIpUsed);
		cloudJSONObject.put("region", cloud.region);
		cloudJSONObject.put("restrictUsage", cloud.restrictUsage);
		cloudJSONObject.put("scaleExecutorsByWeight", cloud.scaleExecutorsByWeight);

		if (cloud.computerConnector) {
			if (cloud.computerConnector instanceof SSHConnector) {
				JSONObject computerConnectorJSONObject = new JSONObject();

				computerConnectorJSONObject.put("className", cloud.computerConnector.class.name);
				computerConnectorJSONObject.put("credentialsId", cloud.computerConnector.credentialsId);
				computerConnectorJSONObject.put("javaPath", cloud.computerConnector.javaPath);
				computerConnectorJSONObject.put("jvmOptions", cloud.computerConnector.jvmOptions);
				computerConnectorJSONObject.put("launchTimeoutSeconds", cloud.computerConnector.launchTimeoutSeconds);
				computerConnectorJSONObject.put("maxNumRetries", cloud.computerConnector.maxNumRetries);
				computerConnectorJSONObject.put("port", cloud.computerConnector.port);
				computerConnectorJSONObject.put("prefixStartSlaveCmd", cloud.computerConnector.prefixStartSlaveCmd);
				computerConnectorJSONObject.put("retryWaitTime", cloud.computerConnector.retryWaitTime);
				computerConnectorJSONObject.put("sshHostKeyVerificationStrategy", cloud.computerConnector.sshHostKeyVerificationStrategy.class.name);
				computerConnectorJSONObject.put("suffixStartSlaveCmd", cloud.computerConnector.suffixStartSlaveCmd);

				cloudJSONObject.put("computerConnector", computerConnectorJSONObject);
			}
		}

		if (cloud.executorScaler) {
			JSONObject executorScalerJSONObject = new JSONObject();

			executorScalerJSONObject.put("className", cloud.executorScaler.class.name);

			if (cloud.executorScaler instanceof EC2FleetCloud.NodeHardwareScaler) {
				executorScalerJSONObject.put("memoryGiBPerExecutor", cloud.executorScaler.memoryGiBPerExecutor);
				executorScalerJSONObject.put("vCpuPerExecutor", cloud.executorScaler.vCpuPerExecutor);
			}

			cloudJSONObject.put("executorScaler", executorScalerJSONObject);
		}

		cloudsJSONArray.put(cloudJSONObject);
	}
}

return cloudsJSONArray.toString(2);