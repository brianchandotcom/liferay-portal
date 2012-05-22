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

package com.liferay.portalweb.kaleo.workflow.resource;

import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigureblogsentrysingleapprover.DefaultConfigureBlogsEntrySingleApproverTests;
import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigurecommentssingleapprover.DefaultConfigureCommentsSingleApproverTests;
import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigurembmessagesingleapprover.DefaultConfigureMBMessageSingleApproverTests;
import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigurepagerevisionsingleapprover.DefaultConfigurePageRevisionSingleApproverTests;
import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigurewebcontentsingleapprover.DefaultConfigureWebContentSingleApproverTests;
import com.liferay.portalweb.kaleo.workflow.resource.defaultconfigurewikipagesingleapprover.DefaultConfigureWikiPageSingleApproverTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			DefaultConfigureBlogsEntrySingleApproverTests.suite());
		testSuite.addTest(DefaultConfigureCommentsSingleApproverTests.suite());
		testSuite.addTest(DefaultConfigureMBMessageSingleApproverTests.suite());
		testSuite.addTest(
			DefaultConfigurePageRevisionSingleApproverTests.suite());
		testSuite.addTest(
			DefaultConfigureWebContentSingleApproverTests.suite());
		testSuite.addTest(DefaultConfigureWikiPageSingleApproverTests.suite());

		return testSuite;
	}

}