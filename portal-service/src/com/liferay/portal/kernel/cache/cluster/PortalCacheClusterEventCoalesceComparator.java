/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache.cluster;

import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterEventCoalesceComparator
	implements Comparator<PortalCacheClusterEvent> {

	public int compare(
		PortalCacheClusterEvent portalCacheClusterEvent1,
		PortalCacheClusterEvent portalCacheClusterEvent2) {

		if ((portalCacheClusterEvent1 == null) ||
			(portalCacheClusterEvent2 == null)) {

			return 1;
		}

		if (portalCacheClusterEvent1.getCacheName().equals(
				portalCacheClusterEvent2.getCacheName()) &&
			(portalCacheClusterEvent1.getEventType() ==
				portalCacheClusterEvent2.getEventType()) &&
			Validator.equals(
				portalCacheClusterEvent1.getElementKey(),
				portalCacheClusterEvent2.getElementKey())) {

			portalCacheClusterEvent1.setElementValue(
				portalCacheClusterEvent2.getElementValue());

			return 0;
		}
		else {
			return -1;
		}
	}

}