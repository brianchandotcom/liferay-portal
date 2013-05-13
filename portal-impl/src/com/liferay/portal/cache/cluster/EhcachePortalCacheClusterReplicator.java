/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.cluster;

import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterLinkUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Properties;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheReplicator;

/**
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheClusterReplicator implements CacheReplicator {

	public EhcachePortalCacheClusterReplicator(Properties properties) {
		if (properties != null) {
			_replicatePuts = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_PUTS));
			_replicatePutsViaCopy = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_PUTS_VIA_COPY));
			_replicateRemovals = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_REMOVALS), true);
			_replicateUpdates = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_UPDATES), true);
			_replicateUpdatesViaCopy = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_UPDATES_VIA_COPY));
		}
	}

	@Override
	public boolean alive() {
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
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
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		if (!_replicatePuts) {
			return;
		}

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.PUT);

		if (_replicatePutsViaCopy) {
			portalCacheClusterEvent.setElementValue(element.getValue());
		}

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		if (!_replicateRemovals) {
			return;
		}

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.REMOVE);

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		if (!_replicateUpdates) {
			return;
		}

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.UPDATE);

		if (_replicateUpdatesViaCopy) {
			portalCacheClusterEvent.setElementValue(element.getValue());
		}

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
		if (!_replicateRemovals) {
			return;
		}

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				ehcache.getName(), null,
				PortalCacheClusterEventType.REMOVE_ALL);

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
	}

	private static final String _REPLICATE_PUTS = "replicatePuts";

	private static final String _REPLICATE_PUTS_VIA_COPY =
		"replicatePutsViaCopy";

	private static final String _REPLICATE_REMOVALS = "replicateRemovals";

	private static final String _REPLICATE_UPDATES = "replicateUpdates";

	private static final String _REPLICATE_UPDATES_VIA_COPY =
		"replicateUpdatesViaCopy";

	private boolean _replicatePuts;
	private boolean _replicatePutsViaCopy;
	private boolean _replicateRemovals = true;
	private boolean _replicateUpdates = true;
	private boolean _replicateUpdatesViaCopy;

}