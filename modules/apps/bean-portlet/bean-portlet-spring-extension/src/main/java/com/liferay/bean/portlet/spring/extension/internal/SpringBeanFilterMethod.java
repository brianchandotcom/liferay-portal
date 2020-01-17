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

package com.liferay.bean.portlet.spring.extension.internal;

import com.liferay.bean.portlet.extension.BeanFilterMethod;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Neil Griffin
 */
public class SpringBeanFilterMethod implements BeanFilterMethod {

	public SpringBeanFilterMethod(
		BeanFactory beanFactory, Method method, Class<?> beanClass) {

		_beanFactory = beanFactory;
		_method = method;
		_beanClass = beanClass;
	}

	@Override
	public Object invoke(Object... args) throws ReflectiveOperationException {
		return _method.invoke(_beanFactory.getBean(_beanClass), args);
	}

	private final Class<?> _beanClass;
	private final BeanFactory _beanFactory;
	private final Method _method;

}