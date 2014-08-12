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

package com.liferay.portal.spring.aop;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanMatcher implements BeanMatcher {

	public ServiceBeanMatcher() {
		this(false);
	}

	public ServiceBeanMatcher(boolean counterMatcher) {
		_counterMatcher = counterMatcher;
	}

	@Override
	public boolean match(Class<?> beanClass, String beanName) {
		if (_counterMatcher) {
			return beanName.equals(_COUNTER_SERVICE_BEAN_NAME);
		}
		else if (!beanName.equals(_COUNTER_SERVICE_BEAN_NAME) &&
				 beanName.endsWith(_SERVICE_SUFFIX)) {

			return true;
		}

		return false;
	}

	private static final String _COUNTER_SERVICE_BEAN_NAME =
		"com.liferay.counter.service.CounterLocalService";

	private static final String _SERVICE_SUFFIX = "Service";

	private final boolean _counterMatcher;

}