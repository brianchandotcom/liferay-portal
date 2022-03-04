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
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Objects;

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
		_adminUser = TestPropsValues.getUser();
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "First Name",
						"firstName", false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "Last Name",
						"lastName", false)));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_setUser(_adminUser);

		Assert.assertNotNull(
			_objectEntryService.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), _adminUser.getUserId())));

		_setUser(_user);

		_assertPrincipalException(ObjectActionKeys.ADD_OBJECT_ENTRY, null);

		_setUser(_defaultUser);

		_assertPrincipalException(ObjectActionKeys.ADD_OBJECT_ENTRY, null);

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			guestRole.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY);

		Assert.assertNotNull(
			_objectEntryService.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), _defaultUser.getUserId())));
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		try {
			_testDeleteObjectEntry(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteObjectEntry(_adminUser, _adminUser);
		_testDeleteObjectEntry(_user, _user);
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		_setUser(_adminUser);

		ObjectEntry adminObjectEntry = _addObjectEntry(_adminUser);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				adminObjectEntry.getObjectEntryId()));

		_setUser(_user);

		ObjectEntry userObjectEntry = _addObjectEntry(_user);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				userObjectEntry.getObjectEntryId()));

		_assertPrincipalException(ActionKeys.VIEW, adminObjectEntry);

		_setUser(_defaultUser);

		_assertPrincipalException(ActionKeys.VIEW, adminObjectEntry);

		ObjectEntry defaultUserObjectEntry = _addObjectEntry(_defaultUser);

		_assertPrincipalException(ActionKeys.VIEW, defaultUserObjectEntry);

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			guestRole.getRoleId(), ActionKeys.VIEW);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				adminObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testSearchObjectEntries() throws Exception {
		_setUser(_adminUser);

		ObjectEntry objectEntry1 = _addObjectEntry(_adminUser);
		ObjectEntry objectEntry2 = _addObjectEntry(_adminUser);

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		_setUser(_user);

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(objectEntry2);
	}

	private ObjectEntry _addObjectEntry(User user) throws Exception {
		return _objectEntryLocalService.addObjectEntry(
			user.getUserId(), 0, _objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomStringUtils.randomAlphabetic(5)
			).put(
				"LastName", RandomStringUtils.randomAlphabetic(5)
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));
	}

	private void _assertPrincipalException(
			String action, ObjectEntry objectEntry)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (Objects.equals(action, ActionKeys.VIEW)) {
				_objectEntryService.getObjectEntry(
					objectEntry.getObjectEntryId());
			}
			else {
				_objectEntryService.addObjectEntry(
					0, _objectDefinition.getObjectDefinitionId(),
					HashMapBuilder.<String, Serializable>put(
						"firstName", RandomStringUtils.randomAlphabetic(5)
					).build(),
					ServiceContextTestUtil.getServiceContext(
						TestPropsValues.getGroupId(),
						permissionChecker.getUserId()));
			}

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					StringBundler.concat(
						"User ", String.valueOf(permissionChecker.getUserId()),
						" must have ", action, " permission for")));
		}
	}

	private void _setUser(User user) throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testDeleteObjectEntry(User ownerUser, User user)
		throws Exception {

		ObjectEntry deleteObjectEntry = null;
		ObjectEntry objectEntry = null;

		try {
			_setUser(user);

			objectEntry = _addObjectEntry(ownerUser);

			deleteObjectEntry = _objectEntryService.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}
		finally {
			if (deleteObjectEntry == null) {
				_objectEntryLocalService.deleteObjectEntry(objectEntry);
			}
		}
	}

	private User _adminUser;
	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryService _objectEntryService;

	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}