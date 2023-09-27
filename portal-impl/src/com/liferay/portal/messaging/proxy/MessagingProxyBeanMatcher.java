/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.proxy;

import com.liferay.petra.lang.ClassResolverUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
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

	public void setBeanClass(String beanClassName)
		throws ClassNotFoundException {

		_beanClass = ClassResolverUtil.resolve(
			beanClassName, PortalClassLoaderUtil.getClassLoader());
	}

	public void setBeanNamePattern(String beanNamePattern) {
		_beanNamePattern = beanNamePattern;
	}

	private Class<?> _beanClass;
	private String _beanNamePattern;

}