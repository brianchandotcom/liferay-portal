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

package com.liferay.portal.cache.ehcache.multiple.internal.bootstrap;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.cache.PortalCacheBootstrapLoaderFactory;
import com.liferay.portal.cache.ehcache.multiple.configuration.EhcacheMultipleConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cache.ehcache.multiple.configuration.EhcacheMultipleConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = PortalCacheBootstrapLoaderFactory.class
)
public class EhcachePortalCacheBootstrapLoaderFactory
	implements PortalCacheBootstrapLoaderFactory {

	@Override
	public PortalCacheBootstrapLoader create(Properties properties) {
		String factoryClassName =
			_ehcacheMultipleConfiguration.bootstrapCacheLoaderFactoryClass();

		try {
			BootstrapCacheLoaderFactory<?> bootstrapCacheLoaderFactory =
				(BootstrapCacheLoaderFactory<?>)InstanceFactory.newInstance(
					getClassLoader(), factoryClassName);

			boolean bootstrapAsynchronously = GetterUtil.getBoolean(
				properties.getProperty(
					PortalCacheBootstrapLoader.BOOTSTRAP_ASYNCHRONOUSLY),
				PortalCacheBootstrapLoader.DEFAULT_BOOTSTRAP_ASYNCHRONOUSLY);

			Properties newProperties = (Properties)properties.clone();

			newProperties.put(
				PortalCacheBootstrapLoader.BOOTSTRAP_ASYNCHRONOUSLY, "false");

			return new EhcachePortalCacheBootstrapLoaderAdapter(
				bootstrapCacheLoaderFactory.createBootstrapCacheLoader(
					newProperties),
				bootstrapAsynchronously, _executorService, _clusterExecutor);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to instantiate bootstrap cache loader factory " +
					factoryClassName,
				e);
		}
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		_ehcacheMultipleConfiguration = ConfigurableUtil.createConfigurable(
			EhcacheMultipleConfiguration.class,
			componentContext.getProperties());

		_executorService = _portalExecutorManager.getPortalExecutor(
			EhcachePortalCacheBootstrapLoaderFactory.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		if (_executorService != null) {
			_executorService.shutdown();
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	@Reference
	private ClusterExecutor _clusterExecutor;

	private volatile EhcacheMultipleConfiguration _ehcacheMultipleConfiguration;
	private ExecutorService _executorService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}