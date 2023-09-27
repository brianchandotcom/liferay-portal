/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;

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