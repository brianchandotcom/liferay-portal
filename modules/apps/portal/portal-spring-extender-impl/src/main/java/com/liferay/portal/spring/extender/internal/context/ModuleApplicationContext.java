/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.spring.extender.internal.jdbc.DataSourceUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContext extends ClassPathXmlApplicationContext {

	public static void registerDataSourceBean(
		ConfigurableListableBeanFactory configurableListableBeanFactory,
		ClassLoader extendeeClassLoader) {

		if (configurableListableBeanFactory.containsBean("liferayDataSource")) {
			return;
		}

		DataSource dataSource = DataSourceUtil.getDataSource(
			extendeeClassLoader);

		configurableListableBeanFactory.registerSingleton(
			"liferayDataSource", dataSource);

		if (InfrastructureUtil.getDataSource() != dataSource) {
			DefaultSingletonBeanRegistry defaultSingletonBeanRegistry =
				(DefaultSingletonBeanRegistry)configurableListableBeanFactory;

			defaultSingletonBeanRegistry.registerDisposableBean(
				"dataSourceDestroyer",
				() -> DataSourceFactoryUtil.destroyDataSource(dataSource));
		}
	}

	public ModuleApplicationContext(
		Bundle bundle, ClassLoader extendeeClassLoader,
		ClassLoader resourceLoaderClassLoader, String[] configLocations) {

		super(configLocations, false, null);

		this.bundle = bundle;

		setClassLoader(resourceLoaderClassLoader);

		super.refreshBeanFactory();

		ConfigurableListableBeanFactory configurableListableBeanFactory =
			getBeanFactory();

		registerDataSourceBean(
			configurableListableBeanFactory, extendeeClassLoader);

		_dataSource = configurableListableBeanFactory.getBean(
			"liferayDataSource", DataSource.class);
	}

	public BundleContext getBundleContext() {
		return bundle.getBundleContext();
	}

	public DataSource getDataSource() {
		return _dataSource;
	}

	@Override
	public Resource[] getResources(String locationPattern) {
		Enumeration<URL> enumeration = bundle.findEntries(
			locationPattern, "*.xml", true);

		List<Resource> resources = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			resources.add(new UrlResource(enumeration.nextElement()));
		}

		return resources.toArray(new Resource[0]);
	}

	@Override
	public void refresh() throws BeansException, IllegalStateException {
		super.refresh();

		_dataSource = getBean("liferayDataSource", DataSource.class);
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory()) {

			@Override
			protected Object getEarlyBeanReference(
				String beanName, RootBeanDefinition rootBeanDefinition,
				Object bean) {

				if ((bean == null) || rootBeanDefinition.isSynthetic()) {
					return bean;
				}

				Object exposedObject = bean;

				for (BeanPostProcessor beanPostProcessor :
						getBeanPostProcessors()) {

					if (!(beanPostProcessor instanceof
							SmartInstantiationAwareBeanPostProcessor)) {

						continue;
					}

					SmartInstantiationAwareBeanPostProcessor
						smartInstantiationAwareBeanPostProcessor =
							(SmartInstantiationAwareBeanPostProcessor)
								beanPostProcessor;

					exposedObject =
						smartInstantiationAwareBeanPostProcessor.
							getEarlyBeanReference(exposedObject, beanName);

					if (exposedObject == null) {
						return exposedObject;
					}
				}

				return exposedObject;
			}

			@Override
			protected boolean hasInstantiationAwareBeanPostProcessors() {
				return false;
			}

		};
	}

	@Override
	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		if (_freshBeanFactory.compareAndSet(true, false)) {
			return getBeanFactory();
		}

		return super.obtainFreshBeanFactory();
	}

	protected final Bundle bundle;

	private volatile DataSource _dataSource;
	private final AtomicBoolean _freshBeanFactory = new AtomicBoolean(true);

}