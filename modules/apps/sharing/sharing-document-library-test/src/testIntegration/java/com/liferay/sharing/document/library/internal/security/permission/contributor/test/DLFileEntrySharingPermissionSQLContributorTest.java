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

package com.liferay.sharing.document.library.internal.security.permission.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Collections;

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
public class DLFileEntrySharingPermissionSQLContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_groupUser = UserTestUtil.addGroupUser(
			_group, RoleConstants.POWER_USER);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_fileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), "text/plain", StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			serviceContext);
	}

	@Test
	public void testDLFileEntryClassNameReturnsPermissionSQL()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				TestPropsValues.getUser(), permissionChecker)) {

			StringBundler sb = new StringBundler(6);

			sb.append("1234 IN (SELECT SharingEntry.classPK FROM ");
			sb.append("SharingEntry WHERE (SharingEntry.toUserId = ");
			sb.append(TestPropsValues.getUserId());
			sb.append(") AND (SharingEntry.classNameId = ");
			sb.append(
				_classNameLocalService.getClassNameId(
					DLFileEntry.class.getName()));
			sb.append("))");

			Assert.assertEquals(
				sb.toString(),
				_permissionSQLContributor.getPermissionSQL(
					DLFileEntry.class.getName(), "1234", null, null, null));
		}
	}

	@Test
	public void testInlinePermissions() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_groupUser);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_groupUser, permissionChecker)) {

			Assert.assertEquals(
				0,
				_dlAppService.getFileEntriesCount(
					_group.getGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

			long classNameId = _classNameLocalService.getClassNameId(
				DLFileEntry.class.getName());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_sharingEntryLocalService.addSharingEntry(
				TestPropsValues.getUserId(), _groupUser.getUserId(),
				classNameId, _fileEntry.getFileEntryId(), _group.getGroupId(),
				true, Collections.singletonList(SharingEntryAction.VIEW), null,
				serviceContext);

			Assert.assertEquals(
				1,
				_dlAppService.getFileEntriesCount(
					_group.getGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
		}
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@Inject
	private static DLAppService _dlAppService;

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		type = PermissionSQLContributor.class
	)
	private static PermissionSQLContributor _permissionSQLContributor;

	@Inject
	private static SharingEntryLocalService _sharingEntryLocalService;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _groupUser;

}