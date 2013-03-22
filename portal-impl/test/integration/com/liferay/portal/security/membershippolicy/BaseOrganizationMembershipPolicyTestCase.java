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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public abstract class BaseOrganizationMembershipPolicyTestCase {

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

	public static long[] getUserIds() {
		return _userIds;
	}

	public static boolean isPropagateMembership() {
		return _propagateMembership;
	}

	public static boolean isPropagateRoles() {
		return _propagateRoles;
	}

	public static boolean isVerify() {
		return _verify;
	}

	public static void setPropagateMembership(boolean propagateMembership) {
		_propagateMembership = propagateMembership;
	}

	public static void setPropagateRoles(boolean propagateRoles) {
		_propagateRoles = propagateRoles;
	}

	public static void setVerify(boolean verify) {
		_verify = verify;
	}

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		group = GroupTestUtil.addGroup();
		organization = OrganizationTestUtil.addOrganization();
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

	protected long[] addUsers() throws Exception {
		User user1= UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		_userIds[0] = user1.getUserId();

		User user2 = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), group.getGroupId());

		_userIds[1] = user2.getUserId();

		return _userIds;
	}

	protected Group group;
	protected Organization organization;

	private static long[] _forbiddenOrganizationIds = new long[2];
	private static long[] _forbiddenRoleIds = new long[2];
	private static boolean _propagateMembership;
	private static boolean _propagateRoles;
	private static long[] _requiredOrganizationIds = new long[2];
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _standardOrganizationIds = new long[2];
	private static long[] _standardRoleIds = new long[2];
	private static long[] _userIds = new long[2];
	private static boolean _verify;

}