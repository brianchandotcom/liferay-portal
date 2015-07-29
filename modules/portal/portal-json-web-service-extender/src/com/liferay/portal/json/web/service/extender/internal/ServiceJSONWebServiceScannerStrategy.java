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

package com.liferay.portal.json.web.service.extender.internal;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceScannerStrategy;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Miguel Pastor
 */
public class ServiceJSONWebServiceScannerStrategy
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

		List<MethodDescriptor> result = new ArrayList<>(methods.length);

		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();

			if (declaringClass != clazz) {
				continue;
			}

			result.add(new MethodDescriptor(method));
		}

		return result.toArray(new MethodDescriptor[result.size()]);
	}

	protected Class<?> getTargetClass(Object service) throws Exception {
		Class<?> clazz = service.getClass();

		if (ProxyUtil.isProxyClass(clazz)) {
			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			TargetSource targetSource = advisedSupport.getTargetSource();

			Object target = targetSource.getTarget();

			clazz = target.getClass();
		}

		return clazz;
	}

}