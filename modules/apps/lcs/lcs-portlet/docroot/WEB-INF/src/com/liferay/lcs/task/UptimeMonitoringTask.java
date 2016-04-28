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

import com.liferay.lcs.advisor.UptimeMonitoringAdvisor;

/**
 * @author Ivica Cardic
 */
public class UptimeMonitoringTask implements Task {

	@Override
	public void run() {
		try {
			_uptimeMonitoringAdvisor.updateCurrentUptime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setUptimeMonitoringAdvisor(
		UptimeMonitoringAdvisor uptimeMonitoringAdvisor) {

		_uptimeMonitoringAdvisor = uptimeMonitoringAdvisor;
	}

	private UptimeMonitoringAdvisor _uptimeMonitoringAdvisor;

}