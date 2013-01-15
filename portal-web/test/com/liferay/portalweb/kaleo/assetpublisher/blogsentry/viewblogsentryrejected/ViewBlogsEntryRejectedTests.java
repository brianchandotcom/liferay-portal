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

package com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryrejected;

import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPortletAPTest;
import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentryassignedtome.AddAPBlogsEntryTest;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrydefaultnoworkflow.TearDownWorkflowConfigurationTest;
import com.liferay.portalweb.kaleo.workflowconfiguration.resource.configureblogsentrysingleapprover.ConfigureBlogsEntrySingleApproverTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.blogs.blogsentry.addblogsentrycp.TearDownBlogsEntryCPTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryRejectedTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureBlogsEntrySingleApproverTest.class);
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddAPBlogsEntryTest.class);
		testSuite.addTestSuite(AssignToMeBlogsEntryActionsTest.class);
		testSuite.addTestSuite(RejectBlogsEntryActionsTest.class);
		testSuite.addTestSuite(ViewBlogsEntryRejectedTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(ViewBlogsEntryRejectedGuestTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryCPTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationTest.class);

		return testSuite;
	}
}