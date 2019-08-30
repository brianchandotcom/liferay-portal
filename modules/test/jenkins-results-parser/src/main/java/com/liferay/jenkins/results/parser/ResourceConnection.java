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

/**
 * @author Michael Hashimoto
 */
public class ResourceConnection implements Comparable {

	public ResourceConnection(
		ResourceMonitor resourceMonitor, EtcdUtil.Node node) {

		_key = node.getKey();
		_node = node;
		_resourceMonitor = resourceMonitor;
	}

	public ResourceConnection(
		ResourceMonitor resourceMonitor, String connectionName) {

		this(resourceMonitor, connectionName, null);
	}

	public ResourceConnection(
		ResourceMonitor resourceMonitor, String connectionName, State state) {

		_resourceMonitor = resourceMonitor;

		String etcdServerURL = _resourceMonitor.getEtcdServerURL();

		_key = _resourceMonitor.getKey() + "/" + connectionName;

		if (state != null) {
			_node = EtcdUtil.put(etcdServerURL, _key, state.toString());

			return;
		}

		EtcdUtil.Node node = EtcdUtil.get(etcdServerURL, _key);

		if (node != null) {
			_node = node;

			return;
		}

		_node = EtcdUtil.put(etcdServerURL, _key, State.IN_QUEUE.toString());
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof ResourceConnection)) {
			throw new RuntimeException("Invalid comparison");
		}

		Long createdIndex = getCreatedIndex();

		ResourceConnection resourceConnection = (ResourceConnection)o;

		return createdIndex.compareTo(resourceConnection.getCreatedIndex());
	}

	public Long getCreatedIndex() {
		return _node.getCreatedIndex();
	}

	public String getKey() {
		return _key;
	}

	public String getMonitorName() {
		return _resourceMonitor.getName();
	}

	public String getName() {
		return _key.replace(_resourceMonitor.getKey() + "/", "");
	}

	public State getState() {
		return State.valueOf(_node.getValue());
	}

	public void setState(State state) {
		if (!state.equals(State.RETIRE)) {
			_node = EtcdUtil.put(
				_resourceMonitor.getEtcdServerURL(), _key, state.toString());

			return;
		}

		if (_node.exists()) {
			_node.delete();
		}
		else {
			System.out.println("Node " + _key + " does not exist.");
		}
	}

	public enum State {

		IN_QUEUE, IN_USE, RETIRE

	}

	private final String _key;
	private EtcdUtil.Node _node;
	private final ResourceMonitor _resourceMonitor;

}