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
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceMonitorAdvice extends ChainableMethodAdvice {

	public ServiceMonitorAdvice(
		ServiceMonitoringControl serviceMonitoringControl) {

		_serviceMonitoringControl = serviceMonitoringControl;
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		if (_serviceMonitoringControl.isMonitorServiceRequest()) {
			return nullResult;
		}

		return null;
	}

	@Override
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		if (!_serviceMonitoringControl.isMonitorServiceRequest()) {
			return serviceBeanMethodInvocation.proceed(arguments);
		}

		boolean included = false;

		Method method = serviceBeanMethodInvocation.getMethod();

		Class<?> declaringClass = method.getDeclaringClass();

		MethodSignature methodSignature = new MethodSignature(method);

		Set<String> serviceClasses =
			_serviceMonitoringControl.getServiceClasses();
		Set<MethodSignature> serviceClassMethods =
			_serviceMonitoringControl.getServiceClassMethods();

		if (serviceClasses.contains(declaringClass.getName()) ||
			serviceClassMethods.contains(methodSignature)) {

			included = true;
		}

		if (_serviceMonitoringControl.isInclusiveMode() != included) {
			return serviceBeanMethodInvocation.proceed(arguments);
		}

		DataSample dataSample =
			DataSampleFactoryUtil.createServiceRequestDataSample(
				methodSignature);

		dataSample.prepare();

		DataSampleThreadLocal.initialize();

		try {
			Object returnValue = serviceBeanMethodInvocation.proceed(arguments);

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

	private final ServiceMonitoringControl _serviceMonitoringControl;

}