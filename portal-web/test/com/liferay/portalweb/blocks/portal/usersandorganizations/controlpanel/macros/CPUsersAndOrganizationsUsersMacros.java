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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.adduser.CPUsersAndOrganizationsAddUserActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser.CPUsersAndOrganizationsEditUserActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser.CPUsersAndOrganizationsEditUserPasswordActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.CPUsersAndOrganizationsHomeActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.CPUsersAndOrganizationsSearchAllActiveUsersActions;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.home.CPUsersAndOrganizationsSearchAllInactiveUsersActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsUsersMacros extends BaseMacros {
	public CPUsersAndOrganizationsUsersMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String screenName, String emailAddress, String firstName,
		String lastName) throws Exception {
		CPUsersAndOrganizationsAddUserActions cPUsersAndOrganizationsAddUserActions =
			new CPUsersAndOrganizationsAddUserActions(selenium);
		CPUsersAndOrganizationsEditUserActions cPUsersAndOrganizationsEditUserActions =
			new CPUsersAndOrganizationsEditUserActions(selenium);
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("TOOLBAR_ADD", "Add");
		cPUsersAndOrganizationsHomeActions.click("ADD_USER", "User");
		cPUsersAndOrganizationsAddUserActions.type("USER_SCREEN_NAME",
			screenName);
		cPUsersAndOrganizationsAddUserActions.type("USER_EMAIL_ADDRESS",
			emailAddress);
		cPUsersAndOrganizationsAddUserActions.type("USER_FIRST_NAME", firstName);
		cPUsersAndOrganizationsAddUserActions.type("USER_LAST_NAME", lastName);
		cPUsersAndOrganizationsAddUserActions.click("BUTTONS_SAVE", "Save");
		cPUsersAndOrganizationsEditUserActions.assertTextEquals("HEADER_PORTLET_SUCCESS",
			"Your request completed successfully.");
	}

	public void changePassword(String firstName, String password)
		throws Exception {
		CPUsersAndOrganizationsEditUserActions cPUsersAndOrganizationsEditUserActions =
			new CPUsersAndOrganizationsEditUserActions(selenium);
		CPUsersAndOrganizationsEditUserPasswordActions cPUsersAndOrganizationsEditUserPasswordActions =
			new CPUsersAndOrganizationsEditUserPasswordActions(selenium);
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		CPUsersAndOrganizationsSearchAllActiveUsersActions cPUsersAndOrganizationsSearchAllActiveUsersActions =
			new CPUsersAndOrganizationsSearchAllActiveUsersActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("USERS_SEARCH_ALL",
			"Search All Users");

		if (cPUsersAndOrganizationsSearchAllActiveUsersActions.isVisible(
					"ADVANCED_SEARCH_BASIC_LINK", "")) {
			cPUsersAndOrganizationsSearchAllActiveUsersActions.click("ADVANCED_SEARCH_BASIC_LINK",
				"Â« Basic");
		}

		cPUsersAndOrganizationsSearchAllActiveUsersActions.type("BASIC_SEARCH_FIELD",
			firstName);
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("BASIC_SEARCH_BUTTON",
			"Search");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("USERS_USER_1_FIRST_NAME",
			"userfn");
		cPUsersAndOrganizationsEditUserActions.click("USER_INFORMATION_PASSWORD",
			"Password");
		cPUsersAndOrganizationsEditUserPasswordActions.type("PASSWORD_NEW_PASSWORD",
			password);
		cPUsersAndOrganizationsEditUserPasswordActions.type("PASSWORD_ENTER_AGAIN",
			password);
		cPUsersAndOrganizationsEditUserPasswordActions.click("BUTTONS_SAVE",
			"Save");
		cPUsersAndOrganizationsEditUserPasswordActions.assertTextEquals("HEADER_PORTLET_SUCCESS",
			"Your request completed successfully.");
	}

	public void deactivate(String firstName) throws Exception {
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		CPUsersAndOrganizationsSearchAllActiveUsersActions cPUsersAndOrganizationsSearchAllActiveUsersActions =
			new CPUsersAndOrganizationsSearchAllActiveUsersActions(selenium);
		CPUsersAndOrganizationsSearchAllInactiveUsersActions cPUsersAndOrganizationsSearchAllInactiveUsersActions =
			new CPUsersAndOrganizationsSearchAllInactiveUsersActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("USERS_SEARCH_ALL",
			"Search All Users");

		if (cPUsersAndOrganizationsSearchAllActiveUsersActions.isVisible(
					"BASIC_SEARCH_ADVANCED_LINK", "")) {
			cPUsersAndOrganizationsSearchAllActiveUsersActions.click("BASIC_SEARCH_ADVANCED_LINK",
				"Advanced Â»");
		}

		cPUsersAndOrganizationsSearchAllActiveUsersActions.type("ADVANCED_SEARCH_FIRST_NAME",
			firstName);
		cPUsersAndOrganizationsSearchAllActiveUsersActions.select("ADVANCED_SEARCH_STATUS",
			"Active");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("ADVANCED_SEARCH_BUTTON",
			"Search");

		while (cPUsersAndOrganizationsSearchAllActiveUsersActions.isElementPresent(
					"USERS_USER_1_CHECK", "")) {
			cPUsersAndOrganizationsSearchAllActiveUsersActions.check("USERS_USER_1_CHECK",
				"Flag First User");
			cPUsersAndOrganizationsSearchAllActiveUsersActions.click("USERS_DEACTIVATE",
				"Deactivate");
			cPUsersAndOrganizationsSearchAllActiveUsersActions.confirm("HEADER_CONFIRMATION",
				"Are you sure you want to deactivate the selected users?");
		}

		cPUsersAndOrganizationsSearchAllInactiveUsersActions.assertTextEquals("USERS_MESSAGE",
			"No users were found.");
	}

	public void delete(String firstName) throws Exception {
		CPUsersAndOrganizationsHomeActions cPUsersAndOrganizationsHomeActions = new CPUsersAndOrganizationsHomeActions(selenium);
		CPUsersAndOrganizationsSearchAllActiveUsersActions cPUsersAndOrganizationsSearchAllActiveUsersActions =
			new CPUsersAndOrganizationsSearchAllActiveUsersActions(selenium);
		CPUsersAndOrganizationsSearchAllInactiveUsersActions cPUsersAndOrganizationsSearchAllInactiveUsersActions =
			new CPUsersAndOrganizationsSearchAllInactiveUsersActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Users and Organizations");
		cPUsersAndOrganizationsHomeActions.click("USERS_SEARCH_ALL",
			"Search All Users");

		if (cPUsersAndOrganizationsSearchAllActiveUsersActions.isVisible(
					"BASIC_SEARCH_ADVANCED_LINK", "")) {
			cPUsersAndOrganizationsSearchAllActiveUsersActions.click("BASIC_SEARCH_ADVANCED_LINK",
				"Advanced Â»");
		}

		cPUsersAndOrganizationsSearchAllActiveUsersActions.type("ADVANCED_SEARCH_FIRST_NAME",
			firstName);
		cPUsersAndOrganizationsSearchAllActiveUsersActions.select("ADVANCED_SEARCH_STATUS",
			"Inactive");
		cPUsersAndOrganizationsSearchAllActiveUsersActions.click("ADVANCED_SEARCH_BUTTON",
			"Search");

		while (cPUsersAndOrganizationsSearchAllInactiveUsersActions.isElementPresent(
					"USERS_USER_1_CHECK", "")) {
			cPUsersAndOrganizationsSearchAllInactiveUsersActions.check("USERS_USER_1_CHECK",
				"Flag First User");
			cPUsersAndOrganizationsSearchAllInactiveUsersActions.click("USERS_DELETE",
				"Delete");
			cPUsersAndOrganizationsSearchAllInactiveUsersActions.confirm("HEADER_CONFIRMATION",
				"Are you sure you want to permanently delete the selected users?");
		}

		cPUsersAndOrganizationsSearchAllInactiveUsersActions.assertTextEquals("USERS_MESSAGE",
			"No users were found.");
		cPUsersAndOrganizationsSearchAllInactiveUsersActions.assertElementNotPresent("USERS_USER_1_FIRST_NAME",
			firstName);
	}

	public void tearDown() throws Exception {
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);

		cPUsersAndOrganizationsUsersMacros.deactivate("user*");
		cPUsersAndOrganizationsUsersMacros.delete("user*");
	}
}