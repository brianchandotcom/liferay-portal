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

package com.liferay.portalweb.portal.controlpanel.users.useraddress;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddress.AddUserAddressTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddresscitynull.AddUserAddressCityNullTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddressmultiple.AddUserAddressMultipleTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddressstreetnull.AddUserAddressStreetNullTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.adduseraddresszipnull.AddUserAddressZipNullTests;
import com.liferay.portalweb.portal.controlpanel.users.useraddress.searchuseraddresscp.SearchUserAddressCPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserAddressTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddUserAddressTests.suite());
		testSuite.addTest(AddUserAddressCityNullTests.suite());
		testSuite.addTest(AddUserAddressMultipleTests.suite());
		testSuite.addTest(AddUserAddressStreetNullTests.suite());
		testSuite.addTest(AddUserAddressZipNullTests.suite());
		testSuite.addTest(SearchUserAddressCPTests.suite());

		return testSuite;
	}

}