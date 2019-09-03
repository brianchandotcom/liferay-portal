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

	public Long getInQueueAge() {
		if (_inQueueStartTime == null) {
			return 0L;
		}

		if (_inUseStartTime == null) {
			return System.currentTimeMillis() - _inQueueStartTime;
		}

		return _inUseStartTime - _inQueueStartTime;
	}

	public Long getInUseAge() {
		if (_inUseStartTime == null) {
			return 0L;
		}

		return System.currentTimeMillis() - _inUseStartTime;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public ResourceMonitor getResourceMonitor() {
		return _resourceMonitor;
	}

	public State getState() {
		return State.valueOf(_node.getValue());
	}

	public void setState(State state) {
		if (!state.equals(State.RETIRE)) {
			_node = EtcdUtil.put(
				_resourceMonitor.getEtcdServerURL(), _key, state.toString());

			_setInQueueStartTime();

			if (state == State.IN_USE) {
				_setInUseStartTime();
			}

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

	protected ResourceConnection(
		EtcdUtil.Node node, ResourceMonitor resourceMonitor) {

		_node = node;
		_resourceMonitor = resourceMonitor;

		_key = _node.getKey();

		_name = _key.replace(_resourceMonitor.getKey() + "/", "");

		_setInQueueStartTime();

		_setInUseStartTime();
	}

	protected ResourceConnection(String name, ResourceMonitor resourceMonitor) {
		_name = name;
		_resourceMonitor = resourceMonitor;

		_key = JenkinsResultsParserUtil.combine(
			_resourceMonitor.getKey(), "/", name);

		String etcdServerURL = _resourceMonitor.getEtcdServerURL();

		EtcdUtil.Node node = EtcdUtil.get(etcdServerURL, _key);

		if (node != null) {
			_node = node;

			return;
		}

		_node = EtcdUtil.put(etcdServerURL, _key, State.IN_QUEUE.toString());

		_setInQueueStartTime();

		_setInUseStartTime();
	}

	private void _setInQueueStartTime() {
		if (_inQueueStartTime != null) {
			return;
		}

		_inQueueStartTime = System.currentTimeMillis();
	}

	private void _setInUseStartTime() {
		if (_inUseStartTime != null) {
			return;
		}

		State state = getState();

		if (state != State.IN_USE) {
			return;
		}

		_inUseStartTime = System.currentTimeMillis();
	}

	private Long _inQueueStartTime;
	private Long _inUseStartTime;
	private final String _key;
	private final String _name;
	private EtcdUtil.Node _node;
	private final ResourceMonitor _resourceMonitor;

}