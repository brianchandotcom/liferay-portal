/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.pool.metrics.ConnectionPoolMetrics;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.rule.ClassTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.Description;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Tom Wang
 */
public class JDBCConnectionLeakDetectionClassTestRule
	extends ClassTestRule<Collection<ServiceReference<ConnectionPoolMetrics>>> {

	public static final JDBCConnectionLeakDetectionClassTestRule INSTANCE =
		new JDBCConnectionLeakDetectionClassTestRule();

	@Override
	public void afterClass(
		Description description,
		Collection<ServiceReference<ConnectionPoolMetrics>> serviceReferences) {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (ServiceReference<ConnectionPoolMetrics> serviceReference :
				serviceReferences) {

			ConnectionPoolMetrics connectionPoolMetrics =
				bundleContext.getService(serviceReference);

			int previousActiveNumber = _connectionPoolActiveNumbers.remove(
				connectionPoolMetrics.getConnectionPoolName());

			int currentActiveNumber = connectionPoolMetrics.getNumActive();

			Assert.assertTrue(
				StringBundler.concat(
					"Active connection count increased after test for ",
					connectionPoolMetrics.getConnectionPoolName(),
					" previous active number: ", previousActiveNumber,
					", current active number: ", currentActiveNumber),
				previousActiveNumber >= currentActiveNumber);

			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public Collection<ServiceReference<ConnectionPoolMetrics>> beforeClass(
			Description description)
		throws Exception {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Collection<ServiceReference<ConnectionPoolMetrics>> serviceReferences =
			bundleContext.getServiceReferences(
				ConnectionPoolMetrics.class, null);

		Assert.assertTrue(
			"Number of connection pool should be 2 or more: " +
				serviceReferences,
			serviceReferences.size() >= 2);

		for (ServiceReference<ConnectionPoolMetrics> serviceReference :
				serviceReferences) {

			ConnectionPoolMetrics connectionPoolMetrics =
				bundleContext.getService(serviceReference);

			_connectionPoolActiveNumbers.put(
				connectionPoolMetrics.getConnectionPoolName(),
				connectionPoolMetrics.getNumActive());

			bundleContext.ungetService(serviceReference);
		}

		return serviceReferences;
	}

	private final Map<String, Integer> _connectionPoolActiveNumbers =
		new HashMap<>();

}