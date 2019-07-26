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

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkServicePermissionTest {

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

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testAddFragmentEntryLink() throws Exception {
		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_user);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		_fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, _fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class), _layout.getPlid(),
			StringPool.BLANK, "<div>test</div>", StringPool.BLANK,
			"{fieldSets: []}", StringPool.BLANK, StringPool.BLANK, 0, null,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryLinkWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, _fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class),
			RandomTestUtil.randomLong(), _fragmentEntry.getCss(),
			_fragmentEntry.getHtml(), _fragmentEntry.getJs(),
			_fragmentEntry.getConfiguration(), StringPool.BLANK,
			StringPool.BLANK, 0, null, serviceContext);
	}

	@Test
	public void testDeleteFragmentEntryLink() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_user);

		_fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentEntryLinkWithoutPermissions()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());
	}

	@Test
	public void testUpdateFragmentEntryLink() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_user);

		_fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(),
			_createEditableValues());
	}

	@Test
	public void testUpdateFragmentEntryLinks() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentTestUtil.addFragmentEntryLink(
			fragmentEntry, PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid());

		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_user);

		long[] fragmentEntryIds = {
			_fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentEntryId()
		};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid(), fragmentEntryIds, _createEditableValues(),
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryLinksWithoutPermissions()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentTestUtil.addFragmentEntryLink(
			fragmentEntry, PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid());

		ServiceTestUtil.setUser(_user);

		long[] fragmentEntryIds = {
			_fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentEntryId()
		};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid(), fragmentEntryIds, _createEditableValues(),
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryLinkWithoutPermissions()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(),
			RandomTestUtil.randomString());
	}

	private void _addSiteMemberUpdatePermission() throws Exception {
		Role siteMemberRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			TestPropsValues.getCompanyId(),
			"com.liferay.portal.kernel.model.Layout",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), ActionKeys.UPDATE);
	}

	private String _createEditableValues() {
		JSONObject jsonObject = JSONUtil.put(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		return jsonObject.toString();
	}

	private FragmentCollection _fragmentCollection;
	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}