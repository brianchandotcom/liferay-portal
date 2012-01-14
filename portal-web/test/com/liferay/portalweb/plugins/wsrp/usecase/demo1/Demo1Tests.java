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

package com.liferay.portalweb.plugins.wsrp.usecase.demo1;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Demo1Tests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageWSRPTest.class);
		testSuite.addTestSuite(AddPortletDPTest.class);
		testSuite.addTestSuite(AddPageDLTest.class);
		testSuite.addTestSuite(AddPortletDLTest.class);
		testSuite.addTestSuite(AddWSRPProducerDPTest.class);
		testSuite.addTestSuite(CopyWSRPProducerDPURLTest.class);
		testSuite.addTestSuite(AddWSRPConsumerDPTest.class);
		testSuite.addTestSuite(AddWSRPConsumerRemoteDPTest.class);
		testSuite.addTestSuite(AddPortletWSRPRemoteDPTest.class);
		testSuite.addTestSuite(
			ViewWSRPClickToInvokeResourceServingPhaseDPTest.class);
		testSuite.addTestSuite(
			ViewWSRPClickToInvokeResouceServingPhaseRDPTest.class);
		testSuite.addTestSuite(TearDownWSRPConsumerDPTest.class);
		testSuite.addTestSuite(TearDownWSRPProducerDPTest.class);
		testSuite.addTestSuite(TearDownDocumentTest.class);
		testSuite.addTestSuite(RemovePortletWSRPRemoteDPTest.class);
		testSuite.addTestSuite(RemovePortletDPTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}