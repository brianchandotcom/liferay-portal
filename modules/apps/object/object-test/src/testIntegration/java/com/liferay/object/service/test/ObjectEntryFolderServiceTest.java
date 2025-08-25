/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.object.test.util.ObjectEntryFolderTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_adminUser = TestPropsValues.getUser();
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@FeatureFlag("LPD-53981")
	@Test
	public void testMoveObjectEntryFolderTrash() throws Exception {
		try {
			_testMoveObjectEntryFolderTrash(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					principalException.getMessage(),
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testMoveObjectEntryFolderTrash(_adminUser, _adminUser);
		_testMoveObjectEntryFolderTrash(_user, _user);
	}

	@FeatureFlag("LPD-53981")
	@Test
	public void testRestoreObjectEntryFolderFromTrash() throws Exception {
		try {
			_testRestoreObjectEntryFolderFromTrash(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertTrue(
				StringUtil.startsWith(
					principalException.getMessage(),
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testRestoreObjectEntryFolderFromTrash(_adminUser, _adminUser);
		_testRestoreObjectEntryFolderFromTrash(_user, _user);
	}

	private void _setUser(User user) throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testMoveObjectEntryFolderTrash(User ownerUser, User user)
		throws Exception {

		_setUser(ownerUser);

		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderTestUtil.addObjectEntryFolder(
				_group.getGroupId(), ownerUser.getUserId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		_setUser(user);

		_objectEntryFolderService.moveObjectEntryFolderToTrash(
			user.getUserId(), objectEntryFolder,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _testRestoreObjectEntryFolderFromTrash(
			User ownerUser, User user)
		throws Exception {

		_setUser(ownerUser);

		ObjectEntryFolder objectEntryFolder =
			ObjectEntryFolderTestUtil.addObjectEntryFolder(
				_group.getGroupId(), ownerUser.getUserId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT);

		objectEntryFolder =
			_objectEntryFolderLocalService.moveObjectEntryFolderToTrash(
				ownerUser.getUserId(), objectEntryFolder,
				ServiceContextTestUtil.getServiceContext());

		_setUser(user);

		_objectEntryFolderService.restoreObjectEntryFolderFromTrash(
			user.getUserId(), objectEntryFolder,
			ServiceContextTestUtil.getServiceContext());
	}

	private User _adminUser;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryFolderService _objectEntryFolderService;

	private User _user;

}