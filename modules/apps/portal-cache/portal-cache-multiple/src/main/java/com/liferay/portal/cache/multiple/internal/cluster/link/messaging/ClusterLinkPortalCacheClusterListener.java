/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link.messaging;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEventType;
import com.liferay.portal.cache.multiple.internal.constants.PortalCacheDestinationNames;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.util.SerializableUtil;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false,
	property = "destination.name=" + PortalCacheDestinationNames.CACHE_REPLICATION,
	service = MessageListener.class
)
public class ClusterLinkPortalCacheClusterListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<PortalCacheManager<? extends Serializable, ?>>)
				(Class<?>)PortalCacheManager.class,
			null,
			(serviceReference, emitter) -> {
				PortalCacheManager<? extends Serializable, ?>
					portalCacheManager = bundleContext.getService(
						serviceReference);

				emitter.emit(portalCacheManager.getPortalCacheManagerName());
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)SerializableUtil.deserialize(
				(byte[])message.getPayload(),
				ClusterLinkPortalCacheClusterListener.class.getClassLoader());

		if (portalCacheClusterEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Payload is null");
			}

			return;
		}

		_handlePortalCacheClusterEvent(portalCacheClusterEvent);
	}

	private void _handlePortalCacheClusterEvent(
		PortalCacheClusterEvent portalCacheClusterEvent) {

		PortalCacheManager<? extends Serializable, ?> portalCacheManager =
			_serviceTrackerMap.getService(
				portalCacheClusterEvent.getPortalCacheManagerName());

		if (portalCacheManager == null) {
			return;
		}

		PortalCache<Serializable, Serializable> portalCache =
			(PortalCache<Serializable, Serializable>)
				portalCacheManager.fetchPortalCache(
					portalCacheClusterEvent.getPortalCacheName());

		if (portalCache == null) {
			return;
		}

		if (portalCache.isSharded()) {
			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(
						portalCacheClusterEvent.getCompanyId())) {

				_handlePortalCacheClusterEvent(
					portalCacheClusterEvent, portalCache);
			}

			return;
		}

		_handlePortalCacheClusterEvent(portalCacheClusterEvent, portalCache);
	}

	private void _handlePortalCacheClusterEvent(
		PortalCacheClusterEvent portalCacheClusterEvent,
		PortalCache<Serializable, Serializable> portalCache) {

		PortalCacheClusterEventType portalCacheClusterEventType =
			portalCacheClusterEvent.getEventType();

		if (portalCacheClusterEventType.equals(
				PortalCacheClusterEventType.REMOVE_ALL)) {

			PortalCacheHelperUtil.removeAllWithoutReplicator(portalCache);
		}
		else if (portalCacheClusterEventType.equals(
					PortalCacheClusterEventType.PUT) ||
				 portalCacheClusterEventType.equals(
					 PortalCacheClusterEventType.UPDATE)) {

			Serializable key = portalCacheClusterEvent.getElementKey();
			Serializable value = portalCacheClusterEvent.getElementValue();

			if (value == null) {
				PortalCacheHelperUtil.removeWithoutReplicator(portalCache, key);
			}
			else {
				PortalCacheHelperUtil.putWithoutReplicator(
					portalCache, key, value,
					portalCacheClusterEvent.getTimeToLive());
			}
		}
		else {
			PortalCacheHelperUtil.removeWithoutReplicator(
				portalCache, portalCacheClusterEvent.getElementKey());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterListener.class);

	@Reference(
		target = "(destination.name=" + PortalCacheDestinationNames.CACHE_REPLICATION + ")"
	)
	private Destination _destination;

	private ServiceTrackerMap
		<String, PortalCacheManager<? extends Serializable, ?>>
			_serviceTrackerMap;

}