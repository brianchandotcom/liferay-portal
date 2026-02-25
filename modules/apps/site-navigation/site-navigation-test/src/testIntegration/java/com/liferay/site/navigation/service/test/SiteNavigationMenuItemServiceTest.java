/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.navigation.exception.DuplicateSiteNavigationMenuItemExternalReferenceCodeException;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;
import com.liferay.site.navigation.test.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuItemServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.site.navigation.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_guestUser = _userLocalService.getGuestUser(
			TestPropsValues.getCompanyId());

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);

		_siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_user = TestPropsValues.getUser();

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, SiteNavigationMenu.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_siteNavigationMenu.getSiteNavigationMenuId()),
			ActionKeys.VIEW);
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

		AssertUtils.assertFailure(
			DuplicateSiteNavigationMenuItemExternalReferenceCodeException.class,
			StringBundler.concat(
				"Duplicate site navigation menu item with external reference ",
				"code ", _siteNavigationMenuItem.getExternalReferenceCode(),
				" and group ", _group.getGroupId()),
			() -> _siteNavigationMenuItemService.addSiteNavigationMenuItem(
				_siteNavigationMenuItem.getExternalReferenceCode(),
				_group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId())));
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

		try {
			_testDeleteSiteNavigationMenuItemByExternalReferenceCode(
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

		_testDeleteSiteNavigationMenuItemByExternalReferenceCode(_user);
	}

	@Test
	public void testDeleteSiteNavigationMenuItems() throws Exception {
		try {
			_testDeleteSiteNavigationMenuItemsBySiteNavigationMenuId(
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

		_testDeleteSiteNavigationMenuItemsBySiteNavigationMenuId(_user);
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
	public void testUpdateSiteNavigationMenuItem() throws Exception {
		try {
			_testUpdateSiteNavigationMenuItemOrder(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateSiteNavigationMenuItemOrder(_user);

		try {
			_testUpdateSiteNavigationMenuItemParent(_guestUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _guestUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateSiteNavigationMenuItemParent(_user);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddSiteNavigationMenuItem(User user)
		throws PortalException {

		_setUser(user);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				null, _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SiteNavigationMenuItem persistedSiteNavigationMenuItem =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			siteNavigationMenuItem, persistedSiteNavigationMenuItem);
	}

	private void _testDeleteSiteNavigationMenuItem(User user)
		throws PortalException {

		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			_siteNavigationMenuItem.getSiteNavigationMenuItemId());

		Assert.assertNull(
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId()));
	}

	private void _testDeleteSiteNavigationMenuItemByExternalReferenceCode(
			User user)
		throws Exception {

		_setUser(user);

		String externalReferenceCode = StringUtil.randomString();

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				externalReferenceCode, _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
			externalReferenceCode, _group.getGroupId());

		Assert.assertNull(
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				siteNavigationMenuItem.getSiteNavigationMenuItemId()));
	}

	private void _testDeleteSiteNavigationMenuItemsBySiteNavigationMenuId(
			User user)
		throws PortalException {

		_setUser(user);

		_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(
			_siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			0,
			_siteNavigationMenuItemPersistence.countBySiteNavigationMenuId(
				_siteNavigationMenu.getSiteNavigationMenuId()));
	}

	private void _testGetSiteNavigationMenuItems(User user)
		throws PortalException {

		_setUser(user);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());

		Assert.assertEquals(
			siteNavigationMenuItems.toString(), 1,
			siteNavigationMenuItems.size());
	}

	private void _testUpdateSiteNavigationMenuItemOrder(User user)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu, 0);

		SiteNavigationMenuItem childSiteNavigationMenuItem1 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		SiteNavigationMenuItem childSiteNavigationMenuItem2 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem.getSiteNavigationMenuItemId());

		_setUser(user);

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem1 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem1.getSiteNavigationMenuItemId());

		Assert.assertEquals(1, childSiteNavigationMenuItem1.getOrder());
	}

	private void _testUpdateSiteNavigationMenuItemParent(User user)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem1 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		SiteNavigationMenuItem childSiteNavigationMenuItem11 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu,
				siteNavigationMenuItem1.getSiteNavigationMenuItemId());

		SiteNavigationMenuItem siteNavigationMenuItem2 =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);

		_setUser(user);

		_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
			childSiteNavigationMenuItem11.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(), 0);

		childSiteNavigationMenuItem11 =
			_siteNavigationMenuItemPersistence.fetchByPrimaryKey(
				childSiteNavigationMenuItem11.getSiteNavigationMenuItemId());

		Assert.assertEquals(
			siteNavigationMenuItem2.getSiteNavigationMenuItemId(),
			childSiteNavigationMenuItem11.getParentSiteNavigationMenuItemId());
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _guestUser;
	private SiteNavigationMenu _siteNavigationMenu;
	private SiteNavigationMenuItem _siteNavigationMenuItem;

	@Inject
	private SiteNavigationMenuItemPersistence
		_siteNavigationMenuItemPersistence;

	@Inject
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}