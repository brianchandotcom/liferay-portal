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

import com.liferay.bean.portlet.extension.BaseBeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Neil Griffin
 */
public class SpringBeanPortletMethod extends BaseBeanPortletMethod {

	public SpringBeanPortletMethod(
		Class<?> beanClass, BeanFactory beanFactory,
		BeanPortletMethodType beanPortletMethodType, Method method) {

		super(beanPortletMethodType, method);

		_beanClass = beanClass;
		_beanFactory = beanFactory;
	}

	@Override
	public Class<?> getBeanType() {
		return _beanClass;
	}

	@Override
	public Object invoke(Object... args) throws ReflectiveOperationException {
		Method method = getMethod();

		return method.invoke(_beanFactory.getBean(_beanClass), args);
	}

	private final Class<?> _beanClass;
	private final BeanFactory _beanFactory;

}