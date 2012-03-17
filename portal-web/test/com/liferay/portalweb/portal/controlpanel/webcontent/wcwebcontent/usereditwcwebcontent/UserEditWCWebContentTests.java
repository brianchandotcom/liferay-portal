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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.usereditwcwebcontent;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontent.AddWebContentTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontent.TearDownWebContentTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserEditWCWebContentTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddWCARoleTest.class);
		testSuite.addTestSuite(DefineWCARoleTest.class);
		testSuite.addTestSuite(AssignUserRoleWCATest.class);
		testSuite.addTestSuite(AddWebContentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(User_LoginTest.class);
		testSuite.addTestSuite(User_EditWCWebContentTest.class);
		testSuite.addTestSuite(User_ViewEditWCWebContentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginTest.class);
		testSuite.addTestSuite(TearDownWebContentTest.class);
		testSuite.addTestSuite(TearDownWCARoleTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}