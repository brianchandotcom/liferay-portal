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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ObjectFieldServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService);
		_systemObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				TestPropsValues.getUserId(), "Test", null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Test", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, 1,
				Collections.<ObjectField>emptyList());
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = TestPropsValues.getUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddCustomObjectField() throws Exception {
		try {
			_testAddCustomObjectField(
				_objectDefinition.getObjectDefinitionId(), _defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		try {
			_testAddCustomObjectField(
				_systemObjectDefinition.getObjectDefinitionId(), _defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					StringBundler.concat(
						"User ", _defaultUser.getUserId(),
						" must have EXTEND_SYSTEM_OBJECT_DEFINITION ",
						"permission for")));
		}

		_testAddCustomObjectField(
			_objectDefinition.getObjectDefinitionId(), _user);
	}

	@Test
	public void testDeleteObjectField() throws Exception {
		try {
			_testDeleteObjectField(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteObjectField(_user);
	}

	@Test
	public void testGetObjectField() throws Exception {
		try {
			_testGetObjectField(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectField(_user);
	}

	@Test
	public void testUpdateObjectField() throws Exception {
		try {
			_testUpdateObjectField(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateObjectField(_user);
	}

	private ObjectField _addObjectField(User user) throws Exception {
		return _objectFieldLocalService.addCustomObjectField(
			user.getUserId(), 0, _objectDefinition.getObjectDefinitionId(),
			"Text", "String", null, false, false, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, false, Collections.emptyList());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddCustomObjectField(long objectDefinitionId, User user)
		throws Exception {

		ObjectField objectField = null;

		try {
			_setUser(user);

			objectField = _objectFieldService.addCustomObjectField(
				0, objectDefinitionId, "Text", "String", null, false, false,
				null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(), true, false, Collections.emptyList());
		}
		finally {
			if (objectField != null) {
				_objectFieldLocalService.deleteObjectField(objectField);
			}
		}
	}

	private void _testDeleteObjectField(User user) throws Exception {
		ObjectField deleteObjectField = null;
		ObjectField objectField = null;

		try {
			_setUser(user);

			objectField = _addObjectField(user);

			deleteObjectField = _objectFieldService.deleteObjectField(
				objectField.getObjectFieldId());
		}
		finally {
			if (deleteObjectField == null) {
				_objectFieldLocalService.deleteObjectField(objectField);
			}
		}
	}

	private void _testGetObjectField(User user) throws Exception {
		ObjectField objectField = null;

		try {
			_setUser(user);

			objectField = _addObjectField(user);

			_objectFieldService.getObjectField(objectField.getObjectFieldId());
		}
		finally {
			if (objectField != null) {
				_objectFieldLocalService.deleteObjectField(objectField);
			}
		}
	}

	private void _testUpdateObjectField(User user) throws Exception {
		ObjectField objectField = null;

		try {
			_setUser(user);

			objectField = _addObjectField(user);

			objectField = _objectFieldService.updateObjectField(
				objectField.getObjectFieldId(), StringPool.BLANK, 0, "Text",
				"String", null, true, false,
				LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
				LocalizedMapUtil.getLocalizedMap("baker"), "baker", true, false,
				Collections.emptyList(), null);
		}
		finally {
			if (objectField != null) {
				_objectFieldLocalService.deleteObjectField(objectField);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldService _objectFieldService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private ObjectDefinition _systemObjectDefinition;

	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}