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

import java.util.Set;

/**
 * @author Tina Tian
 */
public interface PortalCacheClusterEventQueue {

	public long coalescedCount();

	public int pendingCount();

	public void put(PortalCacheClusterEvent portalCacheClusterEvent)
		throws InterruptedException;

	public PortalCacheClusterEvent take() throws InterruptedException;

	public Set<PortalCacheClusterEvent> takeSnapshot();

}