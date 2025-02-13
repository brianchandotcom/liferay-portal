/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.data.control.tasks;

import com.liferay.analytics.settings.data.control.tasks.UsersDataControlTasks;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcos Martins
 */
public class UsersDataControlTasksTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetEmailAddresses() {
		UsersDataControlTasks usersDataControlTasks =
			new UsersDataControlTasksImpl();

		usersDataControlTasks.addEmailAddress(1, "test1@liferay.com");
		usersDataControlTasks.addEmailAddress(1, "test2@liferay.com");
		usersDataControlTasks.addEmailAddress(2, "test3@liferay.com");

		Set<String> emailAddresses = usersDataControlTasks.getEmailAddresses(1);

		Assert.assertEquals(
			emailAddresses.toString(), 2, emailAddresses.size());

		emailAddresses = usersDataControlTasks.getEmailAddresses(2);

		Assert.assertEquals(
			emailAddresses.toString(), 1, emailAddresses.size());

		emailAddresses = usersDataControlTasks.getEmailAddresses(3);

		Assert.assertTrue(emailAddresses.isEmpty());

		usersDataControlTasks.clean(1);

		emailAddresses = usersDataControlTasks.getEmailAddresses(1);

		Assert.assertTrue(emailAddresses.isEmpty());
	}

}