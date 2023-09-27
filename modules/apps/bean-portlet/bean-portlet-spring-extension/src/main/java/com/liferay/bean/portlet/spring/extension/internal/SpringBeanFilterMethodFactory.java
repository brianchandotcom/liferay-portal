/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal;

import com.liferay.bean.portlet.extension.BeanFilterMethod;
import com.liferay.bean.portlet.extension.BeanFilterMethodFactory;

import java.lang.reflect.Method;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Neil Griffin
 */
public class SpringBeanFilterMethodFactory implements BeanFilterMethodFactory {

	public SpringBeanFilterMethodFactory(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
	}

	@Override
	public BeanFilterMethod create(Class<?> beanClass, Method method) {
		return new SpringBeanFilterMethod(beanClass, _beanFactory, method);
	}

	private final BeanFactory _beanFactory;

}