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

package com.liferay.sharing.document.library.internal.security.permission.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class SharingEntryDLFileEntryModelResourcePermissionRegistrarTest {

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_fileEntry = _dlAppLocalService.addFileEntry(
			_user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), "text/plain", StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			serviceContext);

		_classNameId = _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());
	}

	@Test
	public void testUserWithAddDiscussionAndViewSharingEntryActionCanAddDiscussionPrivateFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), _classNameId,
			_fileEntry.getFileEntryId(), _fileEntry.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertTrue(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.ADD_DISCUSSION));
		}
	}

	@Test
	public void testUserWithAddDiscussionAndViewSharingEntryActionCannotUpdatePrivateFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), _classNameId,
			_fileEntry.getFileEntryId(), _fileEntry.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.UPDATE));
		}
	}

	@Test
	public void testUserWithoutAddDiscussionSharingEntryActionCannotAddDiscussionPrivateFileEntry()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.ADD_DISCUSSION));
		}
	}

	@Test
	public void testUserWithoutSharingCannotViewPrivateFileEntry()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.VIEW));
		}
	}

	@Test
	public void testUserWithoutUpdateSharingEntryActionCannotUpdatePrivateFileEntry()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.UPDATE));
		}
	}

	@Test
	public void testUserWithoutViewSharingEntryActionCannotViewPrivateFileEntry()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.VIEW));
		}
	}

	@Test
	public void testUserWithUpdateAndViewSharingEntryActionCannotAddDiscussionPrivateFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), _classNameId,
			_fileEntry.getFileEntryId(), _fileEntry.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertFalse(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.ADD_DISCUSSION));
		}
	}

	@Test
	public void testUserWithUpdateAndViewSharingEntryActionCanUpdatePrivateFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), _classNameId,
			_fileEntry.getFileEntryId(), _fileEntry.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertTrue(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.UPDATE));
		}
	}

	@Test
	public void testUserWithViewSharingEntryActionCanViewPrivateFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _groupUser.getUserId(), _classNameId,
			_fileEntry.getFileEntryId(), _fileEntry.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertTrue(
				_fileEntryModelResourcePermission.contains(
					permissionChecker, _fileEntry, ActionKeys.VIEW));
		}
	}

	@Inject(
		filter = "model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
	)
	private static ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private FileEntry _fileEntry;
	private Group _group;
	private User _groupUser;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	private User _user;

}