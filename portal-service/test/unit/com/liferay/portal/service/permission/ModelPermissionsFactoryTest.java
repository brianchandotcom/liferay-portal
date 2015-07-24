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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Jorge Ferrer
 */
@PrepareForTest({RoleLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class ModelPermissionsFactoryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(RoleLocalServiceUtil.class);

		Role guestRole = Mockito.mock(Role.class);

		Mockito.when(
			guestRole.getName()
		).thenReturn(
			RoleConstants.GUEST
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.GUEST))
		).thenReturn(
			guestRole
		);

		Role siteMemberRole = Mockito.mock(Role.class);

		Mockito.when(
			siteMemberRole.getName()
		).thenReturn(
			RoleConstants.SITE_MEMBER
		);

		when(
			RoleLocalServiceUtil.getDefaultGroupRole(Mockito.anyLong())
		).thenReturn(
			siteMemberRole
		);

		when(
			RoleLocalServiceUtil.getRole(
				Mockito.anyLong(), Mockito.eq(RoleConstants.SITE_MEMBER))
		).thenReturn(
			siteMemberRole
		);
	}

	@Test
	public void testCreateWithEmptyPermissions() throws Exception {
		String[] groupPermissions = {};
		String[] guestPermissions = {};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 0, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(0, roles.size());
	}

	@Test
	public void testCreateWithGroupPermissions() throws Exception {
		String[] groupPermissions = {ActionKeys.VIEW};
		String[] guestPermissions = {};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 1, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(1, roles.size());

		Role role = roles.iterator().next();

		Assert.assertEquals(RoleConstants.SITE_MEMBER, role.getName());
		Assert.assertEquals(
			ListUtil.fromArray(groupPermissions),
			modelPermissions.getActionIds(role));
	}

	@Test
	public void testCreateWithGroupPermissionsAndGroupZero() throws Exception {
		String[] groupPermissions = {ActionKeys.VIEW};
		String[] guestPermissions = {};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 0, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(0, roles.size());
	}

	@Test
	public void testCreateWithGuestAndGroupPermissions() throws Exception {
		String[] groupPermissions = {ActionKeys.VIEW};
		String[] guestPermissions = {ActionKeys.VIEW};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 1, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(2, roles.size());

		List<Role> viewActionIdRoles = modelPermissions.getRoles(
			ActionKeys.VIEW);

		Assert.assertEquals(2, viewActionIdRoles.size());
	}

	@Test
	public void testCreateWithGuestPermissions() throws Exception {
		String[] groupPermissions = {};
		String[] guestPermissions = {ActionKeys.VIEW};

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 1, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(1, roles.size());

		Role role = roles.iterator().next();

		Assert.assertEquals(RoleConstants.GUEST, role.getName());
		Assert.assertEquals(
			ListUtil.fromArray(guestPermissions),
			modelPermissions.getActionIds(role));
	}

	@Test
	public void testCreateWithNullPermissions() throws Exception {
		String[] groupPermissions = null;
		String[] guestPermissions = null;

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			_COMPANY_ID, 0, groupPermissions, guestPermissions);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertTrue(roles.isEmpty());
	}

	@Test
	public void testCreateWithoutParameters() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			parameterMap);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(0, roles.size());
	}

	@Test
	public void testCreateWithParameterForOneRole() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		String[] permissions = {ActionKeys.VIEW};

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			permissions);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			parameterMap);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(1, roles.size());

		Role role = roles.iterator().next();

		Assert.assertEquals(RoleConstants.GUEST, role.getName());
		Assert.assertEquals(
			ListUtil.fromArray(permissions),
			modelPermissions.getActionIds(role));
	}

	@Test
	public void testCreateWithParameterForTwoRoles() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		String[] permissions = {ActionKeys.VIEW};

		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.GUEST,
			permissions);
		parameterMap.put(
			ModelPermissionsFactory.MODEL_PERMISSIONS_PREFIX +
				RoleConstants.SITE_MEMBER,
			permissions);

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			parameterMap);

		Collection<Role> roles = modelPermissions.getRoles();

		Assert.assertEquals(2, roles.size());

		Role role = roles.iterator().next();

		Assert.assertEquals(
			ListUtil.fromArray(permissions),
			modelPermissions.getActionIds(role));
	}

	private static final long _COMPANY_ID = 1;

}