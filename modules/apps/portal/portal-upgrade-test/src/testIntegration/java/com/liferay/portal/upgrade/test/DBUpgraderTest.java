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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DBUpgraderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws SQLException {
		_currentBuildNumber = _getReleaseColumnValue("buildNumber");
		_currentState = _getReleaseColumnValue("state_");
	}

	@After
	public void tearDown() throws Exception {
		_updateRelease(_currentBuildNumber, _currentState);
	}

	@Test
	public void testUpgrade() throws Exception {
		_updateRelease(
			ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER,
			ReleaseConstants.STATE_GOOD);

		DBUpgrader.upgrade();
	}

	@Test
	public void testUpgradeWithFailureDoesNotSupportRetry() throws Exception {
		_updateRelease(
			ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER,
			ReleaseConstants.STATE_UPGRADE_FAILURE);

		try {
			DBUpgrader.upgrade();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
		}
	}

	@Test
	public void testUpgradeWithFailureSupportsRetry() throws Exception {
		_updateRelease(
			ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER,
			ReleaseConstants.STATE_UPGRADE_FAILURE);

		DBUpgrader.upgrade();
	}

	private static int _getReleaseColumnValue(String columnName) {
		return ReflectionTestUtil.invoke(
			DBUpgrader.class, "_getReleaseColumnValue",
			new Class<?>[] {String.class}, columnName);
	}

	private void _updateRelease(int buildNumber, int state) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set buildNumber = ?, state_ = ? where " +
					"releaseId = ?")) {

			preparedStatement.setInt(1, buildNumber);
			preparedStatement.setInt(2, state);
			preparedStatement.setLong(3, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}
	}

	private static int _currentBuildNumber;
	private static int _currentState;

}