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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class DestinationConfig implements Serializable {

	public static DestinationConfig createParallelDestinationConfig(
		String destinationName) {

		return new DestinationConfig("parallel", destinationName);
	}

	public static DestinationConfig createSerialDestinationConfig(
		String destinationName) {

		return new DestinationConfig("serial", destinationName);
	}

	public static DestinationConfig createSynchronousDestinationConfig(
		String destinationName) {

		return new DestinationConfig("synchronous", destinationName);
	}

	public DestinationConfig(String destinationType, String destinationName) {
		_destinationType = destinationType;
		_destinationName = destinationName;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public String getDestinationType() {
		return _destinationType;
	}

	public int getMaximumQueueSize() {
		return _maximumQueueSize;
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return _rejectedExecutionHandler;
	}

	public int getWorkersCoreSize() {
		return _workersCoreSize;
	}

	public int getWorkersMaxSize() {
		return _workersMaxSize;
	}

	public void setDestinationType(String destinationType) {
		_destinationType = destinationType;
	}

	public void setMaximumQueueSize(int maximumQueueSize) {
		_maximumQueueSize = maximumQueueSize;
	}

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setWorkersCoreSize(int workersCoreSize) {
		_workersCoreSize = workersCoreSize;
	}

	public void setWorkersMaxSize(int workersMaxSize) {
		_workersMaxSize = workersMaxSize;
	}

	@Override
	public String toString() {
		return "DestinationConfig{" +
			"_destinationType='" + _destinationType + '\'' +
			", _destinationName='" + _destinationName + '\'' +
			", _maximumQueueSize=" + _maximumQueueSize +
			", _rejectedExecutionHandler=" + _rejectedExecutionHandler +
			", _workersCoreSize=" + _workersCoreSize +
			", _workersMaxSize=" + _workersMaxSize +
			'}';
	}

	private static final int _WORKERS_CORE_SIZE = 2;

	private static final int _WORKERS_MAX_SIZE = 5;

	private final String _destinationName;
	private String _destinationType;
	private int _maximumQueueSize = Integer.MAX_VALUE;
	private RejectedExecutionHandler _rejectedExecutionHandler;
	private int _workersCoreSize = _WORKERS_CORE_SIZE;
	private int _workersMaxSize = _WORKERS_MAX_SIZE;

}