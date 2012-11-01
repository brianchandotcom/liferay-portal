/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.monitoring.statistics.service.ServerStatistics;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceManager implements ServiceManagerMBean {

	public void addMonitoredClass(String className) {
		_serviceMonitorAdvice.addMonitoredClass(className);
	}

	public void addMonitoredMethod(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		_serviceMonitorAdvice.addMonitoredMethod(
			className, methodName, parameterTypes);
	}

	public long getErrorCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		return _serverStatistics.getErrorCount(
			className, methodName, parameterTypes);
	}

	public long getMaxTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		return _serverStatistics.getMaxTime(
			className, methodName, parameterTypes);
	}

	public long getMinTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		return _serverStatistics.getMinTime(
			className, methodName, parameterTypes);
	}

	public Set<String> getMonitoredClasses() {
		return _serviceMonitorAdvice.getMonitoredClasses();
	}

	public Set<MethodSignature> getMonitoredMethods() {
		return _serviceMonitorAdvice.getMonitoredMethods();
	}

	public long getRequestCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		return _serverStatistics.getRequestCount(
			className, methodName, parameterTypes);
	}

	public boolean isActive() {
		return _serviceMonitorAdvice.isActive();
	}

	public boolean isPermissiveMode() {
		return _serviceMonitorAdvice.isPermissiveMode();
	}

	public void setActive(boolean active) {
		_serviceMonitorAdvice.setActive(active);
	}

	public void setPermissiveMode(boolean permissiveMode) {
		_serviceMonitorAdvice.setPermissiveMode(permissiveMode);
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	public void setServiceMonitorAdvice(
		ServiceMonitorAdvice serviceMonitorAdvice) {

		_serviceMonitorAdvice = serviceMonitorAdvice;
	}

	private ServerStatistics _serverStatistics;
	private ServiceMonitorAdvice _serviceMonitorAdvice;

}