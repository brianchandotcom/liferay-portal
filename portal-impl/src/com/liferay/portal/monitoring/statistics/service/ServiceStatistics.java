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

package com.liferay.portal.monitoring.statistics.service;

import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.statistics.RequestStatistics;
import com.liferay.portal.monitoring.jmx.MethodSignature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class ServiceStatistics
	implements DataSampleProcessor<ServiceRequestDataSample> {

	public ServiceStatistics(String className) {
		_className = className;
	}

	public long getAverageTime(String methodName, String[] parameterTypes) {
		MethodSignature methodSignature = new MethodSignature(
			_className, methodName, parameterTypes);

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics != null) {
			return requestStatistics.getAverageTime();
		}

		return -1;
	}

	public long getErrorCount(String methodName, String[] parameterTypes) {
		MethodSignature methodSignature = new MethodSignature(
			_className, methodName, parameterTypes);

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics != null) {
			return requestStatistics.getErrorCount();
		}

		return -1;
	}

	public long getMaxTime(String methodName, String[] parameterTypes) {
		MethodSignature methodSignature = new MethodSignature(
			_className, methodName, parameterTypes);

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics != null) {
			return requestStatistics.getMaxTime();
		}

		return -1;
	}

	public long getMinTime(String methodName, String[] parameterTypes) {
		MethodSignature methodSignature = new MethodSignature(
			_className, methodName, parameterTypes);

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics != null) {
			return requestStatistics.getMinTime();
		}

		return -1;
	}

	public long getRequestCount(String methodName, String[] parameterTypes) {
		MethodSignature methodSignature = new MethodSignature(
			_className, methodName, parameterTypes);

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics != null) {
			return requestStatistics.getRequestCount();
		}

		return -1;
	}

	@Override
	public void processDataSample(
		ServiceRequestDataSample serviceRequestDataSample) {

		MethodSignature methodSignature =
			serviceRequestDataSample.getMethodSignature();

		RequestStatistics requestStatistics = _methodRequestStatistics.get(
			methodSignature);

		if (requestStatistics == null) {
			requestStatistics = new RequestStatistics(
				methodSignature.toString());

			_methodRequestStatistics.put(methodSignature, requestStatistics);
		}

		RequestStatus requestStatus =
			serviceRequestDataSample.getRequestStatus();

		if (requestStatus == RequestStatus.ERROR) {
			requestStatistics.incrementError();
		}
		else if (requestStatus == RequestStatus.TIMEOUT) {
			requestStatistics.incrementTimeout();
		}
		else if (requestStatus == RequestStatus.SUCCESS) {
			requestStatistics.incrementSuccessDuration(
				serviceRequestDataSample.getDuration());
		}
	}

	private final String _className;
	private final Map<MethodSignature, RequestStatistics>
		_methodRequestStatistics =
			new ConcurrentHashMap<MethodSignature, RequestStatistics>();

}