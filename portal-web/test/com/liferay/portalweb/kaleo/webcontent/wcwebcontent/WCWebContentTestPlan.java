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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent;

import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontent.AddWebContentTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentdraft.AddWebContentDraftTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentnoworkflow.AddWebContentNoWorkflowTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentnoworkflowscopepage.AddWebContentNoWorkflowScopePageTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopecommunity.AddWebContentScopeCommunityTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopeglobal.AddWebContentScopeGlobalTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopeguest.AddWebContentScopeGuestTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopemycommunity.AddWebContentScopeMyCommunityTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopeorganization.AddWebContentScopeOrganizationTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopepage.AddWebContentScopePageTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentassignedtomeactions.DeleteWebContentAssignedToMeActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentassignedtomyrolesactions.DeleteWebContentAssignedToMyRolesActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompletedactions.DeleteWebContentCompletedActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompleteddrafteditdetails.DeleteWebContentCompletedDraftEditDetailsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompletedediteddetails.DeleteWebContentCompletedEditedDetailsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompletededitedlist.DeleteWebContentCompletedEditedListTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.editwebcontentassignedtomeactions.EditWebContentAssignedToMeActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.editwebcontentassignedtomyrolesactions.EditWebContentAssignedToMyRolesActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.editwebcontentcompletedactions.EditWebContentCompletedActionsTests;
import com.liferay.portalweb.kaleo.webcontent.wcwebcontent.editwebcontentcompleteddraftactions.EditWebContentCompletedDraftActionsTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentTests.suite());
		testSuite.addTest(AddWebContentDraftTests.suite());
		testSuite.addTest(AddWebContentNoWorkflowTests.suite());
		testSuite.addTest(AddWebContentNoWorkflowScopePageTests.suite());
		testSuite.addTest(AddWebContentScopeCommunityTests.suite());
		testSuite.addTest(AddWebContentScopeGlobalTests.suite());
		testSuite.addTest(AddWebContentScopeGuestTests.suite());
		testSuite.addTest(AddWebContentScopeMyCommunityTests.suite());
		testSuite.addTest(AddWebContentScopeOrganizationTests.suite());
		testSuite.addTest(AddWebContentScopePageTests.suite());
		testSuite.addTest(DeleteWebContentAssignedToMeActionsTests.suite());
		testSuite.addTest(
			DeleteWebContentAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(DeleteWebContentCompletedActionsTests.suite());
		testSuite.addTest(
			DeleteWebContentCompletedDraftEditDetailsTests.suite());
		testSuite.addTest(DeleteWebContentCompletedEditedDetailsTests.suite());
		testSuite.addTest(DeleteWebContentCompletedEditedListTests.suite());
		testSuite.addTest(EditWebContentAssignedToMeActionsTests.suite());
		testSuite.addTest(EditWebContentAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(EditWebContentCompletedActionsTests.suite());
		testSuite.addTest(EditWebContentCompletedDraftActionsTests.suite());

		return testSuite;
	}

}