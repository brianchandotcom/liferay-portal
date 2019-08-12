package com.liferay.jenkins.results.parser;

public class K8sNodeReadWriteResourceMonitor
	extends BaseReadWriteResourceMonitor {

	public K8sNodeReadWriteResourceMonitor(
		String etcdServerURL, String k8sNodeName) {

		super(
			etcdServerURL, "k8s_node_" + k8sNodeName,
			_ALLOWED_RESOURCE_CONNECTIONS);

		_k8sNodeName = k8sNodeName;
	}

	private static final Integer _ALLOWED_RESOURCE_CONNECTIONS = 5;

	private final String _k8sNodeName;

}