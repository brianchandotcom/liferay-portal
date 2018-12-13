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
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		if (_monitorServiceRequest) {
			return nullResult;
		}

		return null;
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
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		if (!_monitorServiceRequest) {
			return serviceBeanMethodInvocation.proceed();
		}

		boolean included = false;

		Method method = serviceBeanMethodInvocation.getMethod();

		Class<?> declaringClass = method.getDeclaringClass();

		MethodSignature methodSignature = new MethodSignature(method);

		if (_serviceClasses.contains(declaringClass.getName()) ||
			_serviceClassMethods.contains(methodSignature)) {

			included = true;
		}

		if (_inclusiveMode != included) {
			return serviceBeanMethodInvocation.proceed();
		}

		DataSample dataSample =
			DataSampleFactoryUtil.createServiceRequestDataSample(
				methodSignature);

		dataSample.prepare();

		DataSampleThreadLocal.initialize();

		try {
			Object returnValue = serviceBeanMethodInvocation.proceed();

			dataSample.capture(RequestStatus.SUCCESS);

			return returnValue;
		}
		catch (Throwable throwable) {
			dataSample.capture(RequestStatus.ERROR);

			throw throwable;
		}
		finally {
			DataSampleThreadLocal.addDataSample(dataSample);
		}
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
			ServiceBeanAopCacheManager.reset();
		}

		_monitorServiceRequest = monitorServiceRequest;
	}

	private static boolean _inclusiveMode = true;
	private static boolean _monitorServiceRequest;
	private static final Set<String> _serviceClasses = new HashSet<>();
	private static final Set<MethodSignature> _serviceClassMethods =
		new HashSet<>();

}