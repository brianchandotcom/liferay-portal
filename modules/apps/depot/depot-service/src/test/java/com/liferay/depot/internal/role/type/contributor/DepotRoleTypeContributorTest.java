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

package com.liferay.depot.internal.role.type.contributor;

import com.liferay.depot.internal.constants.DepotRolesConstants;
import com.liferay.portal.kernel.model.Role;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class DepotRoleTypeContributorTest {

	@Test
	public void testIsAllowAssignMembersWithAdministrator() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_ADMINISTRATOR
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowAssignMembers(role));
	}

	@Test
	public void testIsAllowAssignMembersWithMember() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_MEMBER
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowAssignMembers(role));
	}

	@Test
	public void testIsAllowAssignMembersWithOwner() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_OWNER
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowAssignMembers(role));
	}

	@Test
	public void testIsAllowDefinePermissionsWithAdministrator() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_ADMINISTRATOR
		);

		Assert.assertTrue(
			!depotRoleTypeContributor.isAllowDefinePermissions(role));
	}

	@Test
	public void testIsAllowDefinePermissionsWithMember() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_MEMBER
		);

		Assert.assertTrue(
			depotRoleTypeContributor.isAllowDefinePermissions(role));
	}

	@Test
	public void testIsAllowDefinePermissionsWithOwner() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_OWNER
		);

		Assert.assertTrue(
			!depotRoleTypeContributor.isAllowDefinePermissions(role));
	}

	@Test
	public void testIsAllowDeleteWithAdministrator() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_ADMINISTRATOR
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowDelete(role));
	}

	@Test
	public void testIsAllowDeleteWithMember() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_MEMBER
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowDelete(role));
	}

	@Test
	public void testIsAllowDeleteWithOwner() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.DEPOT_OWNER
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowDelete(role));
	}

}