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

import com.liferay.portal.kernel.spring.aop.AdvisedSupport;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.target.SingletonTargetSource;

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
	public Object getTarget() throws Exception {
		TargetSource targetSource = _advisedSupport.getTargetSource();

		return targetSource.getTarget();
	}

	@Override
	public void setTarget(Object target) {
		_advisedSupport.setTarget(target);
	}

	@Override
	public void setTarget(Object target, final Class<?> targetClass) {
		_advisedSupport.setTargetSource(
			new SingletonTargetSource(target) {

				@Override
				public Class<?> getTargetClass() {
					return targetClass;
				}

			});
	}

	private final org.springframework.aop.framework.AdvisedSupport
		_advisedSupport;

}