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

package com.liferay.list.type.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeDefinitionService;
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
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
public class ListTypeDefinitionServiceTest {

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
	public void testAddListTypeDefinition() throws Exception {
		try {
			_testAddListTypeDefinition(_user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have ADD_LIST_TYPE_DEFINITION permission for"));
		}

		_testAddListTypeDefinition(_adminUser);
	}

	@Test
	public void testDeleteListTypeDefinition() throws Exception {
		try {
			_testDeleteListTypeDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteListTypeDefinition(_adminUser, _adminUser);
		_testDeleteListTypeDefinition(_user, _user);
	}

	@Test
	public void testGetListTypeDefinition() throws Exception {
		try {
			_testGetListTypeDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetListTypeDefinition(_adminUser, _adminUser);
		_testGetListTypeDefinition(_user, _user);
	}

	@Test
	public void testUpdateListTypeDefinition() throws Exception {
		try {
			_testUpdateListTypeDefinition(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateListTypeDefinition(_adminUser, _adminUser);
		_testUpdateListTypeDefinition(_user, _user);
	}

	private ListTypeDefinition _addListTypeDefinition(User user)
		throws Exception {

		return _listTypeDefinitionLocalService.addListTypeDefinition(
			user.getUserId(),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()));
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddListTypeDefinition(User user) throws Exception {
		ListTypeDefinition listTypeDefinition = null;

		try {
			_setUser(user);

			listTypeDefinition =
				_listTypeDefinitionService.addListTypeDefinition(
					Collections.singletonMap(
						LocaleUtil.getDefault(),
						RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeDefinition != null) {
				_listTypeDefinitionLocalService.deleteListTypeDefinition(
					listTypeDefinition);
			}
		}
	}

	private void _testDeleteListTypeDefinition(User ownerUser, User user)
		throws Exception {

		ListTypeDefinition deleteListTypeDefinition = null;
		ListTypeDefinition listTypeDefinition = null;

		try {
			_setUser(user);

			listTypeDefinition = _addListTypeDefinition(ownerUser);

			deleteListTypeDefinition =
				_listTypeDefinitionService.deleteListTypeDefinition(
					listTypeDefinition.getListTypeDefinitionId());
		}
		finally {
			if (deleteListTypeDefinition == null) {
				_listTypeDefinitionLocalService.deleteListTypeDefinition(
					listTypeDefinition);
			}
		}
	}

	private void _testGetListTypeDefinition(User ownerUser, User user)
		throws Exception {

		ListTypeDefinition listTypeDefinition = null;

		try {
			_setUser(user);

			listTypeDefinition = _addListTypeDefinition(ownerUser);

			_listTypeDefinitionService.getListTypeDefinition(
				listTypeDefinition.getListTypeDefinitionId());
		}
		finally {
			if (listTypeDefinition != null) {
				_listTypeDefinitionLocalService.deleteListTypeDefinition(
					listTypeDefinition);
			}
		}
	}

	private void _testUpdateListTypeDefinition(User ownerUser, User user)
		throws Exception {

		ListTypeDefinition listTypeDefinition = null;

		try {
			_setUser(user);

			listTypeDefinition = _addListTypeDefinition(ownerUser);

			listTypeDefinition =
				_listTypeDefinitionService.updateListTypeDefinition(
					listTypeDefinition.getListTypeDefinitionId(),
					Collections.singletonMap(
						LocaleUtil.getDefault(),
						RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeDefinition != null) {
				_listTypeDefinitionLocalService.deleteListTypeDefinition(
					listTypeDefinition);
			}
		}
	}

	private User _adminUser;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeDefinitionService _listTypeDefinitionService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}