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

package com.liferay.message.boards.internal.comment.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
public class MBDiscussionPermissionImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();
		_siteUser1 = UserTestUtil.addUser(_group.getGroupId());
		_siteUser2 = UserTestUtil.addUser(_group.getGroupId());

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		_initializeCommentManager();
	}

	@Test
	public void testAddDiscussionPermissionWhenUserIsDiscussionOwnerButDoesNotHaveNotAddDiscussionPermission()
		throws Exception {

		_addComment(_siteUser1);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			TestPropsValues.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			_resourcePermissionLocalService.removeResourcePermission(
				TestPropsValues.getCompanyId(),
				DLFileEntryConstants.getClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_fileEntry.getFileEntryId()), role.getRoleId(),
				ActionKeys.ADD_DISCUSSION);
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		Assert.assertFalse(
			discussionPermission.hasAddPermission(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				DLFileEntry.class.getName(), _fileEntry.getFileEntryId()));
	}

	@Test
	public void testBannedSiteMemberCannotAddComment() throws Exception {
		_mbBanLocalService.addBan(
			_user.getUserId(), _siteUser1.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		Assert.assertFalse(
			discussionPermission.hasAddPermission(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				DLFileEntry.class.getName(), _fileEntry.getFileEntryId()));
	}

	@Test
	public void testSiteMemberCanAddComment() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		Assert.assertTrue(
			discussionPermission.hasAddPermission(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				DLFileEntry.class.getName(), _fileEntry.getFileEntryId()));
	}

	@Test
	public void testUserCannotUpdateHisComment() throws Exception {
		long commentId = _addComment(_siteUser1);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		DiscussionPermission discussionPermission =
			_commentManager.getDiscussionPermission(permissionChecker);

		Assert.assertFalse(discussionPermission.hasUpdatePermission(commentId));
	}

	@Test
	public void testUserCannotUpdateSomeoneElseCommentIfPropsEnabled()
		throws Exception {

		boolean discussionCommentsAlwaysEditableByOwner =
			PropsValues.DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER", true);

			long commentId = _addComment(_siteUser1);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(_siteUser2);

			DiscussionPermission discussionPermission =
				_commentManager.getDiscussionPermission(permissionChecker);

			Assert.assertFalse(
				discussionPermission.hasUpdatePermission(commentId));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER",
				discussionCommentsAlwaysEditableByOwner);
		}
	}

	@Test
	public void testUserCanUpdateHisCommentIfPropsEnabled() throws Exception {
		boolean discussionCommentsAlwaysEditableByOwner =
			PropsValues.DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER", true);

			long commentId = _addComment(_siteUser1);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(_siteUser1);

			DiscussionPermission discussionPermission =
				_commentManager.getDiscussionPermission(permissionChecker);

			Assert.assertTrue(
				discussionPermission.hasUpdatePermission(commentId));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class,
				"DISCUSSION_COMMENTS_ALWAYS_EDITABLE_BY_OWNER",
				discussionCommentsAlwaysEditableByOwner);
		}
	}

	private long _addComment(User user) throws Exception {
		IdentityServiceContextFunction serviceContextFunction =
			new IdentityServiceContextFunction(
				ServiceContextTestUtil.getServiceContext(
					_group, user.getUserId()));

		return _commentManager.addComment(
			user.getUserId(), _group.getGroupId(),
			DLFileEntryConstants.getClassName(), _fileEntry.getFileEntryId(),
			StringUtil.randomString(), serviceContextFunction);
	}

	private void _initializeCommentManager() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Collection<CommentManager> services = registry.getServices(
			CommentManager.class,
			"(component.name=com.liferay.message.boards.comment.internal." +
				"MBCommentManagerImpl)");

		if (services.isEmpty()) {
			throw new IllegalStateException(
				"MBMessage Comment API implementation was not found");
		}

		Iterator<CommentManager> iterator = services.iterator();

		_commentManager = iterator.next();
	}

	private CommentManager _commentManager;
	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MBBanLocalService _mbBanLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private User _siteUser1;

	@DeleteAfterTestRun
	private User _siteUser2;

	private User _user;

}