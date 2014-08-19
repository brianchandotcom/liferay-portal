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

package com.liferay.portal.kernel.cache.cluster;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class UniformPortalCacheClusterChannelSelector
	implements PortalCacheClusterChannelSelector {

	@Override
	public long getSelectedNumber() {
		return _eventCounter.get();
	}

	@Override
	public PortalCacheClusterChannel select(
		List<PortalCacheClusterChannel> portalCacheClusterChannels,
		PortalCacheClusterEvent portalCacheClusterEvent) {

		long count = _eventCounter.getAndIncrement();
		int size = portalCacheClusterChannels.size();

		return portalCacheClusterChannels.get((int)(count % size));
	}

	private final AtomicLong _eventCounter = new AtomicLong(0);

}