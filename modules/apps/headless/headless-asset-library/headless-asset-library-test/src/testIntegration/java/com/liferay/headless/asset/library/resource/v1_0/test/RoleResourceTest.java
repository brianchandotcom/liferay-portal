/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.asset.library.client.dto.v1_0.Role;
import com.liferay.headless.asset.library.client.pagination.Page;
import com.liferay.headless.asset.library.client.problem.Problem;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class RoleResourceTest extends BaseRoleResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_userGroup = UserGroupTestUtil.addUserGroup();

		_userGroupLocalService.addGroupUserGroup(
			testDepotEntry.getGroupId(), _userGroup);
	}

	@Override
	@Test
	public void testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage()
		throws Exception {

		User user = TestPropsValues.getUser();

		Role randomRole1 = randomRole();

		roleResource.
			putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage(
				testDepotEntryGroup.getExternalReferenceCode(),
				user.getExternalReferenceCode(), new Role[] {randomRole1});

		_assertGetAssetLibraryUserAccountRolesPage(Arrays.asList(randomRole1));

		Role randomRole2 = randomRole();

		roleResource.
			putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage(
				testDepotEntryGroup.getExternalReferenceCode(),
				user.getExternalReferenceCode(),
				new Role[] {randomRole1, randomRole2});

		_assertGetAssetLibraryUserAccountRolesPage(
			Arrays.asList(randomRole1, randomRole2));

		Role randomRole3 = new Role() {
			{
				name = RandomTestUtil.randomString();
			}
		};

		try {
			roleResource.
				putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage(
					testDepotEntryGroup.getExternalReferenceCode(),
					user.getExternalReferenceCode(), new Role[] {randomRole3});

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}

		_assertGetAssetLibraryUserAccountRolesPage(
			Arrays.asList(randomRole1, randomRole2));
	}

	@Override
	@Test
	public void testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage()
		throws Exception {

		Role randomRole1 = randomRole();

		roleResource.
			putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage(
				testDepotEntryGroup.getExternalReferenceCode(),
				_userGroup.getExternalReferenceCode(),
				new Role[] {randomRole1});

		_assertGetAssetLibraryUserGroupRolesPage(Arrays.asList(randomRole1));

		Role randomRole2 = randomRole();

		roleResource.
			putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage(
				testDepotEntryGroup.getExternalReferenceCode(),
				_userGroup.getExternalReferenceCode(),
				new Role[] {randomRole1, randomRole2});

		_assertGetAssetLibraryUserGroupRolesPage(
			Arrays.asList(randomRole1, randomRole2));

		Role randomRole3 = new Role() {
			{
				name = RandomTestUtil.randomString();
			}
		};

		try {
			roleResource.
				putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage(
					testDepotEntryGroup.getExternalReferenceCode(),
					_userGroup.getExternalReferenceCode(),
					new Role[] {randomRole3});

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
		}

		_assertGetAssetLibraryUserGroupRolesPage(
			Arrays.asList(randomRole1, randomRole2));
	}

	@Override
	@Test
	public void testPutAssetLibraryUserAccountRolesPage() throws Exception {
		Role randomRole1 = randomRole();

		roleResource.putAssetLibraryUserAccountRolesPage(
			testDepotEntry.getDepotEntryId(), TestPropsValues.getUserId(),
			new Role[] {randomRole1});

		_assertGetAssetLibraryUserAccountRolesPage(Arrays.asList(randomRole1));

		Role randomRole2 = randomRole();

		roleResource.putAssetLibraryUserAccountRolesPage(
			testDepotEntry.getDepotEntryId(), TestPropsValues.getUserId(),
			new Role[] {randomRole1, randomRole2});

		_assertGetAssetLibraryUserAccountRolesPage(
			Arrays.asList(randomRole1, randomRole2));

		Role randomRole3 = new Role() {
			{
				name = RandomTestUtil.randomString();
			}
		};

		try {
			roleResource.putAssetLibraryUserAccountRolesPage(
				testDepotEntry.getDepotEntryId(), TestPropsValues.getUserId(),
				new Role[] {randomRole3});

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}

		_assertGetAssetLibraryUserAccountRolesPage(
			Arrays.asList(randomRole1, randomRole2));
	}

	@Override
	@Test
	public void testPutAssetLibraryUserGroupRolesPage() throws Exception {
		Role randomRole1 = randomRole();

		roleResource.putAssetLibraryUserGroupRolesPage(
			testDepotEntryGroup.getGroupId(), _userGroup.getUserGroupId(),
			new Role[] {randomRole1});

		_assertGetAssetLibraryUserGroupRolesPage(Arrays.asList(randomRole1));

		Role randomRole2 = randomRole();

		roleResource.putAssetLibraryUserGroupRolesPage(
			testDepotEntryGroup.getGroupId(), _userGroup.getUserGroupId(),
			new Role[] {randomRole1, randomRole2});

		_assertGetAssetLibraryUserGroupRolesPage(
			Arrays.asList(randomRole1, randomRole2));

		Role randomRole3 = new Role() {
			{
				name = RandomTestUtil.randomString();
			}
		};

		try {
			roleResource.putAssetLibraryUserGroupRolesPage(
				testDepotEntryGroup.getGroupId(), _userGroup.getUserGroupId(),
				new Role[] {randomRole3});

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
		}

		_assertGetAssetLibraryUserGroupRolesPage(
			Arrays.asList(randomRole1, randomRole2));
	}

	@Override
	protected Role randomRole() throws Exception {
		long roleId = RoleTestUtil.addGroupRole(testGroup.getGroupId());

		com.liferay.portal.kernel.model.Role role = _roleLocalService.fetchRole(
			roleId);

		_roles.add(role);

		return new Role() {
			{
				externalReferenceCode = role.getExternalReferenceCode();
				id = role.getRoleId();
				name = role.getName();
				roleType = role.getType();
			}
		};
	}

	@Override
	protected Role
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage_addRole(
				String assetLibraryExternalReferenceCode,
				String userAccountExternalReferenceCode, Role role)
		throws Exception {

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, TestPropsValues.getCompanyId());
		User user = _userLocalService.getUserByExternalReferenceCode(
			userAccountExternalReferenceCode, TestPropsValues.getCompanyId());

		_userGroupRoleService.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {role.getId()});

		return role;
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage_getUserAccountExternalReferenceCode()
		throws Exception {

		User user = TestPropsValues.getUser();

		return user.getExternalReferenceCode();
	}

	@Override
	protected Role
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage_addRole(
				String assetLibraryExternalReferenceCode,
				String userGroupExternalReferenceCode, Role role)
		throws Exception {

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, TestPropsValues.getCompanyId());
		UserGroup userGroup =
			_userGroupLocalService.getUserGroupByExternalReferenceCode(
				userGroupExternalReferenceCode, TestPropsValues.getCompanyId());

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroup.getUserGroupId(), group.getGroupId(),
			new long[] {role.getId()});

		return role;
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserGroupByExternalReferenceCodeUserGroupExternalReferenceCodeRolesPage_getUserGroupExternalReferenceCode()
		throws Exception {

		return _userGroup.getExternalReferenceCode();
	}

	@Override
	protected Role testGetAssetLibraryUserAccountRolesPage_addRole(
			Long assetLibraryId, Long userAccountId, Role role)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			assetLibraryId);

		_userGroupRoleService.addUserGroupRoles(
			userAccountId, depotEntry.getGroupId(), new long[] {role.getId()});

		return role;
	}

	@Override
	protected Long testGetAssetLibraryUserAccountRolesPage_getUserAccountId()
		throws Exception {

		return TestPropsValues.getUserId();
	}

	@Override
	protected Role testGetAssetLibraryUserGroupRolesPage_addRole(
		Long assetLibraryId, Long userGroupId, Role role)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			assetLibraryId);

		_userGroupGroupRoleLocalService.addUserGroupGroupRoles(
			userGroupId, depotEntry.getGroupId(), new long[] {role.getId()});

		return role;
	}

	@Override
	protected Long testGetAssetLibraryUserGroupRolesPage_getUserGroupId()
		throws Exception {

		return _userGroup.getUserGroupId();
	}

	private void _assertGetAssetLibraryUserAccountRolesPage(
			List<Role> expectedRoles)
		throws Exception {

		Page<Role> rolesPage = roleResource.getAssetLibraryUserAccountRolesPage(
			testDepotEntry.getDepotEntryId(), TestPropsValues.getUserId());

		Collection<Role> items = rolesPage.getItems();

		Assert.assertEquals(
			items.toString(), expectedRoles.size(), items.size());

		for (Role role : expectedRoles) {
			Assert.assertTrue(items.contains(role));
		}
	}

	private void _assertGetAssetLibraryUserGroupRolesPage(
			List<Role> expectedRoles)
		throws Exception {

		Page<Role> rolesPage = roleResource.getAssetLibraryUserGroupRolesPage(
			testDepotEntry.getDepotEntryId(), _userGroup.getUserGroupId());

		Collection<Role> items = rolesPage.getItems();

		Assert.assertEquals(
			items.toString(), expectedRoles.size(), items.size());

		for (Role role : expectedRoles) {
			Assert.assertTrue(items.contains(role));
		}
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private List<com.liferay.portal.kernel.model.Role> _roles =
		new ArrayList<>();

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserGroupRoleService _userGroupRoleService;

	@Inject
	private UserLocalService _userLocalService;

}