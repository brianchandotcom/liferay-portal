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

package com.liferay.portalweb.kaleo.workflowconfiguration.resource;

import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrydefaultnoworkflow.ConfigureBlogsEntryDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrydefaultsingleapprover.ConfigureBlogsEntryDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrysingleapprover.ConfigureBlogsEntrySingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurecommentsdefaultnoworkflow.ConfigureCommentsDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurecommentsdefaultsingleapprover.ConfigureCommentsDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurecommentssingleapprover.ConfigureCommentsSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurembmessagedefaultnoworkflow.ConfigureMBMessageDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurembmessagedefaultsingleapprover.ConfigureMBMessageDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurembmessagesingleapprover.ConfigureMBMessageSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurepagerevisiondefaultnoworkflow.ConfigurePageRevisionDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurepagerevisiondefaultsingleapprover.ConfigurePageRevisionDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurepagerevisionsingleapprover.ConfigurePageRevisionSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewebcontentdefaultnoworkflow.ConfigureWebContentDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewebcontentdefaultsingleapprover.ConfigureWebContentDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewebcontentsingleapprover.ConfigureWebContentSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewikipagedefaultnoworkflow.ConfigureWikiPageDefaultNoWorkflowTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewikipagedefaultsingleapprover.ConfigureWikiPageDefaultSingleApproverTests;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configurewikipagesingleapprover.ConfigureWikiPageSingleApproverTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ConfigureBlogsEntryDefaultNoWorkflowTests.suite());
		testSuite.addTest(
			ConfigureBlogsEntryDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigureBlogsEntrySingleApproverTests.suite());
		testSuite.addTest(ConfigureCommentsDefaultNoWorkflowTests.suite());
		testSuite.addTest(ConfigureCommentsDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigureCommentsSingleApproverTests.suite());
		testSuite.addTest(ConfigureMBMessageDefaultNoWorkflowTests.suite());
		testSuite.addTest(ConfigureMBMessageDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigureMBMessageSingleApproverTests.suite());
		testSuite.addTest(ConfigurePageRevisionDefaultNoWorkflowTests.suite());
		testSuite.addTest(
			ConfigurePageRevisionDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigurePageRevisionSingleApproverTests.suite());
		testSuite.addTest(ConfigureWebContentDefaultNoWorkflowTests.suite());
		testSuite.addTest(
			ConfigureWebContentDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigureWebContentSingleApproverTests.suite());
		testSuite.addTest(ConfigureWikiPageDefaultNoWorkflowTests.suite());
		testSuite.addTest(ConfigureWikiPageDefaultSingleApproverTests.suite());
		testSuite.addTest(ConfigureWikiPageSingleApproverTests.suite());

		return testSuite;
	}

}