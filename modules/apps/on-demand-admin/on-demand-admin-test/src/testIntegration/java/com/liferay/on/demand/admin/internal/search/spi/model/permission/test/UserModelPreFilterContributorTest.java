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

package com.liferay.on.demand.admin.internal.search.spi.model.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.test.util.OnDemandAdminTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class UserModelPreFilterContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchUsers() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		UserTestUtil.addUser(company);
		UserTestUtil.addUser(company);

		List<User> onDemandAdminUsers = Arrays.asList(
			OnDemandAdminTestUtil.addOnDemandAdminUser(company),
			OnDemandAdminTestUtil.addOnDemandAdminUser(company));

		long[] onDemandAdminUserIds = ListUtil.toLongArray(
			onDemandAdminUsers, User.USER_ID_ACCESSOR);

		BaseModelSearchResult<User> baseModelSearchResult =
			_userLocalService.searchUsers(
				company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
				new LinkedHashMap<String, Object>(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new Sort("userId", false));

		Assert.assertEquals(
			_userLocalService.getCompanyUsersCount(company.getCompanyId()) -
				onDemandAdminUserIds.length,
			baseModelSearchResult.getLength());

		List<User> users = baseModelSearchResult.getBaseModels();

		for (User user : users) {
			Assert.assertFalse(
				ArrayUtil.contains(onDemandAdminUserIds, user.getUserId()));
		}
	}

	@Inject
	private UserLocalService _userLocalService;

}