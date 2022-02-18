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
import com.liferay.object.model.ObjectView;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.ObjectViewService;
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
public class ObjectViewServiceTest {

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
	public void testAddObjectView() throws Exception {
		try {
			_testAddObjectView(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddObjectView(_user);
	}

	@Test
	public void testGetObjectView() throws Exception {
		try {
			_testGetObjectView(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetObjectView(_user);
	}

	@Test
	public void testUpdateObjectView() throws Exception {
		try {
			_testUpdateObjectView(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateObjectView(_user);
	}

	private ObjectView _addObjectView(User user) throws Exception {
		return _objectViewLocalService.addObjectView(
			user.getUserId(), _objectDefinition.getObjectDefinitionId(), false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.emptyList(), Collections.emptyList());
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddObjectView(User user) throws Exception {
		ObjectView objectView = null;

		try {
			_setUser(user);

			objectView = _objectViewService.addObjectView(
				_objectDefinition.getObjectDefinitionId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.emptyList(), Collections.emptyList());
		}
		finally {
			if (objectView != null) {
				_objectViewLocalService.deleteObjectView(objectView);
			}
		}
	}

	private void _testGetObjectView(User user) throws Exception {
		ObjectView objectView = null;

		try {
			_setUser(user);

			objectView = _addObjectView(user);

			_objectViewService.getObjectView(objectView.getObjectViewId());
		}
		finally {
			if (objectView != null) {
				_objectViewLocalService.deleteObjectView(objectView);
			}
		}
	}

	private void _testUpdateObjectView(User user) throws Exception {
		ObjectView objectView = null;

		try {
			_setUser(user);

			objectView = _addObjectView(user);

			objectView = _objectViewService.updateObjectView(
				objectView.getObjectViewId(), false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.emptyList(), Collections.emptyList());
		}
		finally {
			if (objectView != null) {
				_objectViewLocalService.deleteObjectView(objectView);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectViewLocalService _objectViewLocalService;

	@Inject
	private ObjectViewService _objectViewService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}