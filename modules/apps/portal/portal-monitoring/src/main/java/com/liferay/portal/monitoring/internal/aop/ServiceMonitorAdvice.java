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

package com.liferay.portal.monitoring.internal.aop;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ServiceMonitorAdvice extends ChainableMethodAdvice {

	public ServiceMonitorAdvice(
		ServiceMonitoringControl serviceMonitoringControl,
		DataSampleFactory dataSampleFactory) {

		_serviceMonitoringControl = serviceMonitoringControl;
		_dataSampleFactory = dataSampleFactory;
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return new MethodSignature(method);
	}

	@Override
	public Object invoke(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		boolean included = false;

		Method method = aopMethodInvocation.getMethod();

		Class<?> declaringClass = method.getDeclaringClass();

		MethodSignature methodSignature =
			aopMethodInvocation.getAdviceMethodContext();

		Set<String> serviceClasses =
			_serviceMonitoringControl.getServiceClasses();
		Set<MethodSignature> serviceClassMethods =
			_serviceMonitoringControl.getServiceClassMethods();

		if (serviceClasses.contains(declaringClass.getName()) ||
			serviceClassMethods.contains(methodSignature)) {

			included = true;
		}

		if (_serviceMonitoringControl.isInclusiveMode() != included) {
			return aopMethodInvocation.proceed(arguments);
		}

		DataSample dataSample =
			_dataSampleFactory.createServiceRequestDataSample(methodSignature);

		dataSample.prepare();

		DataSampleThreadLocal.initialize();

		try {
			Object returnValue = aopMethodInvocation.proceed(arguments);

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

	private final DataSampleFactory _dataSampleFactory;
	private final ServiceMonitoringControl _serviceMonitoringControl;

}