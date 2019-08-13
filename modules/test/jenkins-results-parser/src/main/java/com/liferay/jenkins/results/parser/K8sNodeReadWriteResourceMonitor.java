package com.liferay.jenkins.results.parser;

public class K8sNodeReadWriteResourceMonitor
	extends BaseReadWriteResourceMonitor {

	public K8sNodeReadWriteResourceMonitor(
		String etcdServerURL, String k8sNodeName) {

		super(
			etcdServerURL, "k8s_node_" + k8sNodeName,
			_ALLOWED_RESOURCE_CONNECTIONS);
	}

	private static final Integer _ALLOWED_RESOURCE_CONNECTIONS = 5;

}