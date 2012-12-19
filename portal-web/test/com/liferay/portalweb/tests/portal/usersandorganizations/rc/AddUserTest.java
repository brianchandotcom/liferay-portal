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

package com.liferay.portalweb.tests.portal.usersandorganizations.rc;

import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.actions.edituser.CPUsersAndOrganizationsEditUserActions;
import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.actions.home.CPUsersAndOrganizationsHomeActions;
import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.actions.home.CPUsersAndOrganizationsSearchAllActiveUsersActions;
import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.macros.CPUsersAndOrganizationsUsersMacros;
import com.liferay.portalweb.blocks.portal.home.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPUsersAndOrganizationsEditUserActions cPUsersAndOrganizationsEditUserActions =
			new CPUsersAndOrganizationsEditUserActions(selenium);
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		CPUsersAndOrganizationsSearchAllActiveUsersActions cPUsersAndOrganizationsSearchAllActiveUsersActions =
			new CPUsersAndOrganizationsSearchAllActiveUsersActions(selenium);
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPUsersAndOrganizationsUsersMacros.add("usersn", "userea@liferay.com",
			"userfn", "userln");
		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("USERS_SEARCH_ALL",
			"Search All Users");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.type("BASIC_SEARCH_FIELD",
			"userfn");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("BASIC_SEARCH_BUTTON",
			"Search");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("USERS_USER_1_FIRST_NAME",
			"userfn");
		cPUsersAndOrganizationsEditUserActions.assertTextEquals("USER_SCREEN_NAME",
			"usersn");
		cPUsersAndOrganizationsEditUserActions.assertTextEquals("USER_EMAIL_ADDRESS",
			"userea@liferay.com");
		cPUsersAndOrganizationsEditUserActions.assertTextEquals("USER_FIRST_NAME",
			"userfn");
		cPUsersAndOrganizationsEditUserActions.assertTextEquals("USER_LAST_NAME",
			"userln");
	}

	@Override
	public void tearDown() throws Exception {
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		cPUsersAndOrganizationsUsersMacros.tearDown();
		portletSignInUserMacros.signOut();
	}
}