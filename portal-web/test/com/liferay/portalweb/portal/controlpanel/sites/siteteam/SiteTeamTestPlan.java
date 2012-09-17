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

package com.liferay.portalweb.portal.controlpanel.sites.siteteam;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.addsitesteam.AddSitesTeamTests;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.addsiteteam.AddSiteTeamTests;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.addsiteteams.AddSiteTeamsTests;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.assignmemberssiteteamuser.AssignMembersSiteTeamUserTests;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.deletesiteteam.DeleteSiteTeamTests;
import com.liferay.portalweb.portal.controlpanel.sites.siteteam.editsiteteam.EditSiteTeamTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTeamTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSiteTeamTests.suite());
		testSuite.addTest(AddSitesTeamTests.suite());
		testSuite.addTest(AddSiteTeamsTests.suite());
		testSuite.addTest(AssignMembersSiteTeamUserTests.suite());
		testSuite.addTest(DeleteSiteTeamTests.suite());
		testSuite.addTest(EditSiteTeamTests.suite());

		return testSuite;
	}

}