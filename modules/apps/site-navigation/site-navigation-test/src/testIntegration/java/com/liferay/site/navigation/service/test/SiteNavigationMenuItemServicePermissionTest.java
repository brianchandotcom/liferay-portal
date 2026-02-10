/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuItemServicePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_guestUser = _userLocalService.getGuestUser(
			TestPropsValues.getCompanyId());

		_originalName = PrincipalThreadLocal.getName();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, SiteNavigationMenu.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_siteNavigationMenu.getSiteNavigationMenuId()),
			ActionKeys.VIEW);

		_siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddSiteNavigationMenuItem() throws Exception {
		try {
			_testAddSiteNavigationMenuItem(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddSiteNavigationMenuItem(_user);
	}

	@Test
	public void testDeleteSiteNavigationMenuItem() throws Exception {
		try {
			_testDeleteSiteNavigationMenuItem(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteSiteNavigationMenuItem(_user);
	}

	@Test
	public void testDeleteSiteNavigationMenuItems() throws Exception {
		try {
			_testDeleteSiteNavigationMenuItems(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteSiteNavigationMenuItems(_user);
	}

	@Test
	public void testDeleteSiteNavigationMenuItemWithDeleteChildren()
		throws Exception {

		try {
			_testDeleteSiteNavigationMenuItemWithDeleteChildren(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteSiteNavigationMenuItemWithDeleteChildren(_user);
	}

	@Test
	public void testDeleteSiteNavigationMenuItemWithExternalReferenceCode()
		throws Exception {

		try {
			_testDeleteSiteNavigationMenuItemWithExternalReferenceCode(
				_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteSiteNavigationMenuItemWithExternalReferenceCode(_user);
	}

	@Test
	public void testGetParentSiteNavigationMenuItemIds() throws Exception {
		try {
			_testGetParentSiteNavigationMenuItemIds(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetParentSiteNavigationMenuItemIds(_user);
	}

	@Test
	public void testGetSiteNavigationMenuItemByExternalReferenceCode()
		throws Exception {

		try {
			_testGetSiteNavigationMenuItemByExternalReferenceCode(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetSiteNavigationMenuItemByExternalReferenceCode(_user);
	}

	@Test
	public void testGetSiteNavigationMenuItems() throws Exception {
		try {
			_testGetSiteNavigationMenuItems(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetSiteNavigationMenuItems(_user);
	}

	@Test
	public void testGetSiteNavigationMenuItemsWithOrderByComparator()
		throws Exception {

		try {
			_testGetSiteNavigationMenuItemsWithOrderByComparator(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetSiteNavigationMenuItemsWithOrderByComparator(_user);
	}

	@Test
	public void testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemId()
		throws Exception {

		try {
			_testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemId(
				_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemId(
			_user);
	}

	@Test
	public void testUpdateSiteNavigationMenuItem() throws Exception {
		try {
			_testUpdateSiteNavigationMenuItem(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateSiteNavigationMenuItem(_user);
	}

	@Test
	public void testUpdateSiteNavigationMenuItemWithTypeSettings()
		throws Exception {

		try {
			_testUpdateSiteNavigationMenuItemWithTypeSettings(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateSiteNavigationMenuItemWithTypeSettings(_user);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddSiteNavigationMenuItem(User user) throws Exception {
		SiteNavigationMenuItem siteNavigationMenuItem = null;

		try {
			_setUser(user);

			siteNavigationMenuItem =
				_siteNavigationMenuItemService.addSiteNavigationMenuItem(
					null, _group.getGroupId(),
					_siteNavigationMenu.getSiteNavigationMenuId(), 0,
					SiteNavigationMenuItemTypeConstants.LAYOUT,
					StringPool.BLANK,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));
		}
		finally {
			if (siteNavigationMenuItem != null) {
				_siteNavigationMenuItemLocalService.
					deleteSiteNavigationMenuItem(siteNavigationMenuItem);
			}
		}
	}

	private void _testDeleteSiteNavigationMenuItem(User user) throws Exception {
		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			_siteNavigationMenuItem.getSiteNavigationMenuItemId());
	}

	private void _testDeleteSiteNavigationMenuItems(User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId());
	}

	private void _testDeleteSiteNavigationMenuItemWithDeleteChildren(User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			_siteNavigationMenuItem.getSiteNavigationMenuItemId(), true);
	}

	private void _testDeleteSiteNavigationMenuItemWithExternalReferenceCode(
			User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			_siteNavigationMenuItem.getExternalReferenceCode(),
			_siteNavigationMenuItem.getGroupId());
	}

	private void _testGetParentSiteNavigationMenuItemIds(User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.getParentSiteNavigationMenuItemIds(
			_siteNavigationMenu.getSiteNavigationMenuId(),
			RandomTestUtil.randomString());
	}

	private void _testGetSiteNavigationMenuItemByExternalReferenceCode(
			User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.
			getSiteNavigationMenuItemByExternalReferenceCode(
				_siteNavigationMenuItem.getExternalReferenceCode(),
				_siteNavigationMenuItem.getGroupId());
	}

	private void _testGetSiteNavigationMenuItems(User user) throws Exception {
		_setUser(user);

		_siteNavigationMenuItemService.getSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId());
	}

	private void _testGetSiteNavigationMenuItemsWithOrderByComparator(User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.getSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId(), null);
	}

	private void
			_testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemId(
				User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.getSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId(), 0);
	}

	private void _testUpdateSiteNavigationMenuItem(User user) throws Exception {
		_setUser(user);

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			_siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);
	}

	private void _testUpdateSiteNavigationMenuItemWithTypeSettings(User user)
		throws Exception {

		_setUser(user);

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			_siteNavigationMenuItem.getTypeSettings(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _guestUser;
	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private SiteNavigationMenu _siteNavigationMenu;
	private SiteNavigationMenuItem _siteNavigationMenuItem;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}