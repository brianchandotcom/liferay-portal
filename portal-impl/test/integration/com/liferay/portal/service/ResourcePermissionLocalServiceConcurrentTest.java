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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.service.impl.SynchronousInvocationHandler;
import com.liferay.portal.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedMultipleLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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
 * @author William Newbury
 * @author Shuyang Zhou
 */
public class ResourcePermissionLocalServiceConcurrentTest {

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

		_actionId = RandomTestUtil.randomString(
			UniqueStringRandomizerBumper.INSTANCE);
		_name = RandomTestUtil.randomString(
			UniqueStringRandomizerBumper.INSTANCE);

		_resourceAction = ResourceActionLocalServiceUtil.addResourceAction(
			_name, _actionId, RandomTestUtil.randomLong());

		ResourceActionLocalServiceUtil.checkResourceActions();

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			ResourcePermissionLocalServiceUtil.getService());

		TargetSource targetSource = advisedSupport.getTargetSource();

		final ResourcePermissionLocalServiceImpl
			resourcePermissionLocalServiceImpl =
				(ResourcePermissionLocalServiceImpl)targetSource.getTarget();

		final ResourcePermissionPersistence resourcePermissionPersistence =
			resourcePermissionLocalServiceImpl.
				getResourcePermissionPersistence();

		ReflectionTestUtil.setFieldValue(
			resourcePermissionLocalServiceImpl, "resourcePermissionPersistence",
			ProxyUtil.newProxyInstance(
				ResourcePermissionPersistence.class.getClassLoader(),
				new Class<?>[] {ResourcePermissionPersistence.class},
				new SynchronousInvocationHandler(
					_threadCount,
					new Runnable() {

						@Override
						public void run() {
							ReflectionTestUtil.setFieldValue(
								resourcePermissionLocalServiceImpl,
								"resourcePermissionPersistence",
								resourcePermissionPersistence);
						}

					},
					ResourcePermissionPersistence.class.getMethod(
						"update", BaseModel.class),
					resourcePermissionPersistence)));
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
						expectedLog = "Duplicate entry '",
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
						expectedType = ExpectedType.CONTAINS
					)
				},
				level = "ERROR", loggerClass = JDBCExceptionReporter.class
			)
		}
	)
	@Test
	public void testAddResourcePermissionConcurrently() throws Exception {
		SynchronousInvocationHandler.enable();

		try {
			final String primKey = RandomTestUtil.randomString(
				UniqueStringRandomizerBumper.INSTANCE);

			Callable<ResourcePermission> callable =
				new Callable<ResourcePermission>() {

					@Override
					public ResourcePermission call() throws PortalException {
						Role role = RoleLocalServiceUtil.getRole(
							TestPropsValues.getCompanyId(),
							RoleConstants.GUEST);

						ResourcePermissionLocalServiceUtil.
							addResourcePermission(
								TestPropsValues.getCompanyId(), _name, 0,
								primKey, role.getRoleId(), _actionId);

						return ResourcePermissionLocalServiceUtil.
							fetchResourcePermission(
								TestPropsValues.getCompanyId(), _name, 0,
								primKey, role.getRoleId());
					}

				};

			List<FutureTask<ResourcePermission>> futureTasks =
				new ArrayList<>();

			for (int i = 0; i < _threadCount; i++) {
				FutureTask<ResourcePermission> futureTask = new FutureTask<>(
					callable);

				Thread thread = new Thread(
					futureTask,
					ResourcePermissionLocalServiceConcurrentTest.class.
						getName() + "-concurrent-addResourcePermission-" + i);

				thread.start();

				futureTasks.add(futureTask);
			}

			Set<ResourcePermission> resourcePermissions = new HashSet<>();

			for (FutureTask<ResourcePermission> futureTask : futureTasks) {
				resourcePermissions.add(futureTask.get());
			}

			Assert.assertEquals(
				resourcePermissions.toString(), 1, resourcePermissions.size());

			Iterator<ResourcePermission> iterator =
				resourcePermissions.iterator();

			_resourcePermission = iterator.next();

			Assert.assertEquals(
				_resourceAction.getBitwiseValue(),
				_resourcePermission.getActionIds());
		}
		finally {
			SynchronousInvocationHandler.disable();
		}
	}

	private String _actionId;
	private String _name;

	@DeleteAfterTestRun
	private ResourceAction _resourceAction;

	@DeleteAfterTestRun
	private ResourcePermission _resourcePermission;

	private int _threadCount;

}