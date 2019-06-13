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
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.List;

import org.junit.After;
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
public class FragmentEntryLinkServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				FragmentPersistenceConstants.BUNDLE_SYMBOLIC_NAME));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@After
	public void tearDown() throws Exception {
		_resourcePermissionLocalService.deleteResourcePermissions(
			TestPropsValues.getCompanyId(),
			"com.liferay.portal.kernel.model.Layout",
			ResourceConstants.SCOPE_GROUP, _group.getGroupId());
	}

	@Test
	public void testAddFragmentEntryLink() throws Exception {
		String css = "div {\\ncolor: red;\\n}";

		String html = "<div>test</div>";

		String js = "alert(\"test\");";

		long classPK = _layout.getPlid();

		_addSiteMemberUpdatePermission();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				_group.getGroupId(), 0, _fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), classPK, css, html, js,
				StringPool.BLANK, StringPool.BLANK, 0, null, serviceContext);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkPersistence.findByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(css, persistedFragmentEntryLink.getCss());

		Assert.assertEquals(html, persistedFragmentEntryLink.getHtml());

		Assert.assertEquals(js, persistedFragmentEntryLink.getJs());

		Assert.assertEquals(fragmentEntryLink, persistedFragmentEntryLink);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryLinkWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, _fragmentEntry.getFragmentEntryId(),
			PortalUtil.getClassNameId(Layout.class),
			RandomTestUtil.randomLong(), _fragmentEntry.getCss(),
			_fragmentEntry.getHtml(), _fragmentEntry.getJs(), StringPool.BLANK,
			StringPool.BLANK, 0, null, serviceContext);
	}

	@Test
	public void testDeleteFragmentEntryLink() throws Exception {
		long classPK = _layout.getPlid();

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				classPK);

		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_groupUser);

		_fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(
			_fragmentEntryLinkPersistence.fetchByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentEntryLinkWithoutPermissions()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());
	}

	@Test
	public void testUpdateFragmentEntryLink() throws Exception {
		long classPK = _layout.getPlid();

		String editableValues = _createEditableValues();

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				classPK);

		_addSiteMemberUpdatePermission();

		ServiceTestUtil.setUser(_groupUser);

		_fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(), editableValues);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkPersistence.findByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(fragmentEntryLink, persistedFragmentEntryLink);

		Assert.assertEquals(
			editableValues, persistedFragmentEntryLink.getEditableValues());
	}

	@Test
	public void testUpdateFragmentEntryLinks() throws Exception {
		long classPK = _layout.getPlid();

		String editableValues = _createEditableValues();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long[] fragmentEntryIds = {
			_fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentEntryId()
		};

		FragmentTestUtil.addFragmentEntryLink(
			fragmentEntry, PortalUtil.getClassNameId(Layout.class), classPK);

		_addSiteMemberUpdatePermission();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkPersistence.findByG_C_C(
				_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
				classPK);

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
			classPK, fragmentEntryIds, editableValues, serviceContext);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkPersistence.findByG_C_C(
				_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
				classPK);

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 1,
			actualFragmentEntryLinks.size());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryLinksWithoutPermissions()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long classPK = RandomTestUtil.randomLong();

		FragmentTestUtil.addFragmentEntryLink(
			fragmentEntry, PortalUtil.getClassNameId(Layout.class), classPK);

		long[] fragmentEntryIds = {
			_fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentEntryId()
		};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _groupUser.getUserId());

		ServiceTestUtil.setUser(_groupUser);

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
			classPK, fragmentEntryIds, RandomTestUtil.randomString(),
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryLinkWithoutPermissions()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				RandomTestUtil.randomLong());

		ServiceTestUtil.setUser(_groupUser);

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
	private FragmentEntryLinkPersistence _fragmentEntryLinkPersistence;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

	private Layout _layout;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}