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

package com.liferay.portalweb.plugins.wsrp.helloworld.addportletwsrphw;

import com.liferay.portalweb.plugins.wsrp.helloworld.addconsumerhw.AddConsumerHWTest;
import com.liferay.portalweb.plugins.wsrp.helloworld.addconsumerhw.TearDownConsumerTest;
import com.liferay.portalweb.plugins.wsrp.helloworld.addproducerhw.AddProducerHWTest;
import com.liferay.portalweb.plugins.wsrp.helloworld.addproducerhw.TearDownProducerTest;
import com.liferay.portalweb.plugins.wsrp.helloworld.manageportletconsumerhw.ManagePortletConsumerHWTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletWSRPHWTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddProducerHWTest.class);
		testSuite.addTestSuite(AddConsumerHWTest.class);
		testSuite.addTestSuite(ManagePortletConsumerHWTest.class);
		testSuite.addTestSuite(AddPageWSRPHWTest.class);
		testSuite.addTestSuite(AddPortletWSRPHWTest.class);
		testSuite.addTestSuite(TearDownConsumerTest.class);
		testSuite.addTestSuite(TearDownProducerTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}