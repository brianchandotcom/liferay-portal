/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.cluster.clusterlink.messaging;

import com.liferay.portal.cache.ehcache.EhcachePortalCacheManager;
import com.liferay.portal.dao.orm.hibernate.region.LiferayEhcacheRegionFactory;
import com.liferay.portal.dao.orm.hibernate.region.SingletonLiferayEhcacheRegionFactory;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterListener extends BaseMessageListener {

	public ClusterLinkPortalCacheClusterListener() {
		LiferayEhcacheRegionFactory liferayEhcacheRegionFactory =
			SingletonLiferayEhcacheRegionFactory.getInstance();

		_hibernateCacheManager = liferayEhcacheRegionFactory.getCacheManager();

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)PortalBeanLocatorUtil.locate(
				_MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME);

		_portalCacheManager = ehcachePortalCacheManager.getEhcacheManager();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)message.getPayload();

		if (portalCacheClusterEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Payload is null");
			}

			return;
		}

		String cacheName = portalCacheClusterEvent.getCacheName();

		Ehcache ehcache = _portalCacheManager.getEhcache(cacheName);

		if (ehcache == null) {
			ehcache = _hibernateCacheManager.getEhcache(cacheName);
		}

		if (ehcache != null) {
			PortalCacheClusterEventType portalCacheClusterEventType =
				portalCacheClusterEvent.getEventType();

			if (portalCacheClusterEventType.equals(
					PortalCacheClusterEventType.REMOVE_ALL)) {

				ehcache.removeAll(true);
			}
			else if (portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.PUT) ||
					portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.UPDATE)) {

				Serializable elementKey =
					portalCacheClusterEvent.getElementKey();
				Serializable elementValue =
					portalCacheClusterEvent.getElementValue();

				if (elementValue == null) {
					ehcache.remove(
						portalCacheClusterEvent.getElementKey(), true);
				}
				else {
					Element oldElement = ehcache.get(elementKey);
					Element newElement = new Element(elementKey, elementValue);

					if (oldElement != null) {
						ehcache.replace(newElement);
					}
					else {
						ehcache.put(newElement);
					}
				}
			}
			else {
				ehcache.remove(portalCacheClusterEvent.getElementKey(), true);
			}
		}
	}

	private static final String _MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterListener.class);

	private CacheManager _hibernateCacheManager;
	private CacheManager _portalCacheManager;

}