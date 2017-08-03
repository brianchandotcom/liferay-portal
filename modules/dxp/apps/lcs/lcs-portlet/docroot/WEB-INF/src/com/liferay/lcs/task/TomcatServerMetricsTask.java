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

import com.liferay.lcs.management.MapKeyStrategy;
import com.liferay.lcs.management.NameMapKeyStrategy;
import com.liferay.lcs.management.ObjectNameAttributeMapKeyStrategy;
import com.liferay.lcs.management.ObjectNameKeyPropertyMapKeyStrategy;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.ObjectName;

/**
 * @author Ivica Cardic
 */
public class TomcatServerMetricsTask extends BaseServerMetricsTask {

	@Override
	protected Map<String, Map<String, Object>> getCurrentThreadsMetrics()
		throws Exception {

		return mBeanServerService.getObjectNamesAttributes(
			getCurrentThreadsObjectNames(),
			new String[] {"currentThreadCount", "currentThreadsBusy"},
			new ObjectNameAttributeMapKeyStrategy(
				mBeanServerService.getMBeanServer(), "name"));
	}

	@Override
	protected Map<String, Map<String, Object>>
		getJNDIJDBCConnectionPoolsMetrics() throws Exception {

		return mBeanServerService.getObjectNamesAttributes(
			getJNDIJDBCConnectionPoolsObjectNames(), _attributeNames,
			_mapKeyStrategy);
	}

	@Override
	protected void setUpCurrentThreadsObjectNames() throws Exception {
		Set<ObjectName> currentThreadsObjectNames =
			mBeanServerService.getObjectNames(
				new ObjectName("Catalina:type=ThreadPool,*"), null);

		setCurrentThreadsObjectNames(currentThreadsObjectNames);
	}

	@Override
	protected void setUpJNDIJDBCConnectionPoolsObjectNames() throws Exception {
		String jndiName = _properties.getProperty("jndi.name");

		ObjectName objectName = new ObjectName(
			"Catalina:type=DataSource,context=/,host=localhost,class=" +
				"javax.sql.DataSource,name=\"" + jndiName + "\"");

		Set<ObjectName> objectNames = mBeanServerService.getObjectNames(
			objectName, null);

		if (!objectNames.isEmpty()) {
			_attributeNames = new String[] {"numActive", "numIdle"};
			_mapKeyStrategy = new ObjectNameKeyPropertyMapKeyStrategy("name");

			setJNDIJDBCConnectionPoolsObjectNames(objectNames);

			return;
		}

		objectName = new ObjectName(
			"Catalina:type=Resource,resourcetype=Context,context=/,host=" +
				"localhost,class=com.mchange.v2.c3p0.ComboPooledDataSource," +
					"name=\"" + jndiName + "\"");

		objectNames = mBeanServerService.getObjectNames(objectName, null);

		if (!objectNames.isEmpty()) {
			_attributeNames =
				new String[] {"numBusyConnections", "numIdleConnections"};
			_mapKeyStrategy = new NameMapKeyStrategy(jndiName);

			setJNDIJDBCConnectionPoolsObjectNames(
				mBeanServerService.getObjectNames(
					new ObjectName(
						"com.mchange.v2.c3p0:type=PooledDataSource,*"),
					null));
		}
	}

	private String[] _attributeNames = new String[0];
	private MapKeyStrategy _mapKeyStrategy;
	private final Properties _properties = PropsUtil.getProperties(
		"jdbc.default.", true);

}