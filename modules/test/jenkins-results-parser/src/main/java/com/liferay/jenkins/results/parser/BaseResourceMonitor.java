/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseResourceMonitor implements ResourceMonitor {

	public BaseResourceMonitor(String etcdServerURL, String monitorName) {
		_etcdServerURL = etcdServerURL;
		_monitorName = monitorName;

		if (!EtcdUtil.has(_etcdServerURL, getKey())) {
			EtcdUtil.put(_etcdServerURL, getKey());
		}
	}

	@Override
	public Integer getAllowedResourceConnections() {
		if (_allowedResourceConnections != null) {
			return _allowedResourceConnections;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String key = JenkinsResultsParserUtil.combine(
				"resource.monitor.allowed.resource.connections[", getType(),
				"]");

			if (!buildProperties.containsKey(key)) {
				_allowedResourceConnections =
					_ALLOWED_RESOURCE_CONNECTIONS_DEFAULT;

				return _allowedResourceConnections;
			}

			_allowedResourceConnections = Integer.valueOf(
				buildProperties.getProperty(key));
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return _allowedResourceConnections;
	}

	@Override
	public String getEtcdServerURL() {
		return _etcdServerURL;
	}

	@Override
	public String getKey() {
		StringBuilder sb = new StringBuilder();

		sb.append("/monitor");

		String monitorName = getName();

		if (!monitorName.startsWith("/")) {
			sb.append("/");
		}

		sb.append(monitorName);

		return sb.toString();
	}

	@Override
	public String getName() {
		return _monitorName;
	}

	@Override
	public String getNewConnectionName() {
		return String.valueOf(
			JenkinsResultsParserUtil.getRandomValue(1, Integer.MAX_VALUE));
	}

	@Override
	public synchronized List<ResourceConnection> getResourceConnectionQueue() {
		Set<ResourceConnection> resourceConnections = new TreeSet<>();

		EtcdUtil.Node node = EtcdUtil.get(getEtcdServerURL(), getKey());

		for (EtcdUtil.Node childNode : node.getNodes()) {
			resourceConnections.add(
				ResourceConnectionFactory.newResourceConnection(
					this, childNode));
		}

		return new ArrayList<>(resourceConnections);
	}

	@Override
	public String getType() {
		return getName();
	}

	@Override
	public void printResourceConnectionQueue() {
		List<ResourceConnection> resourceConnectionQueue =
			getResourceConnectionQueue();

		String[][] table = new String[resourceConnectionQueue.size()][4];

		for (int i = 0; i < resourceConnectionQueue.size(); i++) {
			ResourceConnection resourceConnection = resourceConnectionQueue.get(
				i);

			table[i][0] = String.valueOf(i);
			table[i][1] = String.valueOf(resourceConnection.getState());
			table[i][2] = String.valueOf(resourceConnection.getCreatedIndex());
			table[i][3] = resourceConnection.getKey();
		}

		JenkinsResultsParserUtil.printTable(table);
	}

	@Override
	public void signal(String connectionName) {
		ResourceConnection resourceConnection =
			ResourceConnectionFactory.newResourceConnection(
				this, connectionName);

		resourceConnection.setState(ResourceConnection.State.RETIRE);
	}

	@Override
	public void wait(String connectionName) {
		wait(connectionName, this, null);
	}

	protected void wait(
		String connectionName, ResourceMonitor resourceMonitor,
		String competingResourceMonitorName) {

		ResourceConnection resourceConnection =
			ResourceConnectionFactory.newResourceConnection(
				resourceMonitor, connectionName);

		Integer allowedResourceConnections =
			resourceMonitor.getAllowedResourceConnections();

		while (true) {
			List<ResourceConnection> resourceConnectionQueue =
				getResourceConnectionQueue();

			for (int i = 0; i < allowedResourceConnections; i++) {
				if (i >= resourceConnectionQueue.size()) {
					break;
				}

				ResourceConnection queuedResourceConnection =
					resourceConnectionQueue.get(i);

				if (competingResourceMonitorName != null) {
					String monitorName =
						queuedResourceConnection.getMonitorName();

					if (monitorName.equals(competingResourceMonitorName)) {
						break;
					}
				}

				if (connectionName.equals(queuedResourceConnection.getName())) {
					resourceConnection.setState(
						ResourceConnection.State.IN_USE);

					break;
				}
			}

			if (ResourceConnection.State.IN_USE.equals(
					resourceConnection.getState())) {

				break;
			}

			JenkinsResultsParserUtil.sleep(5000);
		}
	}

	private static final Integer _ALLOWED_RESOURCE_CONNECTIONS_DEFAULT = 1;

	private Integer _allowedResourceConnections;
	private final String _etcdServerURL;
	private final String _monitorName;

}