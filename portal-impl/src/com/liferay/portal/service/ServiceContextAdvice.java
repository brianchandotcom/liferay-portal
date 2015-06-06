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

package com.liferay.portal.service;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.util.LinkedList;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Preston Crary
 */
public class ServiceContextAdvice extends ChainableMethodAdvice {

	public Object before(MethodInvocation methodInvocation) {
		Object[] arguments = methodInvocation.getArguments();

		LinkedList<Boolean> stack = _popServiceContext.get();

		if (arguments != null) {
			for (int i = arguments.length - 1; i >= 0; i--) {
				if (arguments[i] instanceof ServiceContext) {
					ServiceContext serviceContext =
						(ServiceContext)arguments[i];

					if (serviceContext == null) {
						stack.push(false);
					}
					else {
						ServiceContextThreadLocal.pushServiceContext(
							serviceContext);

						stack.push(true);
					}

					return null;
				}
			}
		}

		stack.push(false);

		serviceBeanAopCacheManager.removeMethodInterceptor(
			methodInvocation, this);

		return null;
	}

	public void duringFinally(MethodInvocation methodInvocation) {
		LinkedList<Boolean> stack = _popServiceContext.get();

		if (stack.pop()) {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static final ThreadLocal<LinkedList<Boolean>> _popServiceContext =
		new AutoResetThreadLocal<>(
			ServiceContextAdvice.class.getName() + "._popServiceContext",
			new LinkedList<Boolean>());

}