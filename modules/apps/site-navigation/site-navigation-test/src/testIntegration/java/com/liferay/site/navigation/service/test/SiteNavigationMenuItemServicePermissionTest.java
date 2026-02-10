/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuItemTestUtil;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

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

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		_permissionChecker = PermissionCheckerFactoryUtil.create(_user);

		_siteNavigationMenu = SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group);

		_siteNavigationMenuItem =
			SiteNavigationMenuItemTestUtil.addSiteNavigationMenuItem(
				_siteNavigationMenu);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSiteNavigationMenuItemWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				null, _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
	}

	@Test
	public void testAddSiteNavigationMenuItemWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.addSiteNavigationMenuItem(
				null, _group.getGroupId(),
				_siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSiteNavigationMenuItemsWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());
		}
	}

	@Test
	public void testDeleteSiteNavigationMenuItemsWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSiteNavigationMenuItemWithDeleteChildrenWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(), true);
		}
	}

	@Test
	public void testDeleteSiteNavigationMenuItemWithDeleteChildrenWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(), true);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSiteNavigationMenuItemWithExternalReferenceCodeWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getExternalReferenceCode(),
				_siteNavigationMenuItem.getGroupId());
		}
	}

	@Test
	public void testDeleteSiteNavigationMenuItemWithExternalReferenceCodeWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getExternalReferenceCode(),
				_siteNavigationMenuItem.getGroupId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSiteNavigationMenuItemWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}
	}

	@Test
	public void testDeleteSiteNavigationMenuItemWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.deleteSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetParentSiteNavigationMenuItemIdsWithoutPermissions()
		throws Exception {

		_removeViewResourcePermission();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getParentSiteNavigationMenuItemIds(
				_siteNavigationMenu.getSiteNavigationMenuId(),
				RandomTestUtil.randomString());
		}
	}

	@Test
	public void testGetParentSiteNavigationMenuItemIdsWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getParentSiteNavigationMenuItemIds(
				_siteNavigationMenu.getSiteNavigationMenuId(),
				RandomTestUtil.randomString());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSiteNavigationMenuItemByExternalReferenceCodeWithoutPermissions()
		throws Exception {

		_removeViewResourcePermission();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.
				getSiteNavigationMenuItemByExternalReferenceCode(
					_siteNavigationMenuItem.getExternalReferenceCode(),
					_siteNavigationMenuItem.getGroupId());
		}
	}

	@Test
	public void testGetSiteNavigationMenuItemByExternalReferenceCodeWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.
				getSiteNavigationMenuItemByExternalReferenceCode(
					_siteNavigationMenuItem.getExternalReferenceCode(),
					_siteNavigationMenuItem.getGroupId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSiteNavigationMenuItemsWithOrderByComparatorWithoutPermissions()
		throws Exception {

		_removeViewResourcePermission();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(), null);
		}
	}

	@Test
	public void testGetSiteNavigationMenuItemsWithOrderByComparatorWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(), null);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSiteNavigationMenuItemsWithoutPermissions()
		throws Exception {

		_removeViewResourcePermission();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemIdWithoutPermissions()
		throws Exception {

		_removeViewResourcePermission();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(), 0);
		}
	}

	@Test
	public void testGetSiteNavigationMenuItemsWithParentSiteNavigationMenuItemIdWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId(), 0);
		}
	}

	@Test
	public void testGetSiteNavigationMenuItemsWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.getSiteNavigationMenuItems(
				_siteNavigationMenu.getSiteNavigationMenuId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSiteNavigationMenuItemWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);
		}
	}

	@Test
	public void testUpdateSiteNavigationMenuItemWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(), 0);
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSiteNavigationMenuItemWithTypeSettingsWithoutPermissions()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				_siteNavigationMenuItem.getTypeSettings(),
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test
	public void testUpdateSiteNavigationMenuItemWithTypeSettingsWithPermissions()
		throws Exception {

		_addResourcePermission(ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, _permissionChecker)) {

			_siteNavigationMenuItemService.updateSiteNavigationMenuItem(
				_siteNavigationMenuItem.getSiteNavigationMenuItemId(),
				_siteNavigationMenuItem.getTypeSettings(),
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	private void _addResourcePermission(String actionId) throws Exception {
		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), SiteNavigationMenu.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), actionId);
	}

	private void _removeViewResourcePermission() throws Exception {
		for (Role role :
				_roleLocalService.getRoles(TestPropsValues.getCompanyId())) {

			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			_resourcePermissionLocalService.removeResourcePermission(
				TestPropsValues.getCompanyId(),
				SiteNavigationMenu.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_siteNavigationMenu.getSiteNavigationMenuId()),
				role.getRoleId(), ActionKeys.VIEW);
		}
	}

	private static PermissionChecker _permissionChecker;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private SiteNavigationMenu _siteNavigationMenu;
	private SiteNavigationMenuItem _siteNavigationMenuItem;

	@Inject
	private SiteNavigationMenuItemService _siteNavigationMenuItemService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@DeleteAfterTestRun
	private User _user;

}