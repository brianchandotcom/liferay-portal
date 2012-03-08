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

package com.liferay.portalweb.portal.controlpanel.sites.site;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.activatestagingsite.ActivateStagingSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addmemberssiteguestuser.AddMembersSiteGuestUserTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addmemberssiteuser.AddMembersSiteUserTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitemultiple.AddSiteMultipleTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitenamecomma.AddSiteNameCommaTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitenameduplicate.AddSiteNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitenamenull.AddSiteNameNullTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitenamenumber.AddSiteNameNumberTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsitenamestar.AddSiteNameStarTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.assignmemberssite.AssignMembersSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.autoapprovependingmembers.AutoApprovePendingMembersTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.deactivatestagingsite.DeactivateStagingSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.deactivatestagingsitenavigatingstaging.DeactivateStagingSiteNavigatingStagingTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.deletesite.DeleteSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.deletesiteassignmembers.DeleteSiteAssignMembersTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.deletesiteguest.DeleteSiteGuestTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.editsite.EditSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.joinsiteinactive.JoinSiteInactiveTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.joinsiteopen.JoinSiteOpenTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.joinsiteprivate.JoinSitePrivateTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.joinsiterestricted.JoinSiteRestrictedTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.leavesite.LeaveSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.removememberssite.RemoveMembersSiteTests;
import com.liferay.portalweb.portal.controlpanel.sites.site.searchsite.SearchSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ActivateStagingSiteTests.suite());
		testSuite.addTest(AddMembersSiteGuestUserTests.suite());
		testSuite.addTest(AddMembersSiteUserTests.suite());
		testSuite.addTest(AddSiteTests.suite());
		testSuite.addTest(AddSiteMultipleTests.suite());
		testSuite.addTest(AddSiteNameCommaTests.suite());
		testSuite.addTest(AddSiteNameDuplicateTests.suite());
		testSuite.addTest(AddSiteNameNullTests.suite());
		testSuite.addTest(AddSiteNameNumberTests.suite());
		testSuite.addTest(AddSiteNameStarTests.suite());
		testSuite.addTest(AssignMembersSiteTests.suite());
		testSuite.addTest(AutoApprovePendingMembersTests.suite());
		testSuite.addTest(DeactivateStagingSiteTests.suite());
		testSuite.addTest(DeactivateStagingSiteNavigatingStagingTests.suite());
		testSuite.addTest(DeleteSiteTests.suite());
		testSuite.addTest(DeleteSiteAssignMembersTests.suite());
		testSuite.addTest(DeleteSiteGuestTests.suite());
		testSuite.addTest(EditSiteTests.suite());
		testSuite.addTest(JoinSiteInactiveTests.suite());
		testSuite.addTest(JoinSiteOpenTests.suite());
		testSuite.addTest(JoinSitePrivateTests.suite());
		testSuite.addTest(JoinSiteRestrictedTests.suite());
		testSuite.addTest(LeaveSiteTests.suite());
		testSuite.addTest(RemoveMembersSiteTests.suite());
		testSuite.addTest(SearchSiteTests.suite());

		return testSuite;
	}

}