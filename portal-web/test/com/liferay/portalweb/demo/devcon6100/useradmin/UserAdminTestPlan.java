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

package com.liferay.portalweb.demo.devcon6100.useradmin;

import com.liferay.portalweb.demo.devcon6100.useradmin.permissionsgroupcompanylevel.PermissionsGroupCompanyLevelTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.permissionsindividualscope.PermissionsIndividualScopeTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.permissionssitelevel.PermissionsSiteLevelTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.permissionssitetemplate.PermissionsSiteTemplateTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.permissionsteam.PermissionsTeamTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.permissionsuserpersonalsite.PermissionsUserPersonalSiteTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.usermanagementorganizations.UserManagementOrganizationsTests;
import com.liferay.portalweb.demo.devcon6100.useradmin.usermanagementuserprofile.UserManagementUserProfileTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserAdminTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(PermissionsGroupCompanyLevelTests.suite());
		testSuite.addTest(PermissionsIndividualScopeTests.suite());
		testSuite.addTest(PermissionsSiteLevelTests.suite());
		testSuite.addTest(PermissionsSiteTemplateTests.suite());
		testSuite.addTest(PermissionsTeamTests.suite());
		testSuite.addTest(PermissionsUserPersonalSiteTests.suite());
		testSuite.addTest(UserManagementOrganizationsTests.suite());
		testSuite.addTest(UserManagementUserProfileTests.suite());
		return testSuite;
	}

}