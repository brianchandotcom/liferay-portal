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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.spring.aop.AdvisedSupport;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopProxyUtils;

/**
 * @author Tina Tian
 */
public class AdvisedSupportAdapter implements AdvisedSupport {

	public AdvisedSupportAdapter(
		org.springframework.aop.framework.AdvisedSupport advisedSupport) {

		_advisedSupport = advisedSupport;
	}

	@Override
	public Class<?>[] getProxiedInterfaces() {
		return AopProxyUtils.completeProxiedInterfaces(_advisedSupport);
	}

	@Override
	public Object getTarget() {
		TargetSource targetSource = _advisedSupport.getTargetSource();

		try {
			return targetSource.getTarget();
		}
		catch (Exception e) {
			return ReflectionUtil.throwException(e);
		}
	}

	@Override
	public void setTarget(Object target) {
		_advisedSupport.setTarget(target);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void setTarget(Object target, final Class<?> targetClass) {
		setTarget(target);
	}

	private final org.springframework.aop.framework.AdvisedSupport
		_advisedSupport;

}