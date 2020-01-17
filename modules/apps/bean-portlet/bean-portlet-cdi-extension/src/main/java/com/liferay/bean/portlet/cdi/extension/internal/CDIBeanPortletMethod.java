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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.bean.portlet.extension.BaseBeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;

import java.lang.reflect.Method;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

/**
 * @author Neil Griffin
 */
public class CDIBeanPortletMethod extends BaseBeanPortletMethod {

	public CDIBeanPortletMethod(
		Class<?> beanClass, BeanManager beanManager,
		BeanPortletMethodType beanPortletMethodType, Method method) {

		super(beanPortletMethodType, method);

		_beanClass = beanClass;
		_beanManager = beanManager;
	}

	@Override
	public Class<?> getBeanType() {
		return _beanClass;
	}

	@Override
	public Object invoke(Object... args) throws ReflectiveOperationException {
		Bean<?> bean = _beanManager.resolve(_beanManager.getBeans(_beanClass));

		CreationalContext<?> creationalContext =
			_beanManager.createCreationalContext(bean);

		Object beanInstance = _beanManager.getReference(
			bean, bean.getBeanClass(), creationalContext);

		Method method = getMethod();

		return method.invoke(beanInstance, args);
	}

	private final Class<?> _beanClass;
	private final BeanManager _beanManager;

}