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

package com.liferay.site.navigation.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuService;
import com.liferay.site.navigation.util.SiteNavigationMenuTestUtil;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuCreateDateComparator;
import com.liferay.site.navigation.util.comparator.SiteNavigationMenuNameComparator;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
public class SiteNavigationMenuServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		ServiceTestUtil.setUser(_user);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSiteNavigationMenuWithoutPermissions1()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.addSiteNavigationMenu(
			_group.getGroupId(), RandomTestUtil.randomString(),
			SiteNavigationConstants.TYPE_DEFAULT, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSiteNavigationMenuWithoutPermissions2()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.addSiteNavigationMenu(
			_group.getGroupId(), RandomTestUtil.randomString(), serviceContext);
	}

	@Test
	public void testAddSiteNavigationMenuWithPermissions1() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation",
			SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.addSiteNavigationMenu(
			_group.getGroupId(), RandomTestUtil.randomString(),
			SiteNavigationConstants.TYPE_DEFAULT, serviceContext);
	}

	@Test
	public void testAddSiteNavigationMenuWithPermissions2() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation",
			SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.addSiteNavigationMenu(
			_group.getGroupId(), RandomTestUtil.randomString(), serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSiteNavigationMenuWithoutPermissions()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _user.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.deleteSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());
	}

	@Test
	public void testDeleteSiteNavigationMenuWithPermissions() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation.model.SiteNavigationMenu",
			ActionKeys.DELETE);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.deleteSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());
	}

	@Test
	public void testFetchSiteNavigationMenuWithPermissions() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation.model.SiteNavigationMenu",
			ActionKeys.VIEW);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.fetchSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId());
	}

	@Test
	public void testGetSiteNavigationMenuByCreateDateComparatorAndKeywordsAsc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, Timestamp.valueOf(localDateTime), "CC Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "BB");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "AA Name");

		SiteNavigationMenuCreateDateComparator
			siteNavigationMenuCreateDateComparator =
				new SiteNavigationMenuCreateDateComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), "Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, siteNavigationMenuCreateDateComparator);

		SiteNavigationMenu firstSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(siteNavigationMenu, firstSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenuByCreateDateComparatorAndKeywordsDesc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, Timestamp.valueOf(localDateTime), "CC Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "BB");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "AA Name");

		SiteNavigationMenuCreateDateComparator
			siteNavigationMenuCreateDateComparator =
				new SiteNavigationMenuCreateDateComparator(false);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), "Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, siteNavigationMenuCreateDateComparator);

		SiteNavigationMenu lastSiteNavigationMenu = siteNavigationMenus.get(
			siteNavigationMenus.size() - 1);

		Assert.assertEquals(siteNavigationMenu, lastSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenus() throws Exception {
		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId());

		int originalSiteNavigationMenusCount = siteNavigationMenus.size();

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		siteNavigationMenus = _siteNavigationMenuService.getSiteNavigationMenus(
			_group.getGroupId());

		int actualSiteNavigationMenusCount = siteNavigationMenus.size();

		Assert.assertEquals(
			originalSiteNavigationMenusCount + 2,
			actualSiteNavigationMenusCount);
	}

	@Test
	public void testGetSiteNavigationMenusByCreateDateComparatorAsc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, Timestamp.valueOf(localDateTime), "CC Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "BB Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "AA Name");

		SiteNavigationMenuCreateDateComparator
			siteNavigationMenuCreateDateComparator =
				new SiteNavigationMenuCreateDateComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				siteNavigationMenuCreateDateComparator);

		SiteNavigationMenu firstSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(siteNavigationMenu, firstSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusByCreateDateComparatorDesc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, Timestamp.valueOf(localDateTime), "CC Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "BB Name");

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(
			_group, Timestamp.valueOf(localDateTime), "AA Name");

		SiteNavigationMenuCreateDateComparator
			siteNavigationMenuCreateDateComparator =
				new SiteNavigationMenuCreateDateComparator(false);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				siteNavigationMenuCreateDateComparator);

		SiteNavigationMenu lastSiteNavigationMenu = siteNavigationMenus.get(
			siteNavigationMenus.size() - 1);

		Assert.assertEquals(siteNavigationMenu, lastSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusByNameComparatorAndKeywordsAsc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "BB Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "AA");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "CC Name");

		SiteNavigationMenuNameComparator siteNavigationMenuNameComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), "Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, siteNavigationMenuNameComparator);

		SiteNavigationMenu firstSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(siteNavigationMenu, firstSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusByNameComparatorAndKeywordsDesc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "BB Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "AA");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "CC Name");

		SiteNavigationMenuNameComparator siteNavigationMenuNameComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), "Name", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, siteNavigationMenuNameComparator);

		SiteNavigationMenu lastSiteNavigationMenu = siteNavigationMenus.get(
			siteNavigationMenus.size() - 1);

		Assert.assertEquals(siteNavigationMenu, lastSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusByNameComparatorAsc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "CC Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "BB Name");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "AA Name");

		SiteNavigationMenuNameComparator siteNavigationMenuNameComparator =
			new SiteNavigationMenuNameComparator(true);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				siteNavigationMenuNameComparator);

		SiteNavigationMenu lastSiteNavigationMenu = siteNavigationMenus.get(
			siteNavigationMenus.size() - 1);

		Assert.assertEquals(siteNavigationMenu, lastSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusByNameComparatorDesc()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "CC Name");

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "BB Name");
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group, "AA Name");

		SiteNavigationMenuNameComparator siteNavigationMenuNameComparator =
			new SiteNavigationMenuNameComparator(false);

		List<SiteNavigationMenu> siteNavigationMenus =
			_siteNavigationMenuService.getSiteNavigationMenus(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				siteNavigationMenuNameComparator);

		SiteNavigationMenu firstSiteNavigationMenu = siteNavigationMenus.get(0);

		Assert.assertEquals(siteNavigationMenu, firstSiteNavigationMenu);
	}

	@Test
	public void testGetSiteNavigationMenusCount() throws Exception {
		int originalSiteNavigationMenusCount =
			_siteNavigationMenuService.getSiteNavigationMenusCount(
				_group.getGroupId());

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		int actualSiteNavigationMenusCount =
			_siteNavigationMenuService.getSiteNavigationMenusCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalSiteNavigationMenusCount + 2,
			actualSiteNavigationMenusCount);
	}

	@Test
	public void testGetSiteNavigationMenusCountByKeywords() throws Exception {
		int originalSiteNavigationMenusCount =
			_siteNavigationMenuService.getSiteNavigationMenusCount(
				_group.getGroupId());

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);
		SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		int actualSiteNavigationMenusCount =
			_siteNavigationMenuService.getSiteNavigationMenusCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalSiteNavigationMenusCount + 2,
			actualSiteNavigationMenusCount);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSiteNavigationMenuWithoutUpdatePermissions1()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _user.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId(),
			siteNavigationMenu.getType(), siteNavigationMenu.isAuto(),
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSiteNavigationMenuWithoutUpdatePermissions2()
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _user.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		_siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Test
	public void testUpdateSiteNavigationMenuWithUpdatePermissions1()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation.model.SiteNavigationMenu",
			ActionKeys.UPDATE);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId(),
			siteNavigationMenu.getType(), siteNavigationMenu.isAuto(),
			serviceContext);
	}

	@Test
	public void testUpdateSiteNavigationMenuWithUpdatePermissions2()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(
				_group, _groupUser.getUserId());

		_addSiteMemberRoleResourcePermission(
			"com.liferay.site.navigation.model.SiteNavigationMenu",
			ActionKeys.UPDATE);

		ServiceTestUtil.setUser(_groupUser);

		_siteNavigationMenuService.updateSiteNavigationMenu(
			siteNavigationMenu.getSiteNavigationMenuId(),
			RandomTestUtil.randomString(), serviceContext);
	}

	private void _addSiteMemberRoleResourcePermission(
			String name, String permission)
		throws Exception {

		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_company.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_company.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
			String.valueOf(_group.getGroupId()), siteMemberRole.getRoleId(),
			permission);
	}

	@DeleteAfterTestRun
	private Company _company;

	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	@Inject
	private SiteNavigationMenuService _siteNavigationMenuService;

	@DeleteAfterTestRun
	private User _user;

}