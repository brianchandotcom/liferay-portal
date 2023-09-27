/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.cache.multiple.configuration.PortalCacheClusterConfiguration;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.Priority;

import java.util.ArrayList;
import java.util.List;

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

	public long getSubmittedEventNumber() {
		return _portalCacheClusterChannelSelector.getSelectedNumber();
	}

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		PortalCacheClusterChannel portalCacheClusterChannel =
			_portalCacheClusterChannelSelector.select(
				_portalCacheClusterChannels, portalCacheClusterEvent);

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
				_portalCacheClusterChannelFactory.
					createPortalCacheClusterChannel(priority);

			_portalCacheClusterChannels.add(portalCacheClusterChannel);
		}

		_portalCacheClusterChannelSelector =
			_portalCacheClusterChannelSelectorSnapshot.get();

		if (_portalCacheClusterChannelSelector == null) {
			_portalCacheClusterChannelSelector =
				new UniformPortalCacheClusterChannelSelector();
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

	private static final Snapshot<PortalCacheClusterChannelSelector>
		_portalCacheClusterChannelSelectorSnapshot = new Snapshot<>(
			PortalCacheClusterLink.class,
			PortalCacheClusterChannelSelector.class, null, true);

	@Reference
	private PortalCacheClusterChannelFactory _portalCacheClusterChannelFactory;

	private volatile List<PortalCacheClusterChannel>
		_portalCacheClusterChannels;
	private volatile PortalCacheClusterChannelSelector
		_portalCacheClusterChannelSelector;

}