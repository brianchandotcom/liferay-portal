/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.service;

import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.monitoring.internal.statistics.RequestStatistics;

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
		_methodRequestStatistics = new ConcurrentHashMap<>();

}