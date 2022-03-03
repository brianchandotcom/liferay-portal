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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFieldSettingService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectFieldSettingServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);

		_objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), RandomTestUtil.randomBoolean(),
			Collections.emptyList());

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = TestPropsValues.getUser();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddObjectFieldSetting() throws Exception {
		try {
			_testAddObjectFieldSetting(
				_objectField.getObjectFieldId(), _defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddObjectFieldSetting(_objectField.getObjectFieldId(), _user);
	}

	@Test
	public void testDeleteObjectFieldSetting() throws Exception {
		try {
			_testDeleteObjectFieldSetting(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteObjectFieldSetting(_user);
	}

	@Test
	public void testGetObjectFieldSetting() throws Exception {
		try {
			_testGetObjectFieldSetting(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectFieldSetting(_user);
	}

	@Test
	public void testUpdateObjectFieldSetting() throws Exception {
		try {
			_testUpdateObjectFieldSetting(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateObjectFieldSetting(_user);
	}

	private ObjectFieldSetting _addObjectFieldSetting(User user)
		throws Exception {

		return _objectFieldSettingLocalService.addObjectFieldSetting(
			user.getUserId(), _objectField.getObjectFieldId(),
			StringUtil.randomId(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomString());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectFieldSetting(long objectFieldId, User user)
		throws Exception {

		ObjectFieldSetting objectFieldSetting = null;

		try {
			_setUser(user);

			objectFieldSetting =
				_objectFieldSettingService.addObjectFieldSetting(
					objectFieldId, StringUtil.randomId(),
					RandomTestUtil.randomBoolean(),
					RandomTestUtil.randomString());
		}
		finally {
			if (objectFieldSetting != null) {
				_objectFieldSettingLocalService.deleteObjectFieldSetting(
					objectFieldSetting);
			}
		}
	}

	private void _testDeleteObjectFieldSetting(User user) throws Exception {
		ObjectFieldSetting deletedObjectFieldSetting = null;
		ObjectFieldSetting objectFieldSetting = null;

		try {
			_setUser(user);

			objectFieldSetting = _addObjectFieldSetting(user);

			deletedObjectFieldSetting =
				_objectFieldSettingService.deleteObjectFieldSetting(
					objectFieldSetting.getObjectFieldSettingId());
		}
		finally {
			if (deletedObjectFieldSetting == null) {
				_objectFieldSettingLocalService.deleteObjectFieldSetting(
					objectFieldSetting);
			}
		}
	}

	private void _testGetObjectFieldSetting(User user) throws Exception {
		ObjectFieldSetting objectFieldSetting = null;

		try {
			_setUser(user);

			objectFieldSetting = _addObjectFieldSetting(user);

			_objectFieldSettingService.getObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId());
		}
		finally {
			if (objectFieldSetting != null) {
				_objectFieldSettingLocalService.deleteObjectFieldSetting(
					objectFieldSetting);
			}
		}
	}

	private void _testUpdateObjectFieldSetting(User user) throws Exception {
		ObjectFieldSetting objectFieldSetting = null;

		try {
			_setUser(user);

			objectFieldSetting = _addObjectFieldSetting(user);

			objectFieldSetting =
				_objectFieldSettingService.updateObjectFieldSetting(
					objectFieldSetting.getObjectFieldSettingId(),
					RandomTestUtil.randomString());
		}
		finally {
			if (objectFieldSetting != null) {
				_objectFieldSettingLocalService.deleteObjectFieldSetting(
					objectFieldSetting);
			}
		}
	}

	private static User _defaultUser;
	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectField _objectField;

	@Inject
	private static ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private static ObjectFieldSettingService _objectFieldSettingService;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;
	private static User _user;

	@Inject(type = UserLocalService.class)
	private static UserLocalService _userLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}