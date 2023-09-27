/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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