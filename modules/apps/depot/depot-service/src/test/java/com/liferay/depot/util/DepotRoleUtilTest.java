/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.util;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Stefano Motta
 */
public class DepotRoleUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testFilter() {
		Role role1 = _mockRole(DepotRolesConstants.SUBTYPE_PROJECT);
		Role role2 = _mockRole(DepotRolesConstants.SUBTYPE_SPACE);
		Role role3 = _mockRole(null);
		Role role4 = _mockRole("");

		Assert.assertEquals(
			Arrays.asList(role1, role2, role3, role4),
			DepotRoleUtil.filter(
				(DepotEntry)null, Arrays.asList(role1, role2, role3, role4)));
		Assert.assertEquals(
			Arrays.asList(role3, role4),
			DepotRoleUtil.filter(
				_mockDepotEntry(DepotConstants.TYPE_ASSET_LIBRARY),
				Arrays.asList(role1, role2, role3, role4)));
		Assert.assertEquals(
			Arrays.asList(role1, role3, role4),
			DepotRoleUtil.filter(
				_mockDepotEntry(DepotConstants.TYPE_PROJECT),
				Arrays.asList(role1, role2, role3, role4)));
		Assert.assertEquals(
			Arrays.asList(role2, role3, role4),
			DepotRoleUtil.filter(
				_mockDepotEntry(DepotConstants.TYPE_SPACE),
				Arrays.asList(role1, role2, role3, role4)));
	}

	private DepotEntry _mockDepotEntry(int depotType) {
		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.when(
			depotEntry.getType()
		).thenReturn(
			depotType
		);

		return depotEntry;
	}

	private Role _mockRole(String subtype) {
		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getSubtype()
		).thenReturn(
			subtype
		);

		return role;
	}

}