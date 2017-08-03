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

package com.liferay.lcs.messaging;

import com.liferay.lcs.metrics.PortalMetricsAggregator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.monitoring.DataSample;

import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class PortalMetricsMessageListener implements MessageListener {

	public void destroy() {
	}

	@Override
	public void receive(Message message) {
		if (!_lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Waiting for LCS connection manager");
			}

			return;
		}

		if (message.getPayload() instanceof DataSample) {
			_portalMetricsAggregator.push((DataSample)message.getPayload());
		}
		else {
			_portalMetricsAggregator.push(
				(List<DataSample>)message.getPayload());
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setPortalMetricsAggregator(
		PortalMetricsAggregator portalMetricsAggregator) {

		_portalMetricsAggregator = portalMetricsAggregator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalMetricsMessageListener.class);

	private LCSConnectionManager _lcsConnectionManager;
	private PortalMetricsAggregator _portalMetricsAggregator;

}