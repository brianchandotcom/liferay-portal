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

package com.liferay.bean.portlet.registration.internal;

import com.liferay.bean.portlet.extension.BeanPortletMethodType;

import java.lang.reflect.Method;

/**
 * @author Neil Griffin
 */
public class DiscoveredBeanMethod {

	public DiscoveredBeanMethod(
		Class<?> beanType, Method method,
		BeanPortletMethodType beanPortletMethodType) {

		_beanType = beanType;
		_method = method;
		_beanPortletMethodType = beanPortletMethodType;
	}

	public Class<?> getBeanType() {
		return _beanType;
	}

	public Method getMethod() {
		return _method;
	}

	public BeanPortletMethodType getMethodType() {
		return _beanPortletMethodType;
	}

	public String[] getPortletNames() {
		return _beanPortletMethodType.getPortletNames(_method);
	}

	private final BeanPortletMethodType _beanPortletMethodType;
	private final Class<?> _beanType;
	private final Method _method;

}