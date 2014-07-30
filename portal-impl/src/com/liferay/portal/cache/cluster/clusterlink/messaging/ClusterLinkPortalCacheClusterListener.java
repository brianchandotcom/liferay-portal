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

package com.liferay.portal.cache.cluster.clusterlink.messaging;

import com.liferay.portal.cache.cluster.ClusterReplicationThreadLocal;
import com.liferay.portal.dao.orm.hibernate.region.LiferayEhcacheRegionFactory;
import com.liferay.portal.dao.orm.hibernate.region.SingletonLiferayEhcacheRegionFactory;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterListener extends BaseMessageListener {

	public ClusterLinkPortalCacheClusterListener() {
		LiferayEhcacheRegionFactory liferayEhcacheRegionFactory =
			SingletonLiferayEhcacheRegionFactory.getInstance();

		_hibernateCacheManager =
			liferayEhcacheRegionFactory.getPortalCacheManager();

		_portalCacheManager =
			(PortalCacheManager<Serializable, Serializable>)
				PortalBeanLocatorUtil.locate(
					_MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		byte[] data = (byte[])message.getPayload();

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(data));

		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)deserializer.readObject();

		if (portalCacheClusterEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Payload is null");
			}

			return;
		}

		handlePortalCacheClusterEvent(portalCacheClusterEvent);
	}

	protected void handlePortalCacheClusterEvent(
		PortalCacheClusterEvent portalCacheClusterEvent) {

		String cacheName = portalCacheClusterEvent.getCacheName();

		PortalCache<Serializable, Serializable> portalCache =
			_portalCacheManager.getCache(cacheName);

		if ((portalCache == null) && (_hibernateCacheManager != null)) {
			portalCache = _hibernateCacheManager.getCache(cacheName);
		}

		if (portalCache == null) {
			return;
		}

		PortalCacheClusterEventType portalCacheClusterEventType =
			portalCacheClusterEvent.getEventType();

		boolean replicate = ClusterReplicationThreadLocal.isReplicate();

		ClusterReplicationThreadLocal.setReplicate(false);

		try {
			if (portalCacheClusterEventType.equals(
					PortalCacheClusterEventType.REMOVE_ALL)) {

				portalCache.removeAll();
			}
			else if (portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.PUT) ||
					 portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.UPDATE)) {

				Serializable key = portalCacheClusterEvent.getElementKey();
				Serializable value = portalCacheClusterEvent.getElementValue();

				if (value == null) {
					portalCache.remove(key);
				}
				else {
					portalCache.put(
						key, value, portalCacheClusterEvent.getTimeToLive());
				}
			}
			else {
				portalCache.remove(portalCacheClusterEvent.getElementKey());
			}
		}
		finally {
			ClusterReplicationThreadLocal.setReplicate(replicate);
		}
	}

	private static final String _MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private static Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterListener.class);

	private PortalCacheManager<Serializable, Serializable>
		_hibernateCacheManager;
	private PortalCacheManager<Serializable, Serializable> _portalCacheManager;

}