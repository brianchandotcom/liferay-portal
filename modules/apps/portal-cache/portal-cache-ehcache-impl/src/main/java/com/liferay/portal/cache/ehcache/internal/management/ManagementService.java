/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.management;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Status;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.ehcache.core.events.CacheManagerListener;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.spi.store.InternalCacheManager;

/**
 * @author Preston Crary
 */
public class ManagementService implements CacheManagerListener {

	public ManagementService(
		CacheManager cacheManager, String cacheManagerName,
		MBeanServer mBeanServer, StatisticsService statisticsService) {

		_cacheManager = cacheManager;
		_cacheManagerName = cacheManagerName;
		_mBeanServer = mBeanServer;
		_statisticsService = statisticsService;
	}

	@Override
	public void cacheAdded(String alias, Cache<?, ?> cache) {
		_registerCache(alias, cache);
	}

	@Override
	public void cacheRemoved(String alias, Cache<?, ?> cache) {
		_unregisterMBeans(
			_mBeanServer.queryNames(
				_getObjectName(StringPool.STAR, _cacheManagerName, alias),
				null));
	}

	public void dispose() {
		_unregisterMBeans(
			_mBeanServer.queryNames(
				_getObjectName("CacheManager", null, _cacheManagerName), null));

		_unregisterMBeans(
			_mBeanServer.queryNames(
				_getObjectName(
					StringPool.STAR, _cacheManagerName, StringPool.STAR),
				null));
	}

	public void init() {
		try {
			_mBeanServer.registerMBean(
				new CacheManagerMBeanImpl(_cacheManagerName, _cacheManager),
				_getObjectName("CacheManager", null, _cacheManagerName));
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}

		InternalCacheManager internalCacheManager =
			(InternalCacheManager)_cacheManager;

		internalCacheManager.registerListener(this);

		synchronized (_cacheManager) {
			Configuration configuration =
				_cacheManager.getRuntimeConfiguration();

			Map<String, CacheConfiguration<?, ?>> cacheConfigurationsMap =
				configuration.getCacheConfigurations();

			cacheConfigurationsMap.forEach(
				(cacheName, cacheConfiguration) -> _registerCache(
					cacheName,
					_cacheManager.getCache(
						cacheName, cacheConfiguration.getKeyType(),
						cacheConfiguration.getValueType())));
		}
	}

	@Override
	public void stateTransition(Status from, Status to) {
		if (to == Status.UNINITIALIZED) {
			dispose();
		}
		else if (to == Status.AVAILABLE) {
			init();
		}
	}

	private ObjectName _getObjectName(
		String type, String cacheManagerName, String name) {

		StringBundler sb = new StringBundler(6);

		sb.append("org.ehcache:type=");
		sb.append(type);

		if (cacheManagerName != null) {
			sb.append(",CacheManager=");
			sb.append(cacheManagerName);
		}

		sb.append(",name=");

		if (name != null) {
			name = StringUtil.replace(
				name,
				new char[] {
					CharPool.COMMA, CharPool.COLON, CharPool.EQUAL,
					CharPool.NEW_LINE
				},
				new char[] {
					CharPool.PERIOD, CharPool.PERIOD, CharPool.PERIOD,
					CharPool.PERIOD
				});

			sb.append(name);
		}

		try {
			return new ObjectName(sb.toString());
		}
		catch (MalformedObjectNameException malformedObjectNameException) {
			return ReflectionUtil.throwException(malformedObjectNameException);
		}
	}

	private void _registerCache(String cacheName, Cache<?, ?> cache) {
		try {
			_mBeanServer.registerMBean(
				new CacheMBeanImpl(cacheName, cache),
				_getObjectName("Cache", _cacheManagerName, cacheName));

			_mBeanServer.registerMBean(
				new CacheStatisticsMBeanImpl(
					cacheName,
					_statisticsService.getCacheStatistics(cacheName)),
				_getObjectName(
					"CacheStatistics", _cacheManagerName, cacheName));
		}
		catch (InstanceAlreadyExistsException instanceAlreadyExistsException) {
			if (_log.isDebugEnabled()) {
				_log.debug(instanceAlreadyExistsException);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private void _unregisterMBeans(Set<ObjectName> objectNames) {
		for (ObjectName objectName : objectNames) {
			try {
				if (_mBeanServer.isRegistered(objectName)) {
					_mBeanServer.unregisterMBean(objectName);
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ManagementService.class);

	private final CacheManager _cacheManager;
	private final String _cacheManagerName;
	private final MBeanServer _mBeanServer;
	private final StatisticsService _statisticsService;

}