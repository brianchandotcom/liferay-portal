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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.dao.jdbc.pool.metrics.ConnectionPoolMetrics;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.Description;

/**
 * @author Tom Wang
 */
public class ConnectionPoolLeakTestCallback
	extends BaseTestCallback
		<Collection<ServiceReference<ConnectionPoolMetrics>>,
			Collection<ServiceReference<ConnectionPoolMetrics>>> {

	public static final ConnectionPoolLeakTestCallback INSTANCE =
		new ConnectionPoolLeakTestCallback();

	@Override
	public void afterClass(
		Description description,
		Collection<ServiceReference<ConnectionPoolMetrics>> serviceReferences) {

		for (ServiceReference<ConnectionPoolMetrics> serviceReference :
				serviceReferences) {

			ConnectionPoolMetrics connectionPoolMetrics = _registry.getService(
				serviceReference);

			int[] initialConnectionsCount = _connectionPoolMap.get(
				"class-" + connectionPoolMetrics.getConnectionPoolName());

			Assert.assertEquals(
				"Active connection count differ before and after test for " +
					connectionPoolMetrics.getConnectionPoolName(),
				initialConnectionsCount[0],
				connectionPoolMetrics.getNumActive());

			_registry.ungetService(serviceReference);
		}
	}

	@Override
	public Collection<ServiceReference<ConnectionPoolMetrics>> beforeClass(
			Description description)
		throws Exception {

		_registry = RegistryUtil.getRegistry();

		Collection<ServiceReference<ConnectionPoolMetrics>> serviceReferences =
			_registry.getServiceReferences(ConnectionPoolMetrics.class, null);

		Assert.assertEquals(
			"Number of datasources should be 2", 2, serviceReferences.size());

		for (ServiceReference<ConnectionPoolMetrics> serviceReference :
				serviceReferences) {

			ConnectionPoolMetrics connectionPoolMetrics = _registry.getService(
				serviceReference);

			_connectionPoolMap.put(
				"class-" + connectionPoolMetrics.getConnectionPoolName(),
				new int[] {
					connectionPoolMetrics.getNumActive(),
					connectionPoolMetrics.getNumIdle()
				});

			_registry.ungetService(serviceReference);
		}

		return serviceReferences;
	}

	private static Registry _registry;

	private final Map<String, int[]> _connectionPoolMap = new HashMap<>();

}