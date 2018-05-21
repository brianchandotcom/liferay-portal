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