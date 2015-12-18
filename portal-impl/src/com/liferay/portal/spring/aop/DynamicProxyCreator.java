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

import com.liferay.portal.kernel.spring.aop.InvocationHandlerFactory;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;

/**
 * @author Shuyang Zhou
 */
public class DynamicProxyCreator
	extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {

	public static DynamicProxyCreator getDynamicProxyCreator() {
		return _instance;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		Class<?> beanClass = bean.getClass();

		for (ObjectValuePair<BeanMatcher, InvocationHandlerFactory>
				objectValuePair : _beanMatcherInvocationHandlerFactories) {

			BeanMatcher beanMatcher = objectValuePair.getKey();

			if (beanMatcher.match(beanClass, beanName)) {
				InvocationHandlerFactory invocationHandlerFactory =
					objectValuePair.getValue();

				InvocationHandler invocationHandler =
					invocationHandlerFactory.createInvocationHandler(bean);

				bean = ProxyUtil.newProxyInstance(
					ClassLoaderUtil.getContextClassLoader(),
					beanClass.getInterfaces(), invocationHandler);
			}
		}

		return bean;
	}

	public static class Register {

		public Register(
			BeanMatcher beanMatcher,
			InvocationHandlerFactory invocationHandlerFactory) {

			ObjectValuePair<BeanMatcher, InvocationHandlerFactory>
				objectValuePair = new ObjectValuePair<>(
					beanMatcher, invocationHandlerFactory);

			_instance._beanMatcherInvocationHandlerFactories.add(
				objectValuePair);
		}

	}

	private static final DynamicProxyCreator _instance =
		new DynamicProxyCreator();

	private final List<ObjectValuePair<BeanMatcher, InvocationHandlerFactory>>
		_beanMatcherInvocationHandlerFactories = new ArrayList<>();

}