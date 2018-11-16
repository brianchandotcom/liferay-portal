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

import com.liferay.portal.cache.thread.local.ThreadLocalCacheAdvice;
import com.liferay.portal.increment.BufferedIncrementAdvice;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.messaging.async.AsyncAdvice;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;
import com.liferay.portal.resiliency.service.PortalResiliencyAdvice;
import com.liferay.portal.search.IndexableAdvice;
import com.liferay.portal.security.access.control.AccessControlAdvice;
import com.liferay.portal.spring.context.ConfigurableApplicationContextConfigurator;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Shuyang Zhou
 */
public class AopConfigurableApplicationContextConfigurator
	implements ConfigurableApplicationContextConfigurator {

	@Override
	public void configure(
		ConfigurableApplicationContext configurableApplicationContext) {

		configurableApplicationContext.addBeanFactoryPostProcessor(
			new AopBeanFactoryPostProcessor(
				configurableApplicationContext.getClassLoader()));
	}

	private static class AopBeanFactoryPostProcessor
		implements BeanFactoryPostProcessor {

		@Override
		public void postProcessBeanFactory(
				ConfigurableListableBeanFactory configurableListableBeanFactory)
			throws BeansException {

			if (configurableListableBeanFactory.getBeanDefinitionCount() == 0) {

				// Protection for the those theme wars with no spring xmls.

				return;
			}

			_assembleChainableMethodAdvices(configurableListableBeanFactory);

			// Counter AOP for portal spring context only

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				ServiceBeanAutoProxyCreator counterServiceBeanAutoProxyCreator =
					new ServiceBeanAutoProxyCreator();

				counterServiceBeanAutoProxyCreator.setBeanMatcher(
					new ServiceBeanMatcher(true));
				counterServiceBeanAutoProxyCreator.setMethodInterceptor(
					configurableListableBeanFactory.getBean(
						"counterTransactionAdvice", MethodInterceptor.class));

				counterServiceBeanAutoProxyCreator.setBeanClassLoader(
					_classLoader);

				counterServiceBeanAutoProxyCreator.afterPropertiesSet();

				configurableListableBeanFactory.addBeanPostProcessor(
					counterServiceBeanAutoProxyCreator);
			}

			// Service AOP

			ServiceBeanAutoProxyCreator serviceBeanAutoProxyCreator =
				new ServiceBeanAutoProxyCreator();

			serviceBeanAutoProxyCreator.setBeanMatcher(
				new ServiceBeanMatcher());
			serviceBeanAutoProxyCreator.setMethodInterceptor(
				_createMethodInterceptor(configurableListableBeanFactory));

			serviceBeanAutoProxyCreator.setBeanClassLoader(_classLoader);

			serviceBeanAutoProxyCreator.afterPropertiesSet();

			configurableListableBeanFactory.addBeanPostProcessor(
				serviceBeanAutoProxyCreator);
		}

		private AopBeanFactoryPostProcessor(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		private void _assembleChainableMethodAdvices(
			ListableBeanFactory listableBeanFactory) {

			Map<String, ChainableMethodAdviceInjector>
				chainableMethodAdviceInjectors =
					listableBeanFactory.getBeansOfType(
						ChainableMethodAdviceInjector.class);

			for (ChainableMethodAdviceInjector chainableMethodAdviceInjector :
					chainableMethodAdviceInjectors.values()) {

				chainableMethodAdviceInjector.inject();
			}
		}

		private MethodInterceptor _createMethodInterceptor(
			ConfigurableListableBeanFactory configurableListableBeanFactory) {

			MethodInterceptor methodInterceptor =
				configurableListableBeanFactory.getBean(
					"serviceAdvice", MethodInterceptor.class);

			IndexableAdvice indexableAdvice = new IndexableAdvice();

			indexableAdvice.setNextMethodInterceptor(methodInterceptor);

			BufferedIncrementAdvice bufferedIncrementAdvice =
				new BufferedIncrementAdvice();

			bufferedIncrementAdvice.setNextMethodInterceptor(indexableAdvice);

			ThreadLocalCacheAdvice threadLocalCacheAdvice =
				new ThreadLocalCacheAdvice();

			threadLocalCacheAdvice.setNextMethodInterceptor(
				bufferedIncrementAdvice);

			AsyncAdvice asyncAdvice = new AsyncAdvice();

			asyncAdvice.setDefaultDestinationName("liferay/async_service");
			asyncAdvice.setNextMethodInterceptor(threadLocalCacheAdvice);

			ServiceMonitorAdvice serviceMonitorAdvice =
				new ServiceMonitorAdvice();

			serviceMonitorAdvice.setNextMethodInterceptor(asyncAdvice);

			configurableListableBeanFactory.registerSingleton(
				"serviceMonitoringControl", serviceMonitorAdvice);

			PortalResiliencyAdvice portalResiliencyAdvice =
				new PortalResiliencyAdvice();

			portalResiliencyAdvice.setNextMethodInterceptor(
				serviceMonitorAdvice);

			AccessControlAdvice accessControlAdvice = new AccessControlAdvice();

			accessControlAdvice.setNextMethodInterceptor(
				portalResiliencyAdvice);

			return accessControlAdvice;
		}

		private final ClassLoader _classLoader;

	}

}