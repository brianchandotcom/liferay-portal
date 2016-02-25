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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class DestinationConfiguration implements Serializable {

	public static final String DESTINATION_TYPE_PARALLEL = "parallel";

	public static final String DESTINATION_TYPE_SERIAL = "serial";

	public static final String DESTINATION_TYPE_SYNCHRONOUS = "synchronous";

	public static DestinationConfiguration
		createParallelDestinationConfiguration(String destinationName) {

		return new DestinationConfiguration(
			DESTINATION_TYPE_PARALLEL, destinationName);
	}

	public static DestinationConfiguration createSerialDestinationConfiguration(
		String destinationName) {

		return new DestinationConfiguration(
			DESTINATION_TYPE_SERIAL, destinationName);
	}

	public static DestinationConfiguration
		createSynchronousDestinationConfiguration(String destinationName) {

		return new DestinationConfiguration(
			DESTINATION_TYPE_SYNCHRONOUS, destinationName);
	}

	public DestinationConfiguration(
		String destinationType, String destinationName) {

		_destinationType = destinationType;
		_destinationName = destinationName;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DestinationConfiguration)) {
			return false;
		}

		DestinationConfiguration destinationConfiguration =
			(DestinationConfiguration)object;

		if (Validator.equals(
				_destinationName, destinationConfiguration._destinationName)) {

			return true;
		}

		return false;
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

	@Override
	public int hashCode() {
		return _destinationName.hashCode();
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
		StringBundler sb = new StringBundler(13);

		sb.append("{_destinationName=");
		sb.append(_destinationName);
		sb.append(", _destinationType=");
		sb.append(_destinationType);
		sb.append(", _maximumQueueSize=");
		sb.append(_maximumQueueSize);
		sb.append(", _rejectedExecutionHandler=");
		sb.append(_rejectedExecutionHandler);
		sb.append(", _workersCoreSize=");
		sb.append(_workersCoreSize);
		sb.append(", _workersMaxSize=");
		sb.append(_workersMaxSize);
		sb.append("}");

		return sb.toString();
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