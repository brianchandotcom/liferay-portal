/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.AggregatedPortalCacheManagerListener;
import com.liferay.portal.cache.LowLevelCache;
import com.liferay.portal.cache.MVCCPortalCache;
import com.liferay.portal.cache.PortalCacheListenerFactory;
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

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Joseph Shum
 * @author Raymond Augé
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Edward Han
 */
public abstract class BaseEhcachePortalCacheManager<K extends Serializable, V>
	implements PortalCacheManager<K, V> {

	@Override
	public void clearAll() throws PortalCacheException {
		for (String cacheName : _cacheManager.getCacheNames()) {
			Cache cache = _cacheManager.getCache(cacheName);

			if (cache != null) {
				cache.removeAll();
			}
		}
	}

	@Override
	public void destroy() {
		_portalCaches.clear();

		_cacheManager.shutdown();

		if (_configuratorSettingsServiceTracker != null) {
			_configuratorSettingsServiceTracker.close();

			_configuratorSettingsServiceTracker = null;
		}

		if (_mBeanServerServiceTracker != null) {
			_mBeanServerServiceTracker.close();
		}
	}

	@Override
	public PortalCache<K, V> fetchPortalCache(String portalCacheName) {
		return _portalCaches.get(portalCacheName);
	}

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public PortalCache<K, V> getPortalCache(String portalCacheName)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc)
		throws PortalCacheException {

		return getPortalCache(portalCacheName, mvcc, false);
	}

	@Override
	public PortalCache<K, V> getPortalCache(
			String portalCacheName, boolean mvcc, boolean sharded)
		throws PortalCacheException {

		return _portalCaches.compute(
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

				EhcachePortalCacheConfiguration
					ehcachePortalCacheConfiguration =
						(EhcachePortalCacheConfiguration)
							portalCacheConfiguration;

				if (sharded) {
					value = new ShardedEhcachePortalCache<>(
						this, ehcachePortalCacheConfiguration);
				}
				else {
					value = new EhcachePortalCache<>(
						this, ehcachePortalCacheConfiguration);
				}

				_initPortalCacheListeners(value, portalCacheConfiguration);

				if (mvcc) {
					value = (PortalCache<K, V>)new MVCCPortalCache<>(
						(LowLevelCache<K, MVCCModel>)value);
				}

				if (_transactionalPortalCacheEnabled) {
					for (String namePattern : _transactionalPortalCacheNames) {
						if (StringUtil.wildcardMatches(
								portalCacheName, namePattern, CharPool.QUESTION,
								CharPool.STAR, CharPool.PERCENT, true)) {

							value = new TransactionalPortalCache<>(value, mvcc);
						}
					}
				}

				return value;
			});
	}

	@Override
	public Set<PortalCacheManagerListener> getPortalCacheManagerListeners() {
		return _aggregatedPortalCacheManagerListener.
			getPortalCacheManagerListeners();
	}

	@Override
	public String getPortalCacheManagerName() {
		return _portalCacheManagerName;
	}

	@Override
	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader) {

		BaseEhcachePortalCacheManagerConfigurator
			baseEhcachePortalCacheManagerConfigurator =
				getBaseEhcachePortalCacheManagerConfigurator();

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						_portalCacheManagerName, configurationURL, classLoader,
						_usingDefault);

		_reconfigEhcache(configurationObjectValuePair.getKey());

		_reconfigPortalCache(configurationObjectValuePair.getValue());
	}

	@Override
	public boolean registerPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		return _aggregatedPortalCacheManagerListener.addPortalCacheListener(
			portalCacheManagerListener);
	}

	@Override
	public void removePortalCache(String portalCacheName) {
		PortalCache<K, V> portalCache = _portalCaches.remove(portalCacheName);

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
		for (PortalCache<K, V> portalCache : _portalCaches.values()) {
			if (portalCache.isSharded()) {
				ShardedEhcachePortalCache<K, V> shardedEhcachePortalCache =
					(ShardedEhcachePortalCache<K, V>)
						EhcacheUnwrapUtil.getWrappedPortalCache(portalCache);

				shardedEhcachePortalCache.removeEhcache(companyId);
			}
		}
	}

	public void setConfigFile(String configFile) {
		_configFile = configFile;
	}

	public void setDefaultConfigFile(String defaultConfigFile) {
		_defaultConfigFile = defaultConfigFile;
	}

	public void setPortalCacheManagerName(String portalCacheManagerName) {
		_portalCacheManagerName = portalCacheManagerName;
	}

	@Override
	public boolean unregisterPortalCacheManagerListener(
		PortalCacheManagerListener portalCacheManagerListener) {

		return _aggregatedPortalCacheManagerListener.removePortalCacheListener(
			portalCacheManagerListener);
	}

	@Override
	public void unregisterPortalCacheManagerListeners() {
		_aggregatedPortalCacheManagerListener.clearAll();
	}

	protected abstract BaseEhcachePortalCacheManagerConfigurator
		getBaseEhcachePortalCacheManagerConfigurator();

	protected void initialize() {
		if (_portalCacheManagerConfiguration != null) {
			return;
		}

		if (Validator.isNull(_portalCacheManagerName)) {
			throw new IllegalArgumentException(
				"Portal cache manager name is not specified");
		}

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

		BaseEhcachePortalCacheManagerConfigurator
			baseEhcachePortalCacheManagerConfigurator =
				getBaseEhcachePortalCacheManagerConfigurator();

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						_portalCacheManagerName, configFileURL, classLoader,
						_usingDefault);

		_cacheManager = new CacheManager(configurationObjectValuePair.getKey());

		_portalCacheManagerConfiguration =
			configurationObjectValuePair.getValue();

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(
				_aggregatedPortalCacheManagerListener,
				_portalCacheManagerName));

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

	protected BundleContext bundleContext;

	@Reference
	protected PortalCacheListenerFactory portalCacheListenerFactory;

	@Reference
	protected PortalCacheManagerListenerFactory<PortalCacheManager<K, V>>
		portalCacheManagerListenerFactory;

	@Reference
	protected Props props;

	private void _initPortalCacheListeners(
		PortalCache<K, V> portalCache,
		PortalCacheConfiguration portalCacheConfiguration) {

		if (portalCacheConfiguration == null) {
			return;
		}

		for (Properties properties :
				portalCacheConfiguration.
					getPortalCacheListenerPropertiesSet()) {

			PortalCacheListener<K, V> portalCacheListener =
				portalCacheListenerFactory.create(properties);

			if (portalCacheListener == null) {
				continue;
			}

			PortalCacheListenerScope portalCacheListenerScope =
				(PortalCacheListenerScope)properties.remove(
					PortalCacheConfiguration.
						PORTAL_CACHE_LISTENER_PROPERTIES_KEY_SCOPE);

			if (portalCacheListenerScope == null) {
				portalCacheListenerScope = PortalCacheListenerScope.ALL;
			}

			portalCache.registerPortalCacheListener(
				portalCacheListener, portalCacheListenerScope);
		}
	}

	private void _reconfigEhcache(Configuration configuration) {
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

	private void _reconfigPortalCache(
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		for (String portalCacheName :
				portalCacheManagerConfiguration.getPortalCacheNames()) {

			PortalCacheConfiguration portalCacheConfiguration =
				portalCacheManagerConfiguration.getPortalCacheConfiguration(
					portalCacheName);

			_portalCacheManagerConfiguration.putPortalCacheConfiguration(
				portalCacheName, portalCacheConfiguration);

			PortalCache<K, V> portalCache = _portalCaches.get(portalCacheName);

			if (portalCache == null) {
				continue;
			}

			BaseEhcachePortalCache<K, V> baseEhcachePortalCache =
				EhcacheUnwrapUtil.getWrappedPortalCache(portalCache);

			Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
				portalCacheListeners =
					baseEhcachePortalCache.getPortalCacheListeners();

			for (PortalCacheListener<K, V> portalCacheListener :
					portalCacheListeners.keySet()) {

				if (portalCacheListener instanceof
						ConfigurableEhcachePortalCacheListener) {

					portalCache.unregisterPortalCacheListener(
						portalCacheListener);
				}
			}

			_initPortalCacheListeners(portalCache, portalCacheConfiguration);
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
		sb.append(_portalCacheManagerName);
		sb.append(" as a ");

		if (mvcc) {
			sb.append("MVCC ");
		}
		else {
			sb.append("non-MVCC ");
		}

		sb.append("portal cache, because a ");

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
		sb.append(_portalCacheManagerName);
		sb.append(" as a ");

		if (sharded) {
			sb.append("sharded ");
		}
		else {
			sb.append("nonsharded ");
		}

		sb.append("portal cache, because a ");

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
		BaseEhcachePortalCacheManager.class);

	private final AggregatedPortalCacheManagerListener
		_aggregatedPortalCacheManagerListener =
			new AggregatedPortalCacheManagerListener();
	private CacheManager _cacheManager;
	private String _configFile;
	private ServiceTracker<?, ?> _configuratorSettingsServiceTracker;
	private String _defaultConfigFile;
	private ServiceTracker<MBeanServer, ManagementService>
		_mBeanServerServiceTracker;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private String _portalCacheManagerName;
	private final ConcurrentMap<String, PortalCache<K, V>> _portalCaches =
		new ConcurrentHashMap<>();
	private boolean _transactionalPortalCacheEnabled;
	private String[] _transactionalPortalCacheNames = StringPool.EMPTY_ARRAY;
	private boolean _usingDefault;

}