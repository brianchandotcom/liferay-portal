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

import com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterException;
import com.liferay.portal.cache.multiple.internal.constants.PortalCacheDestinationNames;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration",
	enabled = false, immediate = true,
	service = PortalCacheClusterChannelFactory.class
)
public class ClusterLinkPortalCacheClusterChannelFactory
	implements PortalCacheClusterChannelFactory {

	@Override
	public PortalCacheClusterChannel createPortalCacheClusterChannel(
			Priority priority)
		throws PortalCacheClusterException {

		if (_usingCoalescedPipe) {
			return new ClusterLinkPortalCacheClusterChannel(
				_clusterLink, PortalCacheDestinationNames.CACHE_REPLICATION,
				new CoalescedPipePortalCacheClusterEventQueue(), priority);
		}

		return new ClusterLinkPortalCacheClusterChannel(
			_clusterLink, PortalCacheDestinationNames.CACHE_REPLICATION,
			new BlockingPortalCacheClusterEventQueue(), priority);
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		_usingCoalescedPipe =
			portalCacheClusterConfiguration.usingCoalescedPipe();
	}

	@Reference(unbind = "-")
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	private ClusterLink _clusterLink;
	private volatile boolean _usingCoalescedPipe;

}