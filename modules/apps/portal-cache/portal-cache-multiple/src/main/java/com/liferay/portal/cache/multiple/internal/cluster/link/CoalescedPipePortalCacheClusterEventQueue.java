/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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