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
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.list.type.service.ListTypeEntryService;
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
public class ListTypeEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());
		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()));
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
	public void testAddListTypeEntry() throws Exception {
		try {
			_testAddListTypeEntry(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testAddListTypeEntry(_user);
	}

	@Test
	public void testDeleteListTypeEntry() throws Exception {
		try {
			_testDeleteListTypeEntry(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testDeleteListTypeEntry(_user);
	}

	@Test
	public void testGetListTypeEntry() throws Exception {
		try {
			_testGetListTypeEntry(_defaultUser);
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetListTypeEntry(_user);
	}

	@Test
	public void testUpdateListTypeEntry() throws Exception {
		try {
			_testUpdateListTypeEntry(_defaultUser);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _defaultUser.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateListTypeEntry(_user);
	}

	private ListTypeEntry _addListTypeEntry(User user) throws Exception {
		return _listTypeEntryLocalService.addListTypeEntry(
			user.getUserId(), _listTypeDefinition.getListTypeDefinitionId(),
			RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddListTypeEntry(User user) throws Exception {
		ListTypeEntry listTypeEntry = null;

		try {
			_setUser(user);

			listTypeEntry = _listTypeEntryService.addListTypeEntry(
				_listTypeDefinition.getListTypeDefinitionId(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeEntry != null) {
				_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
			}
		}
	}

	private void _testDeleteListTypeEntry(User user) throws Exception {
		ListTypeEntry deleteListTypeEntry = null;
		ListTypeEntry listTypeEntry = null;

		try {
			_setUser(user);

			listTypeEntry = _addListTypeEntry(user);

			deleteListTypeEntry = _listTypeEntryService.deleteListTypeEntry(
				listTypeEntry.getListTypeEntryId());
		}
		finally {
			if (deleteListTypeEntry == null) {
				_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
			}
		}
	}

	private void _testGetListTypeEntry(User user) throws Exception {
		ListTypeEntry listTypeEntry = null;

		try {
			_setUser(user);

			listTypeEntry = _addListTypeEntry(user);

			_listTypeEntryService.getListTypeEntry(
				listTypeEntry.getListTypeEntryId());
		}
		finally {
			if (listTypeEntry != null) {
				_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
			}
		}
	}

	private void _testUpdateListTypeEntry(User user) throws Exception {
		ListTypeEntry listTypeEntry = null;

		try {
			_setUser(user);

			listTypeEntry = _addListTypeEntry(user);

			listTypeEntry = _listTypeEntryService.updateListTypeEntry(
				listTypeEntry.getListTypeEntryId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()));
		}
		finally {
			if (listTypeEntry != null) {
				_listTypeEntryLocalService.deleteListTypeEntry(listTypeEntry);
			}
		}
	}

	private User _defaultUser;

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ListTypeEntryService _listTypeEntryService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}