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

package com.liferay.notifications.web.internal.upgrade.v2_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class UpgradeUserNotificationEventTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testColumnExists() throws Exception {
		try (Connection con = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(con);

			Assert.assertTrue(dbInspector.hasTable("UserNotificationEvent"));

			Assert.assertTrue(
				dbInspector.hasColumn(
					"UserNotificationEvent", "actionRequired"));
			Assert.assertTrue(
				dbInspector.hasColumn("UserNotificationEvent", "delivered"));
			Assert.assertTrue(
				dbInspector.hasColumn("UserNotificationEvent", "deliveryType"));
			Assert.assertTrue(
				dbInspector.hasColumn("UserNotificationEvent", "payload"));
			Assert.assertTrue(
				dbInspector.hasColumn(
					"UserNotificationEvent", "userNotificationEventId"));
		}
	}

}