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
import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
public class FragmentEntryServicePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);
	}

	@After
	public void tearDown() throws Exception {
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.removeResourcePermissions(
			TestPropsValues.getCompanyId(), "com.liferay.fragment",
			ResourceConstants.SCOPE_GROUP, siteMemberRole.getRoleId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithFragmentEntryKeyAndTypeWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyAndTypeWithPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithFragmentEntryKeyWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyWithPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithHTMLWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithHTMLWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_SECTION, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithTypeAndHTMLWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithTypeAndHTMLWithPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddFragmentEntryWithTypeWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Test
	public void testAddFragmentEntryWithTypeWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testCopyFragmentEntryWithoutPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.copyFragmentEntry(
			_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), serviceContext);
	}

	@Test
	public void testCopyFragmentEntryWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.copyFragmentEntry(
			_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentEntriesWithoutPermissions() throws Exception {
		FragmentEntry fragmentEntry1 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntry fragmentEntry2 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.deleteFragmentEntries(fragmentEntryIds);
	}

	@Test
	public void testDeleteFragmentEntriesWithPermissions() throws Exception {
		FragmentEntry fragmentEntry1 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntry fragmentEntry2 = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long[] fragmentEntryIds = {
			fragmentEntry1.getFragmentEntryId(),
			fragmentEntry2.getFragmentEntryId()
		};

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.deleteFragmentEntries(fragmentEntryIds);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteFragmentEntryWithoutPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());
	}

	@Test
	public void testDeleteFragmentEntryWithPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());
	}

	@Test
	public void testFetchFragmentEntryWithPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.fetchFragmentEntry(
			fragmentEntry.getFragmentEntryId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testMoveFragmentEntryWithoutPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.moveFragmentEntry(
			fragmentEntry.getFragmentEntryId(),
			targetFragmentCollection.getFragmentCollectionId());
	}

	@Test
	public void testMoveFragmentEntryWithPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.moveFragmentEntry(
			fragmentEntry.getFragmentEntryId(),
			targetFragmentCollection.getFragmentCollectionId());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryNameWithoutPermissions()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString());
	}

	@Test
	public void testUpdateFragmentEntryNameWithPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryValuesAndPreviewFileEntryIdWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		Assert.assertEquals(0, fragmentEntry.getPreviewFileEntryId());

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			"{\n\t\"fieldSets\": [\n\t]\n}", 1,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testUpdateFragmentEntryValuesAndPreviewFileEntryIdWithPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		Assert.assertEquals(0, fragmentEntry.getPreviewFileEntryId());

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			"{\n\t\"fieldSets\": [\n\t]\n}", 1,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateFragmentEntryValuesWithoutPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			"{\n\t\"fieldSets\": [\n\t]\n}", WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testUpdateFragmentEntryValuesWithPermissions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(), StringPool.BLANK,
			"{\n\t\"fieldSets\": [\n\t]\n}", WorkflowConstants.STATUS_APPROVED);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdatePreviewFileEntryIdWithoutPermissions()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), previewFileEntryId + 1);
	}

	@Test
	public void testUpdatePreviewFileEntryIdWithPermissions() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		_setRolePermissions(FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		ServiceTestUtil.setUser(_user);

		_fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), previewFileEntryId + 1);
	}

	private void _setRolePermissions(String permissionType) throws Exception {
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			TestPropsValues.getCompanyId(), "com.liferay.fragment",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			siteMemberRole.getRoleId(), permissionType);
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}