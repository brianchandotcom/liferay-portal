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

import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.membershippolicy.samples.TestOrganizationMembershipPolicy;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;

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

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1);

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			OrganizationMembershipPolicy.class,
			new TestOrganizationMembershipPolicy(), properties);

		serviceRegistrations.add(serviceRegistration);

		organization = OrganizationTestUtil.addOrganization();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_forbiddenOrganizationIds = new long[2];
		_forbiddenRoleIds = new long[2];
		_requiredOrganizationIds = new long[2];
		_requiredRoleIds = new long[2];
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

	@DeleteAfterTestRun
	protected Organization organization;

	private static long[] _forbiddenOrganizationIds = new long[2];
	private static long[] _forbiddenRoleIds = new long[2];
	private static long[] _requiredOrganizationIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardOrganizationIds = new long[2];
	private static long[] _standardRoleIds = new long[2];

}