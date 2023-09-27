/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.comment.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.comment.configuration.CommentGroupServiceConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

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

		Assert.assertFalse(
			_discussionPermission.hasAddPermission(
				permissionChecker, TestPropsValues.getCompanyId(),
				_group.getGroupId(), DLFileEntry.class.getName(),
				_fileEntry.getFileEntryId()));
	}

	@Test
	public void testBannedSiteMemberCannotAddComment() throws Exception {
		_mbBanLocalService.addBan(
			_user.getUserId(), _siteUser1.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		Assert.assertFalse(
			_discussionPermission.hasAddPermission(
				permissionChecker, TestPropsValues.getCompanyId(),
				_group.getGroupId(), DLFileEntry.class.getName(),
				_fileEntry.getFileEntryId()));
	}

	@Test
	public void testSiteMemberCanAddComment() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		Assert.assertTrue(
			_discussionPermission.hasAddPermission(
				permissionChecker, TestPropsValues.getCompanyId(),
				_group.getGroupId(), DLFileEntry.class.getName(),
				_fileEntry.getFileEntryId()));
	}

	@Test
	public void testUserCannotUpdateHisComment() throws Exception {
		long commentId = _addComment(_siteUser1);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_siteUser1);

		Assert.assertFalse(
			_discussionPermission.hasUpdatePermission(
				permissionChecker, commentId));
	}

	@Test
	public void testUserCannotUpdateSomeoneElseCommentIfPropsEnabled()
		throws Exception {

		_withAlwaysEditableByOwnerEnabled(
			() -> {
				long commentId = _addComment(_siteUser1);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(_siteUser2);

				Assert.assertFalse(
					_discussionPermission.hasUpdatePermission(
						permissionChecker, commentId));
			});
	}

	@Test
	public void testUserCanUpdateHisCommentIfPropsEnabled() throws Exception {
		_withAlwaysEditableByOwnerEnabled(
			() -> {
				long commentId = _addComment(_siteUser1);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(_siteUser1);

				Assert.assertTrue(
					_discussionPermission.hasUpdatePermission(
						permissionChecker, commentId));
			});
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

	private void _withAlwaysEditableByOwnerEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			CommentGroupServiceConfiguration.class.getName(),
			StringPool.QUESTION);

		Dictionary<String, Object> originalConfigurationProperties =
			configuration.getProperties();

		ConfigurationTestUtil.saveConfiguration(
			configuration,
			HashMapDictionaryBuilder.putAll(
				originalConfigurationProperties
			).put(
				"alwaysEditableByOwner", true
			).build());

		try {
			unsafeRunnable.run();
		}
		finally {
			ConfigurationTestUtil.saveConfiguration(
				configuration, originalConfigurationProperties);
		}
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject(
		filter = "component.name=com.liferay.message.boards.comment.internal.MBCommentManagerImpl"
	)
	private CommentManager _commentManager;

	@Inject
	private DiscussionPermission _discussionPermission;

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