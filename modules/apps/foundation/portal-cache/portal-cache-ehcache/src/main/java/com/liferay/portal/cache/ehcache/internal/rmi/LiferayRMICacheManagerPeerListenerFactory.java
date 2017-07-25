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

package com.liferay.portal.cache.ehcache.internal.rmi;

import java.util.List;
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerListener;
import net.sf.ehcache.distribution.CacheManagerPeerListenerFactory;
import net.sf.ehcache.distribution.CacheReplicator;
import net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Tina Tian
 */
public class LiferayRMICacheManagerPeerListenerFactory
	extends CacheManagerPeerListenerFactory {

	@Override
	public CacheManagerPeerListener createCachePeerListener(
		CacheManager cacheManager, Properties properties) {

		CacheManagerPeerListener cacheManagerPeerListener =
			_cacheManagerPeerListenerFactory.createCachePeerListener(
				cacheManager, properties);

		return new LiferayCacheManagerPeerListener(
			cacheManager, cacheManagerPeerListener);
	}

	private static final CacheReplicator _cacheReplicator =
		new CacheReplicator() {

			@Override
			public boolean alive() {
				return true;
			}

			@Override
			public Object clone() {
				return this;
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean isReplicateUpdatesViaCopy() {
				return false;
			}

			@Override
			public boolean notAlive() {
				return false;
			}

			@Override
			public void notifyElementEvicted(Ehcache ehcache, Element element) {
			}

			@Override
			public void notifyElementExpired(Ehcache ehcache, Element element) {
			}

			@Override
			public void notifyElementPut(Ehcache ehcache, Element element) {
			}

			@Override
			public void notifyElementRemoved(Ehcache ehcache, Element element) {
			}

			@Override
			public void notifyElementUpdated(Ehcache ehcache, Element element) {
			}

			@Override
			public void notifyRemoveAll(Ehcache ehch) {
			}

		};

	private final CacheManagerPeerListenerFactory
		_cacheManagerPeerListenerFactory =
			new RMICacheManagerPeerListenerFactory();

	private static class LiferayCacheManagerPeerListener
		implements CacheManagerPeerListener {

		@Override
		public void attemptResolutionOfUniqueResourceConflict() {
			_cacheManagerPeerListener.
				attemptResolutionOfUniqueResourceConflict();
		}

		@Override
		public void dispose() {
			_cacheManagerPeerListener.dispose();
		}

		@Override
		@SuppressWarnings("rawtypes")
		public List getBoundCachePeers() {
			return _cacheManagerPeerListener.getBoundCachePeers();
		}

		@Override
		public String getScheme() {
			return _cacheManagerPeerListener.getScheme();
		}

		@Override
		public Status getStatus() {
			return _cacheManagerPeerListener.getStatus();
		}

		@Override
		public String getUniqueResourceIdentifier() {
			return _cacheManagerPeerListener.getUniqueResourceIdentifier();
		}

		@Override
		public void init() {
			for (String cacheName : _cacheManager.getCacheNames()) {
				_wrapEhcache(cacheName);
			}

			try {
				_cacheManagerPeerListener.init();
			}
			finally {
				for (String cacheName : _cacheManager.getCacheNames()) {
					_unwrapEhcache(cacheName);
				}
			}
		}

		@Override
		public void notifyCacheAdded(String cacheName) {
			_wrapEhcache(cacheName);

			try {
				_cacheManagerPeerListener.notifyCacheAdded(cacheName);
			}
			finally {
				_unwrapEhcache(cacheName);
			}
		}

		@Override
		public void notifyCacheRemoved(String cacheName) {
			_cacheManagerPeerListener.notifyCacheRemoved(cacheName);
		}

		private LiferayCacheManagerPeerListener(
			CacheManager cacheManager,
			CacheManagerPeerListener cacheManagerPeerListener) {

			_cacheManager = cacheManager;
			_cacheManagerPeerListener = cacheManagerPeerListener;
		}

		private void _unwrapEhcache(String cacheName) {
			Ehcache ehcache = _cacheManager.getEhcache(cacheName);

			if (!(ehcache instanceof LiferayCacheDecorator)) {
				return;
			}

			RegisteredEventListeners registeredEventListeners =
				ehcache.getCacheEventNotificationService();

			LiferayCacheDecorator liferayCacheDecorator =
				(LiferayCacheDecorator)ehcache;

			_cacheManager.replaceCacheWithDecoratedCache(
				liferayCacheDecorator,
				liferayCacheDecorator.getUnderlyingCache());

			registeredEventListeners.unregisterListener(_cacheReplicator);
		}

		private void _wrapEhcache(String cacheName) {
			Ehcache ehcache = _cacheManager.getEhcache(cacheName);

			if (!(ehcache instanceof LiferayCacheDecorator)) {
				_cacheManager.replaceCacheWithDecoratedCache(
					ehcache, new LiferayCacheDecorator(ehcache));
			}

			RegisteredEventListeners registeredEventListeners =
				ehcache.getCacheEventNotificationService();

			registeredEventListeners.registerListener(_cacheReplicator);
		}

		private final CacheManager _cacheManager;
		private final CacheManagerPeerListener _cacheManagerPeerListener;

	}

}