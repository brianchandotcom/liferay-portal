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

import com.liferay.lcs.management.ObjectNameAttributeMapKeyStrategy;
import com.liferay.lcs.management.ObjectNameKeyPropertyMapKeyStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.management.ObjectName;

/**
 * @author Riccardo Ferrari
 */
public class WeblogicServerMetricsTask extends BaseServerMetricsTask {

	@Override
	protected Map<String, Map<String, Object>> getCurrentThreadsMetrics()
		throws Exception {

		return mBeanServerService.getObjectNamesAttributes(
			getCurrentThreadsObjectNames(),
			new String[] {
				"CompletedRequestCount", "ExecuteThreadIdleCount",
				"ExecuteThreadTotalCount", "HoggingThreadCount",
				"PendingUserRequestCount", "QueueLength",
				"SharedCapacityForWorkManagers", "StandbyThreadCount",
				"Suspended", "Throughput"
			},
			new ObjectNameAttributeMapKeyStrategy(
				mBeanServerService.getMBeanServer(), "Name"));
	}

	@Override
	protected Map<String, Map<String, Object>>
			getJNDIJDBCConnectionPoolsMetrics()
		throws Exception {

		return mBeanServerService.getObjectNamesAttributes(
			getJNDIJDBCConnectionPoolsObjectNames(),
			new String[] {
				"ActiveConnectionsAverageCount",
				"ActiveConnectionsCurrentCount", "ConnectionsTotalCount",
				"CurrCapacity", "CurrCapacityHighCount", "HighestNumAvailable",
				"LeakedConnectionCount", "WaitingForConnectionCurrentCount",
				"WaitingForConnectionFailureTotal",
				"WaitingForConnectionHighCount", "WaitingForConnectionTotal",
				"WaitSecondsHighCount"
			},
			new ObjectNameKeyPropertyMapKeyStrategy("Name"));
	}

	@Override
	protected void setUpCurrentThreadsObjectNames() throws Exception {
		ObjectName objectName = new ObjectName(
			"com.bea:Name=RuntimeService,Type=" +
				"weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");

		setCurrentThreadsObjectNames(
			mBeanServerService.getObjectNames(
				objectName,
				new ArrayList<String>(
					Arrays.asList("ServerRuntime", "ThreadPoolRuntime"))));
	}

	@Override
	protected void setUpJNDIJDBCConnectionPoolsObjectNames() throws Exception {
		ObjectName objectName = new ObjectName(
			"com.bea:Name=RuntimeService,Type=" +
				"weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");

		setJNDIJDBCConnectionPoolsObjectNames(
			mBeanServerService.getObjectNames(
				objectName,

				// Order matters

				new ArrayList<String>(
					Arrays.asList(
						"ServerRuntime", "JDBCServiceRuntime",
						"JDBCDataSourceRuntimeMBeans"))));
	}

}