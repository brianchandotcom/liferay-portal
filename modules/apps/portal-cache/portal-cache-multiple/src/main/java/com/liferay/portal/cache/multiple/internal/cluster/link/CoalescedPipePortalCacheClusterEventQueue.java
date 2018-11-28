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
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEventCoalesceComparator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tina Tian
 */
public class CoalescedPipePortalCacheClusterEventQueue
	implements PortalCacheClusterEventQueue {

	public CoalescedPipePortalCacheClusterEventQueue() {
		_coalescedPipe = new CoalescedPipe<>(
			new PortalCacheClusterEventCoalesceComparator());
	}

	@Override
	public long coalescedCount() {
		return _coalescedPipe.coalescedCount();
	}

	@Override
	public int pendingCount() {
		return _coalescedPipe.pendingCount();
	}

	@Override
	public void put(PortalCacheClusterEvent portalCacheClusterEvent)
		throws InterruptedException {

		_coalescedPipe.put(portalCacheClusterEvent);
	}

	@Override
	public PortalCacheClusterEvent take() throws InterruptedException {
		return _coalescedPipe.take();
	}

	@Override
	public Set<PortalCacheClusterEvent> takeSnapshot() {
		Set<PortalCacheClusterEvent> portalCacheClusterEvents = new HashSet<>();

		for (Object object : _coalescedPipe.takeSnapshot()) {
			portalCacheClusterEvents.add((PortalCacheClusterEvent)object);
		}

		return portalCacheClusterEvents;
	}

	private final CoalescedPipe<PortalCacheClusterEvent> _coalescedPipe;

}