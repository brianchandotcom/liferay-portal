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

package com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopecommunity;

import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopecommunity.AddWebContentScopeCommunityTest;
import com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopecommunity.AssignToMeWebContentScopeCommunityActionsTest;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ApproveWebContentScopeCommunityTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCommunityTest.class);
		testSuite.addTestSuite(ConfigureWebContentSingleApproverScopeCommunityTest.class);
		testSuite.addTestSuite(AddWebContentScopeCommunityTest.class);
		testSuite.addTestSuite(AssignToMeWebContentScopeCommunityActionsTest.class);
		testSuite.addTestSuite(ApproveWebContentScopeCommunityActionsTest.class);
		testSuite.addTestSuite(ViewWebContentScopeCommunityCompletedTest.class);
		testSuite.addTestSuite(TearDownCommunityWebContentTest.class);
		testSuite.addTestSuite(TearDownCommunityWorkflowConfigurationTest.class);
		testSuite.addTestSuite(TearDownCommunityTest.class);

		return testSuite;
	}
}