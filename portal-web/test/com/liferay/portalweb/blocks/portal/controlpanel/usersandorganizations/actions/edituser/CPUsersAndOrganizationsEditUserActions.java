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

package com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.actions.edituser;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.functions.AssertTextEqualsFunctions;
import com.liferay.portalweb.blocks.base.functions.ClickFunctions;
import com.liferay.portalweb.blocks.portal.controlpanel.usersandorganizations.paths.edituser.CPUsersAndOrganizationsEditUserPaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsEditUserActions extends BaseActionsImpl
	implements LiferayActions {
	public CPUsersAndOrganizationsEditUserActions(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPUsersAndOrganizationsEditUserPaths.getPaths();
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		AssertTextEqualsFunctions assertTextEqualsFunctions = new AssertTextEqualsFunctions(selenium);

		if ((param1.equals("USER_EMAIL_ADDRESS") ||
				param1.equals("USER_FIRST_NAME") ||
				param1.equals("USER_LAST_NAME") ||
				param1.equals("USER_SCREEN_NAME"))) {
			assertTextEqualsFunctions.assertValue(params[0], params[1]);
		}
		else {
			super.assertTextEquals(params[0], params[1]);
		}
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((param1.equals("BUTTONS_CANCEL") || param1.equals("BUTTONS_SAVE"))) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else if ((param1.equals("USER_INFORMATION_PASSWORD"))) {
			clickFunctions.partialTextClickAt(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}
}