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

package com.liferay.portal.internal.cluster;

import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class SPIClusterableAdvice extends ChainableMethodAdvice {

	@Override
	public void afterReturning(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments, Object result)
		throws Throwable {

		Clusterable clusterable =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		SPI spi = SPIUtil.getSPI();

		IntrabandRPCUtil.execute(
			spi.getRegistrationReference(),
			new MethodHandlerProcessCallable<Serializable>(
				ClusterableInvokerUtil.createMethodHandler(
					clusterable.acceptor(),
					serviceBeanMethodInvocation.getThis(),
					serviceBeanMethodInvocation.getMethod(), arguments)));
	}

	@Override
	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		Clusterable clusterable =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		if (!clusterable.onMaster()) {
			return null;
		}

		SPI spi = SPIUtil.getSPI();

		Future<Serializable> futureResult = IntrabandRPCUtil.execute(
			spi.getRegistrationReference(),
			new MethodHandlerProcessCallable<Serializable>(
				ClusterableInvokerUtil.createMethodHandler(
					clusterable.acceptor(),
					serviceBeanMethodInvocation.getThis(),
					serviceBeanMethodInvocation.getMethod(), arguments)));

		Object result = futureResult.get();

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
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return annotations.get(Clusterable.class);
	}

}