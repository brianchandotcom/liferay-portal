/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Huy Le Nguyen
 */
@RunWith(Arquillian.class)
public class UserServiceWhenAddingDefaultGroupTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_parentGroup = GroupTestUtil.addGroupToCompany(_company.getCompanyId());

		_childGroup = GroupTestUtil.addGroupToCompany(
			_company.getCompanyId(), _parentGroup.getGroupId());

		_childGroup.setMembershipRestriction(
			GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

		_childGroup = _groupLocalService.updateGroup(_childGroup);

		_grandChildGroup1 = GroupTestUtil.addGroupToCompany(
			_company.getCompanyId(), _childGroup.getGroupId());

		_grandChildGroup1.setMembershipRestriction(
			GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS);

		_grandChildGroup1 = _groupLocalService.updateGroup(_grandChildGroup1);

		_grandChildGroup2 = GroupTestUtil.addGroupToCompany(
			_company.getCompanyId(), _childGroup.getGroupId());

		_user = UserTestUtil.addUser(_company);

		_companyLocalService.updatePreferences(
			_company.getCompanyId(),
			UnicodePropertiesBuilder.put(
				PropsKeys.ADMIN_DEFAULT_GROUP_NAMES,
				StringBundler.concat(
					_parentGroup.getName(LocaleUtil.US), StringPool.NEW_LINE,
					_grandChildGroup2.getName(LocaleUtil.US),
					StringPool.NEW_LINE,
					_grandChildGroup1.getName(LocaleUtil.US))
			).build());
	}

	@After
	public void tearDown() throws PortalException {
		_userLocalService.deleteUser(_user);

		_groupLocalService.deleteGroup(_grandChildGroup1);

		_groupLocalService.deleteGroup(_grandChildGroup2);

		_groupLocalService.deleteGroup(_childGroup);

		_groupLocalService.deleteGroup(_parentGroup);

		_companyLocalService.deleteCompany(_company);
	}

	@Test
	public void testAddDefaultGroup() throws Exception {
		_userLocalService.addDefaultGroups(_user.getUserId());

		Assert.assertTrue(
			ArrayUtil.contains(_user.getGroupIds(), _parentGroup.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.contains(
				_user.getGroupIds(), _grandChildGroup2.getGroupId()));

		Assert.assertFalse(
			ArrayUtil.contains(_user.getGroupIds(), _childGroup.getGroupId()));

		Assert.assertFalse(
			ArrayUtil.contains(
				_user.getGroupIds(), _grandChildGroup1.getGroupId()));
	}

	private Group _childGroup;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _grandChildGroup1;
	private Group _grandChildGroup2;

	@Inject
	private GroupLocalService _groupLocalService;

	private Group _parentGroup;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}