/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.cache.cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheClusterLink {

	public void afterPropertiesSet() {
		_portalCacheClusterChannels = new ArrayList<PortalCacheClusterChannel>(
			_channelNumber);

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

	public void destroy() {
		for (PortalCacheClusterChannel portalCacheClusterChannel :
				_portalCacheClusterChannels) {

			portalCacheClusterChannel.destroy();
		}
	}

	public long getSubmittedEventNumber() {
		return _portalCacheClusterChannelSelector.getSelectedNumber();
	}

	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		PortalCacheClusterChannel portalCacheClusterChannel =
			_portalCacheClusterChannelSelector.select(
				_portalCacheClusterChannels, portalCacheClusterEvent);

		portalCacheClusterChannel.sendEvent(portalCacheClusterEvent);
	}

	public void setChannelNumber(int channelNumber) {
		_channelNumber = channelNumber;
	}

	public void setPortalCacheClusterChannelFactory(
		PortalCacheClusterChannelFactory portalCacheClusterChannelFactory) {

		_portalCacheClusterChannelFactory = portalCacheClusterChannelFactory;
	}

	public void setPortalCacheClusterChannelSelector(
		PortalCacheClusterChannelSelector portalCacheClusterChannelSelector) {

		_portalCacheClusterChannelSelector = portalCacheClusterChannelSelector;
	}

	private static final int _DEFAULT_CHANNEL_NUMBER = 10;

	private int _channelNumber = _DEFAULT_CHANNEL_NUMBER;
	private PortalCacheClusterChannelFactory _portalCacheClusterChannelFactory;
	private List<PortalCacheClusterChannel> _portalCacheClusterChannels;
	private PortalCacheClusterChannelSelector
		_portalCacheClusterChannelSelector;

}