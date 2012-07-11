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

package com.liferay.portal.security.sac;

import com.liferay.portal.security.MethodSecurity;
import com.liferay.portal.security.RemoteAccessTypeThreadLocal;
import com.liferay.portal.security.RemoteMethodAccessType;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 *
 *
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 */
public class SACAdvice extends
	AnnotationChainableMethodAdvice<MethodSecurity> {

	@Override
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		boolean remoteAccess = RemoteAccessTypeThreadLocal.isRemoteAccess();

		MethodSecurity methodSecurity = findAnnotation(methodInvocation);

		if (remoteAccess) {
			Method targetMethod = methodInvocation.getMethod();

			_serviceAccessControlManager.accept(targetMethod, methodSecurity);
		}

		return null;
	}

	@Override
	public MethodSecurity getNullAnnotation() {
		return _nullMethodSecurity;
	}

	public void setServiceAccessControlManager(
		ServiceAccessControlManagerImpl serviceAccessControlManager) {

		_serviceAccessControlManager = serviceAccessControlManager;
	}

	private static MethodSecurity _nullMethodSecurity = new MethodSecurity() {

		public RemoteMethodAccessType remoteMethodAccessType() {
			return RemoteMethodAccessType.ANONYMOUS;
		}

		public Class<? extends Annotation> annotationType() {
			return MethodSecurity.class;
		}

	};

	private ServiceAccessControlManagerImpl _serviceAccessControlManager;

}