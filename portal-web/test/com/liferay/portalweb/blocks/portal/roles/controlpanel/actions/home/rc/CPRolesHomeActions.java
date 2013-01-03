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

package com.liferay.portalweb.blocks.portal.roles.controlpanel.actions.home.rc;

import com.liferay.portalweb.blocks.base.actions.rc.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.rc.LiferayActions;
import com.liferay.portalweb.blocks.base.functions.rc.ClickFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRolesHomeActions extends BaseActionsImpl
	implements LiferayActions {
	public CPRolesHomeActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPRolesHomePaths.getPaths();
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((param1.equals("ACTIONS_ASSIGN_MEMBERS"))) {
			clickFunctions.textClickAtAndWait(params[0], params[1]);
		}
		else if ((param1.equals("ADD_REGULAR_ROLE") ||
				param1.equals("ADD_SITE_ROLE") ||
				param1.equals("ADD_ORGANIZATION_ROLE"))) {
			clickFunctions.textClickAndWait(params[0], params[1]);
		}
		else if ((param1.equals("SEARCH_BUTTON"))) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}
}