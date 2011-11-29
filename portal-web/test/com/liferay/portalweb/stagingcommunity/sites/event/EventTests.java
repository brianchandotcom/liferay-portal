/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.stagingcommunity.sites.event;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.stagingcommunity.sites.event.addeventrepeatdailysptl.AddEventRepeatDailySPTLTests;
import com.liferay.portalweb.stagingcommunity.sites.event.addeventrepeatmonthlysptl.AddEventRepeatMonthlySPTLTests;
import com.liferay.portalweb.stagingcommunity.sites.event.addeventrepeatneversptl.AddEventRepeatNeverSPTLTests;
import com.liferay.portalweb.stagingcommunity.sites.event.addeventrepeatweeklysptl.AddEventRepeatWeeklySPTLTests;
import com.liferay.portalweb.stagingcommunity.sites.event.addeventrepeatyearlysptl.AddEventRepeatYearlySPTLTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EventTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddEventRepeatDailySPTLTests.suite());
		testSuite.addTest(AddEventRepeatMonthlySPTLTests.suite());
		testSuite.addTest(AddEventRepeatNeverSPTLTests.suite());
		testSuite.addTest(AddEventRepeatWeeklySPTLTests.suite());
		testSuite.addTest(AddEventRepeatYearlySPTLTests.suite());

		return testSuite;
	}

}