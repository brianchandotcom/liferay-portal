/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Connor McKay
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ResourceBlockLocalServiceTest {

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

	@Test
	public void testConcurrentAccessing() throws Exception {
		PermissionedModel permissionedModel = new MockPermissionedModel();

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			new ResourceBlockPermissionsContainer();

		resourceBlockPermissionsContainer.addPermission(_ROLE_ID, _ACTION_IDS);

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		Semaphore semaphore = new Semaphore(0);

		List<Callable<Void>> callables = new ArrayList<Callable<Void>>();

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(
				new UpdateResourceBlockIdCallable(
					permissionedModel, permissionsHash,
					resourceBlockPermissionsContainer, semaphore));

			callables.add(
				new ReleaseResourceBlockCallable(permissionedModel, semaphore));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT);

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

		List<Callable<Void>> callables = new ArrayList<Callable<Void>>();

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(
				new ReleaseResourceBlockCallable(permissionedModel, null));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT);

		List<Future<Void>> futures = executorService.invokeAll(callables);

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdownNow();

		_assertNoSuchResourceBlock(_RESOURCE_BLOCK_ID);
	}

	@Test
	public void testConcurrentUpdateResourceBlockId() throws Exception {
		PermissionedModel permissionedModel = new MockPermissionedModel();

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			new ResourceBlockPermissionsContainer();

		resourceBlockPermissionsContainer.addPermission(_ROLE_ID, _ACTION_IDS);

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		List<Callable<Void>> callables = new ArrayList<Callable<Void>>();

		for (int i = 0; i < _REFERENCE_COUNT; i++) {
			callables.add(
				new UpdateResourceBlockIdCallable(
					permissionedModel, permissionsHash,
					resourceBlockPermissionsContainer, null));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_THREAD_COUNT);

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
			"INSERT INTO ResourceBlock (resourceBlockId, referenceCount) " +
				"VALUES (?, ?)");

		preparedStatement.setLong(1, resourceBlockId);
		preparedStatement.setLong(2, referenceCount);

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

		public long getResourceBlockId() {
			return _resourceBlockId;
		}

		public void persist() {
		}

		public void setResourceBlockId(long resourceBlockId) {
			_resourceBlockId = resourceBlockId;
		}

		private long _resourceBlockId;

	}

	private class ReleaseResourceBlockCallable implements Callable<Void> {

		public ReleaseResourceBlockCallable(
			PermissionedModel permissionedModel, Semaphore semaphore) {

			_permissionedModel = permissionedModel;
			_semaphore = semaphore;
		}

		public Void call() throws Exception {
			if (_semaphore != null) {
				_semaphore.acquire();
			}

			ResourceBlockLocalServiceUtil.releasePermissionedModelResourceBlock(
				_permissionedModel);

			return null;
		}

		private PermissionedModel _permissionedModel;
		private Semaphore _semaphore;

	}

	private class UpdateResourceBlockIdCallable implements Callable<Void> {

		public UpdateResourceBlockIdCallable(
			PermissionedModel permissionedModel, String permissionsHash,
			ResourceBlockPermissionsContainer resourceBlockPermissionsContainer,
			Semaphore semaphore) {

			_permissionedModel = permissionedModel;
			_permissionsHash = permissionsHash;
			_resourceBlockPermissionsContainer =
				resourceBlockPermissionsContainer;
			_semaphore = semaphore;
		}

		public Void call() throws Exception {
			while (true) {
				try {
					ResourceBlockLocalServiceUtil.updateResourceBlockId(
						_COMPANY_ID, _GROUP_ID, _MODEL_NAME, _permissionedModel,
						_permissionsHash, _resourceBlockPermissionsContainer);

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

		private PermissionedModel _permissionedModel;
		private String _permissionsHash;
		private ResourceBlockPermissionsContainer
			_resourceBlockPermissionsContainer;
		private Semaphore _semaphore;

	}

}