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

package com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.actions.home;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.functions.ClickFunctions;
import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.paths.home.CPUsersAndOrganizationsSearchAllActiveUsersPaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsSearchAllActiveUsersActions
	extends BaseActionsImpl implements LiferayActions {
	public CPUsersAndOrganizationsSearchAllActiveUsersActions(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPUsersAndOrganizationsSearchAllActiveUsersPaths.getPaths();
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((param1.equals("BASIC_SEARCH_ADVANCED_LINK") ||
				param1.equals("ADVANCED_SEARCH_BASIC_LINK"))) {
			clickFunctions.clickAt(params[0], params[1]);
		}
		else if ((param1.equals("ADVANCED_SEARCH_BUTTON") ||
				param1.equals("BASIC_SEARCH_BUTTON") ||
				param1.equals("USERS_DEACTIVATE"))) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else if ((param1.equals("USERS_USER_1_FIRST_NAME"))) {
			clickFunctions.textClickAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}
}