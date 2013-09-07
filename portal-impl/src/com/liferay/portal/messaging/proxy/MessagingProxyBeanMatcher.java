/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging.proxy;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassResolverUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.BeanMatcher;

/**
 * @author Shuyang Zhou
 */
public class MessagingProxyBeanMatcher implements BeanMatcher {

	@Override
	public boolean match(Class<?> beanClass, String beanName) {
		if (_beanClass.isAssignableFrom(beanClass) &&
			StringUtil.wildcardMatches(
				beanName, _beanNamePattern, CharPool.QUESTION, CharPool.STAR,
				CharPool.PERCENT, true)) {

			return true;
		}

		return false;
	}

	public void setBeanClass(String beanClassName) {
		_beanClass = ClassResolverUtil.resolveByPortalClassLoader(
			beanClassName);
	}

	public void setBeanNamePattern(String beanNamePattern) {
		_beanNamePattern = beanNamePattern;
	}

	private Class<?> _beanClass;
	private String _beanNamePattern;

}