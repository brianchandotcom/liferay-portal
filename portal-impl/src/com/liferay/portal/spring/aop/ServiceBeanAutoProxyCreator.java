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

import com.liferay.portal.kernel.spring.aop.AopProxyFactory;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator
	extends AbstractAdvisorAutoProxyCreator {

	public void afterPropertiesSet() {

		// Backwards compatibility

		if (_beanMatcher == null) {
			_beanMatcher = new ServiceBeanMatcher();
		}
	}

	public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
		_aopProxyFactory = aopProxyFactory;
	}

	public void setBeanMatcher(BeanMatcher beanMatcher) {
		_beanMatcher = beanMatcher;
	}

	@Override
	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
		proxyFactory.setAopProxyFactory(
			new org.springframework.aop.framework.AopProxyFactory() {

				@Override
				public AopProxy createAopProxy(AdvisedSupport advisedSupport)
					throws AopConfigException {

					return new AopProxyAdapter(
						_aopProxyFactory.getAopProxy(
							new AdvisedSupportAdapter(advisedSupport)));
				}

			});
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Object[] getAdvicesAndAdvisorsForBean(
		Class beanClass, String beanName, TargetSource targetSource) {

		Object[] advices = DO_NOT_PROXY;

		if (_beanMatcher.match(beanClass, beanName)) {
			advices = super.getAdvicesAndAdvisorsForBean(
				beanClass, beanName, targetSource);

			if (advices == DO_NOT_PROXY) {
				advices = PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
			}
		}

		return advices;
	}

	private AopProxyFactory _aopProxyFactory;
	private BeanMatcher _beanMatcher;

}