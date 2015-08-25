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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedMultipleLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.util.Assert;

/**
 * @author Matthew Tambara
 * @author William Newbury
 */
public class ResourcePermissionLocalServiceImplTest
	extends ConcurrentTestCase<Void> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			_COMPANY_ID, _NAME, _SCOPE, _PRIM_KEY);

		ResourceActionLocalServiceUtil.addResourceAction(_NAME, _ACTION_ID, 0);
		ResourceActionLocalServiceUtil.checkResourceActions();

		Role role = RoleLocalServiceUtil.fetchRole(
			_COMPANY_ID, RoleConstants.GUEST);

		if (role == null) {
			role = new RoleImpl();

			role.setCompanyId(_COMPANY_ID);
			role.setName(RoleConstants.GUEST);

			RoleLocalServiceUtil.addRole(role);
		}

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			ResourcePermissionLocalServiceUtil.getService());

		TargetSource targetSource = advisedSupport.getTargetSource();

		localServiceBaseImpl =
			(ResourcePermissionLocalServiceBaseImpl)targetSource.getTarget();

		originalPersistence =
			((ResourcePermissionLocalServiceBaseImpl)localServiceBaseImpl).
				getResourcePermissionPersistence();

		persistenceField = "resourcePermissionPersistence";

		ReflectionTestUtil.setFieldValue(
			localServiceBaseImpl, persistenceField,
			ProxyUtil.newProxyInstance(
				ResourcePermissionPersistence.class.getClassLoader(),
				new Class<?>[] {ResourcePermissionPersistence.class},
				new SynchronousInvocationHandler()));

		syncMethod = ResourcePermissionPersistence.class.getMethod(
			"update", BaseModel.class);
	}

	@After
	public void tearDown() throws Exception {
		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			_COMPANY_ID, RoleConstants.GUEST, _SCOPE, _PRIM_KEY);
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
							"Duplicate entry '" + _COMPANY_ID + "-" + _NAME +
								"-" + + _SCOPE + "-" + _PRIM_KEY + "-" +
									_ROLE_ID + "' for key",
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
	public void testAddResourcePermissionConcurrently() throws Exception {
		doConcurrentTest();
	}

	@Override
	protected void assertResults(Set<FutureTask<Void>> futureTasks)
		throws Exception {

		for (FutureTask<Void> futureTask : futureTasks) {
			futureTask.get();
		}

		Assert.notNull(
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				_COMPANY_ID, _NAME, _SCOPE, _PRIM_KEY, _ROLE_ID));
	}

	@Override
	protected Callable<Void> createCallable() {
		return new Callable<Void>() {

			@Override
			public Void call() throws PortalException {
				ResourcePermissionLocalServiceUtil.addResourcePermission(
					_COMPANY_ID, _NAME, _SCOPE, _PRIM_KEY, _ROLE_ID,
					_ACTION_ID);

				return null;
			}
		};
	}

	private static final String _ACTION_ID = "testActionId";

	private static final long _COMPANY_ID = 99999;

	private static final String _NAME = "testName";

	private static final String _PRIM_KEY = "testPrimKey";

	private static final long _ROLE_ID = 0;

	private static final int _SCOPE = 0;

}