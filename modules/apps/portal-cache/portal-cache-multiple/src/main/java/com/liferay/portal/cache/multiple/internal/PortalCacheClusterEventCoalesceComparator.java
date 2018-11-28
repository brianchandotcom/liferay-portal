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

package com.liferay.portal.cache.multiple.internal;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEventCoalesceComparator
	implements Comparator<PortalCacheClusterEvent> {

	@Override
	public int compare(
		PortalCacheClusterEvent portalCacheClusterEvent1,
		PortalCacheClusterEvent portalCacheClusterEvent2) {

		if ((portalCacheClusterEvent1 == null) ||
			(portalCacheClusterEvent2 == null)) {

			return 1;
		}

		// Check event type first. See LPS-65443 for more information.

		if (portalCacheClusterEvent1.getEventType() !=
				portalCacheClusterEvent2.getEventType()) {

			return -1;
		}

		if (!Objects.equals(
				portalCacheClusterEvent1.getPortalCacheManagerName(),
				portalCacheClusterEvent2.getPortalCacheManagerName()) ||
			!Objects.equals(
				portalCacheClusterEvent1.getPortalCacheName(),
				portalCacheClusterEvent2.getPortalCacheName())) {

			return -1;
		}

		// Must compare keys last as some cache keys do direct casting

		if (Objects.equals(
				portalCacheClusterEvent1.getElementKey(),
				portalCacheClusterEvent2.getElementKey())) {

			portalCacheClusterEvent1.setElementValue(
				portalCacheClusterEvent2.getElementValue());

			return 0;
		}

		return -1;
	}

}