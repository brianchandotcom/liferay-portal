import com.amazon.jenkins.ec2fleet.EC2FleetCloud;

import hudson.plugins.sshslaves.SSHConnector;

import org.json.JSONArray;
import org.json.JSONObject;

JSONArray cloudsJSONArray = new JSONArray();

Jenkins.instance.clouds.each {
	cloud ->
		if (cloud instanceof EC2FleetCloud) {
			JSONObject cloudJSONObject = new JSONObject();

			cloudJSONObject.put("add.node.only.if.running", cloud.addNodeOnlyIfRunning);
			cloudJSONObject.put("always.reconnect", cloud.alwaysReconnect);
			cloudJSONObject.put("aws.credentials.id", cloud.awsCredentialsId);
			cloudJSONObject.put("cloud.status.interval.sec", cloud.cloudStatusIntervalSec);
			cloudJSONObject.put("disable.task.resubmit", cloud.disableTaskResubmit);
			cloudJSONObject.put("endpoint", cloud.endpoint);
			cloudJSONObject.put("fleet", cloud.fleet);
			cloudJSONObject.put("fs.root", cloud.fsRoot);
			cloudJSONObject.put("idle.minutes", cloud.idleMinutes);
			cloudJSONObject.put("init.online.check.interval.sec", cloud.initOnlineCheckIntervalSec);
			cloudJSONObject.put("init.online.timeout.sec", cloud.initOnlineTimeoutSec);
			cloudJSONObject.put("label.string", cloud.labelString);
			cloudJSONObject.put("max.size", cloud.maxSize);
			cloudJSONObject.put("max.total.uses", cloud.maxTotalUses);
			cloudJSONObject.put("min.size", cloud.minSize);
			cloudJSONObject.put("min.spare.size", cloud.minSpareSize);
			cloudJSONObject.put("name", cloud.name);
			cloudJSONObject.put("no.delay.provision", cloud.noDelayProvision);
			cloudJSONObject.put("num.executors", cloud.numExecutors);
			cloudJSONObject.put("private.ip.used", cloud.privateIpUsed);
			cloudJSONObject.put("region", cloud.region);
			cloudJSONObject.put("restrict.usage", cloud.restrictUsage);
			cloudJSONObject.put("scale.executors.by.weight", cloud.scaleExecutorsByWeight);

			if (cloud.computerConnector) {
				if (cloud.computerConnector instanceof SSHConnector) {
					cloudJSONObject.put("computer.connector.class.name", cloud.computerConnector.class.name);
					cloudJSONObject.put("computer.connector.credentials.id", cloud.computerConnector.credentialsId);
					cloudJSONObject.put("computer.connector.java.path", cloud.computerConnector.javaPath);
					cloudJSONObject.put("computer.connector.jvm.options", cloud.computerConnector.jvmOptions);
					cloudJSONObject.put("computer.connector.launch.timeout.seconds", cloud.computerConnector.launchTimeoutSeconds);
					cloudJSONObject.put("computer.connector.max.num.retries", cloud.computerConnector.maxNumRetries);
					cloudJSONObject.put("computer.connector.port", cloud.computerConnector.port);
					cloudJSONObject.put("computer.connector.prefix.start.slave.cmd", cloud.computerConnector.prefixStartSlaveCmd);
					cloudJSONObject.put("computer.connector.retry.wait.time", cloud.computerConnector.retryWaitTime);
					cloudJSONObject.put("computer.connector.ssh.host.key.verification.strategy", cloud.computerConnector.sshHostKeyVerificationStrategy.class.name);
					cloudJSONObject.put("computer.connector.suffix.start.slave.cmd", cloud.computerConnector.suffixStartSlaveCmd);
				}
			}

			if (cloud.executorScaler) {
				cloudJSONObject.put("executor.scaler.class.name", cloud.executorScaler.class.name);
				cloudJSONObject.put("executor.scaler.num.executors", cloud.executorScaler.numExecutors);

				if (cloud.executorScaler instanceof EC2FleetCloud.NodeHardwareScaler) {
					cloudJSONObject.put("executor.scaler.memory.gib.per.executor", cloud.executorScaler.memoryGiBPerExecutor);
					cloudJSONObject.put("executor.scaler.vcpu.per.executor", cloud.executorScaler.vCpuPerExecutor);
				}
			}

			cloudsJSONArray.put(cloudJSONObject);
		}
	}

return cloudsJSONArray.toString();