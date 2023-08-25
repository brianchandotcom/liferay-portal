/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;

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
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ObjectDefinitionServiceTest {

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
	public void testAddCustomObjectDefinition() throws Exception {
		try {
			_testAddCustomObjectDefinition(_user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have ADD_OBJECT_DEFINITION permission for"));
		}

		_testAddCustomObjectDefinition(_adminUser);
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		try {
			_testDeleteObjectDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteObjectDefinition(_adminUser, _adminUser);
		_testDeleteObjectDefinition(_user, _user);
	}

	@Test
	public void testGetObjectDefinition() throws Exception {
		try {
			_testGetObjectDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectDefinition(_adminUser, _adminUser);
		_testGetObjectDefinition(_user, _user);
	}

	@Test
	public void testPublishCustomObjectDefinition() throws Exception {
		try {
			_testPublishCustomObjectDefinition(_user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have PUBLISH_OBJECT_DEFINITION permission for"));
		}

		_testPublishCustomObjectDefinition(_adminUser);
	}

	@Test
	public void testUpdateCustomObjectDefinition() throws Exception {
		try {
			_testUpdateCustomObjectDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateCustomObjectDefinition(_adminUser, _adminUser);
		_testUpdateCustomObjectDefinition(_user, _user);
	}

	@Test
	public void testUpdateRootObjectDefinitionId() throws Exception {
		try {
			_testUpdateRootObjectDefinitionId(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateRootObjectDefinitionId(_adminUser, _adminUser);
		_testUpdateRootObjectDefinitionId(_user, _user);
	}

	@Test
	public void testUpdateTitleObjectFieldId() throws Exception {
		try {
			_testUpdateTitleObjectFieldId(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateTitleObjectFieldId(_adminUser, _adminUser);
		_testUpdateTitleObjectFieldId(_user, _user);
	}

	private ObjectDefinition _addCustomObjectDefinition(User user)
		throws Exception {

		// Do not publish the custom object definition to ensure we test that
		// permission resources are added before publishing

		/*ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user.getUserId(), "Test", null);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			user.getUserId(), objectDefinition.getObjectDefinitionId());*/

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			user.getUserId(), 0, false, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			true, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING,
					RandomTestUtil.randomString(), StringUtil.randomId())));
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddCustomObjectDefinition(User user) throws Exception {
		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition =
				_objectDefinitionService.addCustomObjectDefinition(
					0, false, false, false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"A" + RandomTestUtil.randomString(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					true, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							RandomTestUtil.randomString(),
							StringUtil.randomId())));

			objectDefinition =
				_objectDefinitionLocalService.publishCustomObjectDefinition(
					user.getUserId(), objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testDeleteObjectDefinition(User ownerUser, User user)
		throws Exception {

		ObjectDefinition deleteObjectDefinition = null;
		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition = _addCustomObjectDefinition(ownerUser);

			deleteObjectDefinition =
				_objectDefinitionService.deleteObjectDefinition(
					objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (deleteObjectDefinition == null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testGetObjectDefinition(User ownerUser, User user)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition = _addCustomObjectDefinition(ownerUser);

			_objectDefinitionService.getObjectDefinition(
				objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testPublishCustomObjectDefinition(User user)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					user.getUserId(), 0, false, false, false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"A" + RandomTestUtil.randomString(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					true, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							RandomTestUtil.randomString(),
							StringUtil.randomId())));

			objectDefinition =
				_objectDefinitionService.publishCustomObjectDefinition(
					objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testUpdateCustomObjectDefinition(User ownerUser, User user)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					ownerUser.getUserId(), 0, false, false, false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"A" + RandomTestUtil.randomString(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					true, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							RandomTestUtil.randomString(),
							StringUtil.randomId())));

			objectDefinition =
				_objectDefinitionService.updateCustomObjectDefinition(
					null, objectDefinition.getObjectDefinitionId(), 0, 0, 0, 0,
					false, objectDefinition.isActive(), true, false, false,
					false, false, LocalizedMapUtil.getLocalizedMap("Able"),
					"Able", null, null, false,
					LocalizedMapUtil.getLocalizedMap("Ables"),
					objectDefinition.getScope());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testUpdateRootObjectDefinitionId(User ownerUser, User user)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition = _addCustomObjectDefinition(ownerUser);

			_objectDefinitionService.updateRootObjectDefinitionId(
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getObjectDefinitionId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testUpdateTitleObjectFieldId(User ownerUser, User user)
		throws Exception {

		ObjectDefinition objectDefinition = null;

		try {
			_setUser(user);

			objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					ownerUser.getUserId(), 0, false, false, false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					"A" + RandomTestUtil.randomString(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					true, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							RandomTestUtil.randomString(),
							StringUtil.randomId())));

			ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
				new TextObjectFieldBuilder(
				).userId(
					ownerUser.getUserId()
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					StringUtil.randomId()
				).objectDefinitionId(
					objectDefinition.getObjectDefinitionId()
				).build());

			objectDefinition =
				_objectDefinitionService.updateTitleObjectFieldId(
					objectDefinition.getObjectDefinitionId(),
					objectField.getObjectFieldId());
		}
		finally {
			if (objectDefinition != null) {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private User _adminUser;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionService _objectDefinitionService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}