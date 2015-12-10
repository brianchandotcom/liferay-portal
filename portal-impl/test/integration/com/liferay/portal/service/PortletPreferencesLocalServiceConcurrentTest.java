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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.impl.PortletPreferencesLocalServiceImpl;
import com.liferay.portal.service.impl.SynchronousInvocationHandler;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedMultipleLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.portlet.PortletPreferences;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
public class PortletPreferencesLocalServiceConcurrentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(PropsValues.RETRY_ADVICE_MAX_RETRIES != 0);

		_threadCount = ServiceTestUtil.THREAD_COUNT;

		if ((PropsValues.RETRY_ADVICE_MAX_RETRIES > 0) &&
			(_threadCount > PropsValues.RETRY_ADVICE_MAX_RETRIES)) {

			_threadCount = PropsValues.RETRY_ADVICE_MAX_RETRIES;
		}

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			PortletPreferencesLocalServiceUtil.getService());

		TargetSource targetSource = advisedSupport.getTargetSource();

		final PortletPreferencesLocalServiceImpl
			portletPreferencesLocalServiceImpl =
				(PortletPreferencesLocalServiceImpl)targetSource.getTarget();

		final PortletPreferencesPersistence portletPreferencesPersistence =
			portletPreferencesLocalServiceImpl.
				getPortletPreferencesPersistence();

		ReflectionTestUtil.setFieldValue(
			portletPreferencesLocalServiceImpl, "portletPreferencesPersistence",
			ProxyUtil.newProxyInstance(
				PortletPreferencesPersistence.class.getClassLoader(),
				new Class<?>[] {PortletPreferencesPersistence.class},
				new SynchronousInvocationHandler(
					_threadCount,
					new Runnable() {

						@Override
						public void run() {
							ReflectionTestUtil.setFieldValue(
								portletPreferencesLocalServiceImpl,
								"portletPreferencesPersistence",
								portletPreferencesPersistence);
						}

					},
					PortletPreferencesPersistence.class.getMethod(
						"update", BaseModel.class),
					portletPreferencesPersistence)));
	}

	@ExpectedMultipleLogs(
		expectedMultipleLogs = {
			@ExpectedLogs(
				expectedLogs = {
					@ExpectedLog(
						expectedLog =
							"Application exception overridden by commit " +
								"exception",
						expectedType = ExpectedType.PREFIX
					)
				},
				level = "ERROR", loggerClass = DefaultTransactionExecutor.class
			),
			@ExpectedLogs(
				expectedLogs = {
					@ExpectedLog(
						expectedDBType = ExpectedDBType.DB2,
						expectedLog = "Batch failure",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.DB2,
						expectedLog = "DB2 SQL Error: SQLCODE=-803",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.HYPERSONIC,
						expectedLog = "integrity constraint violation",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.MYSQL,
						expectedLog = "Duplicate entry '",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.ORACLE,
						expectedLog = "ORA-00001: unique constraint",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.POSTGRESQL,
						expectedLog = "Batch entry",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.POSTGRESQL,
						expectedLog = "duplicate key",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						expectedDBType = ExpectedDBType.SYBASE,
						expectedLog = "Attempt to insert duplicate key row",
						expectedType = ExpectedType.CONTAINS
					)
				},
				level = "ERROR", loggerClass = JDBCExceptionReporter.class
			)
		}
	)
	@Test
	public void testAddPortletPreferencesConcurrently() throws Exception {
		SynchronousInvocationHandler.enable();

		try {
			final long ownerId = RandomTestUtil.randomLong();
			final int ownerType = RandomTestUtil.randomInt();
			final long plid = RandomTestUtil.randomLong();
			final String portletId = RandomTestUtil.randomString(
				UniqueStringRandomizerBumper.INSTANCE);

			Callable<PortletPreferences> callable =
				new Callable<PortletPreferences>() {

					@Override
					public PortletPreferences call() throws PortalException {
						return
							PortletPreferencesLocalServiceUtil.getPreferences(
								TestPropsValues.getCompanyId(), ownerId,
								ownerType, plid, portletId);
					}

				};

			List<FutureTask<PortletPreferences>> futureTasks =
				new ArrayList<>();

			for (int i = 0; i < _threadCount; i++) {
				FutureTask<PortletPreferences> futureTask = new FutureTask<>(
					callable);

				Thread thread = new Thread(
					futureTask,
					PortletPreferencesLocalServiceConcurrentTest.class.
						getName() + "-concurrent-getPreferences-" + i);

				thread.start();

				futureTasks.add(futureTask);
			}

			Set<PortletPreferences> portletPreferencesSet = new HashSet<>();

			for (FutureTask<PortletPreferences> futureTask : futureTasks) {
				portletPreferencesSet.add(futureTask.get());
			}

			Assert.assertEquals(
				portletPreferencesSet.toString(), 1,
				portletPreferencesSet.size());

			_portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}
		finally {
			SynchronousInvocationHandler.disable();
		}
	}

	@DeleteAfterTestRun
	private com.liferay.portal.model.PortletPreferences _portletPreferences;

	private int _threadCount;

}