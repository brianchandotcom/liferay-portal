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
import com.liferay.portal.cache.cluster.internal.PortalCacheClusterEvent;

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
	immediate = true, service = PortalCacheClusterLink.class
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

	@Reference(unbind = "-")
	public void setPortalCacheClusterChannelFactory(
		PortalCacheClusterChannelFactory portalCacheClusterChannelFactory) {

		_portalCacheClusterChannelFactory = portalCacheClusterChannelFactory;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC
	)
	public void setPortalCacheClusterChannelSelector(
		PortalCacheClusterChannelSelector portalCacheClusterChannelSelector) {

		_portalCacheClusterChannelSelector = portalCacheClusterChannelSelector;
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		PortalCacheClusterConfiguration portalCacheClusterConfiguration =
			Configurable.createConfigurable(
				PortalCacheClusterConfiguration.class,
				componentContext.getProperties());

		_channelNumber = portalCacheClusterConfiguration.channelNumber();

		_portalCacheClusterChannels = new ArrayList<>(_channelNumber);

		for (int i = 0; i < _channelNumber; i++) {
			_portalCacheClusterChannels.add(
				_portalCacheClusterChannelFactory.
					createPortalCacheClusterChannel());
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
	}

	protected void unsetPortalCacheClusterChannelSelector(
		PortalCacheClusterChannelSelector portalCacheClusterChannelSelector) {
	}

	private volatile int _channelNumber;
	private volatile PortalCacheClusterChannelFactory
		_portalCacheClusterChannelFactory;
	private volatile List<PortalCacheClusterChannel>
		_portalCacheClusterChannels;
	private volatile PortalCacheClusterChannelSelector
		_portalCacheClusterChannelSelector;

}