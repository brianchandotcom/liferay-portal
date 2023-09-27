/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.on.demand.admin.test.util.OnDemandAdminTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCleanUpOnDemandAdminUsers() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user1 = OnDemandAdminTestUtil.addOnDemandAdminUser(company);

		_assertOnDemandAdminUser(user1);

		User user2 = OnDemandAdminTestUtil.addOnDemandAdminUser(company);

		_assertOnDemandAdminUser(user2);

		_onDemandAdminManager.cleanUpOnDemandAdminUsers(
			new Date(System.currentTimeMillis()));

		_assertDeleted(user1.getUserId());
		_assertDeleted(user2.getUserId());
	}

	private void _assertDeleted(long userId) {
		Assert.assertNull(_userLocalService.fetchUser(userId));
	}

	private void _assertOnDemandAdminUser(User user) {
		Assert.assertNotNull(_userLocalService.fetchUser(user.getUserId()));
		Assert.assertTrue(_onDemandAdminManager.isOnDemandAdminUser(user));
	}

	@Inject
	private OnDemandAdminManager _onDemandAdminManager;

	@Inject
	private UserLocalService _userLocalService;

}