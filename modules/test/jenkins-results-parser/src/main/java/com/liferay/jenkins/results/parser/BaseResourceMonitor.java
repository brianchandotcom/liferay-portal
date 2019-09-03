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

	public BaseResourceMonitor(String etcdServerURL, String name) {
		_etcdServerURL = etcdServerURL;
		_name = name;

		if (!EtcdUtil.has(_etcdServerURL, getKey())) {
			EtcdUtil.put(_etcdServerURL, getKey());
		}
	}

	@Override
	public String getEtcdServerURL() {
		return _etcdServerURL;
	}

	@Override
	public String getKey() {
		StringBuilder sb = new StringBuilder();

		sb.append("/monitor");

		String name = getName();

		if (!name.startsWith("/")) {
			sb.append("/");
		}

		sb.append(name);

		return sb.toString();
	}

	@Override
	public Integer getMaxResourceConnections() {
		if (_maxResourceConnections != null) {
			return _maxResourceConnections;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String key = JenkinsResultsParserUtil.combine(
				"resource.monitor.max.resource.connections[", getType(), "]");

			if (!buildProperties.containsKey(key)) {
				_maxResourceConnections = _MAX_RESOURCE_CONNECTIONS_DEFAULT;

				return _maxResourceConnections;
			}

			_maxResourceConnections = Integer.valueOf(
				buildProperties.getProperty(key));
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return _maxResourceConnections;
	}

	@Override
	public String getName() {
		return _name;
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
					childNode, this));
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

		String[][] table = new String[resourceConnectionQueue.size()][6];

		for (int i = 0; i < resourceConnectionQueue.size(); i++) {
			ResourceConnection resourceConnection = resourceConnectionQueue.get(
				i);

			table[i] = new String[] {
				String.valueOf(i),
				String.valueOf(resourceConnection.getState()),
				String.valueOf(resourceConnection.getCreatedIndex()),
				resourceConnection.getKey(),
				JenkinsResultsParserUtil.toDurationString(
					resourceConnection.getInQueueAge()),
				JenkinsResultsParserUtil.toDurationString(
					resourceConnection.getInUseAge())
			};
		}

		JenkinsResultsParserUtil.printTable(table);
	}

	@Override
	public void signal(String connectionName) {
		ResourceConnection resourceConnection =
			ResourceConnectionFactory.newResourceConnection(
				connectionName, this);

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
				connectionName, resourceMonitor);

		Integer maxResourceConnections =
			resourceMonitor.getMaxResourceConnections();

		while (true) {
			List<ResourceConnection> resourceConnectionQueue =
				getResourceConnectionQueue();

			for (int i = 0; i < maxResourceConnections; i++) {
				if (i >= resourceConnectionQueue.size()) {
					break;
				}

				ResourceConnection queuedResourceConnection =
					resourceConnectionQueue.get(i);

				if (competingResourceMonitorName != null) {
					ResourceMonitor queuedResourceMonitor =
						queuedResourceConnection.getResourceMonitor();

					if (competingResourceMonitorName.equals(
							queuedResourceMonitor.getName())) {

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

			ResourceConnection firstResourceConnection =
				resourceConnectionQueue.get(0);

			long inQueueAge = firstResourceConnection.getInQueueAge();
			long inUseAge = firstResourceConnection.getInUseAge();
			String key = firstResourceConnection.getKey();

			if (inUseAge == 0) {
				if (inQueueAge > _getMaxInQueueAge()) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Retiring ", key, " due to duration 'In Queue'"));

					firstResourceConnection.setState(
						ResourceConnection.State.RETIRE);
				}
			}
			else if (inUseAge > _getMaxInUseAge()) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Retiring ", key, " due to duration 'In Use'"));

				firstResourceConnection.setState(
					ResourceConnection.State.RETIRE);

				continue;
			}

			JenkinsResultsParserUtil.sleep(5000);
		}
	}

	private Long _getMaxInQueueAge() {
		if (_maxInQueueAge != null) {
			return _maxInQueueAge;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String key = JenkinsResultsParserUtil.combine(
				"resource.monitor.max.in.queue.age[", getType(), "]");

			if (!buildProperties.containsKey(key)) {
				_maxInQueueAge = _MAX_IN_QUEUE_AGE_DEFAULT;

				return _maxInQueueAge;
			}

			_maxInQueueAge = Long.valueOf(buildProperties.getProperty(key));
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return _maxInQueueAge;
	}

	private Long _getMaxInUseAge() {
		if (_maxInUseAge != null) {
			return _maxInUseAge;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String key = JenkinsResultsParserUtil.combine(
				"resource.monitor.max.in.use.age[", getType(), "]");

			if (!buildProperties.containsKey(key)) {
				_maxInUseAge = _MAX_IN_USE_AGE_DEFAULT;

				return _maxInUseAge;
			}

			_maxInUseAge = Long.valueOf(buildProperties.getProperty(key));
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return _maxInUseAge;
	}

	private static final long _MAX_IN_QUEUE_AGE_DEFAULT = 2 * 60 * 60 * 1000;

	private static final long _MAX_IN_USE_AGE_DEFAULT = 60 * 60 * 1000;

	private static final Integer _MAX_RESOURCE_CONNECTIONS_DEFAULT = 1;

	private final String _etcdServerURL;
	private Long _maxInQueueAge;
	private Long _maxInUseAge;
	private Integer _maxResourceConnections;
	private final String _name;

}