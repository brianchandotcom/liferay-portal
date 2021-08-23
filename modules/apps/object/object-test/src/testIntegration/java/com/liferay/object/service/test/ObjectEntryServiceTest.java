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
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@RunWith(Arquillian.class)
public class ObjectEntryServiceTest {

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

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Test"), "Test", null, null,
				LocalizedMapUtil.getLocalizedMap("Tests"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						true, false, "First Name", "firstName", false,
						"String"),
					ObjectFieldUtil.createObjectField(
						true, false, "Last Name", "lastName", false,
						"String")));

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		try {
			_testAddObjectEntry(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have ADD_OBJECT_ENTRY permission for"));
		}

		_testAddObjectEntry(_user);
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		try {
			_testDeleteObjectEntry(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteObjectEntry(_user);
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		try {
			_testGetObjectEntry(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectEntry(_user);
	}

	@Test
	public void testSearchObjectEntries() throws Exception {
		_setUser(_user);

		ObjectEntry objectEntry1 = _addObjectEntry(_user);
		ObjectEntry objectEntry2 = _addObjectEntry(_user);

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			ObjectEntryLocalServiceUtil.searchObjectEntries(
				_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		_setUser(_defaultUser);

		baseModelSearchResult = ObjectEntryLocalServiceUtil.searchObjectEntries(
			_objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry1);
		ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry2);
	}

	private ObjectEntry _addObjectEntry(User user) throws Exception {
		return ObjectEntryLocalServiceUtil.addObjectEntry(
			user.getUserId(), 0, _objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomStringUtils.randomAlphabetic(5)
			).put(
				"LastName", RandomStringUtils.randomAlphabetic(5)
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectEntry(User user) throws Exception {
		ObjectEntry objectEntry = null;

		try {
			_setUser(user);

			objectEntry = ObjectEntryServiceUtil.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).put(
					"LastName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), user.getUserId()));
		}
		finally {
			if (objectEntry != null) {
				ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry);
			}
		}
	}

	private void _testDeleteObjectEntry(User user) throws Exception {
		ObjectEntry deleteObjectEntry = null;
		ObjectEntry objectEntry = null;

		try {
			_setUser(user);

			objectEntry = _addObjectEntry(user);

			deleteObjectEntry = ObjectEntryServiceUtil.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}
		finally {
			if (deleteObjectEntry == null) {
				ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry);
			}
		}
	}

	private void _testGetObjectEntry(User user) throws Exception {
		ObjectEntry objectEntry = null;

		try {
			_setUser(user);

			objectEntry = _addObjectEntry(user);

			ObjectEntryServiceUtil.getObjectEntry(
				objectEntry.getObjectEntryId());
		}
		finally {
			if (objectEntry != null) {
				ObjectEntryLocalServiceUtil.deleteObjectEntry(objectEntry);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}