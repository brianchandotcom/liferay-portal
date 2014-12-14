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

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.User;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.ResetDatabaseTestRule;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class UserLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			ResetDatabaseTestRule.INSTANCE);

	@Test
	public void testGetNoAnnouncementsDeliveries() throws Exception {
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		AnnouncementsDeliveryLocalServiceUtil.addUserDelivery(
			user1.getUserId(), "general");

		List<User> users = UserLocalServiceUtil.getNoAnnouncementsDeliveries(
			"general");

		Assert.assertFalse(users.contains(user1));
		Assert.assertTrue(users.contains(user2));
	}

	@Test
	public void testGetNoContacts() throws Exception {
		User user = UserTestUtil.addUser();

		ContactLocalServiceUtil.deleteContact(user.getContactId());

		List<User> users = UserLocalServiceUtil.getNoContacts();

		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testGetNoGroups() throws Exception {
		User user = UserTestUtil.addUser();

		GroupLocalServiceUtil.deleteGroup(user.getGroupId());

		List<User> users = UserLocalServiceUtil.getNoGroups();

		Assert.assertTrue(users.contains(user));
	}

}