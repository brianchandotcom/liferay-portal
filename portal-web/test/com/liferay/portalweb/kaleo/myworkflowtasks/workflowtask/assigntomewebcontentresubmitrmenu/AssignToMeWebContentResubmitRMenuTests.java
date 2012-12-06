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

package com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentresubmitrmenu;

import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.advancedsearchwebcontentworkflowtaskname.AddWebContentTest;
import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentactions.AssignToMeWebContentActionsTest;
import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentresubmitactions.ViewWebContentResubmitAssignedToMeTest;
import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.rejectwebcontentactions.RejectWebContentActionsTest;
import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.resubmitwebcontentactions.ResubmitWebContentActionsTest;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrydefaultnoworkflow.TearDownWorkflowConfigurationTest;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewebcontentsingleapprover.ConfigureWebContentSingleApproverTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignToMeWebContentResubmitRMenuTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureWebContentSingleApproverTest.class);
		testSuite.addTestSuite(AddWebContentTest.class);
		testSuite.addTestSuite(AssignToMeWebContentActionsTest.class);
		testSuite.addTestSuite(RejectWebContentActionsTest.class);
		testSuite.addTestSuite(ResubmitWebContentActionsTest.class);
		testSuite.addTestSuite(AssignToMeWebContentResubmitRMenuTest.class);
		testSuite.addTestSuite(ViewWebContentResubmitAssignedToMeTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationTest.class);

		return testSuite;
	}
}