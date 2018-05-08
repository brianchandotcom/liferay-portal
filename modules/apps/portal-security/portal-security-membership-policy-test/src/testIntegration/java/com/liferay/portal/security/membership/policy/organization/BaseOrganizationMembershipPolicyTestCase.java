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

package com.liferay.portal.security.membership.policy.organization;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.security.membership.policy.BaseMembershipPolicyTestCase;

import org.junit.After;
import org.junit.Before;

/**
 * @author Roberto Díaz
 */
public abstract class BaseOrganizationMembershipPolicyTestCase
	extends BaseMembershipPolicyTestCase {

	public static long[] getForbiddenOrganizationIds() {
		return _forbiddenOrganizationIds;
	}

	public static long[] getForbiddenRoleIds() {
		return _forbiddenRoleIds;
	}

	public static long[] getRequiredOrganizationIds() {
		return _requiredOrganizationIds;
	}

	public static long[] getRequiredRoleIds() {
		return _requiredRoleIds;
	}

	public static long[] getStandardOrganizationIds() {
		return _standardOrganizationIds;
	}

	public static long[] getStandardRoleIds() {
		return _standardRoleIds;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		organization = OrganizationTestUtil.addOrganization();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		deleteOrganizations(_forbiddenOrganizationIds);

		_forbiddenOrganizationIds = new long[2];
		_forbiddenRoleIds = new long[2];

		deleteOrganizations(_requiredOrganizationIds);

		_requiredOrganizationIds = new long[2];
		_requiredRoleIds = new long[2];

		deleteOrganizations(_standardOrganizationIds);

		_standardOrganizationIds = new long[2];
		_standardRoleIds = new long[2];
	}

	protected long[] addForbiddenOrganizations() throws Exception {
		Organization forbiddenOrganization1 =
			OrganizationTestUtil.addOrganization();

		_forbiddenOrganizationIds[0] =
			forbiddenOrganization1.getOrganizationId();

		Organization forbiddenOrganization2 =
			OrganizationTestUtil.addOrganization();

		_forbiddenOrganizationIds[1] =
			forbiddenOrganization2.getOrganizationId();

		return _forbiddenOrganizationIds;
	}

	protected long[] addForbiddenRoles() throws Exception {
		_forbiddenRoleIds[0] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());
		_forbiddenRoleIds[1] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());

		return _forbiddenRoleIds;
	}

	protected long[] addRequiredOrganizations() throws Exception {
		Organization requiredOrganization1 =
			OrganizationTestUtil.addOrganization();

		_requiredOrganizationIds[0] = requiredOrganization1.getOrganizationId();

		Organization requiredOrganization2 =
			OrganizationTestUtil.addOrganization();

		_requiredOrganizationIds[1] = requiredOrganization2.getOrganizationId();

		return _requiredOrganizationIds;
	}

	protected long[] addRequiredRoles() throws Exception {
		_requiredRoleIds[0] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());
		_requiredRoleIds[1] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());

		return _requiredRoleIds;
	}

	protected long[] addStandardOrganizations() throws Exception {
		Organization standardOrganization1 =
			OrganizationTestUtil.addOrganization();

		_standardOrganizationIds[0] = standardOrganization1.getOrganizationId();

		Organization standardOrganization2 =
			OrganizationTestUtil.addOrganization();

		_standardOrganizationIds[1] = standardOrganization2.getOrganizationId();

		return _standardOrganizationIds;
	}

	protected long[] addStandardRoles() throws Exception {
		_standardRoleIds[0] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());
		_standardRoleIds[1] = RoleTestUtil.addOrganizationRole(
			group.getGroupId());

		return _standardRoleIds;
	}

	protected void deleteOrganizations(long[] organizationIds)
		throws PortalException {

		for (long organizationId : organizationIds) {
			Organization organization =
				OrganizationLocalServiceUtil.fetchOrganization(organizationId);

			if (organization == null) {
				continue;
			}

			for (User user :
					UserLocalServiceUtil.getOrganizationUsers(organizationId)) {

				UserLocalServiceUtil.deleteUser(user);
			}

			OrganizationLocalServiceUtil.deleteOrganization(organization);
		}
	}

	@DeleteAfterTestRun
	protected Organization organization;

	private static long[] _forbiddenOrganizationIds = new long[2];
	private static long[] _forbiddenRoleIds = new long[2];
	private static long[] _requiredOrganizationIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardOrganizationIds = new long[2];
	private static long[] _standardRoleIds = new long[2];

}