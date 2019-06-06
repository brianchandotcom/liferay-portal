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
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.portal.cache.cluster.configuration.PortalCacheClusterConfiguration",
	enabled = false, immediate = true, service = PortalCacheClusterLink.class
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

	@Reference
	private PortalCacheClusterChannelFactory _portalCacheClusterChannelFactory;

	private volatile List<PortalCacheClusterChannel>
		_portalCacheClusterChannels;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC
	)
	private volatile PortalCacheClusterChannelSelector
		_portalCacheClusterChannelSelector;

}