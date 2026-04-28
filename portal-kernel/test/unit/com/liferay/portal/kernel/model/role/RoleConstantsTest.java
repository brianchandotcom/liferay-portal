/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model.role;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Stefano Motta
 */
public class RoleConstantsTest {

	@Test
	public void testIsProtected() {
		Assert.assertFalse(RoleConstants.isProtected(null));
		Assert.assertFalse(RoleConstants.isProtected(""));
		Assert.assertFalse(
			RoleConstants.isProtected(RandomTestUtil.randomString()));
		Assert.assertFalse(
			RoleConstants.isProtected(RoleConstants.ADMINISTRATOR));
		Assert.assertTrue(
			RoleConstants.isProtected(RoleConstants.CMS_ADMINISTRATOR));
	}

	@Test
	public void testToSystemRoleExternalReferenceCode() {
		Assert.assertEquals(
			"L_AA", RoleConstants.toSystemRoleExternalReferenceCode("AA"));
		Assert.assertEquals(
			"L_AA", RoleConstants.toSystemRoleExternalReferenceCode("aA"));
		Assert.assertEquals(
			"L_AA", RoleConstants.toSystemRoleExternalReferenceCode("aa"));
		Assert.assertEquals(
			"L_AA-BB",
			RoleConstants.toSystemRoleExternalReferenceCode("aa-bb"));
		Assert.assertEquals(
			"L_AA_BB",
			RoleConstants.toSystemRoleExternalReferenceCode("aa bb"));
		Assert.assertEquals(
			"L_AA_BB",
			RoleConstants.toSystemRoleExternalReferenceCode("aa_bb"));
		Assert.assertEquals(
			"L_AA_BB_CC",
			RoleConstants.toSystemRoleExternalReferenceCode("aa bb cc"));
	}

}