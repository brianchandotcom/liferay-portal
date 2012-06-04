/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.roles.role;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.roles.role.addorgrole.AddOrgRoleTests;
import com.liferay.portalweb.portal.controlpanel.roles.role.addregrole.AddRegRoleTests;
import com.liferay.portalweb.portal.controlpanel.roles.role.addsiterole.AddSiteRoleTests;
import com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgroleuser.AssignMembersOrgRoleUserTests;
import com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersregroleuser.AssignMembersRegRoleUserTests;
import com.liferay.portalweb.portal.controlpanel.roles.role.assignmemberssiteroleuser.AssignMembersSiteRoleUserTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddOrgRoleTests.suite());
		testSuite.addTest(AddRegRoleTests.suite());
		testSuite.addTest(AddSiteRoleTests.suite());
		testSuite.addTest(AssignMembersOrgRoleUserTests.suite());
		testSuite.addTest(AssignMembersRegRoleUserTests.suite());
		testSuite.addTest(AssignMembersSiteRoleUserTests.suite());

		return testSuite;
	}

}