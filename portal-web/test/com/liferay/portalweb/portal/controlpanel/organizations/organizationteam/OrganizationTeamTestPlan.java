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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationteam;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationsteam.AddOrganizationsTeamTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteam.AddOrganizationTeamTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteams.AddOrganizationTeamsTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.assignmembersorganizationteamuser.AssignMembersOrganizationTeamUserTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.deleteorganizationteam.DeleteOrganizationTeamTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.editorganizationteam.EditOrganizationTeamTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationTeamTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddOrganizationTeamTests.suite());
		testSuite.addTest(AddOrganizationsTeamTests.suite());
		testSuite.addTest(AddOrganizationTeamsTests.suite());
		testSuite.addTest(AssignMembersOrganizationTeamUserTests.suite());
		testSuite.addTest(DeleteOrganizationTeamTests.suite());
		testSuite.addTest(EditOrganizationTeamTests.suite());

		return testSuite;
	}

}