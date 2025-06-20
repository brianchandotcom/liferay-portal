/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import java.util.Objects;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class BackgroundTaskLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_backgroundTaskExecutor =
			(BackgroundTaskExecutor)ProxyUtil.newProxyInstance(
				BackgroundTaskExecutor.class.getClassLoader(),
				new Class<?>[] {BackgroundTaskExecutor.class},
				(proxy, method, argus) -> null);
	}

	@Test
	public void testAddBackgroundTaskWithSystemDefaults()
		throws PortalException {

		BackgroundTaskExecutor backgroundTaskExecutor =
			(BackgroundTaskExecutor)ProxyUtil.newProxyInstance(
				BackgroundTaskExecutor.class.getClassLoader(),
				new Class<?>[] {BackgroundTaskExecutor.class},
				(proxy, method, argus) -> {
					if (Objects.equals(method.getName(), "clone")) {
						return proxy;
					}
					else if (Objects.equals(method.getName(), "execute")) {
						return new BackgroundTaskResult(
							BackgroundTaskConstants.STATUS_FAILED);
					}
					else if (Objects.equals(
						method.getName(), "getIsolationLevel")) {

						return BackgroundTaskConstants.
							ISOLATION_LEVEL_NOT_ISOLATED;
					}
					else if (Objects.equals(method.getName(), "isSerial")) {
						return false;
					}

					return null;
				});

		Class<?> backgroundTaskExecutorClass =
			backgroundTaskExecutor.getClass();

		Bundle bundle = FrameworkUtil.getBundle(BackgroundTaskLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				HashMapDictionaryBuilder.<String, Object>put(
					"background.task.executor.class.name",
					backgroundTaskExecutorClass.getName()
				).build());

		try {
			_companyLocalService.forEachCompanyId(
				companyId -> {
					BackgroundTask backgroundTask =
						_backgroundTaskLocalService.addBackgroundTask(
							UserConstants.USER_ID_DEFAULT, CompanyConstants.SYSTEM,
							RandomTestUtil.randomString(), null,
							backgroundTaskExecutor.getClass(), null, null);

					Assert.assertEquals(
						backgroundTask.getCompanyId(), (long)companyId);

					_backgroundTaskLocalService.deleteBackgroundTask(
						backgroundTask.getBackgroundTaskId());
				});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static BackgroundTaskExecutor _backgroundTaskExecutor;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

}