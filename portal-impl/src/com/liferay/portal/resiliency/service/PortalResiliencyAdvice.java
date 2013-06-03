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

package com.liferay.portal.resiliency.service;

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.security.ac.AccessControl;
import com.liferay.portal.security.ac.AccessControlThreadLocal;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

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

		if (targetObject == null) {
			return null;
		}

		Class<?> targetClass = targetObject.getClass();

		String servletContextName = ClassLoaderPool.getContextName(
			targetClass.getClassLoader());

		SPI spi = SPIRegistryUtil.getServletContextSPI(servletContextName);

		if (spi == null) {
			serviceBeanAopCacheManager.removeMethodInterceptor(
				methodInvocation, this);

			return null;
		}

		if (!(targetObject instanceof IdentifiableBean)) {
			Class<?> clazz = targetObject.getClass();

			_log.error(
				"Unable to bridge portal resiliency call for " +
					clazz.getName() + " because it does not implement " +
						IdentifiableBean.class.getName());

			return null;
		}

		IdentifiableBean identifiableBean = (IdentifiableBean)targetObject;

		ServiceMethodProcessCallable serviceMethodProcessCallable =
			new ServiceMethodProcessCallable(
				servletContextName, identifiableBean.getBeanIdentifier(),
				new MethodHandler(
					methodInvocation.getMethod(),
					methodInvocation.getArguments()));

		return IntrabandRPCUtil.execute(
			spi.getRegistrationReference(), serviceMethodProcessCallable);
	}

	@Override
	public AccessControlled getNullAnnotation() {
		return AccessControl.NULL_ACCESS_CONTROLLED;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalResiliencyAdvice.class);

}