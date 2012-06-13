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

package com.liferay.portalweb.portal.controlpanel.users.user.addusermultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUser1Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUser2Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUser3Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser1NoTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser1Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser2NoTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser2Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser3NoTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.ViewUser3Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserMultipleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUser3Test.class);
		testSuite.addTestSuite(ViewUser1Test.class);
		testSuite.addTestSuite(ViewUser2Test.class);
		testSuite.addTestSuite(ViewUser3Test.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(ViewUser1NoTest.class);
		testSuite.addTestSuite(ViewUser2NoTest.class);
		testSuite.addTestSuite(ViewUser3NoTest.class);

		return testSuite;
	}
}