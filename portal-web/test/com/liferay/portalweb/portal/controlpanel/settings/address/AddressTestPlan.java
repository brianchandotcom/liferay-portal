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

package com.liferay.portalweb.portal.controlpanel.settings.address;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddress.AddSettingsAddressTests;
import com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddresscitynull.AddSettingsAddressCityNullTests;
import com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddresses.AddSettingsAddressesTests;
import com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddressstreetnull.AddSettingsAddressStreetNullTests;
import com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddresszipnull.AddSettingsAddressZipNullTests;
import com.liferay.portalweb.portal.controlpanel.settings.address.deletesettingsaddress.DeleteSettingsAddressTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddressTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSettingsAddressTests.suite());
		testSuite.addTest(AddSettingsAddressCityNullTests.suite());
		testSuite.addTest(AddSettingsAddressesTests.suite());
		testSuite.addTest(AddSettingsAddressStreetNullTests.suite());
		testSuite.addTest(AddSettingsAddressZipNullTests.suite());
		testSuite.addTest(DeleteSettingsAddressTests.suite());

		return testSuite;
	}

}