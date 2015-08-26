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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.cache.cluster.internal.PortalCacheClusterException;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.concurrent.atomic.AtomicInteger;

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
	immediate = true, service = PortalCacheClusterChannelFactory.class
)
public class ClusterLinkPortalCacheClusterChannelFactory
	implements PortalCacheClusterChannelFactory {

	@Override
	public PortalCacheClusterChannel createPortalCacheClusterChannel()
		throws PortalCacheClusterException {

		int count = _counter.getAndIncrement();

		if (count >= _priorities.length) {
			throw new IllegalStateException(
				"Cannot create more than " + _priorities.length + " channels");
		}

		return new ClusterLinkPortalCacheClusterChannel(
			_clusterLink, _destinationName, _priorities[count]);
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			Configurable.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		_destinationName = portalCacheClusterConfiguration.destinationName();
		_priorities = portalCacheClusterConfiguration.priorities();
	}

	@Reference(unbind = "-")
	protected void setClusterLink(ClusterLink clusterLink) {
		_clusterLink = clusterLink;
	}

	private ClusterLink _clusterLink;
	private final AtomicInteger _counter = new AtomicInteger(0);
	private volatile String _destinationName;
	private volatile Priority[] _priorities;

}