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

import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.editrole.rc.CPRolesEditRoleActions;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.home.rc.CPRolesHomeActions;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.macros.rc.CPRolesMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.rc.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSiteRoleTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPRolesEditRoleActions cPRolesEditRoleActions = new CPRolesEditRoleActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPRolesMacros.addSiteRole("Siterole Name ", "Siterole Title",
			"Siterole Description");
		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.type("SEARCH_FILED", "Siterole Title");
		cPRolesHomeActions.click("SEARCH_BUTTON", "Search");
		cPRolesHomeActions.assertTextEquals("ROLES_NAME", "Siterole Title");
		cPRolesHomeActions.click("ROLES_NAME", "Siterole Title");
		cPRolesEditRoleActions.assertTextEquals("ROLE_TYPE", "Site");
		cPRolesEditRoleActions.assertTextEquals("ROLE_NAME", "Siterole Name");
		cPRolesEditRoleActions.assertTextEquals("ROLE_TITLE", "Siterole Title");
		cPRolesEditRoleActions.assertTextEquals("ROLE_DESCRIPTION",
			"Siterole Description");
	}

	@Override
	public void tearDown() throws Exception {
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPRolesMacros.tearDown();
		signInUserMacros.signOut();
	}
}