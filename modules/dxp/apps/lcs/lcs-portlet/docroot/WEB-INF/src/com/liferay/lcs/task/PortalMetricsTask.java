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

package com.liferay.lcs.task;

import com.liferay.lcs.messaging.MetricsMessage;
import com.liferay.lcs.metrics.PortalMetricsAggregator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Igor Beslic
 */
public class PortalMetricsTask extends BaseScheduledTask {

	@Override
	public Type getType() {
		return Type.LOCAL;
	}

	public void setPortalMetricsAggregator(
		PortalMetricsAggregator portalMetricsAggregator) {

		_portalMetricsAggregator = portalMetricsAggregator;
	}

	@Override
	protected void doRun() throws Exception {
		if (_portalMetricsAggregator.isEmpty()) {
			if (_log.isDebugEnabled()) {
				_log.debug("No portal metrics to send");
			}

			return;
		}

		MetricsMessage metricsMessage = new MetricsMessage();

		metricsMessage.setCreateTime(System.currentTimeMillis());
		metricsMessage.setKey(getKey());
		metricsMessage.setMetricsType(MetricsMessage.METRICS_TYPE_PORTAL);

		metricsMessage.setPayload(_portalMetricsAggregator.pop());

		sendMessage(metricsMessage);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalMetricsTask.class);

	private PortalMetricsAggregator _portalMetricsAggregator;

}