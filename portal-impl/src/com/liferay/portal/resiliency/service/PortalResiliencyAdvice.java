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

import com.liferay.portal.bean.IdentifiableOSGIServiceInvokerUtil;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControl;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.concurrent.Future;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyAdvice
	extends AnnotationChainableMethodAdvice<AccessControlled> {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		AccessControlled accessControlled = findAnnotation(methodInvocation);

		if (accessControlled == AccessControl.NULL_ACCESS_CONTROLLED) {
			return null;
		}

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (!remoteAccess) {
			return null;
		}

		Object targetObject = methodInvocation.getThis();

		Class<?> targetClass = targetObject.getClass();

		String servletContextName = ClassLoaderPool.getContextName(
			targetClass.getClassLoader());

		SPI spi = SPIRegistryUtil.getServletContextSPI(servletContextName);

		if (spi == null) {
			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			return null;
		}

		ServiceMethodProcessCallable serviceMethodProcessCallable =
			new ServiceMethodProcessCallable(
				IdentifiableOSGIServiceInvokerUtil.createMethodHandler(
					methodInvocation));

		Future<Serializable> future = IntrabandRPCUtil.execute(
			spi.getRegistrationReference(), serviceMethodProcessCallable);

		Object result = future.get();

		Method method = methodInvocation.getMethod();

		Class<?> returnType = method.getReturnType();

		if (returnType == void.class) {
			result = nullResult;
		}

		return result;
	}

	@Override
	public AccessControlled getNullAnnotation() {
		return AccessControl.NULL_ACCESS_CONTROLLED;
	}

}