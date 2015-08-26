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

package com.liferay.portal.cache.cluster.internal.clusterlink;

import com.liferay.portal.cache.cluster.internal.DestinationNames;
import com.liferay.portal.cache.cluster.internal.PortalCacheClusterException;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = PortalCacheClusterChannelFactory.class)
public class ClusterLinkPortalCacheClusterChannelFactory
	implements PortalCacheClusterChannelFactory {

	@Override
	public PortalCacheClusterChannel createPortalCacheClusterChannel(
			Priority priority)
		throws PortalCacheClusterException {

		return new ClusterLinkPortalCacheClusterChannel(
			_clusterLink, DestinationNames.CACHE_REPLICATION, priority);
	}

	@Reference(unbind = "-")
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	private ClusterLink _clusterLink;

}