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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceScannerStrategy;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Miguel Pastor
 */
public class SpringJSONWebServiceScannerStrategy
	implements JSONWebServiceScannerStrategy {

	@Override
	public MethodDescriptor[] scan(Object service) {
		Class<?> clazz = null;

		try {
			clazz = getTargetClass(service);
		}
		catch (Exception e) {
			return new MethodDescriptor[0];
		}

		Method[] methods = clazz.getMethods();

		List<MethodDescriptor> methodDescriptors = new ArrayList<>(
			methods.length);

		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();

			if ((declaringClass != clazz) || !isInterfaceMethod(method)) {
				continue;
			}

			methodDescriptors.add(new MethodDescriptor(method));
		}

		return methodDescriptors.toArray(
			new MethodDescriptor[methodDescriptors.size()]);
	}

	protected Class<?> getTargetClass(Object service) throws Exception {
		if (ProxyUtil.isProxyClass(service.getClass())) {
			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			TargetSource targetSource = advisedSupport.getTargetSource();

			service = targetSource.getTarget();
		}

		return service.getClass();
	}

	protected boolean isInterfaceMethod(Method method) {
		Class<?> declaringClass = method.getDeclaringClass();

		if (declaringClass.isInterface()) {
			return true;
		}

		Queue<Class<?>> queue = new LinkedList<>(
			Arrays.asList(declaringClass.getInterfaces()));

		Class<?> superClass = declaringClass.getSuperclass();

		if (superClass != null) {
			queue.add(superClass);
		}

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			if (clazz.isInterface()) {
				try {
					clazz.getMethod(
						method.getName(), method.getParameterTypes());

					return true;
				}
				catch (ReflectiveOperationException roe) {
				}
			}
			else {
				queue.addAll(Arrays.asList(clazz.getInterfaces()));

				superClass = clazz.getSuperclass();

				if (superClass != null) {
					queue.add(superClass);
				}
			}
		}

		return false;
	}

}