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

package com.liferay.portal.service.persistence;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.OrganizationFinderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Minhchau Dang
 */
public class OrganizationFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_group = GroupTestUtil.addGroup();
		_organization = OrganizationTestUtil.addOrganization();
		_user = UserTestUtil.addUser();

		GroupLocalServiceUtil.addUserGroup(_user.getUserId(), _group);

		OrganizationLocalServiceUtil.addGroupOrganization(
			_group.getGroupId(), _organization);

		OrganizationLocalServiceUtil.addUserOrganization(
			_user.getUserId(), _organization);
	}

	@Test
	public void testCountByKeywordsWithDifferentParameterOrder() {
		LinkedHashMap<String, Object> params1 = new LinkedHashMap<>();

		params1.put("usersOrgs", _user.getUserId());
		params1.put("groupOrganization", _group.getGroupId());

		int count1 = OrganizationFinderUtil.countO_ByKeywords(
			_user.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			StringPool.NOT_EQUAL, null, null, null, null, params1);

		Assert.assertEquals(1, count1);

		LinkedHashMap<String, Object> params2 = new LinkedHashMap<>();

		params2.put("groupOrganization", _group.getGroupId());
		params2.put("usersOrgs", _user.getUserId());

		int count2 = OrganizationFinderUtil.countO_ByKeywords(
			_user.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			StringPool.NOT_EQUAL, null, null, null, null, params2);

		Assert.assertEquals(count1, count2);
	}

	@DeleteAfterTestRun
	private static Group _group;

	@DeleteAfterTestRun
	private static Organization _organization;

	@DeleteAfterTestRun
	private static User _user;

}