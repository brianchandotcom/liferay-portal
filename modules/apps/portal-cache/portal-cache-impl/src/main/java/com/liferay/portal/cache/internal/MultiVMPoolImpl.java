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

package com.liferay.portal.cache.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.cache.SPIPortalCacheManagerConfigurator;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@Component(immediate = true, service = MultiVMPool.class)
public class MultiVMPoolImpl implements MultiVMPool {

	@Override
	public void clear() {
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = getPortalCacheManager();

		portalCacheManager.clearAll();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getPortalCache(String)}
	 */
	@Deprecated
	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getCache(String portalCacheName) {

		return getPortalCache(portalCacheName);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getPortalCache(String, boolean)}
	 */
	@Deprecated
	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getCache(String portalCacheName, boolean blocking) {

		return getPortalCache(portalCacheName, blocking);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getPortalCacheManager()}
	 */
	@Deprecated
	@Override
	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getCacheManager() {

		return getPortalCacheManager();
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName) {

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = getPortalCacheManager();

		return portalCacheManager.getPortalCache(portalCacheName);
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean blocking) {

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = getPortalCacheManager();

		return portalCacheManager.getPortalCache(portalCacheName, blocking);
	}

	@Override
	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean blocking, boolean mvcc) {

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = getPortalCacheManager();

		return portalCacheManager.getPortalCache(
			portalCacheName, blocking, mvcc);
	}

	@Override
	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getPortalCacheManager() {

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = _spiPortalCacheManager;

		if (portalCacheManager != null) {
			return portalCacheManager;
		}

		return _portalCacheManager;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #removePortalCache(String)}
	 */
	@Deprecated
	@Override
	public void removeCache(String portalCacheName) {
		removePortalCache(portalCacheName);
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = getPortalCacheManager();

		portalCacheManager.removePortalCache(portalCacheName);
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, SPIPortalCacheManagerConfigurator.class,
			new SPIPortalCacheManagerConfiguratorServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM + ")",
		unbind = "-"
	)
	protected void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager) {

		_portalCacheManager = portalCacheManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultiVMPoolImpl.class);

	private BundleContext _bundleContext;
	private PortalCacheManager<? extends Serializable, ? extends Serializable>
		_portalCacheManager;
	private ServiceTracker
		<SPIPortalCacheManagerConfigurator, SPIPortalCacheManagerConfigurator>
			_serviceTracker;
	private volatile
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			_spiPortalCacheManager;

	private class SPIPortalCacheManagerConfiguratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SPIPortalCacheManagerConfigurator,
			 SPIPortalCacheManagerConfigurator> {

		@Override
		public SPIPortalCacheManagerConfigurator addingService(
			ServiceReference<SPIPortalCacheManagerConfigurator>
				serviceReference) {

			SPIPortalCacheManagerConfigurator
				spiPortalCacheManagerConfigurator = _bundleContext.getService(
					serviceReference);

			try {
				PortalCacheManager
					<? extends Serializable, ? extends Serializable>
						portalCacheManager =
							spiPortalCacheManagerConfigurator.
								createSPIPortalCacheManager(
									_portalCacheManager);

				if (portalCacheManager != _portalCacheManager) {
					_spiPortalCacheManager = portalCacheManager;

					_portalCacheManager.clearAll();
				}
			}
			catch (Exception e) {
				_log.error("Unable to create SPI portal cache manager", e);
			}

			return spiPortalCacheManagerConfigurator;
		}

		@Override
		public void modifiedService(
			ServiceReference<SPIPortalCacheManagerConfigurator> reference,
			SPIPortalCacheManagerConfigurator service) {
		}

		@Override
		public void removedService(
			ServiceReference<SPIPortalCacheManagerConfigurator> reference,
			SPIPortalCacheManagerConfigurator service) {

			_spiPortalCacheManager = null;
		}

	}

}