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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.base.PortletPreferencesLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedMultipleLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Matthew Tambara
 */
public class PortletPreferencesLocalServiceImplTest
	extends ConcurrentTestCase<PortletPreferencesImpl> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Throwable {
		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_OWNER_ID, _OWNER_TYPE, _PLID);

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			PortletPreferencesLocalServiceUtil.getService());

		TargetSource targetSource = advisedSupport.getTargetSource();

		localServiceBaseImpl =
			(PortletPreferencesLocalServiceBaseImpl)targetSource.getTarget();

		originalPersistence =
			((PortletPreferencesLocalServiceBaseImpl)localServiceBaseImpl).
				getPortletPreferencesPersistence();

		persistenceField = "portletPreferencesPersistence";

		ReflectionTestUtil.setFieldValue(
			localServiceBaseImpl, persistenceField,
			ProxyUtil.newProxyInstance(
				PortletPreferencesPersistence.class.getClassLoader(),
				new Class<?>[] {PortletPreferencesPersistence.class},
				new SynchronousInvocationHandler()));

		syncMethod = PortletPreferencesPersistence.class.getMethod(
			"update", BaseModel.class);
	}

	@After
	public void tearDown() throws Throwable {
		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			_OWNER_ID, _OWNER_TYPE, _PLID);
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
						dbType = DB.TYPE_DB2, expectedLog = "Batch failure",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						dbType = DB.TYPE_DB2,
						expectedLog = "DB2 SQL Error: SQLCODE=-803",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						dbType = DB.TYPE_HYPERSONIC,
						expectedLog = "integrity constraint violation",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						dbType = DB.TYPE_MYSQL,
						expectedLog =
							"Duplicate entry '" + _OWNER_ID + "-" +
								_OWNER_TYPE + "-" + _PLID + "-" + _PORTLET_ID +
									"' for key",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						dbType = DB.TYPE_ORACLE,
						expectedLog = "ORA-00001: unique constraint",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						dbType = DB.TYPE_POSTGRESQL,
						expectedLog = "Batch entry",
						expectedType = ExpectedType.PREFIX
					),
					@ExpectedLog(
						dbType = DB.TYPE_POSTGRESQL,
						expectedLog = "duplicate key",
						expectedType = ExpectedType.CONTAINS
					),
					@ExpectedLog(
						dbType = DB.TYPE_SYBASE,
						expectedLog = "Attempt to insert duplicate key row",
						expectedType = ExpectedType.PREFIX
					)
				},
				level = "ERROR", loggerClass = JDBCExceptionReporter.class
			)
		}
	)
	@Test
	public void testAddPortletPreferencesConcurrently() throws Exception {
		doConcurrentTest();
	}

	@Override
	protected void assertResults(
			Set<FutureTask<PortletPreferencesImpl>> futureTasks)
		throws Exception {

		Set<PortletPreferencesImpl> portletPreferencesImpls = new HashSet<>();

		for (FutureTask<PortletPreferencesImpl> futureTask : futureTasks) {
			portletPreferencesImpls.add(futureTask.get());
		}

		Assert.assertEquals(1, portletPreferencesImpls.size());
	}

	@Override
	protected Callable<PortletPreferencesImpl> createCallable() {
		return new Callable<PortletPreferencesImpl>() {

			@Override
			public PortletPreferencesImpl call() {
				return (PortletPreferencesImpl)
					PortletPreferencesLocalServiceUtil.getPreferences(
						_COMPANY_ID, _OWNER_ID, _OWNER_TYPE, _PLID, _PORTLET_ID,
						null);
			}

		};
	}

	private static final long _COMPANY_ID = 0;

	private static final long _OWNER_ID = 0;

	private static final int _OWNER_TYPE = 3;

	private static final long _PLID = 99999;

	private static final String _PORTLET_ID = "Test";

}