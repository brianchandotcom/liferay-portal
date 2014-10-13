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
import com.liferay.portal.kernel.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.monitoring.jmx.MethodSignature;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michael C. Han
 */
public class ServiceMonitorAdvice extends ChainableMethodAdvice {

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	public static ServiceMonitorAdvice getInstance() {
		return new ServiceMonitorAdvice();
	}

	public static boolean isActive() {
		return _active;
	}

	public void addMonitoredClass(String className) {
		_monitoredClasses.add(className);
	}

	public void addMonitoredMethod(
		String className, String methodName, String[] parameterTypes) {

		MethodSignature methodSignature = new MethodSignature(
			className, methodName, parameterTypes);

		_monitoredMethods.add(methodSignature);
	}

	@Override
	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			serviceRequestDataSample.capture(RequestStatus.SUCCESS);
		}
	}

	@Override
	public void afterThrowing(
			MethodInvocation methodInvocation, Throwable throwable)
		throws Throwable {

		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			serviceRequestDataSample.capture(RequestStatus.ERROR);
		}
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		if (!_active) {
			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			return null;
		}

		if (!_permissiveMode && !isMonitored(methodInvocation)) {
			return null;
		}

		ServiceRequestDataSample serviceRequestDataSample =
			new ServiceRequestDataSample(methodInvocation);

		serviceRequestDataSample.prepare();

		_serviceRequestDataSampleThreadLocal.set(serviceRequestDataSample);

		DataSampleThreadLocal.initialize();

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			_serviceRequestDataSampleThreadLocal.remove();

			DataSampleThreadLocal.addDataSample(serviceRequestDataSample);
		}
	}

	public Set<String> getMonitoredClasses() {
		return _monitoredClasses;
	}

	public Set<MethodSignature> getMonitoredMethods() {
		return _monitoredMethods;
	}

	public boolean isPermissiveMode() {
		return _permissiveMode;
	}

	public void setActive(boolean active) {
		if (active && !_active) {
			serviceBeanAopCacheManager.reset();
		}

		_active = active;
	}

	public void setMonitoredClasses(Set<String> monitoredClasses) {
		_monitoredClasses = monitoredClasses;
	}

	public void setMonitoredMethods(Set<MethodSignature> monitoredMethods) {
		_monitoredMethods = monitoredMethods;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setMonitoringDestinationName(String monitoringDestinationName) {
	}

	public void setPermissiveMode(boolean permissiveMode) {
		_permissiveMode = permissiveMode;
	}

	protected boolean isMonitored(MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		Class<?> declaringClass = method.getDeclaringClass();

		String className = declaringClass.getName();

		if (_monitoredClasses.contains(className)) {
			return true;
		}

		MethodSignature methodSignature = new MethodSignature(method);

		if (_monitoredMethods.contains(methodSignature)) {
			return true;
		}

		return false;
	}

	private static boolean _active;
	private static Set<String> _monitoredClasses = new HashSet<String>();
	private static Set<MethodSignature> _monitoredMethods =
		new HashSet<MethodSignature>();
	private static boolean _permissiveMode;
	private static final ThreadLocal<ServiceRequestDataSample>
		_serviceRequestDataSampleThreadLocal =
			new AutoResetThreadLocal<ServiceRequestDataSample>(
				ServiceRequestDataSample.class +
					"._serviceRequestDataSampleThreadLocal");

}