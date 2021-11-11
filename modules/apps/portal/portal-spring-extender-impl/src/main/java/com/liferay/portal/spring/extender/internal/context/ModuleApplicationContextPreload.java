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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.configurator.ConfigurableApplicationContextConfigurator;
import com.liferay.portal.spring.extender.internal.jdbc.DataSourceUtil;
import com.liferay.portal.spring.extender.internal.loader.ModuleAggregareClassLoader;

import java.beans.Introspector;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(immediate = true, service = ModuleApplicationContextPreload.class)
public class ModuleApplicationContextPreload {

	public void start(Bundle extendeeBundle) throws Exception {
		_countDownLatch.await();

		BundleWiring extendeeBundleWiring = extendeeBundle.adapt(
			BundleWiring.class);

		ClassLoader extendeeClassLoader = extendeeBundleWiring.getClassLoader();

		ClassLoader classLoader = new ModuleAggregareClassLoader(
			extendeeClassLoader, extendeeBundle.getSymbolicName());

		try {
			Dictionary<String, String> headers = extendeeBundle.getHeaders(
				StringPool.BLANK);

			ConfigurableApplicationContext configurableApplicationContext =
				new ModuleApplicationContext(
					extendeeBundle, classLoader,
					StringUtil.split(
						headers.get("Liferay-Spring-Context"),
						CharPool.COMMA)) {

					@Override
					public Resource[] getResources(String locationPattern) {
						Enumeration<URL> enumeration = bundle.findEntries(
							locationPattern, "ext-spring.xml", true);

						List<Resource> resources = new ArrayList<>();

						if (enumeration != null) {
							while (enumeration.hasMoreElements()) {
								resources.add(
									new UrlResource(enumeration.nextElement()));
							}
						}

						return resources.toArray(new Resource[0]);
					}

				};

			configurableApplicationContext.addBeanFactoryPostProcessor(
				beanFactory -> {
					if (!beanFactory.containsBean("liferayDataSource")) {
						beanFactory.registerSingleton(
							"liferayDataSource",
							DataSourceUtil.getProviderDataSource(
								extendeeClassLoader));
					}

					DataSourceUtil.setSpringDataSource(
						extendeeClassLoader,
						beanFactory.getBean(
							"liferayDataSource", DataSource.class));
				});

			_configurableApplicationContextConfigurator.configure(
				configurableApplicationContext);

			configurableApplicationContext.refresh();

			_configurableApplicationContexts.put(
				extendeeBundle, configurableApplicationContext);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to start " + extendeeBundle.getSymbolicName(),
				exception);
		}
		finally {
			CachedIntrospectionResults.clearClassLoader(classLoader);

			CachedIntrospectionResults.clearClassLoader(extendeeClassLoader);

			Bundle bundle = _bundleContext.getBundle();

			BundleWiring extenderBundleWiring = bundle.adapt(
				BundleWiring.class);

			CachedIntrospectionResults.clearClassLoader(
				extenderBundleWiring.getClassLoader());

			Introspector.flushCaches();
		}
	}

	public void stop(Bundle extendeeBundle) {
		try {
			_countDownLatch.await();
		}
		catch (InterruptedException interruptedException) {
		}

		_configurableApplicationContexts.computeIfPresent(
			extendeeBundle,
			(key, configurableApplicationContext) -> {
				configurableApplicationContext.close();

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_configurableApplicationContexts = new ConcurrentHashMap<>();
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		_bundleContext = null;

		_configurableApplicationContexts.forEach((k, v) -> v.stop());

		_configurableApplicationContexts = null;

		_countDownLatch = new CountDownLatch(1);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setConfigurableApplicationContextConfigurator(
		ConfigurableApplicationContextConfigurator
			configurableApplicationContextConfigurator) {

		_configurableApplicationContextConfigurator =
			configurableApplicationContextConfigurator;

		_countDownLatch.countDown();
	}

	protected void unsetConfigurableApplicationContextConfigurator(
		ConfigurableApplicationContextConfigurator
			configurableApplicationContextConfigurator) {

		_configurableApplicationContextConfigurator = null;

		_countDownLatch = new CountDownLatch(1);
	}

	private BundleContext _bundleContext;
	private volatile ConfigurableApplicationContextConfigurator
		_configurableApplicationContextConfigurator;
	private Map<Bundle, ConfigurableApplicationContext>
		_configurableApplicationContexts;
	private CountDownLatch _countDownLatch = new CountDownLatch(1);

}