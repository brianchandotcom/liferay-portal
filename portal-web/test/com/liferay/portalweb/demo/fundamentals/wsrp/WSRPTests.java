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

package com.liferay.portalweb.demo.fundamentals.wsrp;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WSRPTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTMTest.class);
		testSuite.addTestSuite(AddPortletTMTest.class);
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddWSRPProducerDPTest.class);
		testSuite.addTestSuite(CopyWSRPProducerDPURLTest.class);
		testSuite.addTestSuite(AddWSRPConsumerDPTest.class);
		testSuite.addTestSuite(AddWSRPConsumerRemoteDPTest.class);
		testSuite.addTestSuite(AddPageWSRPRemoteTMTest.class);
		testSuite.addTestSuite(AddPortletWSRPRemoteTMTest.class);
		testSuite.addTestSuite(ViewWSRPClickToInvokeResourceServingPhaseDPTest.class);
		testSuite.addTestSuite(ViewWSRPClickToInvokeResourceServingPhaseRDPTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownWSRPConsumerDPTest.class);
		testSuite.addTestSuite(TearDownWSRPProducerDPTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}