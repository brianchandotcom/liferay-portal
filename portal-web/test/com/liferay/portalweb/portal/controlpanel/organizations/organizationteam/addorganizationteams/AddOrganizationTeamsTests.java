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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteams;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganizationSiteTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteam.AddOrganizationTeam1Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteam.AddOrganizationTeam2Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.addorganizationteam.AddOrganizationTeam3Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationTeamsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(EditOrganizationSiteTest.class);
		testSuite.addTestSuite(AddOrganizationTeam1Test.class);
		testSuite.addTestSuite(AddOrganizationTeam2Test.class);
		testSuite.addTestSuite(AddOrganizationTeam3Test.class);
		testSuite.addTestSuite(ViewOrganizationTeamsTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}