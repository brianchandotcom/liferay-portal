/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mario Gomes
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
	}

	@After
	public void tearDown() throws Exception {
		_setUser(_adminUser);
	}

	@Test
	public void testGetOrAddEmptyObjectEntryFolder() throws Exception {
		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			// With permissions

			Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

			RoleTestUtil.addResourcePermission(
				role, ObjectEntryFolderConstants.RESOURCE_NAME,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(TestPropsValues.getCompanyId()),
				ActionKeys.ADD_FOLDER);

			User user = UserTestUtil.addUser();

			UserLocalServiceUtil.addRoleUser(
				role.getRoleId(), user.getUserId());

			_setUser(user);

			Group group = GroupTestUtil.addGroup();

			ObjectEntryFolder objectEntryFolder =
				_objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					RandomTestUtil.randomString(), group.getGroupId(),
					TestPropsValues.getCompanyId(), user.getUserId(),
					ServiceContextTestUtil.getServiceContext());

			// Without permissions

			user = UserTestUtil.addUser();

			long userId = user.getUserId();

			_setUser(user);

			AssertUtils.assertFailure(
				PrincipalException.MustHavePermission.class,
				StringBundler.concat(
					"User ", userId, " must have ADD_FOLDER permission for ",
					ObjectEntryFolderConstants.RESOURCE_NAME, " ",
					group.getGroupId()),
				() -> _objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					RandomTestUtil.randomString(), group.getGroupId(),
					TestPropsValues.getCompanyId(), userId,
					ServiceContextTestUtil.getServiceContext()));

			AssertUtils.assertFailure(
				PrincipalException.MustHavePermission.class,
				StringBundler.concat(
					"User ", userId, " must have VIEW permission for ",
					ObjectEntryFolderConstants.RESOURCE_NAME, " ",
					objectEntryFolder.getObjectEntryFolderId()),
				() -> _objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					objectEntryFolder.getExternalReferenceCode(),
					objectEntryFolder.getGroupId(),
					objectEntryFolder.getCompanyId(), userId,
					ServiceContextTestUtil.getServiceContext()));
		}
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private User _adminUser;

	@Inject
	private ObjectEntryFolderService _objectEntryFolderService;

}