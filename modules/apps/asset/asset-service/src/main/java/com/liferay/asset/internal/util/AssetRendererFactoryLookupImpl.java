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

package com.liferay.asset.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.time.Duration;
import java.time.Instant;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cristina Rodríguez Yrezábal
 * @author Mariano Álvaro Sáiz
 */
@Component(immediate = true, service = AssetRendererFactoryLookup.class)
public class AssetRendererFactoryLookupImpl
	implements AssetRendererFactoryLookup {

	@Override
	public AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory != null) ||
			!_isIndexOnStartupWithDelayEnabled() ||
			_isAssetRendererFactoryInitialized(className)) {

			return assetRendererFactory;
		}

		_waitAssetRendererFactoryLoaded(className);

		_initializedAssetRendererFactories.add(className);

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(className);
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactoryByType(String type) {
		return AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
			type);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			(Class<AssetRendererFactory<?>>)
				(Class<?>)AssetRendererFactory.class,
			new AssetRendererFactoryServiceTrackerCustomizer());

		_activated = Instant.now();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	private static boolean _isIndexOnStartupWithDelayEnabled() {
		if (_INDEX_ON_STARTUP && (_INDEX_ON_STARTUP_DELAY > 0)) {
			return true;
		}

		return false;
	}

	private boolean _isAssetRendererFactoryInitialized(String className) {
		return _initializedAssetRendererFactories.contains(className);
	}

	private long _secondsElapsedSinceActivated() {
		Instant now = Instant.now();

		Duration elapsedDuration = Duration.between(_activated, now);

		return elapsedDuration.getSeconds();
	}

	private void _waitAssetRendererFactoryLoaded(String className) {
		CountDownLatch countDownLatch =
			_assetRenderFactoriesCountDownLatchMap.computeIfAbsent(
				className, key -> new CountDownLatch(1));

		long secondsToWait = Math.max(
			0, _INDEX_ON_STARTUP_DELAY - _secondsElapsedSinceActivated());

		try {
			countDownLatch.await(secondsToWait, TimeUnit.SECONDS);
		}
		catch (InterruptedException interruptedException) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Interrupted while waiting to load asset renderer factory",
					interruptedException);
			}
		}
	}

	private static final boolean _INDEX_ON_STARTUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_STARTUP));

	private static final long _INDEX_ON_STARTUP_DELAY = GetterUtil.getLong(
		PropsUtil.get(PropsKeys.INDEX_ON_STARTUP_DELAY));

	private static final Log _log = LogFactoryUtil.getLog(
		AssetRendererFactoryLookupImpl.class);

	private Instant _activated;
	private final Map<String, CountDownLatch>
		_assetRenderFactoriesCountDownLatchMap = new ConcurrentHashMap<>();
	private BundleContext _bundleContext;
	private final Set<String> _initializedAssetRendererFactories =
		ConcurrentHashMap.newKeySet();
	private ServiceTracker<AssetRendererFactory<?>, AssetRendererFactory<?>>
		_serviceTracker;

	private class AssetRendererFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AssetRendererFactory<?>, AssetRendererFactory<?>> {

		@Override
		public AssetRendererFactory<?> addingService(
			ServiceReference<AssetRendererFactory<?>> serviceReference) {

			AssetRendererFactory<?> assetRendererFactory =
				_bundleContext.getService(serviceReference);

			_assetRenderFactoriesCountDownLatchMap.computeIfPresent(
				assetRendererFactory.getClassName(),
				(key, countDownLatch) -> {
					countDownLatch.countDown();

					return countDownLatch;
				});

			return assetRendererFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> service) {
		}

		@Override
		public void removedService(
			ServiceReference<AssetRendererFactory<?>> serviceReference,
			AssetRendererFactory<?> service) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}