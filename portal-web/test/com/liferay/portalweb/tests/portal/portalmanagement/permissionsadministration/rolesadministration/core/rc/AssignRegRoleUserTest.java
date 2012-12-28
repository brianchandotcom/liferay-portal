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

package com.liferay.portalweb.tests.portal.portalmanagement.permissionsadministration.rolesadministration.core.rc;

import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.macros.CPRolesMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.SignInUserMacros;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser.CPUsersAndOrganizationsEditUserActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser.CPUsersAndOrganizationsEditUserRolesActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.CPUsersAndOrganizationsHomeActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.CPUsersAndOrganizationsSearchAllActiveUsersActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.macros.CPUsersAndOrganizationsUsersMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignRegRoleUserTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPUsersAndOrganizationsUsersMacros.add("usersn", "userea@liferay.com",
			"userfn", "userln");
		cPRolesMacros.addRegRole("Regrole Name ", "Regrole Title",
			"Regrole Description");
	}

	public void test() throws Exception {
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		CPUsersAndOrganizationsEditUserActions cPUsersAndOrganizationsEditUserActions =
			new CPUsersAndOrganizationsEditUserActions(selenium);
		CPUsersAndOrganizationsEditUserRolesActions cPUsersAndOrganizationsEditUserRolesActions =
			new CPUsersAndOrganizationsEditUserRolesActions(selenium);
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		CPUsersAndOrganizationsSearchAllActiveUsersActions cPUsersAndOrganizationsSearchAllActiveUsersActions =
			new CPUsersAndOrganizationsSearchAllActiveUsersActions(selenium);
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPRolesMacros.assignRegrole("Regrole Title", "userfn ");
		cPUsersAndOrganizationsUsersMacros.basicSearch();
		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("USERS_SEARCH_ALL",
			"Search All Users");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.type("BASIC_SEARCH_FIELD",
			"userfn");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("BASIC_SEARCH_BUTTON",
			"Search");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("USERS_USER_1_FIRST_NAME",
			"userfn");
		cPUsersAndOrganizationsEditUserActions.click("USER_INFORMATION_ROLES",
			"Roles");
		cPUsersAndOrganizationsEditUserRolesActions.assertTextEquals("REGULAR_ROLES_SECOND_REGULAR_ROLE",
			"Regrole Title");
	}

	@Override
	public void tearDown() throws Exception {
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPUsersAndOrganizationsUsersMacros.tearDown();
		cPRolesMacros.tearDown();
		signInUserMacros.signOut();
	}
}