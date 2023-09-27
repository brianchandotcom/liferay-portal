/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membership.policy;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.After;
import org.junit.Before;

/**
 * @author Roberto Díaz
 */
public abstract class BaseMembershipPolicyTestCase {

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
		group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_propagateMembership = false;
		_propagateRoles = false;
		_userIds = new long[2];
		_verify = false;
	}

	protected long[] addUsers() throws Exception {
		User user1 = UserTestUtil.addUser(group.getGroupId());

		_userIds[0] = user1.getUserId();

		User user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), group.getGroupId());

		_userIds[1] = user2.getUserId();

		return _userIds;
	}

	@DeleteAfterTestRun
	protected Group group;

	private static boolean _propagateMembership;
	private static boolean _propagateRoles;
	private static long[] _userIds = new long[2];
	private static boolean _verify;

}