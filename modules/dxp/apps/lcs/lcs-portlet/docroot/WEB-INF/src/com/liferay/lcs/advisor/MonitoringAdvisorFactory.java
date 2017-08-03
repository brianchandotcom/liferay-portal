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

package com.liferay.lcs.advisor;

import com.liferay.lcs.messaging.PortalMetricsMessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class MonitoringAdvisorFactory {

	public static MonitoringAdvisor getInstance(Class messageListenerClass) {
		if (_monitoringAdvisors.containsKey(messageListenerClass)) {
			return _monitoringAdvisors.get(messageListenerClass);
		}

		MonitoringAdvisor monitoringAdvisor = null;

		if (messageListenerClass.equals(PortalMetricsMessageListener.class)) {
			monitoringAdvisor = new PortalMetricsMonitoringAdvisor();

			_monitoringAdvisors.put(messageListenerClass, monitoringAdvisor);
		}

		return monitoringAdvisor;
	}

	private static final Map<Class, MonitoringAdvisor> _monitoringAdvisors =
		new HashMap<>();

}