/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

import com.liferay.bean.portlet.extension.BeanPortletMethodType;

import java.lang.reflect.Method;

/**
 * @author Neil Griffin
 */
public class DiscoveredBeanMethod {

	public DiscoveredBeanMethod(
		Class<?> beanClass, BeanPortletMethodType beanPortletMethodType,
		Method method) {

		_beanClass = beanClass;
		_beanPortletMethodType = beanPortletMethodType;
		_method = method;
	}

	public Class<?> getBeanClass() {
		return _beanClass;
	}

	public BeanPortletMethodType getBeanPortletMethodType() {
		return _beanPortletMethodType;
	}

	public Method getMethod() {
		return _method;
	}

	public String[] getPortletNames() {
		return _beanPortletMethodType.getPortletNames(_method);
	}

	private final Class<?> _beanClass;
	private final BeanPortletMethodType _beanPortletMethodType;
	private final Method _method;

}