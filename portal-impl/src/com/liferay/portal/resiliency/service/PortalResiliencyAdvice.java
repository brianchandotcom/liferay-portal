/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.rpc.IntraBandRPCUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIRuntimeMappingUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.security.ac.AccessControlThreadLocal;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class PortalResiliencyAdvice extends
	AnnotationChainableMethodAdvice<AccessControlled> {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		AccessControlled accessControlled = findAnnotation(methodInvocation);

		if (accessControlled == AccessControlled.NULL_ACCESS_CONTROLLED) {
			return null;
		}

		boolean remoteAccess = AccessControlThreadLocal.isRemoteAccess();

		if (remoteAccess) {
			Method targetMethod = methodInvocation.getMethod();

			Object targetObject = methodInvocation.getThis();

			Class<?> targetClass = null;

			if (targetObject == null) {
				targetClass = targetMethod.getDeclaringClass();
			}
			else {
				targetClass = targetObject.getClass();
			}

			ClassLoader classLoader = targetClass.getClassLoader();

			String servletContextName = ClassLoaderPool.getContextName(
				classLoader);

			SPI spi = SPIRuntimeMappingUtil.getMappingSPIForServletContext(
				servletContextName);

			if (spi != null) {
				if (!(targetObject instanceof IdentifiableBean)) {
					_log.error(
						"Can not bridge portal resiliency call for " +
							targetObject.getClass().getName() +
								" because it does not implement " +
									IdentifiableBean.class.getName());

					return null;
				}

				MethodHandler methodHandler = new MethodHandler(
					targetMethod, methodInvocation.getArguments());

				IdentifiableBean identifiableBean =
					(IdentifiableBean)targetObject;

				ServiceMethodProcessCallable methodHandlerProcessCallable =
					new ServiceMethodProcessCallable(
						servletContextName,
						identifiableBean.getBeanIdentifier(), methodHandler);

				RegistrationReference registrationReference =
					spi.getRegistrationReference();

				Object result = IntraBandRPCUtil.execute(
					registrationReference, methodHandlerProcessCallable);

				return result;
			}
			else {
				serviceBeanAopCacheManager.removeMethodInterceptor(
					methodInvocation, this);
			}
		}

		return null;
	}

	@Override
	public AccessControlled getNullAnnotation() {
		return AccessControlled.NULL_ACCESS_CONTROLLED;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalResiliencyAdvice.class);

}