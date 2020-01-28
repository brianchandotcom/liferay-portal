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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotActionKeys;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddDepotEntryWithoutPermissions() throws Exception {
		_withRegularUser((user, role) -> _addDepotEntry(user));
	}

	@Test
	public void testAddDepotEntryWithPermissions() throws Exception {
		_withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					DepotActionKeys.ADD_DEPOT_ENTRY);

				Assert.assertNotNull(_addDepotEntry(user));
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteDepotEntryWithoutPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> _depotEntryService.deleteDepotEntry(
				depotEntry.getDepotEntryId()));
	}

	@Test
	public void testDeleteDepotEntryWithPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.DELETE);

				Assert.assertNotNull(
					_depotEntryService.deleteDepotEntry(
						depotEntry.getDepotEntryId()));

				_depotEntries.remove(depotEntry);
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetDepotEntryWithoutPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> _depotEntryService.getDepotEntry(
				depotEntry.getDepotEntryId()));
	}

	@Test
	public void testGetDepotEntryWithPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Assert.assertNotNull(
					_depotEntryService.getDepotEntry(
						depotEntry.getDepotEntryId()));
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetGroupDepotEntryWithoutPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> _depotEntryService.getGroupDepotEntry(
				depotEntry.getGroupId()));
	}

	@Test
	public void testGetGroupDepotEntryWithPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Assert.assertNotNull(
					_depotEntryService.getGroupDepotEntry(
						depotEntry.getGroupId()));
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateDepotEntryWithoutPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser((user, role) -> _updateDepotEntry(depotEntry, user));
	}

	@Test
	public void testUpdateDepotEntryWithPermissions() throws Exception {
		DepotEntry depotEntry = _addDepotEntry(TestPropsValues.getUser());

		_withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, DepotEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.UPDATE);

				Assert.assertNotNull(_updateDepotEntry(depotEntry, user));
			});
	}

	private DepotEntry _addDepotEntry(User user) throws Exception {
		DepotEntry depotEntry = _depotEntryService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				user.getGroupId(), user.getUserId()));

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	private DepotEntry _updateDepotEntry(DepotEntry depotEntry, User user)
		throws PortalException {

		return _depotEntryService.updateDepotEntry(
			depotEntry.getDepotEntryId(),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.emptyMap(), new UnicodeProperties(),
			ServiceContextTestUtil.getServiceContext(
				user.getGroupId(), user.getUserId()));
	}

	private void _withRegularUser(
			UnsafeBiConsumer<User, Role, Exception> consumer)
		throws Exception {

		User user = UserTestUtil.addUser();
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_userLocalService.addRoleUser(role.getRoleId(), user);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			consumer.accept(user, role);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
			_userLocalService.deleteUser(user);
		}
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryService _depotEntryService;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private UserLocalService _userLocalService;

}