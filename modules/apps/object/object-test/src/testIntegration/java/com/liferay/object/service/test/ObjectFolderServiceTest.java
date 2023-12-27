/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectFolderService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectFolderServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_adminUser = TestPropsValues.getUser();
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddObjectFolder() throws Exception {
		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have ADD_OBJECT_FOLDER ",
				"permission for com.liferay.object "),
			() -> _testAddObjectFolder(_user));

		_testAddObjectFolder(_adminUser);
	}

	@Test
	public void testDeleteObjectFolder() throws Exception {
		ObjectFolder objectFolder = _addObjectFolder(_adminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have DELETE permission for ",
				"com.liferay.object.model.ObjectFolder ",
				objectFolder.getObjectFolderId()),
			() -> _testDeleteObjectFolder(objectFolder, _user));

		_testDeleteObjectFolder(_addObjectFolder(_adminUser), _adminUser);
		_testDeleteObjectFolder(_addObjectFolder(_user), _user);
	}

	@Test
	public void testGetObjectFolder() throws Exception {
		ObjectFolder objectFolder = _addObjectFolder(_adminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have VIEW permission for ",
				"com.liferay.object.model.ObjectFolder ",
				objectFolder.getObjectFolderId()),
			() -> _testGetObjectFolder(objectFolder, _user));

		_testGetObjectFolder(_addObjectFolder(_adminUser), _adminUser);
		_testGetObjectFolder(_addObjectFolder(_user), _user);

		ObjectFolder defaultObjectFolder =
			_objectFolderLocalService.getObjectFolder(
				TestPropsValues.getCompanyId(),
				ObjectFolderConstants.NAME_DEFAULT);

		_testGetObjectFolder(defaultObjectFolder, _adminUser);
		_testGetObjectFolder(defaultObjectFolder, _user);
	}

	@Test
	public void testGetObjectFolderByExternalReferenceCode() throws Exception {
		ObjectFolder objectFolder = _addObjectFolder(_adminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have VIEW permission for ",
				"com.liferay.object.model.ObjectFolder ",
				objectFolder.getObjectFolderId()),
			() -> _testGetObjectFolderByExternalReferenceCode(
				objectFolder, _user));

		_testGetObjectFolderByExternalReferenceCode(
			_addObjectFolder(_adminUser), _adminUser);
		_testGetObjectFolderByExternalReferenceCode(
			_addObjectFolder(_user), _user);

		ObjectFolder defaultObjectFolder =
			_objectFolderLocalService.getObjectFolder(
				TestPropsValues.getCompanyId(),
				ObjectFolderConstants.NAME_DEFAULT);

		_testGetObjectFolderByExternalReferenceCode(
			defaultObjectFolder, _adminUser);
		_testGetObjectFolderByExternalReferenceCode(defaultObjectFolder, _user);
	}

	@Test
	public void testUpdateObjectFolder() throws Exception {
		ObjectFolder objectFolder = _addObjectFolder(_adminUser);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", _user.getUserId(), " must have UPDATE permission for ",
				"com.liferay.object.model.ObjectFolder ",
				objectFolder.getObjectFolderId()),
			() -> _testUpdateObjectFolder(objectFolder, _user));

		_testUpdateObjectFolder(_addObjectFolder(_adminUser), _adminUser);
		_testUpdateObjectFolder(_addObjectFolder(_user), _user);
	}

	private ObjectFolder _addObjectFolder(User user) throws Exception {
		return _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), user.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectFolder(User user) throws Exception {
		ObjectFolder objectFolder = null;

		try {
			_setUser(user);

			objectFolder = _objectFolderService.addObjectFolder(
				RandomTestUtil.randomString(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString());
		}
		finally {
			if (objectFolder != null) {
				_objectFolderLocalService.deleteObjectFolder(objectFolder);
			}
		}
	}

	private void _testDeleteObjectFolder(ObjectFolder objectFolder, User user)
		throws Exception {

		ObjectFolder deleteObjectFolder = null;

		try {
			_setUser(user);

			deleteObjectFolder = _objectFolderService.deleteObjectFolder(
				objectFolder.getObjectFolderId());
		}
		finally {
			if (deleteObjectFolder == null) {
				_objectFolderLocalService.deleteObjectFolder(objectFolder);
			}
		}
	}

	private void _testGetObjectFolder(ObjectFolder objectFolder, User user)
		throws Exception {

		try {
			_setUser(user);

			_objectFolderService.getObjectFolder(
				objectFolder.getObjectFolderId());
		}
		finally {
			if ((objectFolder != null) &&
				!StringUtil.equals(
					objectFolder.getName(),
					ObjectFolderConstants.NAME_DEFAULT)) {

				_objectFolderLocalService.deleteObjectFolder(objectFolder);
			}
		}
	}

	private void _testGetObjectFolderByExternalReferenceCode(
			ObjectFolder objectFolder, User user)
		throws Exception {

		try {
			_setUser(user);

			_objectFolderService.getObjectFolderByExternalReferenceCode(
				objectFolder.getExternalReferenceCode(), user.getCompanyId());
		}
		finally {
			if ((objectFolder != null) &&
				!StringUtil.equals(
					objectFolder.getName(),
					ObjectFolderConstants.NAME_DEFAULT)) {

				_objectFolderLocalService.deleteObjectFolder(objectFolder);
			}
		}
	}

	private void _testUpdateObjectFolder(ObjectFolder objectFolder, User user)
		throws Exception {

		try {
			_setUser(user);

			_objectFolderService.updateObjectFolder(
				RandomTestUtil.randomString(), objectFolder.getObjectFolderId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));
		}
		finally {
			if (objectFolder != null) {
				_objectFolderLocalService.deleteObjectFolder(objectFolder);
			}
		}
	}

	private User _adminUser;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ObjectFolderService _objectFolderService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}