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
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectLayoutService;
import com.liferay.object.util.LocalizedMapUtil;
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
public class ObjectLayoutServiceTest {

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
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddObjectLayout() throws Exception {
		try {
			_testAddObjectLayout(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddObjectLayout(_user);
	}

	@Test
	public void testGetObjectLayout() throws Exception {
		try {
			_testGetObjectLayout(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectLayout(_user);
	}

	@Test
	public void testUpdateObjectLayout() throws Exception {
		try {
			_testUpdateObjectLayout(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateObjectLayout(_user);
	}

	private ObjectLayout _addObjectLayout(User user) throws Exception {
		return _objectLayoutLocalService.addObjectLayout(
			user.getUserId(), _objectDefinition.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.emptyList());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectLayout(User user) throws Exception {
		ObjectLayout objectLayout = null;

		try {
			_setUser(user);

			objectLayout = _objectLayoutService.addObjectLayout(
				_objectDefinition.getObjectDefinitionId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.emptyList());
		}
		finally {
			if (objectLayout != null) {
				_objectLayoutLocalService.deleteObjectLayout(objectLayout);
			}
		}
	}

	private void _testGetObjectLayout(User user) throws Exception {
		ObjectLayout objectLayout = null;

		try {
			_setUser(user);

			objectLayout = _addObjectLayout(user);

			_objectLayoutService.getObjectLayout(
				objectLayout.getObjectLayoutId());
		}
		finally {
			if (objectLayout != null) {
				_objectLayoutLocalService.deleteObjectLayout(objectLayout);
			}
		}
	}

	private void _testUpdateObjectLayout(User user) throws Exception {
		ObjectLayout objectLayout = null;

		try {
			_setUser(user);

			objectLayout = _addObjectLayout(user);

			objectLayout = _objectLayoutService.updateObjectLayout(
				objectLayout.getObjectLayoutId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.emptyList());
		}
		finally {
			if (objectLayout != null) {
				_objectLayoutLocalService.deleteObjectLayout(objectLayout);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Inject
	private ObjectLayoutService _objectLayoutService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}