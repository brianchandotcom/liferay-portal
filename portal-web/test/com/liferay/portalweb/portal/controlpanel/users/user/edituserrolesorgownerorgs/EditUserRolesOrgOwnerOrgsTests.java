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

package com.liferay.portalweb.portal.controlpanel.users.user.edituserrolesorgownerorgs;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization1Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization2Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization3Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.AssignMembersOrganization1UserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.AssignMembersOrganization2UserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.AssignMembersOrganization3UserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.ViewAssignMembersOrganization1UserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.ViewAssignMembersOrganization2UserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.ViewAssignMembersOrganization3UserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUserRolesOrgOwnerOrgsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddOrganization1Test.class);
		testSuite.addTestSuite(AssignMembersOrganization1UserTest.class);
		testSuite.addTestSuite(ViewAssignMembersOrganization1UserTest.class);
		testSuite.addTestSuite(EditUserRolesOrgOwnerOrg1Test.class);
		testSuite.addTestSuite(ViewEditUserRolesOrgOwnerOrg1Test.class);
		testSuite.addTestSuite(AddOrganization2Test.class);
		testSuite.addTestSuite(AssignMembersOrganization2UserTest.class);
		testSuite.addTestSuite(ViewAssignMembersOrganization2UserTest.class);
		testSuite.addTestSuite(EditUserRolesOrgOwnerOrg2Test.class);
		testSuite.addTestSuite(ViewEditUserRolesOrgOwnerOrg2Test.class);
		testSuite.addTestSuite(AddOrganization3Test.class);
		testSuite.addTestSuite(AssignMembersOrganization3UserTest.class);
		testSuite.addTestSuite(ViewAssignMembersOrganization3UserTest.class);
		testSuite.addTestSuite(EditUserRolesOrgOwnerOrg3Test.class);
		testSuite.addTestSuite(ViewEditUserRolesOrgOwnerOrg3Test.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}