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

package com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent;

import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentassignedtome.ViewWebContentAssignedToMeTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentassignedtomyroles.ViewWebContentAssignedToMyRolesTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentcompleted.ViewWebContentCompletedTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentrejected.ViewWebContentRejectedTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentresubmitted.ViewWebContentResubmittedTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentversion2assignedtome.ViewWebContentVersion2AssignedToMeTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentversion2assignedtomyroles.ViewWebContentVersion2AssignedToMyRolesTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentversion2completed.ViewWebContentVersion2CompletedTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentversion2rejected.ViewWebContentVersion2RejectedTests;
import com.liferay.portalweb.kaleo.webcontentdisplay.wcwebcontent.viewwebcontentversion2resubmitted.ViewWebContentVersion2ResubmittedTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewWebContentAssignedToMeTests.suite());
		testSuite.addTest(ViewWebContentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewWebContentCompletedTests.suite());
		testSuite.addTest(ViewWebContentRejectedTests.suite());
		testSuite.addTest(ViewWebContentResubmittedTests.suite());
		testSuite.addTest(ViewWebContentVersion2AssignedToMeTests.suite());
		testSuite.addTest(ViewWebContentVersion2AssignedToMyRolesTests.suite());
		testSuite.addTest(ViewWebContentVersion2CompletedTests.suite());
		testSuite.addTest(ViewWebContentVersion2RejectedTests.suite());
		testSuite.addTest(ViewWebContentVersion2ResubmittedTests.suite());

		return testSuite;
	}

}