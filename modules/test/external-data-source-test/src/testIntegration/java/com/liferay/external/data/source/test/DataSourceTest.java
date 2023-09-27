/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test;

import com.liferay.external.data.source.test.model.TestEntity;
import com.liferay.external.data.source.test.service.TestEntityLocalServiceUtil;
import com.liferay.external.data.source.test.service.persistence.TestEntityPersistence;
import com.liferay.external.data.source.test.service.persistence.TestEntityUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class DataSourceTest {

	@Before
	public void setUp() {
		_persistence = TestEntityUtil.getPersistence();

		_dataSource = _persistence.getDataSource();
	}

	@Test
	public void testDataSource() throws Exception {
		Assert.assertNotSame(InfrastructureUtil.getDataSource(), _dataSource);
	}

	@Test
	public void testUpdate() throws Exception {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from TestEntity");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(
				"Missing upgrade process created record", resultSet.next());

			Assert.assertEquals(-1, resultSet.getLong("id_"));
			Assert.assertEquals(
				"Test Upgrade Value", resultSet.getString("data_"));

			Assert.assertFalse("Found more than 1 record", resultSet.next());
		}

		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from TestEntity")) {

			preparedStatement.executeUpdate();
		}

		long pk = RandomTestUtil.nextLong();

		TestEntity testEntity = _persistence.create(pk);

		testEntity.setData(DataSourceTest.class.getName());

		TestEntityLocalServiceUtil.addTestEntity(testEntity);

		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from TestEntity");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(pk, resultSet.getLong("id_"));
			Assert.assertEquals(
				DataSourceTest.class.getName(), resultSet.getString("data_"));
		}
	}

	private DataSource _dataSource;
	private TestEntityPersistence _persistence;

}