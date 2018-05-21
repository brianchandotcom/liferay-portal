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