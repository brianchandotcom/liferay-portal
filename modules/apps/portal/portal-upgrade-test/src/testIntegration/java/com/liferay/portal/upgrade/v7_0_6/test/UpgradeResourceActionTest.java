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

package com.liferay.portal.upgrade.v7_0_6.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.upgrade.v7_0_6.UpgradeResourceAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class UpgradeResourceActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();
	}

	@AfterClass
	public static void tearDownClass() {
		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_createResourceAction(_NAME_1, _ACTION_ID_1, 2);
		_createResourceAction(_NAME_1, _ACTION_ID_2, 2);
		_createResourceAction(_NAME_1, _ACTION_ID_3, 2);
		_createResourceAction(_NAME_2, _ACTION_ID_1, 2);
		_createResourceAction(_NAME_2, _ACTION_ID_2, 4);
	}

	@After
	public void tearDown() throws Exception {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"delete from ResourceAction where name in (?, ?)")) {

			preparedStatement.setString(1, _NAME_1);
			preparedStatement.setString(2, _NAME_2);

			preparedStatement.executeUpdate();
		}
	}

	@Test
	public void testDeleteDuplicateBitwiseValuesOnResource() throws Throwable {
		_assertResourceAction(_NAME_1, _ACTION_ID_1, false);
		_assertResourceAction(_NAME_1, _ACTION_ID_2, false);
		_assertResourceAction(_NAME_1, _ACTION_ID_3, false);
		_assertResourceAction(_NAME_2, _ACTION_ID_1, false);
		_assertResourceAction(_NAME_2, _ACTION_ID_2, false);

		UpgradeResourceAction upgradeResourceAction =
			new UpgradeResourceAction();

		upgradeResourceAction.upgrade();

		_assertResourceAction(_NAME_1, _ACTION_ID_1, false);
		_assertResourceAction(_NAME_1, _ACTION_ID_2, true);
		_assertResourceAction(_NAME_1, _ACTION_ID_3, true);
		_assertResourceAction(_NAME_2, _ACTION_ID_1, false);
		_assertResourceAction(_NAME_2, _ACTION_ID_2, false);
	}

	private void _assertResourceAction(
			String name, String actionId, boolean expectsNull)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select resourceActionId from ResourceAction where name = ? " +
					"and actionid = ?")) {

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, actionId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (expectsNull) {
				Assert.assertFalse(resultSet.next());
			}
			else {
				Assert.assertTrue(resultSet.next());
			}
		}
	}

	private void _createResourceAction(
			final String name, final String actionId, final long bitwiseValue)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"insert into ResourceAction (resourceActionId, name, " +
					"actionId, bitwiseValue) values (?, ?, ?, ?)")) {

			preparedStatement.setLong(
				1,
				_counterLocalService.increment(ResourceAction.class.getName()));

			preparedStatement.setString(2, name);
			preparedStatement.setString(3, actionId);
			preparedStatement.setLong(4, bitwiseValue);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _ACTION_ID_1 = "action1";

	private static final String _ACTION_ID_2 = "action2";

	private static final String _ACTION_ID_3 = "action3";

	private static final String _NAME_1 = "portlet1";

	private static final String _NAME_2 = "portlet2";

	private static Connection _connection;

	@Inject
	private CounterLocalService _counterLocalService;

}