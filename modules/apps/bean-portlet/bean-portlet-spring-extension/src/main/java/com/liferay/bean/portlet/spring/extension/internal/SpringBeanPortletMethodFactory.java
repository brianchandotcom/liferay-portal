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

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodFactory;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Neil Griffin
 */
public class SpringBeanPortletMethodFactory
	implements BeanPortletMethodFactory {

	public SpringBeanPortletMethodFactory(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
	}

	@Override
	public BeanPortletMethod create(
		Class<?> beanClass, BeanPortletMethodType beanPortletMethodType,
		Method method) {

		return new SpringBeanPortletMethod(
			beanClass, _beanFactory, beanPortletMethodType, method);
	}

	private final BeanFactory _beanFactory;

}