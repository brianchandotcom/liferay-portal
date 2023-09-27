/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public Class<?> getBeanClass() {
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