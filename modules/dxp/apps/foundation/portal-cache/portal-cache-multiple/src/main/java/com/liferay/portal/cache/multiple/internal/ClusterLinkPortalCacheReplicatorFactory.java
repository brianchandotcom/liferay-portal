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

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.cache.multiple.internal.cluster.link.PortalCacheClusterLink;

import java.io.Serializable;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	enabled = false, immediate = true,
	service = PortalCacheReplicatorFactory.class
)
public class ClusterLinkPortalCacheReplicatorFactory
	implements PortalCacheReplicatorFactory {

	@Override
	public <K extends Serializable, V extends Serializable>
		PortalCacheReplicator<K, V> create(Properties properties) {

		return new ClusterLinkPortalCacheReplicator<>(
			properties, _portalCacheClusterLink);
	}

	@Reference(unbind = "-")
	protected void setPortalCacheClusterLink(
		PortalCacheClusterLink portalCacheClusterLink) {

		_portalCacheClusterLink = portalCacheClusterLink;
	}

	private PortalCacheClusterLink _portalCacheClusterLink;

}