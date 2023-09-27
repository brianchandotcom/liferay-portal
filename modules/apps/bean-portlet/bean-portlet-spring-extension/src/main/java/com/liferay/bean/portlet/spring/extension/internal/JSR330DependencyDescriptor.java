/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;

/**
 * @author Neil Griffin
 */
public class JSR330DependencyDescriptor extends DependencyDescriptor {

	public JSR330DependencyDescriptor(
		String beanName, DependencyDescriptor original,
		Class<?> requiredClass) {

		super(original);

		_beanName = beanName;
		_requiredClass = requiredClass;
	}

	@Override
	public Object resolveShortcut(BeanFactory beanFactory) {
		return beanFactory.getBean(_beanName, _requiredClass);
	}

	private static final long serialVersionUID = 1L;

	private final String _beanName;
	private final Class<?> _requiredClass;

}