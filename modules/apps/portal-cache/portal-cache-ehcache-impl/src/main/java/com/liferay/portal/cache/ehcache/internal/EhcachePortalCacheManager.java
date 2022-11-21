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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.LowLevelCache;
import com.liferay.portal.cache.MVCCPortalCache;
import com.liferay.portal.cache.PortalCacheManagerListenerFactory;
import com.liferay.portal.cache.TransactionalPortalCache;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.internal.configurator.BaseEhcachePortalCacheManagerConfigurator;
import com.liferay.portal.cache.ehcache.internal.event.ConfigurableEhcachePortalCacheListener;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheManagerEventListener;
import com.liferay.portal.cache.ehcache.internal.management.ManagementService;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.URL;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Joseph Shum
 * @author Raymond Augé
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class EhcachePortalCacheManager<K extends Serializable, V>
	extends BasePortalCacheManager<K, V> {

	@Override
	public void clearAll() throws PortalCacheException {
		for (String cacheName : _cacheManager.getCacheNames()) {
			Cache cache = _cacheManager.getCache(cacheName);

			if (cache != null) {
				cache.removeAll();
			}
		}
	}

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc, boolean sharded)
		throws PortalCacheException {

		return portalCaches.compute(
			portalCacheName,
			(key, value) -> {
				if (value != null) {
					_verifyMVCCPortalCache(value, mvcc);
					_verifyShardedPortalCache(value, sharded);

					return value;
				}

				PortalCacheConfiguration portalCacheConfiguration =
					_portalCacheManagerConfiguration.
						getPortalCacheConfiguration(portalCacheName);

				value = createPortalCache(portalCacheConfiguration, sharded);

				initPortalCacheListeners(value, portalCacheConfiguration);

				if (mvcc) {
					value = (PortalCache<K, V>)new MVCCPortalCache<>(
						(LowLevelCache<K, MVCCModel>)value);
				}

				if (_transactionalPortalCacheEnabled &&
					_isTransactionalPortalCache(portalCacheName)) {

					value = new TransactionalPortalCache<>(value, mvcc);
				}

				return value;
			});
	}

	@Override
	public String getPortalCacheManagerName() {
		return portalCacheManagerName;
	}

	@Override
	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader) {

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configurationURL,
						classLoader, _usingDefault);

		reconfigEhcache(configurationObjectValuePair.getKey());

		_reconfigPortalCache(configurationObjectValuePair.getValue());
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		PortalCache<K, V> portalCache = portalCaches.remove(portalCacheName);

		if (portalCache == null) {
			return;
		}

		BaseEhcachePortalCache<K, V> baseEhcachePortalCache =
			EhcacheUnwrapUtil.getWrappedPortalCache(portalCache);

		if (baseEhcachePortalCache != null) {
			baseEhcachePortalCache.dispose();
		}
		else {
			_log.error(
				"Unable to dispose cache with name " +
					portalCache.getPortalCacheName());
		}
	}

	@Override
	public void removePortalCaches(long companyId) {
		Set<PortalCache<K, V>> shardedPortalCaches = new HashSet<>();

		for (PortalCache<K, V> portalCache : portalCaches.values()) {
			if (portalCache.isSharded()) {
				shardedPortalCaches.add(portalCache);
			}
		}

		if (shardedPortalCaches.isEmpty()) {
			return;
		}

		doRemoveShardedPortalCache(companyId, shardedPortalCaches);
	}

	public void setConfigFile(String configFile) {
		_configFile = configFile;
	}

	public void setDefaultConfigFile(String defaultConfigFile) {
		_defaultConfigFile = defaultConfigFile;
	}

	public void setPortalCacheManagerName(String portalCacheManagerName) {
		this.portalCacheManagerName = portalCacheManagerName;
	}

	protected PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration, boolean sharded) {

		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration =
			(EhcachePortalCacheConfiguration)portalCacheConfiguration;

		if (sharded) {
			return new ShardedEhcachePortalCache<>(
				this, ehcachePortalCacheConfiguration);
		}

		return new EhcachePortalCache<>(this, ehcachePortalCacheConfiguration);
	}

	@Override
	protected void doDestroy() {
		_cacheManager.shutdown();

		if (_configuratorSettingsServiceTracker != null) {
			_configuratorSettingsServiceTracker.close();

			_configuratorSettingsServiceTracker = null;
		}

		if (_mBeanServerServiceTracker != null) {
			_mBeanServerServiceTracker.close();
		}
	}

	protected void doRemoveShardedPortalCache(
		long companyId, Set<PortalCache<K, V>> shardedPortalCaches) {

		for (PortalCache<K, V> shardedPortalCache : shardedPortalCaches) {
			ShardedEhcachePortalCache<K, V> shardedEhcachePortalCache =
				(ShardedEhcachePortalCache<K, V>)
					EhcacheUnwrapUtil.getWrappedPortalCache(shardedPortalCache);

			shardedEhcachePortalCache.removeEhcache(companyId);
		}
	}

	protected void initialize() {
		if (_portalCacheManagerConfiguration != null) {
			return;
		}

		if (Validator.isNull(portalCacheManagerName)) {
			throw new IllegalArgumentException(
				"Portal cache manager name is not specified");
		}

		initPortalCacheManager();

		for (Properties properties :
				_portalCacheManagerConfiguration.
					getPortalCacheManagerListenerPropertiesSet()) {

			PortalCacheManagerListener portalCacheManagerListener =
				portalCacheManagerListenerFactory.create(this, properties);

			if (portalCacheManagerListener != null) {
				registerPortalCacheManagerListener(portalCacheManagerListener);
			}
		}
	}

	@Override
	protected void initPortalCacheManager() {
		_transactionalPortalCacheEnabled = GetterUtil.getBoolean(
			props.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED));

		_transactionalPortalCacheNames = GetterUtil.getStringValues(
			props.getArray(PropsKeys.TRANSACTIONAL_CACHE_NAMES));

		if (Validator.isNull(_configFile)) {
			_configFile = _defaultConfigFile;
		}

		ClassLoader classLoader =
			BaseEhcachePortalCacheManagerConfigurator.class.getClassLoader();

		URL configFileURL = classLoader.getResource(_configFile);

		if (configFileURL == null) {
			classLoader = PortalClassLoaderUtil.getClassLoader();

			configFileURL = classLoader.getResource(_configFile);
		}

		_usingDefault = _configFile.equals(_defaultConfigFile);

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configFileURL, classLoader,
						_usingDefault);

		_cacheManager = new CacheManager(configurationObjectValuePair.getKey());

		_portalCacheManagerConfiguration =
			configurationObjectValuePair.getValue();

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(
				aggregatedPortalCacheManagerListener));

		if (!GetterUtil.getBoolean(
				props.get(
					PropsKeys.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED))) {

			return;
		}

		_mBeanServerServiceTracker =
			new ServiceTracker<MBeanServer, ManagementService>(
				bundleContext, MBeanServer.class, null) {

				@Override
				public ManagementService addingService(
					ServiceReference<MBeanServer> serviceReference) {

					MBeanServer mBeanServer = bundleContext.getService(
						serviceReference);

					ManagementService managementService = new ManagementService(
						_cacheManager, mBeanServer);

					managementService.init();

					return managementService;
				}

				@Override
				public void removedService(
					ServiceReference<MBeanServer> serviceReference,
					ManagementService managementService) {

					managementService.dispose();

					bundleContext.ungetService(serviceReference);
				}

			};

		_mBeanServerServiceTracker.open();
	}

	protected void reconfigEhcache(Configuration configuration) {
		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			String portalCacheName = cacheConfiguration.getName();

			synchronized (_cacheManager) {
				if (_cacheManager.cacheExists(portalCacheName)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Overriding existing cache " + portalCacheName);
					}

					PortalCache<K, V> portalCache = fetchPortalCache(
						portalCacheName);

					if (portalCache != null) {
						BaseEhcachePortalCache<K, V> baseEhcachePortalCache =
							EhcacheUnwrapUtil.getWrappedPortalCache(
								portalCache);

						if (baseEhcachePortalCache != null) {
							baseEhcachePortalCache.resetEhcache();
						}
						else {
							_log.error(
								"Unable to reconfigure cache with name " +
									portalCacheName);
						}
					}

					_cacheManager.removeCache(portalCacheName);
				}

				_cacheManager.addCache(new Cache(cacheConfiguration));
			}
		}
	}

	protected void removeConfigurableEhcachePortalCacheListeners(
		PortalCache<K, V> portalCache) {

		BaseEhcachePortalCache<K, V> baseEhcachePortalCache =
			EhcacheUnwrapUtil.getWrappedPortalCache(portalCache);

		Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
			portalCacheListeners =
				baseEhcachePortalCache.getPortalCacheListeners();

		for (PortalCacheListener<K, V> portalCacheListener :
				portalCacheListeners.keySet()) {

			if (portalCacheListener instanceof
					ConfigurableEhcachePortalCacheListener) {

				portalCache.unregisterPortalCacheListener(portalCacheListener);
			}
		}
	}

	protected BaseEhcachePortalCacheManagerConfigurator
		baseEhcachePortalCacheManagerConfigurator;
	protected BundleContext bundleContext;
	protected PortalCacheManagerListenerFactory<PortalCacheManager<K, V>>
		portalCacheManagerListenerFactory;
	protected String portalCacheManagerName;
	protected volatile Props props;

	private boolean _isTransactionalPortalCache(String portalCacheName) {
		for (String namePattern : _transactionalPortalCacheNames) {
			if (StringUtil.wildcardMatches(
					portalCacheName, namePattern, CharPool.QUESTION,
					CharPool.STAR, CharPool.PERCENT, true)) {

				return true;
			}
		}

		return false;
	}

	private void _reconfigPortalCache(
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		for (String portalCacheName :
				portalCacheManagerConfiguration.getPortalCacheNames()) {

			PortalCacheConfiguration portalCacheConfiguration =
				portalCacheManagerConfiguration.getPortalCacheConfiguration(
					portalCacheName);

			_portalCacheManagerConfiguration.putPortalCacheConfiguration(
				portalCacheName, portalCacheConfiguration);

			PortalCache<K, V> portalCache = portalCaches.get(portalCacheName);

			if (portalCache == null) {
				continue;
			}

			removeConfigurableEhcachePortalCacheListeners(portalCache);

			initPortalCacheListeners(portalCache, portalCacheConfiguration);
		}
	}

	private void _verifyMVCCPortalCache(
		PortalCache<K, V> portalCache, boolean mvcc) {

		if (mvcc == portalCache.isMVCC()) {
			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append("Unable to get portal cache ");
		sb.append(portalCache.getPortalCacheName());
		sb.append(" from portal cache manager ");
		sb.append(portalCacheManagerName);
		sb.append(" as a ");

		if (mvcc) {
			sb.append("MVCC ");
		}
		else {
			sb.append("non-MVCC ");
		}

		sb.append("portal cache, cause a ");

		if (portalCache.isMVCC()) {
			sb.append("MVCC ");
		}
		else {
			sb.append("non-MVCC ");
		}

		sb.append("portal cache with same name exists.");

		throw new IllegalStateException(sb.toString());
	}

	private void _verifyShardedPortalCache(
		PortalCache<K, V> portalCache, boolean sharded) {

		if (sharded == portalCache.isSharded()) {
			return;
		}

		StringBundler sb = new StringBundler(9);

		sb.append("Unable to get portal cache ");
		sb.append(portalCache.getPortalCacheName());
		sb.append(" from portal cache manager ");
		sb.append(portalCacheManagerName);
		sb.append(" as a ");

		if (sharded) {
			sb.append("sharded ");
		}
		else {
			sb.append("nonsharded ");
		}

		sb.append("portal cache, cause a ");

		if (portalCache.isSharded()) {
			sb.append("sharded ");
		}
		else {
			sb.append("nonsharded ");
		}

		sb.append("portal cache with same name exists.");

		throw new IllegalStateException(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private String _configFile;
	private ServiceTracker<?, ?> _configuratorSettingsServiceTracker;
	private String _defaultConfigFile;
	private ServiceTracker<MBeanServer, ManagementService>
		_mBeanServerServiceTracker;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private boolean _transactionalPortalCacheEnabled;
	private String[] _transactionalPortalCacheNames = StringPool.EMPTY_ARRAY;
	private boolean _usingDefault;

}