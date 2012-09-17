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

package com.liferay.portalweb.portal.controlpanel.organizations.organization;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganizationnameduplicate.AddOrganizationNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganizationnamenull.AddOrganizationNameNullTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganizations.AddOrganizationsTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.advancedsearchorganization.AdvancedSearchOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.AssignMembersOrganizationUserTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.deleteorganization.DeleteOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.deleteorganizationassignmembers.DeleteOrganizationAssignMembersTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganizationSiteTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationssite.EditOrganizationsSiteTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.removemembersorganization.RemoveMembersOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.searchorganization.SearchOrganizationTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddOrganizationTests.suite());
		testSuite.addTest(AddOrganizationNameDuplicateTests.suite());
		testSuite.addTest(AddOrganizationNameNullTests.suite());
		testSuite.addTest(AddOrganizationsTests.suite());
		testSuite.addTest(AdvancedSearchOrganizationTests.suite());
		testSuite.addTest(AssignMembersOrganizationUserTests.suite());
		testSuite.addTest(DeleteOrganizationTests.suite());
		testSuite.addTest(DeleteOrganizationAssignMembersTests.suite());
		testSuite.addTest(EditOrganizationSiteTests.suite());
		testSuite.addTest(EditOrganizationsSiteTests.suite());
		testSuite.addTest(RemoveMembersOrganizationTests.suite());
		testSuite.addTest(SearchOrganizationTests.suite());

		return testSuite;
	}

}