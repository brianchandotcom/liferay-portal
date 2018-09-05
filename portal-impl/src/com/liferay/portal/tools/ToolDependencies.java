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

package com.liferay.portal.tools;

import com.liferay.portal.cache.key.SimpleCacheKeyGenerator;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.security.auth.DefaultFullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.model.DefaultModelHintsImpl;
import com.liferay.portal.security.permission.ResourceActionsImpl;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.service.permission.PortletPermissionImpl;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.FriendlyURLNormalizerImpl;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Raymond Augé
 */
public class ToolDependencies {

	public static void wireBasic() {
		InitUtil.init();

		wireCaches();

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			FullNameGenerator.class, new DefaultFullNameGenerator());

		CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
			new CacheKeyGeneratorUtil();

		cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(
			new SimpleCacheKeyGenerator());

		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		FriendlyURLNormalizerUtil friendlyURLNormalizerUtil =
			new FriendlyURLNormalizerUtil();

		friendlyURLNormalizerUtil.setFriendlyURLNormalizer(
			new FriendlyURLNormalizerImpl());

		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		PortletPermissionUtil portletPermissionUtil =
			new PortletPermissionUtil();

		portletPermissionUtil.setPortletPermission(new PortletPermissionImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReader = new SAXReaderImpl();

		secureSAXReader.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReader);

		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReader = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReader);

		// DefaultModelHintsImpl requires SecureXMLFactoryProviderUtil

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		DefaultModelHintsImpl defaultModelHintsImpl =
			new DefaultModelHintsImpl();

		defaultModelHintsImpl.afterPropertiesSet();

		modelHintsUtil.setModelHints(defaultModelHintsImpl);
	}

	public static void wireCaches() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(MultiVMPool.class, new TestMultiVMPool());
		registry.registerService(SingleVMPool.class, new TestSingleVMPool());
	}

	public static void wireDeployers() {
		wireBasic();

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	public static void wireServiceBuilder() {
		wireDeployers();

		ResourceActionsUtil resourceActionsUtil = new ResourceActionsUtil();

		ResourceActionsImpl resourceActionsImpl = new ResourceActionsImpl();

		resourceActionsImpl.afterPropertiesSet();

		resourceActionsUtil.setResourceActions(resourceActionsImpl);
	}

	private static class TestMultiVMPool implements MultiVMPool {

		@Override
		public void clear() {
			_portalCaches.clear();
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCache(String)}
		 */
		@Deprecated
		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getCache(String portalCacheName) {

			return getPortalCache(portalCacheName);
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCache(String, boolean)}
		 */
		@Deprecated
		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getCache(String portalCacheName, boolean blocking) {

			return getPortalCache(portalCacheName, blocking);
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCacheManager()}
		 */
		@Deprecated
		@Override
		public PortalCacheManager
			<? extends Serializable, ? extends Serializable>
				getCacheManager() {

			return getPortalCacheManager();
		}

		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getPortalCache(String portalCacheName) {

			PortalCache<? extends Serializable, ? extends Serializable>
				portalCache = _portalCaches.get(portalCacheName);

			if (portalCache != null) {
				return portalCache;
			}

			portalCache = new TestPortalCache<>(portalCacheName);

			_portalCaches.putIfAbsent(portalCacheName, portalCache);

			return _portalCaches.get(portalCacheName);
		}

		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getPortalCache(String portalCacheName, boolean blocking) {

			return getPortalCache(portalCacheName);
		}

		@Override
		public PortalCache<? extends Serializable, ? extends Serializable>
			getPortalCache(
				String portalCacheName, boolean blocking, boolean mvcc) {

			return getPortalCache(portalCacheName);
		}

		@Override
		public PortalCacheManager
			<? extends Serializable, ? extends Serializable>
				getPortalCacheManager() {

			return null;
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #removePortalCache(String)}
		 */
		@Deprecated
		@Override
		public void removeCache(String portalCacheName) {
			removePortalCache(portalCacheName);
		}

		@Override
		public void removePortalCache(String portalCacheName) {
			_portalCaches.remove(portalCacheName);
		}

		private final ConcurrentMap
			<String,
			 PortalCache<? extends Serializable, ? extends Serializable>>
				_portalCaches = new ConcurrentHashMap<>();

	}

	private static class TestPortalCache<K extends Serializable, V>
		implements PortalCache<K, V> {

		public TestPortalCache(String portalCacheName) {
			_portalCacheName = portalCacheName;
		}

		@Override
		public V get(K key) {
			return _map.get(key);
		}

		@Override
		public List<K> getKeys() {
			return new ArrayList<>(_map.keySet());
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCacheName()}
		 */
		@Deprecated
		@Override
		public String getName() {
			return getPortalCacheName();
		}

		@Override
		public PortalCacheManager<K, V> getPortalCacheManager() {
			return null;
		}

		@Override
		public String getPortalCacheName() {
			return _portalCacheName;
		}

		@Override
		public void put(K key, V value) {
			put(key, value, DEFAULT_TIME_TO_LIVE);
		}

		@Override
		public void put(K key, V value, int timeToLive) {
			V oldValue = _map.put(key, value);

			for (PortalCacheListener<K, V> portalCacheListener :
					_portalCacheListeners) {

				if (oldValue != null) {
					portalCacheListener.notifyEntryUpdated(
						this, key, value, timeToLive);
				}
				else {
					portalCacheListener.notifyEntryPut(
						this, key, value, timeToLive);
				}
			}
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener) {

			_portalCacheListeners.add(portalCacheListener);
		}

		@Override
		public void registerPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener,
			PortalCacheListenerScope portalCacheListenerScope) {

			_portalCacheListeners.add(portalCacheListener);
		}

		@Override
		public void remove(K key) {
			_map.remove(key);

			for (PortalCacheListener<K, V> portalCacheListener :
					_portalCacheListeners) {

				portalCacheListener.notifyEntryRemoved(
					this, key, null, DEFAULT_TIME_TO_LIVE);
			}
		}

		@Override
		public void removeAll() {
			_map.clear();

			for (PortalCacheListener<K, V> portalCacheListener :
					_portalCacheListeners) {

				portalCacheListener.notifyRemoveAll(this);
			}
		}

		@Override
		public void unregisterPortalCacheListener(
			PortalCacheListener<K, V> portalCacheListener) {

			portalCacheListener.dispose();

			_portalCacheListeners.remove(portalCacheListener);
		}

		@Override
		public void unregisterPortalCacheListeners() {
			for (PortalCacheListener<K, V> portalCacheListener :
					_portalCacheListeners) {

				portalCacheListener.dispose();
			}

			_portalCacheListeners.clear();
		}

		private final Map<K, V> _map = new ConcurrentHashMap<>();
		private final List<PortalCacheListener<K, V>> _portalCacheListeners =
			new ArrayList<>();
		private final String _portalCacheName;

	}

	private static class TestSingleVMPool implements SingleVMPool {

		public TestSingleVMPool() {
		}

		@Override
		public void clear() {
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCache(String)}
		 */
		@Deprecated
		@Override
		public PortalCache<? extends Serializable, ?> getCache(
			String portalCacheName) {

			return getPortalCache(portalCacheName);
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCache(String, boolean)}
		 */
		@Deprecated
		@Override
		public PortalCache<? extends Serializable, ?> getCache(
			String portalCacheName, boolean blocking) {

			return getPortalCache(portalCacheName, blocking);
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #getPortalCacheManager()}
		 */
		@Deprecated
		@Override
		public PortalCacheManager<? extends Serializable, ?> getCacheManager() {
			return getPortalCacheManager();
		}

		@Override
		public PortalCache<? extends Serializable, ?> getPortalCache(
			String portalCacheName) {

			PortalCache<? extends Serializable, ?> portalCache =
				_portalCaches.get(portalCacheName);

			if (portalCache != null) {
				return portalCache;
			}

			portalCache = new TestPortalCache<>(portalCacheName);

			_portalCaches.putIfAbsent(portalCacheName, portalCache);

			return _portalCaches.get(portalCacheName);
		}

		@Override
		public PortalCache<? extends Serializable, ?> getPortalCache(
			String portalCacheName, boolean blocking) {

			return getPortalCache(portalCacheName);
		}

		@Override
		public PortalCacheManager<? extends Serializable, ?>
			getPortalCacheManager() {

			return null;
		}

		/**
		 * @deprecated As of Judson (7.1.x), replaced by {@link
		 *             #removePortalCache(String)}
		 */
		@Deprecated
		@Override
		public void removeCache(String portalCacheName) {
			removePortalCache(portalCacheName);
		}

		@Override
		public void removePortalCache(String portalCacheName) {
			_portalCaches.remove(portalCacheName);
		}

		private final
			ConcurrentMap<String, PortalCache<? extends Serializable, ?>>
				_portalCaches = new ConcurrentHashMap<>();

	}

}