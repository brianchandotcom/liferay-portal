/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.exception.NoSuchObjectEntryFolderException;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		_user = TestPropsValues.getUser();

		_setUser(_user);
	}

	@After
	public void tearDown() throws Exception {
		_setUser(_user);
	}

	@Test
	public void testGetOrAddEmptyObjectEntryFolder() throws Exception {

		// Lazy referencing disabled

		Group group = GroupTestUtil.addGroup();

		AssertUtils.assertFailure(
			NoSuchObjectEntryFolderException.class, null,
			() -> _objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
				RandomTestUtil.randomString(), group.getGroupId(),
				TestPropsValues.getCompanyId(), _user.getUserId(),
				ServiceContextTestUtil.getServiceContext()));

		// Lazy referencing enabled

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			// With permissions

			_objectEntryFolder =
				_objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					RandomTestUtil.randomString(), group.getGroupId(),
					TestPropsValues.getCompanyId(), _user.getUserId(),
					ServiceContextTestUtil.getServiceContext());

			// Without permissions

			User user = UserTestUtil.addUser();

			_setUser(user);

			AssertUtils.assertFailure(
				PrincipalException.MustHavePermission.class,
				StringBundler.concat(
					"User ", user.getUserId(),
					" must have ADD_OBJECT_ENTRY_FOLDER permission for ",
					ObjectConstants.RESOURCE_NAME_OBJECT_ENTRY_FOLDER, " ",
					group.getGroupId()),
				() -> _objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					RandomTestUtil.randomString(), group.getGroupId(),
					TestPropsValues.getCompanyId(), user.getUserId(),
					ServiceContextTestUtil.getServiceContext()));

			// Without permissions, existing object entry folder

			AssertUtils.assertFailure(
				PrincipalException.MustHavePermission.class,
				StringBundler.concat(
					"User ", user.getUserId(),
					" must have VIEW permission for ",
					"com.liferay.object.model.ObjectEntryFolder ",
					_objectEntryFolder.getObjectEntryFolderId()),
				() -> _objectEntryFolderService.getOrAddEmptyObjectEntryFolder(
					_objectEntryFolder.getExternalReferenceCode(),
					_objectEntryFolder.getGroupId(),
					_objectEntryFolder.getCompanyId(), user.getUserId(),
					ServiceContextTestUtil.getServiceContext()));
		}
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	@DeleteAfterTestRun
	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderService _objectEntryFolderService;

	private User _user;

}