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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.User;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.listeners.ResetDatabaseExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@ExecutionTestListeners(
	listeners = {
		ResetDatabaseExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserLocalServiceTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Test
	public void testGetNoAnnouncementsDeliveries() throws Exception {
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		AnnouncementsDeliveryLocalServiceUtil.addUserDelivery(
			user1.getUserId(), "general");

		List<User> users = UserLocalServiceUtil.getNoAnnouncementsDeliveries(
			"general");

		boolean success = false;

		for (User user : users) {
			if (user.getUserId() == user2.getUserId()) {
				success = true;
			}
			else if (user.getUserId() == user1.getUserId()) {
				Assert.fail(
					"User " + user.getUserId() +
						" should not have announcement deliveries");
			}
		}

		Assert.assertTrue(
			"No user found with user ID " + user2.getUserId(), success);
	}

	@Test
	public void testGetNoContacts() throws Exception {
		User user = UserTestUtil.addUser();

		ContactLocalServiceUtil.deleteContact(user.getContactId());

		List<User> users = UserLocalServiceUtil.getNoContacts();

		Assert.assertEquals(1, users.size());
		Assert.assertEquals(user, users.get(0));
	}

	@Test
	public void testGetNoGroups() throws Exception {
		User user = UserTestUtil.addUser();

		GroupLocalServiceUtil.deleteGroup(user.getGroupId());

		List<User> users = UserLocalServiceUtil.getNoGroups();

		Assert.assertEquals(1, users.size());
		Assert.assertEquals(user, users.get(0));
	}

}