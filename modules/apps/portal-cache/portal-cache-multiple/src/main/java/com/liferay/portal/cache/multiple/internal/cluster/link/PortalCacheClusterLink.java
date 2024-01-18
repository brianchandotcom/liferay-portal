/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration",
	enabled = false, service = PortalCacheClusterLink.class
)
public class PortalCacheClusterLink {

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		long count = _eventCounter.getAndIncrement();
		int size = _portalCacheClusterChannels.size();

		PortalCacheClusterChannel portalCacheClusterChannel =
			_portalCacheClusterChannels.get((int)(count % size));

		portalCacheClusterChannel.sendEvent(portalCacheClusterEvent);
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			ConfigurableUtil.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		Priority[] priorities = portalCacheClusterConfiguration.priorities();

		_portalCacheClusterChannels = new ArrayList<>(priorities.length);

		for (Priority priority : priorities) {
			PortalCacheClusterChannel portalCacheClusterChannel =
				PortalCacheClusterChannelFactory.
					createPortalCacheClusterChannel(
						_clusterLink, priority,
						portalCacheClusterConfiguration.usingCoalescedPipe());

			_portalCacheClusterChannels.add(portalCacheClusterChannel);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (PortalCacheClusterChannel portalCacheClusterChannel :
				_portalCacheClusterChannels) {

			portalCacheClusterChannel.destroy();
		}

		_portalCacheClusterChannels.clear();

		_portalCacheClusterChannels = null;
	}

	@Reference
	private ClusterLink _clusterLink;

	private final AtomicLong _eventCounter = new AtomicLong(0);
	private volatile List<PortalCacheClusterChannel>
		_portalCacheClusterChannels;

}