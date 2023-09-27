/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Class<?> beanClass, BeanFactory beanFactory, Method method) {

		_beanClass = beanClass;
		_beanFactory = beanFactory;
		_method = method;
	}

	@Override
	public Object invoke(Object... arguments)
		throws ReflectiveOperationException {

		return _method.invoke(_beanFactory.getBean(_beanClass), arguments);
	}

	private final Class<?> _beanClass;
	private final BeanFactory _beanFactory;
	private final Method _method;

}