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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.useradddmdocumentscopesite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AddPageDmTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AddPortletDmTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AddRegularRoleTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AddUserPasswordTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AddUserTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.AssignMembersRegularRoleUserTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.SignInTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.SignOutTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.User_AddDmDocumentTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.User_NoAddDmDocumentTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.User_SignInTest;
import com.liferay.portalweb.portal.permissions.documentsandmedia.utils.User_SignOutTest;
import com.liferay.portalweb.utils.TearDownDLDocumentTest;
import com.liferay.portalweb.utils.TearDownPageTest;
import com.liferay.portalweb.utils.TearDownRoleTest;
import com.liferay.portalweb.utils.TearDownUserTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AddDmDocumentScopeSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddUserPasswordTest.class);
		testSuite.addTestSuite(AddRegularRoleTest.class);
		testSuite.addTestSuite(AssignMembersRegularRoleUserTest.class);
		testSuite.addTestSuite(AddPageDmTest.class);
		testSuite.addTestSuite(AddPortletDmTest.class);
		testSuite.addTestSuite(DefinePermissionsRoleAddDocumentSiteOffTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_NoAddDmDocumentTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(DefinePermissionsRoleAddDocumentSiteOnTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddDmDocumentTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownRoleTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}