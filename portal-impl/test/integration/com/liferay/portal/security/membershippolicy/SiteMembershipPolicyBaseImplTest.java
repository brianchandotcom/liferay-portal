/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyBaseImplTest
	extends BaseSiteMembershipPolicyTestCase {

	@Test
	public void testIsMembershipAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();

		Assert.assertTrue(
			SiteMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], standardSiteIds[0]));
	}

	@Test
	public void testIsMembershipNotAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] forbiddenSiteIds = addForbiddenSites();

		Assert.assertFalse(
			SiteMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], forbiddenSiteIds[0]));
	}

	@Test
	public void testIsMembershipNotRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();

		Assert.assertFalse(
			SiteMembershipPolicyUtil.isMembershipRequired(
				userIds[0], standardSiteIds[0]));
	}

	@Test
	public void testIsMembershipRequired() throws Exception {
		long[] userIds = addUsers();
		long[] requiredSiteIds = addRequiredSites();

		Assert.assertTrue(
			SiteMembershipPolicyUtil.isMembershipRequired(
				userIds[0], requiredSiteIds[0]));
	}

	@Test
	public void testIsRoleAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] standardRoleIds = addStandardRoles();

		Assert.assertTrue(
			SiteMembershipPolicyUtil.isRoleAllowed(
				userIds[0], standardSiteIds[0], standardRoleIds[0]));
	}

	@Test
	public void testIsRoleNotAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] forbiddenRoleIds = addForbiddenRoles();

		Assert.assertFalse(
			SiteMembershipPolicyUtil.isRoleAllowed(
				userIds[0], standardSiteIds[0], forbiddenRoleIds[0]));
	}

	@Test
	public void testIsRoleNotRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] standardRoleIds = addStandardRoles();

		Assert.assertFalse(
			SiteMembershipPolicyUtil.isRoleRequired(
				userIds[0], standardSiteIds[0], standardRoleIds[0]));
	}

	@Test
	public void testIsRoleRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] requiredRoleIds = addRequiredRoles();

		Assert.assertTrue(
			SiteMembershipPolicyUtil.isRoleRequired(
				userIds[0], standardSiteIds[0], requiredRoleIds[0]));
	}

}