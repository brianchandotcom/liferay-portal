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

package com.liferay.portalweb.plugins.googlemaps.portlet;

import com.liferay.portalweb.plugins.googlemaps.portlet.addportletgm.AddPortletGMTests;
import com.liferay.portalweb.plugins.googlemaps.portlet.configureportletdirectionsaddress.ConfigurePortletDirectionsAddressTests;
import com.liferay.portalweb.plugins.googlemaps.portlet.configureportletdirectionsinputenabled.ConfigurePortletDirectionsInputEnabledTests;
import com.liferay.portalweb.plugins.googlemaps.portlet.configureportletmapaddress.ConfigurePortletMapAddressTests;
import com.liferay.portalweb.plugins.googlemaps.portlet.configureportletmapsinputenabled.ConfigurePortletMapsInputEnabledTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletGMTests.suite());
		testSuite.addTest(ConfigurePortletDirectionsAddressTests.suite());
		testSuite.addTest(ConfigurePortletDirectionsInputEnabledTests.suite());
		testSuite.addTest(ConfigurePortletMapAddressTests.suite());
		testSuite.addTest(ConfigurePortletMapsInputEnabledTests.suite());

		return testSuite;
	}

}