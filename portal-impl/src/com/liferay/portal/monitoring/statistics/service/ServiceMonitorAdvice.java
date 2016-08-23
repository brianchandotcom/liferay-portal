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

import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michael C. Han
 */
public class ServiceMonitorAdvice
	extends ChainableMethodAdvice implements ServiceMonitoringControl {

	@Override
	public void addServiceClass(String className) {
		_serviceClasses.add(className);
	}

	@Override
	public void addServiceClassMethod(
		String className, String methodName, String[] parameterTypes) {

		MethodSignature methodSignature = new MethodSignature(
			className, methodName, parameterTypes);

		_serviceClassMethods.add(methodSignature);
	}

	@Override
	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		DataSample dataSample = _dataSampleThreadLocal.get();

		if (dataSample != null) {
			dataSample.capture(RequestStatus.SUCCESS);
		}
	}

	@Override
	public void afterThrowing(
			MethodInvocation methodInvocation, Throwable throwable)
		throws Throwable {

		DataSample dataSample = _dataSampleThreadLocal.get();

		if (dataSample != null) {
			dataSample.capture(RequestStatus.ERROR);
		}
	}

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		if (!_monitorServiceRequest) {
			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			return null;
		}

		boolean included = isIncluded(methodInvocation);

		if ((!_inclusiveMode && included) || (_inclusiveMode && !included)) {
			return null;
		}

		MethodSignature methodSignature = new MethodSignature(
			methodInvocation.getMethod());

		DataSample dataSample =
			DataSampleFactoryUtil.createServiceRequestDataSample(
				methodSignature);

		dataSample.prepare();

		_dataSampleThreadLocal.set(dataSample);

		DataSampleThreadLocal.initialize();

		return null;
	}

	@Override
	public void duringFinally(MethodInvocation methodInvocation) {
		DataSample dataSample = _dataSampleThreadLocal.get();

		if (dataSample != null) {
			_dataSampleThreadLocal.remove();

			DataSampleThreadLocal.addDataSample(dataSample);
		}
	}

	@Override
	public Set<String> getServiceClasses() {
		return Collections.unmodifiableSet(_serviceClasses);
	}

	@Override
	public Set<MethodSignature> getServiceClassMethods() {
		return Collections.unmodifiableSet(_serviceClassMethods);
	}

	@Override
	public boolean isInclusiveMode() {
		return _inclusiveMode;
	}

	@Override
	public boolean isMonitorServiceRequest() {
		return _monitorServiceRequest;
	}

	@Override
	public void setInclusiveMode(boolean inclusiveMode) {
		_inclusiveMode = inclusiveMode;
	}

	@Override
	public void setMonitorServiceRequest(boolean monitorServiceRequest) {
		if (monitorServiceRequest && !_monitorServiceRequest) {
			serviceBeanAopCacheManager.reset();
		}

		_monitorServiceRequest = monitorServiceRequest;
	}

	protected boolean isIncluded(MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		Class<?> declaringClass = method.getDeclaringClass();

		String className = declaringClass.getName();

		if (_serviceClasses.contains(className)) {
			return true;
		}

		MethodSignature methodSignature = new MethodSignature(method);

		if (_serviceClassMethods.contains(methodSignature)) {
			return true;
		}

		return false;
	}

	private static final ThreadLocal<DataSample> _dataSampleThreadLocal =
		new AutoResetThreadLocal<>(
			ServiceMonitorAdvice.class + "._dataSampleThreadLocal");
	private static boolean _inclusiveMode = true;
	private static boolean _monitorServiceRequest;
	private static final Set<String> _serviceClasses = new HashSet<>();
	private static final Set<MethodSignature> _serviceClassMethods =
		new HashSet<>();

}