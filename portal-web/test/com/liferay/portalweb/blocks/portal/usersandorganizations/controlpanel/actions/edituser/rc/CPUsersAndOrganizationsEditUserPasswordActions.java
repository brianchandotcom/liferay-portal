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

package com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.actions.edituser.rc;

import com.liferay.portalweb.blocks.base.actions.rc.ActionsUtil;
import com.liferay.portalweb.blocks.base.actions.rc.BaseLiferayActions;
import com.liferay.portalweb.blocks.base.functions.rc.ClickFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPUsersAndOrganizationsEditUserPasswordActions
	extends BaseLiferayActions {
	public CPUsersAndOrganizationsEditUserPasswordActions(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPUsersAndOrganizationsEditUserPasswordPaths.getPaths();
	}

	public void click(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((target.equals("BUTTONS_CANCEL") || target.equals("BUTTONS_SAVE"))) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}
}