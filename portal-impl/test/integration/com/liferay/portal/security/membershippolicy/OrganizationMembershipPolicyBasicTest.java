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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class OrganizationMembershipPolicyBasicTest
	extends BaseOrganizationMembershipPolicyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsMembershipAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();

		Assert.assertTrue(
			OrganizationMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], standardOrganizationIds[0]));
	}

	@Test
	public void testIsMembershipNotAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] forbiddenOrganizationIds = addForbiddenOrganizations();

		Assert.assertFalse(
			OrganizationMembershipPolicyUtil.isMembershipAllowed(
				userIds[0], forbiddenOrganizationIds[0]));
	}

	@Test
	public void testIsMembershipNotRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();

		Assert.assertFalse(
			OrganizationMembershipPolicyUtil.isMembershipRequired(
				userIds[0], standardOrganizationIds[0]));
	}

	@Test
	public void testIsMembershipRequired() throws Exception {
		long[] userIds = addUsers();
		long[] requiredOrganizationIds = addRequiredOrganizations();

		Assert.assertTrue(
			OrganizationMembershipPolicyUtil.isMembershipRequired(
				userIds[0], requiredOrganizationIds[0]));
	}

	@Test
	public void testIsRoleAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();
		long[] standardRoleIds = addStandardRoles();

		Assert.assertTrue(
			OrganizationMembershipPolicyUtil.isRoleAllowed(
				userIds[0], standardOrganizationIds[0], standardRoleIds[0]));
	}

	@Test
	public void testIsRoleNotAllowed() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();
		long[] forbiddenRoleIds = addForbiddenRoles();

		Assert.assertFalse(
			OrganizationMembershipPolicyUtil.isRoleAllowed(
				userIds[0], standardOrganizationIds[0], forbiddenRoleIds[0]));
	}

	@Test
	public void testIsRoleNotRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();
		long[] standardRoleIds = addStandardRoles();

		Assert.assertFalse(
			OrganizationMembershipPolicyUtil.isRoleRequired(
				userIds[0], standardOrganizationIds[0], standardRoleIds[0]));
	}

	@Test
	public void testIsRoleRequired() throws Exception {
		long[] userIds = addUsers();
		long[] standardOrganizationIds = addStandardOrganizations();
		long[] requiredRoleIds = addRequiredRoles();

		Assert.assertTrue(
			OrganizationMembershipPolicyUtil.isRoleRequired(
				userIds[0], standardOrganizationIds[0], requiredRoleIds[0]));
	}

}