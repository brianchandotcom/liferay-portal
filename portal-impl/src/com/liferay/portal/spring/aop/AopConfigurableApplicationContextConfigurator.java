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

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.spring.context.ConfigurableApplicationContextConfigurator;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Shuyang Zhou
 */
public class AopConfigurableApplicationContextConfigurator
	implements ApplicationContextAware, BeanFactoryPostProcessor,
			   ConfigurableApplicationContextConfigurator {

	@Override
	public void configure(
		ConfigurableApplicationContext configurableApplicationContext) {

		configurableApplicationContext.addBeanFactoryPostProcessor(
			configurableListableBeanFactory -> _postProcessBeanFactory(
				configurableApplicationContext.getClassLoader(),
				configurableListableBeanFactory));
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory configurableListableBeanFactory)
		throws BeansException {

		_postProcessBeanFactory(_classLoader, configurableListableBeanFactory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {

		_classLoader = applicationContext.getClassLoader();
	}

	private static void _postProcessBeanFactory(
		ClassLoader classLoader,
		ConfigurableListableBeanFactory configurableListableBeanFactory) {

		// Ensure the ChainableMethodAdvice assembling is done

		configurableListableBeanFactory.getBean(
			"chainableMethodAdviceAssembler",
			ChainableMethodAdviceAssembler.class);

		// Counter AOP for portal spring context only

		if (PortalClassLoaderUtil.isPortalClassLoader(classLoader)) {
			ServiceBeanAutoProxyCreator counterServiceBeanAutoProxyCreator =
				new ServiceBeanAutoProxyCreator();

			counterServiceBeanAutoProxyCreator.setBeanMatcher(
				new ServiceBeanMatcher(true));
			counterServiceBeanAutoProxyCreator.setMethodInterceptor(
				configurableListableBeanFactory.getBean(
					"counterTransactionAdvice", MethodInterceptor.class));

			counterServiceBeanAutoProxyCreator.setBeanClassLoader(classLoader);

			counterServiceBeanAutoProxyCreator.afterPropertiesSet();

			configurableListableBeanFactory.addBeanPostProcessor(
				counterServiceBeanAutoProxyCreator);
		}

		// Service AOP

		ServiceBeanAutoProxyCreator serviceBeanAutoProxyCreator =
			new ServiceBeanAutoProxyCreator();

		serviceBeanAutoProxyCreator.setBeanMatcher(new ServiceBeanMatcher());
		serviceBeanAutoProxyCreator.setMethodInterceptor(
			configurableListableBeanFactory.getBean(
				"serviceAdvice", MethodInterceptor.class));

		serviceBeanAutoProxyCreator.setBeanClassLoader(classLoader);

		serviceBeanAutoProxyCreator.afterPropertiesSet();

		configurableListableBeanFactory.addBeanPostProcessor(
			serviceBeanAutoProxyCreator);
	}

	private ClassLoader _classLoader;

}