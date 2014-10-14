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

package com.liferay.portal.cache.cluster.clusterlink;

import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterChannel;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterChannelFactory;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterException;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterChannelFactory
	implements PortalCacheClusterChannelFactory {

	@Override
	public PortalCacheClusterChannel createPortalCacheClusterChannel()
		throws PortalCacheClusterException {

		int count = _counter.getAndIncrement();

		if (count >= _priorities.size()) {
			throw new IllegalStateException(
				"Cannot create more than " + _priorities.size() + " channels");
		}

		return new ClusterLinkPortalCacheClusterChannel(
			_destinationName, _priorities.get(count));
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setPriorities(List<Priority> priorities) {
		_priorities = priorities;

		Collections.sort(priorities);
	}

	private final AtomicInteger _counter = new AtomicInteger(0);
	private String _destinationName;
	private List<Priority> _priorities;

}