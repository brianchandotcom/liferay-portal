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
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.MethodContextHelper;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyAdvice extends ChainableMethodAdvice {

	@Override
	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (!remoteAccess) {
			return null;
		}

		SPI spi = serviceBeanMethodInvocation.getCurrentAdviceMethodContext();

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
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		MethodContextHelper methodContextHelper) {

		if (!PropsValues.PORTAL_RESILIENCY_ENABLED) {
			return null;
		}

		AccessControlled accessControlled = methodContextHelper.findAnnotation(
			AccessControlled.class);

		if (accessControlled == null) {
			return null;
		}

		String servletContextName =
			ServletContextClassLoaderPool.getServletContextName(
				targetClass.getClassLoader());

		if (servletContextName == null) {
			return null;
		}

		return SPIRegistryUtil.getServletContextSPI(servletContextName);
	}

}