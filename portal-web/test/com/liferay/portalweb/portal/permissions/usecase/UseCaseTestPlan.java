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

package com.liferay.portalweb.portal.permissions.usecase;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.permissions.usecase.permissionsgroupcompanydemo.PermissionsGroupCompanyDemoTests;
import com.liferay.portalweb.portal.permissions.usecase.permissionsindividualscopedemo.PermissionsIndividualScopeDemoTests;
import com.liferay.portalweb.portal.permissions.usecase.permissionsteamdemo.PermissionsTeamDemoTests;
import com.liferay.portalweb.portal.permissions.usecase.permissionsuserpersonalsitedemo.PermissionsUserPersonalSiteDemoTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UseCaseTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(PermissionsGroupCompanyDemoTests.suite());
		testSuite.addTest(PermissionsIndividualScopeDemoTests.suite());
		testSuite.addTest(PermissionsTeamDemoTests.suite());
		testSuite.addTest(PermissionsUserPersonalSiteDemoTests.suite());

		return testSuite;
	}

}