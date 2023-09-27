/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private final BlockingQueue<PortalCacheClusterEvent> _blockingQueue =
		new LinkedBlockingQueue<>();

}