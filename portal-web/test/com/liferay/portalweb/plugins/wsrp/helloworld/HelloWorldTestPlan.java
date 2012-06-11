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

package com.liferay.portalweb.plugins.wsrp.helloworld;

import com.liferay.portalweb.plugins.wsrp.helloworld.addconsumerhw.AddConsumerHWTests;
import com.liferay.portalweb.plugins.wsrp.helloworld.addportletwsrphw.AddPortletWSRPHWTests;
import com.liferay.portalweb.plugins.wsrp.helloworld.addportletwsrphwutf8.AddPortletWSRPHWUTF8Tests;
import com.liferay.portalweb.plugins.wsrp.helloworld.addproducerhw.AddProducerHWTests;
import com.liferay.portalweb.plugins.wsrp.helloworld.manageportletconsumerhw.ManagePortletConsumerHWTests;
import com.liferay.portalweb.plugins.wsrp.helloworld.manageportletconsumerhwutf8.ManagePortletConsumerHWUTF8Tests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class HelloWorldTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddConsumerHWTests.suite());
		testSuite.addTest(AddPortletWSRPHWTests.suite());
		testSuite.addTest(AddPortletWSRPHWUTF8Tests.suite());
		testSuite.addTest(AddProducerHWTests.suite());
		testSuite.addTest(ManagePortletConsumerHWTests.suite());
		testSuite.addTest(ManagePortletConsumerHWUTF8Tests.suite());

		return testSuite;
	}

}