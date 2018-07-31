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

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;
import net.sf.ehcache.management.Cache;
import net.sf.ehcache.management.CacheConfiguration;
import net.sf.ehcache.management.CacheConfigurationMBean;
import net.sf.ehcache.management.CacheMBean;
import net.sf.ehcache.management.CacheStatistics;
import net.sf.ehcache.management.CacheStatisticsMBean;
import net.sf.ehcache.management.ManagedCacheManagerPeerProvider;

/**
 * @author Preston Crary
 */
public class ManagementService implements CacheManagerEventListener {

	public ManagementService(
		CacheManager cacheManager, MBeanServer mBeanServer) {

		_cacheManager = cacheManager;
		_mBeanServer = mBeanServer;

		_status = Status.STATUS_UNINITIALISED;
	}

	@Override
	public void dispose() {
		try {
			_unregisterMBeans(
				_mBeanServer.queryNames(_getObjectName(_cacheManager), null));

			_unregisterMBeans(
				_mBeanServer.queryNames(
					new ObjectName(
						"net.sf.ehcache:*,CacheManager=" +
							_getMBeanSafeName(_cacheManager.getName())),
					null));
		}
		catch (MalformedObjectNameException mone) {
			ReflectionUtil.throwException(mone);
		}

		_status = Status.STATUS_SHUTDOWN;
	}

	@Override
	public Status getStatus() {
		return _status;
	}

	@Override
	public void init() {
		try {
			_mBeanServer.registerMBean(
				new net.sf.ehcache.management.CacheManager(_cacheManager),
				_getObjectName(_cacheManager));
		}
		catch (Exception e) {
			throw new CacheException(e);
		}

		Map<String, CacheManagerPeerProvider> cacheManagerPeerProviders =
			_cacheManager.getCacheManagerPeerProviders();

		for (CacheManagerPeerProvider cacheManagerPeerProvider :
				cacheManagerPeerProviders.values()) {

			if (cacheManagerPeerProvider instanceof
					ManagedCacheManagerPeerProvider) {

				ManagedCacheManagerPeerProvider
					managedCacheManagerPeerProvider =
						(ManagedCacheManagerPeerProvider)
							cacheManagerPeerProvider;

				managedCacheManagerPeerProvider.register(_mBeanServer);
			}
		}

		_registerListener();

		String[] cacheNames = _cacheManager.getCacheNames();

		for (String cacheName : cacheNames) {
			_registerCache(cacheName);
		}

		_status = Status.STATUS_ALIVE;
	}

	@Override
	public void notifyCacheAdded(String cacheName) {
		_registerCache(cacheName);
	}

	@Override
	public void notifyCacheRemoved(String cacheName) {
		try {
			_unregisterMBeans(
				_mBeanServer.queryNames(
					new ObjectName(
						StringBundler.concat(
							"net.sf.ehcache:*,CacheManager=",
							_getMBeanSafeName(_cacheManager.getName()),
							", name=", cacheName)),
					null));
		}
		catch (MalformedObjectNameException mone) {
			ReflectionUtil.throwException(mone);
		}
	}

	private static String _getMBeanSafeName(String name) {
		if (name == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(
			name,
			new char[] {
				CharPool.COMMA, CharPool.COLON, CharPool.EQUAL,
				CharPool.NEW_LINE
			},
			new char[] {
				CharPool.PERIOD, CharPool.PERIOD, CharPool.PERIOD,
				CharPool.PERIOD
			});
	}

	private static ObjectName _getObjectName(CacheManager cacheManager) {
		return _getObjectName("CacheManager", null, cacheManager.getName());
	}

	private static ObjectName _getObjectName(
		CacheManager cacheManager,
		CacheConfigurationMBean cacheConfigurationMBean) {

		return _getObjectName(
			"CacheConfiguration", cacheManager.getName(),
			cacheConfigurationMBean.getName());
	}

	private static ObjectName _getObjectName(
		CacheManager cacheManager, CacheMBean cacheMBean) {

		return _getObjectName(
			"Cache", cacheManager.getName(), cacheMBean.getName());
	}

	private static ObjectName _getObjectName(
		CacheManager cacheManager, CacheStatisticsMBean cacheStatisticsMBean) {

		return _getObjectName(
			"CacheStatistics", cacheManager.getName(),
			cacheStatisticsMBean.getAssociatedCacheName());
	}

	private static ObjectName _getObjectName(
		String type, String cacheManagerName, String name) {

		StringBundler sb = new StringBundler(6);

		sb.append("net.sf.ehcache:type=");
		sb.append(type);

		if (cacheManagerName != null) {
			sb.append(",CacheManager=");
			sb.append(cacheManagerName);
		}

		sb.append(",name=");
		sb.append(_getMBeanSafeName(name));

		try {
			return new ObjectName(sb.toString());
		}
		catch (MalformedObjectNameException mone) {
			return ReflectionUtil.throwException(mone);
		}
	}

	private void _registerCache(String cacheName) {
		Ehcache ehcache = _cacheManager.getEhcache(cacheName);

		if (ehcache == null) {
			return;
		}

		CacheMBean cacheMBean = new Cache(ehcache);

		try {
			_mBeanServer.registerMBean(
				cacheMBean, _getObjectName(_cacheManager, cacheMBean));

			CacheStatisticsMBean cacheStatisticsMBean = new CacheStatistics(
				ehcache);

			_mBeanServer.registerMBean(
				cacheStatisticsMBean,
				_getObjectName(_cacheManager, cacheStatisticsMBean));

			CacheConfigurationMBean cacheConfigurationMBean =
				new CacheConfiguration(ehcache);

			_mBeanServer.registerMBean(
				cacheConfigurationMBean,
				_getObjectName(_cacheManager, cacheConfigurationMBean));
		}
		catch (InstanceAlreadyExistsException iaee) {
			if (_log.isDebugEnabled()) {
				_log.debug(iaee, iaee);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	private void _registerListener() {
		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(this);
	}

	private void _unregisterMBeans(Set<ObjectName> objectNames) {
		for (ObjectName objectName : objectNames) {
			try {
				if (_mBeanServer.isRegistered(objectName)) {
					_mBeanServer.unregisterMBean(objectName);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ManagementService.class);

	private final CacheManager _cacheManager;
	private final MBeanServer _mBeanServer;
	private Status _status;

}