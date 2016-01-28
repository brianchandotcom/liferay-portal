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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Preston Crary
 */
public class BeanNameMatcher implements BeanMatcher {

	public void afterPropertiesSet() {
		if (_beanNamePattern == null) {
			throw new IllegalStateException("Bean name pattern must be set");
		}
	}

	@Override
	public boolean match(Class<?> beanClass, String beanName) {
		return StringUtil.wildcardMatches(
			beanName, _beanNamePattern, CharPool.QUESTION, CharPool.STAR,
			CharPool.PERCENT, true);
	}

	public void setBeanNamePattern(String beanNamePattern) {
		_beanNamePattern = beanNamePattern;
	}

	private String _beanNamePattern;

}