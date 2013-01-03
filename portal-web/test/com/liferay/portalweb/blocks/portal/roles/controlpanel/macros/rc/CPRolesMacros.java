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

package com.liferay.portalweb.blocks.portal.roles.controlpanel.macros.rc;

import com.liferay.portalweb.blocks.base.macros.rc.BaseMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.addrole.rc.CPRolesAddRoleActions;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.assignmembers.rc.CPRolesAssignMembersUsersActions;
import com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.home.rc.CPRolesHomeActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRolesMacros extends BaseMacros {
	public CPRolesMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void addOrgRole(String name, String title, String description)
		throws Exception {
		CPRolesAddRoleActions cPRolesAddRoleActions = new CPRolesAddRoleActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPRolesHomeActions.click("ADD_ORGANIZATION_ROLE", "Organization Role");
		cPRolesAddRoleActions.type("ROLE_NAME", name);
		cPRolesAddRoleActions.type("ROLE_TITLE", title);
		cPRolesAddRoleActions.type("ROLE_DESCRIPTION", description);
		cPRolesAddRoleActions.click("BUTTON_SAVE", "Save");
		cPRolesHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
	}

	public void addRegRole(String name, String title, String description)
		throws Exception {
		CPRolesAddRoleActions cPRolesAddRoleActions = new CPRolesAddRoleActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPRolesHomeActions.click("ADD_REGULAR_ROLE", "Regular Role");
		cPRolesAddRoleActions.type("ROLE_NAME", name);
		cPRolesAddRoleActions.type("ROLE_TITLE", title);
		cPRolesAddRoleActions.type("ROLE_DESCRIPTION", description);
		cPRolesAddRoleActions.click("BUTTON_SAVE", "Save");
		cPRolesHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
	}

	public void addSiteRole(String name, String title, String description)
		throws Exception {
		CPRolesAddRoleActions cPRolesAddRoleActions = new CPRolesAddRoleActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPRolesHomeActions.click("ADD_SITE_ROLE", "Site Role");
		cPRolesAddRoleActions.type("ROLE_NAME", name);
		cPRolesAddRoleActions.type("ROLE_TITLE", title);
		cPRolesAddRoleActions.type("ROLE_DESCRIPTION", description);
		cPRolesAddRoleActions.click("BUTTON_SAVE", "Save");
		cPRolesHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
	}

	public void advancedSearch() throws Exception {
		CPRolesAssignMembersUsersActions cPRolesAssignMembersUsersActions = new CPRolesAssignMembersUsersActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.type("SEARCH_FILED", "Administrator");
		cPRolesHomeActions.click("SEARCH_BUTTON", "Search");
		cPRolesHomeActions.click("ROLES_ACTIONS", "Actions");
		cPRolesHomeActions.click("ACTIONS_ASSIGN_MEMBERS", "Assign Members");

		if (cPRolesAssignMembersUsersActions.isVisible(
					"BASIC_SEARCH_ADVANCED_LINK", "")) {
			cPRolesAssignMembersUsersActions.click("BASIC_SEARCH_ADVANCED_LINK",
				"Advanced Â»");
		}
	}

	public void assignRegrole(String role, String firstName)
		throws Exception {
		CPRolesAssignMembersUsersActions cPRolesAssignMembersUsersActions = new CPRolesAssignMembersUsersActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPRolesMacros.basicSearch();
		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.type("SEARCH_FILED", role);
		cPRolesHomeActions.click("SEARCH_BUTTON", "Search");
		cPRolesHomeActions.click("ROLES_ACTIONS", "Actions");
		cPRolesHomeActions.click("ACTIONS_ASSIGN_MEMBERS", "Assign Members");
		cPRolesAssignMembersUsersActions.click("STATUS_AVAILABLE", "Available");
		cPRolesAssignMembersUsersActions.type("BASIC_SEARCH_FIELD", firstName);
		cPRolesAssignMembersUsersActions.click("BASIC_SEARCH_BUTTON", "Search");
		cPRolesAssignMembersUsersActions.check("USERS_USER_1_CHECK", null);
		cPRolesAssignMembersUsersActions.click("BUTTON_UPDATE_ASSOCIATIONS",
			"Update Associations");
		cPRolesHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
	}

	public void basicSearch() throws Exception {
		CPRolesAssignMembersUsersActions cPRolesAssignMembersUsersActions = new CPRolesAssignMembersUsersActions(selenium);
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.type("SEARCH_FILED", "Administrator");
		cPRolesHomeActions.click("SEARCH_BUTTON", "Search");
		cPRolesHomeActions.click("ROLES_ACTIONS", "Actions");
		cPRolesHomeActions.click("ACTIONS_ASSIGN_MEMBERS", "Assign Members");

		if (cPRolesAssignMembersUsersActions.isVisible(
					"ADVANCED_SEARCH_BASIC_LINK", "")) {
			cPRolesAssignMembersUsersActions.click("ADVANCED_SEARCH_BASIC_LINK",
				"Â« Basic");
		}
	}

	public void delete(String title) throws Exception {
		CPRolesHomeActions cPRolesHomeActions = new CPRolesHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Roles");
		cPRolesHomeActions.type("SEARCH_FILED", "" + title + "*");
		cPRolesHomeActions.click("SEARCH_BUTTON", "Search");

		while (cPRolesHomeActions.isElementPresent("ROLES_NAME", "")) {
			cPRolesHomeActions.click("ROLES_ACTIONS", "Actions");
			cPRolesHomeActions.click("ACTIONS_DELETE", "Delete");
			cPRolesHomeActions.confirm("HEADER_CONFIRMATION",
				"Are you sure you want to delete this? It will be deleted immediately.");
		}

		cPRolesHomeActions.assertTextEquals("ROLES_MESSAGE",
			"No roles were found.");
	}

	public void tearDown() throws Exception {
		CPRolesMacros cPRolesMacros = new CPRolesMacros(selenium);

		cPRolesMacros.delete("Orgrole Title*");
		cPRolesMacros.delete("Regrole Title*");
		cPRolesMacros.delete("Siterole Title*");
	}
}