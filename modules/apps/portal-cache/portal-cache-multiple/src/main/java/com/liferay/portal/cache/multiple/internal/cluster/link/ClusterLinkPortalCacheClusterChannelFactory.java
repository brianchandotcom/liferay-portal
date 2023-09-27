/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	configurationPid = "com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration",
	enabled = false, service = PortalCacheClusterChannelFactory.class
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

	@Reference
	private ClusterLink _clusterLink;

	private volatile boolean _usingCoalescedPipe;

}