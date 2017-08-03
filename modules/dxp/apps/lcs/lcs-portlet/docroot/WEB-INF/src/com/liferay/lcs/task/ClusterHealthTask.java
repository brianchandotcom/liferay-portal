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

import com.liferay.lcs.messaging.HealthMessage;
import com.liferay.lcs.util.ClusterNodeUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class ClusterHealthTask extends BaseScheduledTask {

	@Override
	public Type getType() {
		return Type.LOCAL;
	}

	@Override
	protected void doRun() throws Exception {
		HealthMessage healthMessage = new HealthMessage();

		healthMessage.setCreateTime(System.currentTimeMillis());
		healthMessage.setHealthType(HealthMessage.HEALTH_TYPE_CLUSTER);
		healthMessage.setKey(getKey());

		try {
			healthMessage.setPayload(
				ClusterNodeUtil.getRegisteredClusterNodeKeys());
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isTraceEnabled()) {
				_log.error(cnfe, cnfe);
			}

			return;
		}

		sendMessage(healthMessage);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterHealthTask.class);

}