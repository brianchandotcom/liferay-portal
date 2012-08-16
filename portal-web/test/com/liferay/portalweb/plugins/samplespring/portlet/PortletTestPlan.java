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

package com.liferay.portalweb.plugins.samplespring.portlet;

import com.liferay.portalweb.plugins.samplespring.portlet.addportletdu.AddPortletDUTests;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletpets.AddPortletPetsTests;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletpm.AddPortletPMTests;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletps.AddPortletPSTests;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletwelcome.AddPortletWelcomeTests;
import com.liferay.portalweb.plugins.samplespring.portlet.modifydateformatdaymonthdash.ModifyDateFormatDayMonthDashTests;
import com.liferay.portalweb.plugins.samplespring.portlet.modifydateformatdaymonthslash.ModifyDateFormatDayMonthSlashTests;
import com.liferay.portalweb.plugins.samplespring.portlet.modifydateformatmonthdaydash.ModifyDateFormatMonthDayDashTests;
import com.liferay.portalweb.plugins.samplespring.portlet.modifydateformatmonthdayslash.ModifyDateFormatMonthDaySlashTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletDUTests.suite());
		testSuite.addTest(AddPortletPetsTests.suite());
		testSuite.addTest(AddPortletPMTests.suite());
		testSuite.addTest(AddPortletPSTests.suite());
		testSuite.addTest(AddPortletWelcomeTests.suite());
		testSuite.addTest(ModifyDateFormatDayMonthDashTests.suite());
		testSuite.addTest(ModifyDateFormatDayMonthSlashTests.suite());
		testSuite.addTest(ModifyDateFormatMonthDayDashTests.suite());
		testSuite.addTest(ModifyDateFormatMonthDaySlashTests.suite());

		return testSuite;
	}

}