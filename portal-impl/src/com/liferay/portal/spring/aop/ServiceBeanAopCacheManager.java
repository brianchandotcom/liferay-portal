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

package com.liferay.portal.spring.aop;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
public class ServiceBeanAopCacheManager {

	public static ServiceBeanAopInvocationHandler create(
		Object target, ChainableMethodAdvice[] chainableMethodAdvices) {

		ServiceBeanAopInvocationHandler serviceBeanAopCacheManager =
			new ServiceBeanAopInvocationHandler(target, chainableMethodAdvices);

		_serviceBeanAopInvocationHandlers.add(serviceBeanAopCacheManager);

		return serviceBeanAopCacheManager;
	}

	public static void destroy(
		ServiceBeanAopInvocationHandler serviceBeanAopInvocationHandler) {

		_serviceBeanAopInvocationHandlers.remove(
			serviceBeanAopInvocationHandler);
	}

	public static void reset() {
		for (ServiceBeanAopInvocationHandler serviceBeanAopInvocationHandler :
				_serviceBeanAopInvocationHandlers) {

			serviceBeanAopInvocationHandler.reset();
		}
	}

	private ServiceBeanAopCacheManager() {
	}

	private static final Set<ServiceBeanAopInvocationHandler>
		_serviceBeanAopInvocationHandlers = Collections.newSetFromMap(
			new ConcurrentHashMap<>());

}