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

package com.liferay.portalweb.portal.controlpanel.organizations;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.OrganizationTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationaddress.OrganizationAddressTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationcomment.OrganizationCommentTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationemailaddress.OrganizationEmailAddressTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.OrganizationPageTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationphonenumber.OrganizationPhoneNumberTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationservice.OrganizationServiceTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.OrganizationTeamTestPlan;
import com.liferay.portalweb.portal.controlpanel.organizations.organizationwebsite.OrganizationWebsiteTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationsTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(OrganizationTestPlan.suite());
		testSuite.addTest(OrganizationCommentTestPlan.suite());
		testSuite.addTest(OrganizationAddressTestPlan.suite());
		testSuite.addTest(OrganizationEmailAddressTestPlan.suite());
		testSuite.addTest(OrganizationPageTestPlan.suite());
		testSuite.addTest(OrganizationPhoneNumberTestPlan.suite());
		testSuite.addTest(OrganizationServiceTestPlan.suite());
		testSuite.addTest(OrganizationTeamTestPlan.suite());
		testSuite.addTest(OrganizationWebsiteTestPlan.suite());

		return testSuite;
	}

}