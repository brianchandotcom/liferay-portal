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

package com.liferay.portal.resiliency.service;

import com.liferay.portal.internal.resiliency.service.ServiceMethodProcessCallable;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceInvokerUtil;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyAdvice
	extends AnnotationChainableMethodAdvice<AccessControlled> {

	public PortalResiliencyAdvice() {
		super(AccessControlled.class);
	}

	@Override
	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (!remoteAccess) {
			return null;
		}

		Object targetObject = serviceBeanMethodInvocation.getThis();

		Class<?> targetClass = targetObject.getClass();

		SPI spi = _getSPI(targetClass);

		if (spi == null) {
			return null;
		}

		ServiceMethodProcessCallable serviceMethodProcessCallable =
			new ServiceMethodProcessCallable(
				IdentifiableOSGiServiceInvokerUtil.createMethodHandler(
					serviceBeanMethodInvocation.getThis(),
					serviceBeanMethodInvocation.getMethod(),
					serviceBeanMethodInvocation.getArguments()));

		Future<Serializable> future = IntrabandRPCUtil.execute(
			spi.getRegistrationReference(), serviceMethodProcessCallable);

		Object result = future.get();

		Method method = serviceBeanMethodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			result = nullResult;
		}

		return result;
	}

	@Override
	public boolean isEnabled(
		Class<?> targetClass, Method method,
		AnnotationHelper annotationHelper) {

		if (!PropsValues.PORTAL_RESILIENCY_ENABLED) {
			return false;
		}

		if (!super.isEnabled(targetClass, method, annotationHelper)) {
			return false;
		}

		SPI spi = _getSPI(targetClass);

		if (spi == null) {
			return false;
		}

		return true;
	}

	private SPI _getSPI(Class<?> targetClass) {
		String servletContextName =
			ServletContextClassLoaderPool.getServletContextName(
				targetClass.getClassLoader());

		if (servletContextName == null) {
			return null;
		}

		return SPIRegistryUtil.getServletContextSPI(servletContextName);
	}

}