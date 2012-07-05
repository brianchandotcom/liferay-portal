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

package com.liferay.portalweb.plugins.testevent.event.sendevent;

import com.liferay.portalweb.plugins.testevent.portletec.addportletec.AddPageECTest;
import com.liferay.portalweb.plugins.testevent.portletec.addportletec.AddPortletECTest;
import com.liferay.portalweb.plugins.testevent.portletep.addportletep.AddPageEPTest;
import com.liferay.portalweb.plugins.testevent.portletep.addportletep.AddPortletEPTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SendEventTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageECTest.class);
		testSuite.addTestSuite(AddPortletECTest.class);
		testSuite.addTestSuite(AddPageEPTest.class);
		testSuite.addTestSuite(AddPortletEPTest.class);
		testSuite.addTestSuite(ConfigurePortletEPLinkPortletURLsPageECTest.class);
		testSuite.addTestSuite(SendEventTest.class);
		testSuite.addTestSuite(ViewSendEventECTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}