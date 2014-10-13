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

package com.liferay.portal.resiliency.spi.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.nio.intraband.cache.BaseIntrabandPortalCacheManager;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyInstallationUtil;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil;
import com.liferay.portal.nio.intraband.proxy.WarnLogExceptionHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class SPIPortalCacheManagerConfigurator {

	public static <K extends Serializable, V extends Serializable>
		PortalCacheManager<K, V> createSPIPortalCacheManager(
			PortalCacheManager<K, V> portalCacheManager)
		throws Exception {

		if (!SPIUtil.isSPI()) {
			return portalCacheManager;
		}

		SPI spi = SPIUtil.getSPI();

		RegistrationReference registrationReference =
			spi.getRegistrationReference();

		Future<String[]> future =
			IntrabandProxyInstallationUtil.installSkeleton(
				registrationReference, PortalCache.class,
				new IntrabandPortalCacheTargetLocator(
					portalCacheManager.getName(), false));

		String[] skeletonProxyMethodSignatures = future.get();

		String[] stubProxyMethodSignatures =
			IntrabandProxyUtil.getProxyMethodSignatures(
				BaseIntrabandPortalCacheManager.getPortalCacheStubClass());

		IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
			skeletonProxyMethodSignatures, stubProxyMethodSignatures);

		future = IntrabandProxyInstallationUtil.installSkeleton(
			registrationReference, PortalCacheManager.class,
			new IntrabandPortalCacheTargetLocator(
				portalCacheManager.getName(), true));

		skeletonProxyMethodSignatures = future.get();

		Class<? extends PortalCacheManager<K, V>> stubClass =
			(Class<? extends PortalCacheManager<K, V>>)
				IntrabandProxyUtil.getStubClass(
					BaseIntrabandPortalCacheManager.class,
					PortalCacheManager.class.getName());

		stubProxyMethodSignatures = IntrabandProxyUtil.getProxyMethodSignatures(
			stubClass);

		IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
			skeletonProxyMethodSignatures, stubProxyMethodSignatures);

		portalCacheManager = IntrabandProxyUtil.newStubInstance(
			stubClass, StringPool.BLANK, registrationReference,
			WarnLogExceptionHandler.INSTANCE);

		return portalCacheManager;
	}

	private static class IntrabandPortalCacheTargetLocator
		implements TargetLocator {

		public IntrabandPortalCacheTargetLocator(
			String portalCacheManagerName, boolean manager) {

			_portalCacheManagerName = portalCacheManagerName;
			_manager = manager;
		}

		@Override
		public Object getTarget(String id) {
			if (_manager) {
				return _portalCacheManager;
			}

			return _portalCacheManager.getCache(id);
		}

		private void readObject(ObjectInputStream objectInputStream)
			throws ClassNotFoundException, IOException {

			objectInputStream.defaultReadObject();

			_portalCacheManager = PortalCacheProvider.getPortalCacheManager(
				_portalCacheManagerName);
		}

		private final boolean _manager;
		private transient PortalCacheManager<?, ?> _portalCacheManager;
		private final String _portalCacheManagerName;

	}

}