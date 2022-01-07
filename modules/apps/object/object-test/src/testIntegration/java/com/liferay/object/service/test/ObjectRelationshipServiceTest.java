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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

import org.junit.After;
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
public class ObjectRelationshipServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = TestPropsValues.getUser();

		_objectDefinition1 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Able"), "Able", null, null,
				LocalizedMapUtil.getLocalizedMap("Ables"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_objectDefinition1 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition1.getObjectDefinitionId());

		_objectDefinition2 =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Baker"), "Baker", null, null,
				LocalizedMapUtil.getLocalizedMap("Bakers"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", RandomTestUtil.randomString(),
						StringUtil.randomId())));

		_objectDefinition2 =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition2.getObjectDefinitionId());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddObjectRelationship() throws Exception {
		try {
			_testAddObjectRelationship(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddObjectRelationship(_user);
	}

	@Test
	public void testDeleteObjectRelationship() throws Exception {
		try {
			_testDeleteObjectRelationship(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteObjectRelationship(_user);
	}

	@Test
	public void testGetObjectRelationship() throws Exception {
		try {
			_testGetObjectRelationship(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectRelationship(_user);
	}

	@Test
	public void testGetObjectRelationships() throws Exception {
		try {
			_testGetObjectRelationships(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectRelationships(_user);
	}

	@Test
	public void testUpdateObjectRelationship() throws Exception {
		try {
			_testUpdateObjectRelationship(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateObjectRelationship(_user);
	}

	private ObjectRelationship _addObjectRelationship(User user)
		throws Exception {

		return _objectRelationshipLocalService.addObjectRelationship(
			user.getUserId(), _objectDefinition1.getObjectDefinitionId(),
			_objectDefinition2.getObjectDefinitionId(),
			ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectRelationship(User user) throws Exception {
		ObjectRelationship objectRelationship = null;

		try {
			_setUser(user);

			objectRelationship =
				_objectRelationshipService.addObjectRelationship(
					_objectDefinition1.getObjectDefinitionId(),
					_objectDefinition2.getObjectDefinitionId(),
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					StringUtil.randomId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		}
		finally {
			if (objectRelationship != null) {
				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			}
		}
	}

	private void _testDeleteObjectRelationship(User user) throws Exception {
		ObjectRelationship deleteObjectRelationship = null;
		ObjectRelationship objectRelationship = null;

		try {
			_setUser(user);

			objectRelationship = _addObjectRelationship(user);

			deleteObjectRelationship =
				_objectRelationshipService.deleteObjectRelationship(
					objectRelationship.getObjectRelationshipId());
		}
		finally {
			if (deleteObjectRelationship == null) {
				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			}
		}
	}

	private void _testGetObjectRelationship(User user) throws Exception {
		ObjectRelationship objectRelationship = null;

		try {
			_setUser(user);

			objectRelationship = _addObjectRelationship(user);

			_objectRelationshipService.getObjectRelationship(
				objectRelationship.getObjectRelationshipId());
		}
		finally {
			if (objectRelationship != null) {
				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			}
		}
	}

	private void _testGetObjectRelationships(User user) throws Exception {
		ObjectRelationship objectRelationship = null;

		try {
			_setUser(user);

			objectRelationship = _addObjectRelationship(user);

			_objectRelationshipService.getObjectRelationships(
				objectRelationship.getObjectDefinitionId1(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		finally {
			if (objectRelationship != null) {
				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			}
		}
	}

	private void _testUpdateObjectRelationship(User user) throws Exception {
		ObjectRelationship objectRelationship = null;

		try {
			_setUser(user);

			objectRelationship = _addObjectRelationship(user);

			objectRelationship =
				_objectRelationshipService.updateObjectRelationship(
					objectRelationship.getObjectRelationshipId(),
					objectRelationship.getDeletionType(),
					LocalizedMapUtil.getLocalizedMap("Baker"));
		}
		finally {
			if (objectRelationship != null) {
				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ObjectRelationshipService _objectRelationshipService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}