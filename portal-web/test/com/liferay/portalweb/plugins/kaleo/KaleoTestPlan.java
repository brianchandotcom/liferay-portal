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

package com.liferay.portalweb.plugins.kaleo;

import com.liferay.portalweb.plugins.kaleo.assetpublisher.AssetPublisherTestPlan;
import com.liferay.portalweb.plugins.kaleo.blogs.BlogsTestPlan;
import com.liferay.portalweb.plugins.kaleo.messageboards.MessageBoardsTestPlan;
import com.liferay.portalweb.plugins.kaleo.mysubmissions.MySubmissionsTestPlan;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.MyWorkflowTasksTestPlan;
import com.liferay.portalweb.plugins.kaleo.pagecomments.PageCommentsTestPlan;
import com.liferay.portalweb.plugins.kaleo.webcontent.WebContentTestPlan;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.WebContentDisplayTestPlan;
import com.liferay.portalweb.plugins.kaleo.wiki.WikiTestPlan;
import com.liferay.portalweb.plugins.kaleo.workflow.WorkflowTestPlan;
import com.liferay.portalweb.plugins.kaleo.workflowconfiguration.WorkflowConfigurationTestPlan;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AssetPublisherTestPlan.suite());
		testSuite.addTest(BlogsTestPlan.suite());
		testSuite.addTest(MessageBoardsTestPlan.suite());
		testSuite.addTest(MySubmissionsTestPlan.suite());
		testSuite.addTest(MyWorkflowTasksTestPlan.suite());
		testSuite.addTest(PageCommentsTestPlan.suite());
		testSuite.addTest(WebContentTestPlan.suite());
		testSuite.addTest(WebContentDisplayTestPlan.suite());
		testSuite.addTest(WikiTestPlan.suite());
		testSuite.addTest(WorkflowTestPlan.suite());
		testSuite.addTest(WorkflowConfigurationTestPlan.suite());

		return testSuite;
	}

}