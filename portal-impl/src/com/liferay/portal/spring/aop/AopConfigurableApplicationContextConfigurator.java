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
import com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl;
import com.liferay.portal.dao.orm.hibernate.VerifySessionFactoryWrapper;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.spring.bean.BeanReferenceAnnotationBeanPostProcessor;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;
import com.liferay.portal.spring.hibernate.PortletTransactionManager;
import com.liferay.portal.spring.transaction.CounterTransactionExecutor;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionHandler;
import com.liferay.portal.spring.transaction.TransactionInvokerImpl;
import com.liferay.portal.spring.transaction.TransactionManagerFactory;

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

	public static class ServiceBeanMatcher implements BeanMatcher {

		@Override
		public boolean match(Class<?> beanClass, String beanName) {
			if (_counterMatcher) {
				return beanName.equals(_COUNTER_SERVICE_BEAN_NAME);
			}

			if (!beanName.equals(_COUNTER_SERVICE_BEAN_NAME) &&
				beanName.endsWith(_SERVICE_SUFFIX)) {

				return true;
			}

			return false;
		}

		private ServiceBeanMatcher(boolean counterMatcher) {
			_counterMatcher = counterMatcher;
		}

		private static final String _COUNTER_SERVICE_BEAN_NAME =
			"com.liferay.counter.kernel.service.CounterLocalService";

		private static final String _SERVICE_SUFFIX = "Service";

		private final boolean _counterMatcher;

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

			DefaultTransactionExecutor defaultTransactionExecutor =
				new DefaultTransactionExecutor(
					_getPlatformTransactionManager(
						configurableListableBeanFactory));

			configurableListableBeanFactory.registerSingleton(
				"transactionExecutor", defaultTransactionExecutor);

			// Portal Spring context only

			if (PortalClassLoaderUtil.isPortalClassLoader(_classLoader)) {
				TransactionInvokerImpl transactionInvokerImpl =
					new TransactionInvokerImpl();

				transactionInvokerImpl.setTransactionExecutor(
					defaultTransactionExecutor);

				TransactionInvokerUtil transactionInvokerUtil =
					new TransactionInvokerUtil();

				transactionInvokerUtil.setTransactionInvoker(
					transactionInvokerImpl);

				CounterServiceBeanAutoProxyCreator
					counterServiceBeanAutoProxyCreator =
						new CounterServiceBeanAutoProxyCreator(
							_classLoader,
							configurableListableBeanFactory.getBean(
								"counterTransactionExecutor",
								CounterTransactionExecutor.class));

				configurableListableBeanFactory.addBeanPostProcessor(
					counterServiceBeanAutoProxyCreator);
			}

			// Service AOP

			ServiceBeanAutoProxyCreator serviceBeanAutoProxyCreator =
				new ServiceBeanAutoProxyCreator(
					_classLoader, defaultTransactionExecutor);

			defaultSingletonBeanRegistry.registerDisposableBean(
				"serviceBeanAutoProxyCreatorDestroyer",
				serviceBeanAutoProxyCreator::destroy);

			configurableListableBeanFactory.addBeanPostProcessor(
				serviceBeanAutoProxyCreator);
		}

		private AopBeanFactoryPostProcessor(ClassLoader classLoader) {
			_classLoader = classLoader;
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

				DefaultSingletonBeanRegistry defaultSingletonBeanRegistry =
					(DefaultSingletonBeanRegistry)
						configurableListableBeanFactory;

				defaultSingletonBeanRegistry.registerDisposableBean(
					"liferayHibernateSessionFactoryDestroyer",
					liferayHibernateSessionFactory::close);
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

	private static class CounterServiceBeanAutoProxyCreator
		extends BaseServiceBeanAutoProxyCreator {

		@Override
		protected AopInvocationHandler createAopInvocationHandler(Object bean) {
			return new AopInvocationHandler(
				bean, _emptyChainableMethodAdvices,
				_counterTransactionExecutor);
		}

		private CounterServiceBeanAutoProxyCreator(
			ClassLoader classLoader,
			CounterTransactionExecutor counterTransactionExecutor) {

			super(new ServiceBeanMatcher(true), classLoader);

			_counterTransactionExecutor = counterTransactionExecutor;
		}

		private static final ChainableMethodAdvice[]
			_emptyChainableMethodAdvices = new ChainableMethodAdvice[0];

		private final CounterTransactionExecutor _counterTransactionExecutor;

	}

	private static class ServiceBeanAutoProxyCreator
		extends BaseServiceBeanAutoProxyCreator {

		public void destroy() {
			for (AopInvocationHandler aopInvocationHandler :
					_aopInvocationHandlers) {

				AopCacheManager.destroy(aopInvocationHandler);
			}
		}

		@Override
		protected AopInvocationHandler createAopInvocationHandler(Object bean) {
			AopInvocationHandler aopInvocationHandler = AopCacheManager.create(
				bean, _transactionHandler);

			_aopInvocationHandlers.add(aopInvocationHandler);

			return aopInvocationHandler;
		}

		private ServiceBeanAutoProxyCreator(
			ClassLoader classLoader, TransactionHandler transactionHandler) {

			super(new ServiceBeanMatcher(false), classLoader);

			_transactionHandler = transactionHandler;
		}

		private final List<AopInvocationHandler> _aopInvocationHandlers =
			new ArrayList<>();
		private final TransactionHandler _transactionHandler;

	}

}