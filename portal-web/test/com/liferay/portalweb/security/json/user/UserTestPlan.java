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

package com.liferay.portalweb.security.json.user;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.security.json.user.adduserjson.AddUserJSONTests;
import com.liferay.portalweb.security.json.user.guestadduserjson.Guest_AddUserJSONTests;
import com.liferay.portalweb.security.json.user.user1adduser2json.User1_AddUser2JSONTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddUserJSONTests.suite());
		testSuite.addTest(Guest_AddUserJSONTests.suite());
		testSuite.addTest(User1_AddUser2JSONTests.suite());

		return testSuite;
	}

}