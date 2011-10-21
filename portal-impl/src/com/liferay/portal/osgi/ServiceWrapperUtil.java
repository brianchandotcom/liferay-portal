/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.osgi;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Raymond Augé
 */
public class ServiceWrapperUtil {

	public static AdvisedSupport getAdvisedSupport(Object serviceProxy)
		throws Exception {

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			serviceProxy);

		Class<?> invocationHandlerClass = invocationHandler.getClass();

		Field advisedSupportField = invocationHandlerClass.getDeclaredField(
			"_advisedSupport");

		advisedSupportField.setAccessible(true);

		return (AdvisedSupport)advisedSupportField.get(invocationHandler);
	}

	public static Object getTarget(AdvisedSupport advisedSupport)
		throws Exception {

		TargetSource targetSource = advisedSupport.getTargetSource();

		Object previousService = targetSource.getTarget();

		if (!ProxyUtil.isProxyClass(previousService.getClass())) {
			return previousService;
		}

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			previousService);

		if (invocationHandler instanceof ClassLoaderBeanHandler) {
			ClassLoaderBeanHandler classLoaderBeanHandler =
				(ClassLoaderBeanHandler)invocationHandler;

			previousService = classLoaderBeanHandler.getBean();
		}

		return previousService;
	}

}