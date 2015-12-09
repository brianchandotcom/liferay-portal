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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Connor McKay
 * @author Shuyang Zhou
 */
public class ResourceBlockLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Connection connection = DataAccess.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(
			"DELETE FROM ResourceBlock WHERE companyId = ? AND groupId " +
				"= ? AND name = ?");

		preparedStatement.setLong(1, _COMPANY_ID);
		preparedStatement.setLong(2, _GROUP_ID);
		preparedStatement.setString(3, _MODEL_NAME);

		preparedStatement.executeUpdate();

		DataAccess.cleanUp(connection, preparedStatement);
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Error for batch element",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "[jcc][t4][102][10040][4.16.53] Batch failure.",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog =
					"integrity constraint violation: unique constraint or " +
						"index violation:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog =
					"Deadlock found when trying to get lock; try restarting " +
						"transaction",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Duplicate entry ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog ="ORA-00001: unique constraint",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "Batch entry 0 insert into ResourceBlock ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog =
					"ERROR: duplicate key value violates unique constraint ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog =
					"Attempt to insert duplicate key row in object " +
						"'ResourceBlock'",
				expectedType = ExpectedType.CONTAINS
			)
		},
		level = "ERROR", loggerClass = JDBCExceptionReporter.class
	)
	@Test
	public void testConcurrentAccessing() throws Exception {
		PermissionedModel permissionedModel = new MockPermissionedModel();

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			new ResourceBlockPermissionsContainer();

		resourceBlockPermissionsContainer.addPermission(_ROLE_ID, _ACTION_IDS);

		Semaphore semaphore = new Semaphore(0);

		Callable<Void> updateResourceBlockIdCallable =
			new UpdateResourceBlockIdCallable(
				permissionedModel, resourceBlockPermissionsContainer,
				semaphore);

		Callable<Void> releaseResourceBlockCallable =
			new ReleaseResourceBlockCallable(permissionedModel, semaphore);

		List<Callable<Void>> callables = new ArrayList<>(_REFERENCE_COUNT * 2);

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(updateResourceBlockIdCallable);
			callables.add(releaseResourceBlockCallable);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT,
			new NamedThreadFactory(
				"Concurrent Accessing-", Thread.NORM_PRIORITY, null));

		List<Future<Void>> futures = executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdownNow();

		_assertNoSuchResourceBlock(_COMPANY_ID, _GROUP_ID, _MODEL_NAME);
	}

	@Test
	public void testConcurrentReleaseResourceBlock() throws Exception {
		_addResourceBlock(_RESOURCE_BLOCK_ID, _REFERENCE_COUNT);

		PermissionedModel permissionedModel = new MockPermissionedModel();

		permissionedModel.setResourceBlockId(_RESOURCE_BLOCK_ID);

		Callable<Void> releaseResourceBlockCallable =
			new ReleaseResourceBlockCallable(permissionedModel, null);

		List<Callable<Void>> callables = new ArrayList<>(_REFERENCE_COUNT);

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(releaseResourceBlockCallable);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT,
			new NamedThreadFactory(
				"Concurrent Release-", Thread.NORM_PRIORITY, null));

		List<Future<Void>> futures = executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdownNow();

		_assertNoSuchResourceBlock(_RESOURCE_BLOCK_ID);
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Error for batch element",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "[jcc][t4][102][10040][4.16.53] Batch failure.",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog =
					"integrity constraint violation: unique constraint or " +
						"index violation:",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog =
					"Deadlock found when trying to get lock; try restarting " +
						"transaction",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Duplicate entry ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog ="ORA-00001: unique constraint",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "Batch entry 0 insert into ResourceBlock ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog =
					"ERROR: duplicate key value violates unique constraint ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog =
					"Attempt to insert duplicate key row in object " +
						"'ResourceBlock'",
				expectedType = ExpectedType.CONTAINS
			)
		},
		level = "ERROR", loggerClass = JDBCExceptionReporter.class
	)
	@Test
	public void testConcurrentUpdateResourceBlockId() throws Exception {
		PermissionedModel permissionedModel = new MockPermissionedModel();

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			new ResourceBlockPermissionsContainer();

		resourceBlockPermissionsContainer.addPermission(_ROLE_ID, _ACTION_IDS);

		Callable<Void> updateResourceBlockIdCallable =
			new UpdateResourceBlockIdCallable(
				permissionedModel, resourceBlockPermissionsContainer, null);

		List<Callable<Void>> callables = new ArrayList<>(_REFERENCE_COUNT);

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(updateResourceBlockIdCallable);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT,
			new NamedThreadFactory(
				"Concurrent Update-", Thread.NORM_PRIORITY, null));

		List<Future<Void>> futures = executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdownNow();

		_assertResourceBlockReferenceCount(
			permissionedModel.getResourceBlockId(), _REFERENCE_COUNT);
	}

	private void _addResourceBlock(long resourceBlockId, long referenceCount)
		throws Exception {

		Connection connection = DataAccess.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(
			"INSERT INTO ResourceBlock (resourceBlockId, companyId, groupId, " +
				"name, referenceCount) VALUES (?, ?, ?, ?, ?)");

		preparedStatement.setLong(1, resourceBlockId);
		preparedStatement.setLong(2, _COMPANY_ID);
		preparedStatement.setLong(3, _GROUP_ID);
		preparedStatement.setString(4, _MODEL_NAME);
		preparedStatement.setLong(5, referenceCount);

		Assert.assertEquals(1, preparedStatement.executeUpdate());

		DataAccess.cleanUp(connection, preparedStatement);
	}

	private void _assertNoSuchResourceBlock(long resourceBlockId)
		throws Exception {

		Connection connection = DataAccess.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(
			"SELECT * FROM ResourceBlock WHERE resourceBlockId = ?");

		preparedStatement.setLong(1, resourceBlockId);

		ResultSet resultSet = preparedStatement.executeQuery();

		Assert.assertFalse(resultSet.next());

		DataAccess.cleanUp(connection, preparedStatement, resultSet);
	}

	private void _assertNoSuchResourceBlock(
			long companyId, long groupId, String name)
		throws Exception {

		Connection connection = DataAccess.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(
			"SELECT * FROM ResourceBlock WHERE companyId = ? AND groupId " +
				"= ? AND name = ?");

		preparedStatement.setLong(1, companyId);
		preparedStatement.setLong(2, groupId);
		preparedStatement.setString(3, name);

		ResultSet resultSet = preparedStatement.executeQuery();

		Assert.assertFalse(resultSet.next());

		DataAccess.cleanUp(connection, preparedStatement, resultSet);
	}

	private void _assertResourceBlockReferenceCount(
			long resourceBlockId, long expectedCountValue)
		throws Exception {

		Connection connection = DataAccess.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(
			"SELECT referenceCount FROM ResourceBlock WHERE " +
				"resourceBlockId = " + resourceBlockId);

		ResultSet resultSet = preparedStatement.executeQuery();

		Assert.assertTrue(resultSet.next());
		Assert.assertEquals(expectedCountValue, resultSet.getLong(1));

		DataAccess.cleanUp(connection, preparedStatement, resultSet);
	}

	private static final long _ACTION_IDS = 12;

	private static final long _COMPANY_ID = -1;

	private static final long _GROUP_ID = -1;

	private static final String _MODEL_NAME = "permissionedmodel";

	private static final int _REFERENCE_COUNT = 1000;

	private static final long _RESOURCE_BLOCK_ID = -1;

	private static final long _ROLE_ID = -1;

	private static final int _THREAD_COUNT = 10;

	private class MockPermissionedModel implements PermissionedModel {

		@Override
		public long getResourceBlockId() {
			return _resourceBlockId;
		}

		@Override
		public void persist() {
		}

		@Override
		public void setResourceBlockId(long resourceBlockId) {
			_resourceBlockId = resourceBlockId;
		}

		private long _resourceBlockId;

	}

	private class ReleaseResourceBlockCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			if (_semaphore != null) {
				_semaphore.acquire();
			}

			ResourceBlockLocalServiceUtil.releasePermissionedModelResourceBlock(
				_permissionedModel);

			return null;
		}

		private ReleaseResourceBlockCallable(
			PermissionedModel permissionedModel, Semaphore semaphore) {

			_permissionedModel = permissionedModel;
			_semaphore = semaphore;
		}

		private final PermissionedModel _permissionedModel;
		private final Semaphore _semaphore;

	}

	private class UpdateResourceBlockIdCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			while (true) {
				try {
					ResourceBlockLocalServiceUtil.updateResourceBlockId(
						_COMPANY_ID, _GROUP_ID, _MODEL_NAME, _permissionedModel,
						_resourceBlockPermissionsContainer.getPermissionsHash(),
						_resourceBlockPermissionsContainer);

					if (_semaphore != null) {
						_semaphore.release();
					}

					break;
				}
				catch (SystemException se) {
				}
			}

			return null;
		}

		private UpdateResourceBlockIdCallable(
			PermissionedModel permissionedModel,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer,
			Semaphore semaphore) {

			_permissionedModel = permissionedModel;
			_resourceBlockPermissionsContainer =
				resourceBlockPermissionsContainer;
			_semaphore = semaphore;
		}

		private final PermissionedModel _permissionedModel;
		private final ResourceBlockPermissionsContainer
			_resourceBlockPermissionsContainer;
		private final Semaphore _semaphore;

	}

}