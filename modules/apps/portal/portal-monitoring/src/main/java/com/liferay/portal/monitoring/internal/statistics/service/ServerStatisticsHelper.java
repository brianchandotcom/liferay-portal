/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(enabled = false, service = ServerStatisticsHelper.class)
public class ServerStatisticsHelper {

	public long getAverageTime(
		String className, String methodName, String[] parameterTypes) {

		ServiceStatistics serviceStatistics = getServiceStatistics(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getAverageTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getErrorCount(
		String className, String methodName, String[] parameterTypes) {

		ServiceStatistics serviceStatistics = getServiceStatistics(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getErrorCount(methodName, parameterTypes);
		}

		return -1;
	}

	public long getMaxTime(
		String className, String methodName, String[] parameterTypes) {

		ServiceStatistics serviceStatistics = getServiceStatistics(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getMaxTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getMinTime(
		String className, String methodName, String[] parameterTypes) {

		ServiceStatistics serviceStatistics = getServiceStatistics(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getMinTime(methodName, parameterTypes);
		}

		return -1;
	}

	public long getRequestCount(
		String className, String methodName, String[] parameterTypes) {

		ServiceStatistics serviceStatistics = getServiceStatistics(className);

		if (serviceStatistics != null) {
			return serviceStatistics.getRequestCount(
				methodName, parameterTypes);
		}

		return -1;
	}

	public ServiceStatistics getServiceStatistics(String className) {
		return _serviceStatistics.get(className);
	}

	public void setServiceStatistics(
		String className, ServiceStatistics serviceStatistics) {

		_serviceStatistics.put(className, serviceStatistics);
	}

	private final Map<String, ServiceStatistics> _serviceStatistics =
		new ConcurrentHashMap<>();

}