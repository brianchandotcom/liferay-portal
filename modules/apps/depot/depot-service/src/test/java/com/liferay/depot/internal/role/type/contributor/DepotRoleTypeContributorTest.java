/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.role.type.contributor;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.internal.roles.admin.role.type.contributor.DepotRoleTypeContributor;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class DepotRoleTypeContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsAllowAssignMembersWithAdministrator() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR
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
			DepotRolesConstants.ASSET_LIBRARY_MEMBER
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
			DepotRolesConstants.ASSET_LIBRARY_OWNER
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
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR
		);

		Assert.assertTrue(
			depotRoleTypeContributor.isAllowDefinePermissions(role));
	}

	@Test
	public void testIsAllowDefinePermissionsWithMember() {
		DepotRoleTypeContributor depotRoleTypeContributor =
			new DepotRoleTypeContributor();

		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			DepotRolesConstants.ASSET_LIBRARY_MEMBER
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
			DepotRolesConstants.ASSET_LIBRARY_OWNER
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
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR
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
			DepotRolesConstants.ASSET_LIBRARY_MEMBER
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
			DepotRolesConstants.ASSET_LIBRARY_OWNER
		);

		Assert.assertTrue(!depotRoleTypeContributor.isAllowDelete(role));
	}

}