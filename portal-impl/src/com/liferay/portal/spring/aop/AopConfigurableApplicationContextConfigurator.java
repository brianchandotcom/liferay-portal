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
import com.liferay.portal.cache.thread.local.ThreadLocalCacheAdvice;
import com.liferay.portal.dao.jdbc.aop.DynamicDataSourceAdvice;
import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.dao.orm.hibernate.VerifySessionFactoryWrapper;
import com.liferay.portal.increment.BufferedIncrementAdvice;
import com.liferay.portal.internal.cluster.ClusterableAdvice;
import com.liferay.portal.internal.cluster.SPIClusterableAdvice;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.messaging.async.AsyncAdvice;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitorAdvice;
import com.liferay.portal.monitoring.statistics.service.ServiceMonitoringControlImpl;
import com.liferay.portal.resiliency.service.PortalResiliencyAdvice;
import com.liferay.portal.search.IndexableAdvice;
import com.liferay.portal.security.access.control.AccessControlAdvice;
import com.liferay.portal.service.ServiceContextAdvice;
import com.liferay.portal.spring.bean.BeanReferenceAnnotationBeanPostProcessor;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;
import com.liferay.portal.spring.hibernate.PortletTransactionManager;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionExecutorFactory;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.spring.transaction.TransactionInvokerImpl;
import com.liferay.portal.spring.transaction.TransactionManagerFactory;
import com.liferay.portal.systemevent.SystemEventAdvice;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

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

				// Protection for the those theme wars with no Spring XML files

				return;
			}

			configurableListableBeanFactory.addBeanPostProcessor(
				new BeanReferenceAnnotationBeanPostProcessor(
					configurableListableBeanFactory));

			DefaultSingletonBeanRegistry defaultSingletonBeanRegistry =
				(DefaultSingletonBeanRegistry)configurableListableBeanFactory;

			// Counter AOP for portal Spring context only

			ServiceMonitoringControl serviceMonitoringControl = null;

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				ServiceBeanAopCacheManager serviceBeanAopCacheManager =
					ServiceBeanAopCacheManager.create(
						new ChainableMethodAdvice[] {
							configurableListableBeanFactory.getBean(
								"counterTransactionAdvice",
								ChainableMethodAdvice.class)
						});

				defaultSingletonBeanRegistry.registerDisposableBean(
					"counterServiceBeanAopCacheManagerDestroyer",
					() -> ServiceBeanAopCacheManager.destroy(
						serviceBeanAopCacheManager));

				configurableListableBeanFactory.addBeanPostProcessor(
					new ServiceBeanAutoProxyCreator(
						new ServiceBeanMatcher(true), _classLoader,
						serviceBeanAopCacheManager));

				serviceMonitoringControl = new ServiceMonitoringControlImpl();

				configurableListableBeanFactory.registerSingleton(
					"serviceMonitoringControl", serviceMonitoringControl);
			}
			else {
				serviceMonitoringControl =
					(ServiceMonitoringControl)PortalBeanLocatorUtil.locate(
						"serviceMonitoringControl");
			}

			// Service AOP

			ServiceBeanAopCacheManager serviceBeanAopCacheManager =
				ServiceBeanAopCacheManager.create(
					_createChainableMethodAdvices(
						configurableListableBeanFactory,
						serviceMonitoringControl));

			defaultSingletonBeanRegistry.registerDisposableBean(
				"serviceBeanAopCacheManagerDestroyer",
				() -> ServiceBeanAopCacheManager.destroy(
					serviceBeanAopCacheManager));

			configurableListableBeanFactory.addBeanPostProcessor(
				new ServiceBeanAutoProxyCreator(
					new ServiceBeanMatcher(), _classLoader,
					serviceBeanAopCacheManager));
		}

		private AopBeanFactoryPostProcessor(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		private ChainableMethodAdvice[] _createChainableMethodAdvices(
			ConfigurableListableBeanFactory configurableListableBeanFactory,
			ServiceMonitoringControl serviceMonitoringControl) {

			List<ChainableMethodAdvice> chainableMethodAdvices =
				new ArrayList<>(14);

			if (SPIUtil.isSPI()) {
				chainableMethodAdvices.add(new SPIClusterableAdvice());
			}

			if (PropsValues.CLUSTER_LINK_ENABLED) {
				chainableMethodAdvices.add(new ClusterableAdvice());
			}

			chainableMethodAdvices.add(new AccessControlAdvice());

			chainableMethodAdvices.add(new PortalResiliencyAdvice());

			chainableMethodAdvices.add(
				new ServiceMonitorAdvice(serviceMonitoringControl));

			AsyncAdvice asyncAdvice = new AsyncAdvice();

			asyncAdvice.setDefaultDestinationName("liferay/async_service");

			chainableMethodAdvices.add(asyncAdvice);

			chainableMethodAdvices.add(new ThreadLocalCacheAdvice());

			chainableMethodAdvices.add(new BufferedIncrementAdvice());

			chainableMethodAdvices.add(new IndexableAdvice());

			chainableMethodAdvices.add(new SystemEventAdvice());

			chainableMethodAdvices.add(new ServiceContextAdvice());

			chainableMethodAdvices.add(new RetryAdvice());

			PlatformTransactionManager platformTransactionManager =
				_getPlatformTransactionManager(configurableListableBeanFactory);

			TransactionExecutor transactionExecutor =
				TransactionExecutorFactory.createTransactionExecutor(
					platformTransactionManager, false);

			configurableListableBeanFactory.registerSingleton(
				"transactionExecutor", transactionExecutor);

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				TransactionInvokerImpl transactionInvokerImpl =
					new TransactionInvokerImpl();

				transactionInvokerImpl.setTransactionExecutor(
					transactionExecutor);

				TransactionInvokerUtil transactionInvokerUtil =
					new TransactionInvokerUtil();

				transactionInvokerUtil.setTransactionInvoker(
					transactionInvokerImpl);
			}

			TransactionInterceptor transactionInterceptor =
				new TransactionInterceptor();

			transactionInterceptor.setTransactionExecutor(transactionExecutor);

			DynamicDataSourceTargetSource dynamicDataSourceTargetSource =
				InfrastructureUtil.getDynamicDataSourceTargetSource();

			if (dynamicDataSourceTargetSource != null) {
				DynamicDataSourceAdvice dynamicDataSourceAdvice =
					new DynamicDataSourceAdvice();

				dynamicDataSourceAdvice.setDynamicDataSourceTargetSource(
					dynamicDataSourceTargetSource);

				chainableMethodAdvices.add(dynamicDataSourceAdvice);
			}

			chainableMethodAdvices.add(transactionInterceptor);

			return chainableMethodAdvices.toArray(
				new ChainableMethodAdvice[chainableMethodAdvices.size()]);
		}

		private PlatformTransactionManager _getPlatformTransactionManager(
			ConfigurableListableBeanFactory configurableListableBeanFactory) {

			BeanDefinitionRegistry beanDefinitionRegistry =
				(BeanDefinitionRegistry)configurableListableBeanFactory;

			GenericBeanDefinition genericBeanDefinition =
				new GenericBeanDefinition();

			genericBeanDefinition.setAbstract(true);

			beanDefinitionRegistry.registerBeanDefinition(
				"basePersistence", genericBeanDefinition);

			DataSource liferayDataSource =
				configurableListableBeanFactory.getBean(
					"liferayDataSource", DataSource.class);

			SessionFactoryImplementor liferayHibernateSessionFactory = null;

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				liferayHibernateSessionFactory =
					configurableListableBeanFactory.getBean(
						"liferayHibernateSessionFactory",
						SessionFactoryImplementor.class);
			}
			else {
				PortletHibernateConfiguration portletHibernateConfiguration =
					new PortletHibernateConfiguration(
						_classLoader, liferayDataSource);

				try {
					liferayHibernateSessionFactory =
						(SessionFactoryImplementor)
							portletHibernateConfiguration.buildSessionFactory();
				}
				catch (Exception e) {
					return ReflectionUtil.throwException(e);
				}
			}

			SessionFactoryImpl sessionFactoryImpl = new SessionFactoryImpl();

			sessionFactoryImpl.setSessionFactoryClassLoader(_classLoader);
			sessionFactoryImpl.setSessionFactoryImplementor(
				liferayHibernateSessionFactory);

			SessionFactory sessionFactory =
				VerifySessionFactoryWrapper.createVerifySessionFactoryWrapper(
					sessionFactoryImpl);

			configurableListableBeanFactory.addBeanPostProcessor(
				new BasePersistenceInjectionBeanPostProcessor(
					liferayDataSource, sessionFactory));

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				return configurableListableBeanFactory.getBean(
					"liferayTransactionManager",
					PlatformTransactionManager.class);
			}

			if (InfrastructureUtil.getDataSource() == liferayDataSource) {
				return new PortletTransactionManager(
					(HibernateTransactionManager)
						InfrastructureUtil.getTransactionManager(),
					liferayHibernateSessionFactory);
			}

			return TransactionManagerFactory.createTransactionManager(
				liferayDataSource, liferayHibernateSessionFactory);
		}

		private final ClassLoader _classLoader;

	}

	private static class BasePersistenceInjectionBeanPostProcessor
		implements BeanPostProcessor {

		@Override
		public Object postProcessAfterInitialization(
			Object bean, String beanName) {

			return bean;
		}

		@Override
		public Object postProcessBeforeInitialization(
			Object bean, String beanName) {

			if (bean instanceof BasePersistenceImpl) {
				BasePersistenceImpl<?> basePersistenceImpl =
					(BasePersistenceImpl<?>)bean;

				if (basePersistenceImpl.getDataSource() == null) {
					basePersistenceImpl.setDataSource(_dataSource);
				}

				if (basePersistenceImpl.getDialect() == null) {
					basePersistenceImpl.setSessionFactory(_sessionFactory);
				}
			}

			return bean;
		}

		private BasePersistenceInjectionBeanPostProcessor(
			DataSource dataSource, SessionFactory sessionFactory) {

			_dataSource = dataSource;
			_sessionFactory = sessionFactory;
		}

		private final DataSource _dataSource;
		private final SessionFactory _sessionFactory;

	}

}