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

package com.liferay.organizations.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.OrganizationFinder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Minhchau Dang
 */
@RunWith(Arquillian.class)
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

		_groupLocalService.addUserGroup(_user.getUserId(), _group);

		_organizationLocalService.addGroupOrganization(
			_group.getGroupId(), _organization);

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), _organization);
	}

	@Test
	public void testCountByKeywordsWithDifferentParameterOrder() {
		int count1 = _organizationFinder.countO_ByKeywords(
			_user.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			StringPool.NOT_EQUAL, null, null, null, null,
			LinkedHashMapBuilder.<String, Object>put(
				"usersOrgs", _user.getUserId()
			).put(
				"groupOrganization", _group.getGroupId()
			).build());

		Assert.assertEquals(1, count1);

		int count2 = _organizationFinder.countO_ByKeywords(
			_user.getCompanyId(),
			OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			StringPool.NOT_EQUAL, null, null, null, null,
			LinkedHashMapBuilder.<String, Object>put(
				"groupOrganization", _group.getGroupId()
			).put(
				"usersOrgs", _user.getUserId()
			).build());

		Assert.assertEquals(count1, count2);
	}

	@Test
	public void testSearchOrganizationsWithOrganizationsTreeParameter()
		throws Exception {

		testSearchOrganizationsWithOrganizationsTreeParameter(false, false);
	}

	@Test
	public void testSearchOrganizationsWithOrganizationsTreeParameterAsAdminUser()
		throws Exception {

		testSearchOrganizationsWithOrganizationsTreeParameter(true, true);
	}

	@Test
	public void testSearchOrganizationsWithOrganizationsTreeParameterAsUser()
		throws Exception {

		testSearchOrganizationsWithOrganizationsTreeParameter(true, false);
	}

	protected void testSearchOrganizationsWithOrganizationsTreeParameter(
			boolean searchAsUser, boolean searchAsAdminUser)
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);

		OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_user = UserTestUtil.addUser();

		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user.getUserId()});

		if (searchAsAdminUser) {
			_user = UserTestUtil.addOrganizationAdminUser(organization);
		}

		if (searchAsUser) {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(_user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}

		LinkedHashMap<String, Object> organizationParams =
			LinkedHashMapBuilder.<String, Object>put(
				"organizationsTree", _user.getOrganizations(true)
			).build();

		List<Organization> finderSearchResults =
			_organizationLocalService.search(
				_user.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null, null,
				null, null, organizationParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (searchAsUser && searchAsAdminUser) {
			Assert.assertEquals(
				finderSearchResults.toString(), 2, finderSearchResults.size());
		}
		else {
			Assert.assertEquals(
				finderSearchResults.toString(), 1, finderSearchResults.size());
		}
	}

	@DeleteAfterTestRun
	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private static Organization _organization;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	@DeleteAfterTestRun
	private static User _user;

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private OrganizationFinder _organizationFinder;

}