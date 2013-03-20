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
import com.liferay.portal.model.User;
import com.liferay.portal.security.membershippolicy.util.RoleTestUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
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
public abstract class BaseSiteMembershipPolicyTestCase {

	public static long[] getForbiddenRoleIds() {
		return _forbiddenRoleIds;
	}

	public static long[] getForbiddenSiteIds() {
		return _forbiddenSiteIds;
	}

	public static boolean getPropagateMembershipMethodFlag() {
		return _propagateMembershipMethodFlag;
	}

	public static boolean getPropagateRolesMethodFlag() {
		return _propagateRolesMethodFlag;
	}

	public static long[] getRequiredRoleIds() {
		return _requiredRoleIds;
	}

	public static long[] getRequiredSiteIds() {
		return _requiredSiteIds;
	}

	public static long[] getStandardRoleIds() {
		return _standardRoleIds;
	}

	public static long[] getStandardSiteIds() {
		return _standardSiteIds;
	}

	public static long[] getUserIds() {
		return _userIds;
	}

	public static boolean getVerifyMethodFlag() {
		return _verifyMethodFlag;
	}

	public static void setPropagateMembershipMethodFlag(
		boolean propagateMembershipMethodFlag) {

		_propagateMembershipMethodFlag = propagateMembershipMethodFlag;
	}

	public static void setPropagateRolesMethodFlag(
		boolean propagateRolesMethodFlag) {

		_propagateRolesMethodFlag = propagateRolesMethodFlag;
	}

	public static void setVerifyMethodFlag(boolean verifyMethodFlag) {
		_verifyMethodFlag = verifyMethodFlag;
	}

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();
	}

	protected long[] addForbiddenRoles() throws Exception {
		_forbiddenRoleIds[0] = RoleTestUtil.addGroupRole(_group.getGroupId());
		_forbiddenRoleIds[1] = RoleTestUtil.addGroupRole(_group.getGroupId());

		return _forbiddenRoleIds;
	}

	protected long[] addForbiddenSites() throws Exception {
		Group forbiddenSite1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_forbiddenSiteIds[0] = forbiddenSite1.getGroupId();

		Group forbiddenSite2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_forbiddenSiteIds[1] = forbiddenSite2.getGroupId();

		return _forbiddenSiteIds;
	}

	protected long[] addRequiredRoles() throws Exception {
		_requiredRoleIds[0] = RoleTestUtil.addGroupRole(_group.getGroupId());
		_requiredRoleIds[1] = RoleTestUtil.addGroupRole(_group.getGroupId());

		return _requiredRoleIds;
	}

	protected long[] addRequiredSites() throws Exception {
		Group requiredSite1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_requiredSiteIds[0] = requiredSite1.getGroupId();

		Group requiredSite2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_requiredSiteIds[1] = requiredSite2.getGroupId();

		return _requiredSiteIds;
	}

	protected long[] addStandardRoles() throws Exception {
		_standardRoleIds[0] = RoleTestUtil.addGroupRole(_group.getGroupId());
		_standardRoleIds[1] = RoleTestUtil.addGroupRole(_group.getGroupId());

		return _standardRoleIds;
	}

	protected long[] addStandardSites() throws Exception {
		Group standardSite1 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_standardSiteIds[0] = standardSite1.getGroupId();

		Group standardSite2 = GroupTestUtil.addGroup(
			ServiceTestUtil.randomString());
		_standardSiteIds[1] = standardSite2.getGroupId();

		return _standardSiteIds;
	}

	protected long[] addUsers() throws Exception {
		User user1= UserTestUtil.addUser(
			ServiceTestUtil.randomString(), _group.getGroupId());
		_userIds[0] = user1.getUserId();

		User user2 = UserTestUtil.addUser(
			ServiceTestUtil.randomString(), _group.getGroupId());
		_userIds[1] = user2.getUserId();

		return _userIds;
	}

	private static long[] _forbiddenRoleIds = new long[2];
	private static long[] _forbiddenSiteIds = new long[2];
	private static Group _group;
	private static boolean _propagateMembershipMethodFlag = false;
	private static boolean _propagateRolesMethodFlag = false;
	private static long[] _requiredRoleIds = new long[2];
	private static long[] _requiredSiteIds = new long[2];
	private static long[] _standardRoleIds = new long[2];
	private static long[] _standardSiteIds = new long[2];
	private static long[] _userIds = new long[2];
	private static boolean _verifyMethodFlag;

}