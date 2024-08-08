/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.scheduler.test;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class AntivirusAsyncFileStoreSchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule integrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_configuration = _configurationAdmin.getConfiguration(
			AntivirusAsyncConfiguration.class.getName(), "?");

		_configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				"batchScanCronExpression", "0 0 23 * * ?"
			).put(
				"maximumQueueSize", 0
			).put(
				"retryCronExpression", "0 0/5 * * * ?"
			).build());

		_schedulerJobConfiguration = _getSchedulerJobConfiguration();
	}

	@After
	public void tearDown() throws Exception {
		_configuration.delete();
	}

	@Test(expected = Test.None.class)
	public void testRun() throws Exception {
		UnsafeRunnable<Exception> jobExecutorUnsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		jobExecutorUnsafeRunnable.run();
	}

	private SchedulerJobConfiguration _getSchedulerJobConfiguration()
		throws Exception {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		String filterString =
			"(component.name=com.liferay.antivirus.async.store.internal." +
				"scheduler.AntivirusAsyncFileStoreSchedulerJobConfiguration)";

		ServiceReference<SchedulerJobConfiguration>[] serviceReferences = null;

		int waitTime = 0;

		while (ArrayUtil.isEmpty(serviceReferences)) {
			serviceReferences =
				(ServiceReference<SchedulerJobConfiguration>[])
					bundleContext.getServiceReferences(
						SchedulerJobConfiguration.class.getName(),
						filterString);

			if (waitTime >= TestPropsValues.CI_TEST_TIMEOUT_TIME) {
				throw new IllegalStateException(
					StringBundler.concat(
						"Timed out while waiting for service ",
						SchedulerJobConfiguration.class.getName(), " ",
						filterString));
			}

			if (serviceReferences == null) {
				Thread.sleep(_SLEEP_TIME);

				waitTime += _SLEEP_TIME;
			}
		}

		return bundleContext.getService(serviceReferences[0]);
	}

	private static final int _SLEEP_TIME = 2000;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private Configuration _configuration;
	private SchedulerJobConfiguration _schedulerJobConfiguration;

}