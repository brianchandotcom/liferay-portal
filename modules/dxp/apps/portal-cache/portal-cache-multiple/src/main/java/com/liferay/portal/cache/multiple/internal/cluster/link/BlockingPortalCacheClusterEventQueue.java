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

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Tina Tian
 */
public class BlockingPortalCacheClusterEventQueue
	implements PortalCacheClusterEventQueue {

	public BlockingPortalCacheClusterEventQueue() {
		_blockingQueue = new LinkedBlockingQueue<>();
	}

	@Override
	public long coalescedCount() {
		return 0;
	}

	@Override
	public int pendingCount() {
		return _blockingQueue.size();
	}

	@Override
	public void put(PortalCacheClusterEvent portalCacheClusterEvent)
		throws InterruptedException {

		_blockingQueue.put(portalCacheClusterEvent);
	}

	@Override
	public PortalCacheClusterEvent take() throws InterruptedException {
		return _blockingQueue.take();
	}

	@Override
	public Set<PortalCacheClusterEvent> takeSnapshot() {
		Set<PortalCacheClusterEvent> portalCacheClusterEvents = new HashSet<>();

		_blockingQueue.drainTo(portalCacheClusterEvents);

		return portalCacheClusterEvents;
	}

	private final BlockingQueue<PortalCacheClusterEvent> _blockingQueue;

}