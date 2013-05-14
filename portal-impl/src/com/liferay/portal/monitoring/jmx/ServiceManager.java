/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.monitoring.statistics.service.ServerStatistics;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceManager implements ServiceManagerMBean {

	@Override
	public void addMonitoredClass(String className) {
		_serviceMonitorAdvice.addMonitoredClass(className);
	}

	@Override
	public void addMonitoredMethod(
		String className, String methodName, String[] parameterTypes) {

		_serviceMonitorAdvice.addMonitoredMethod(
			className, methodName, parameterTypes);
	}

	@Override
	public long getErrorCount(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getErrorCount(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMaxTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getMaxTime(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMinTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getMinTime(
			className, methodName, parameterTypes);
	}

	@Override
	public Set<String> getMonitoredClasses() {
		return _serviceMonitorAdvice.getMonitoredClasses();
	}

	@Override
	public Set<MethodSignature> getMonitoredMethods() {
		return _serviceMonitorAdvice.getMonitoredMethods();
	}

	@Override
	public long getRequestCount(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getRequestCount(
			className, methodName, parameterTypes);
	}

	@Override
	public boolean isActive() {
		return _serviceMonitorAdvice.isActive();
	}

	@Override
	public boolean isPermissiveMode() {
		return _serviceMonitorAdvice.isPermissiveMode();
	}

	@Override
	public void setActive(boolean active) {
		_serviceMonitorAdvice.setActive(active);
	}

	@Override
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