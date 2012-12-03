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

package com.liferay.portalweb.blocks.portal.home.actions;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.functions.ClickFunctions;
import com.liferay.portalweb.blocks.base.functions.MouseOverFunctions;
import com.liferay.portalweb.blocks.base.functions.TypeFunctions;
import com.liferay.portalweb.blocks.portal.home.paths.HomePaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class HomeActions extends BaseActionsImpl implements LiferayActions {
	public HomeActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = HomePaths.getPaths();
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((param1.equals("ADD_LINK") || param1.equals("GOTO_LINK") ||
				param1.equals("MANAGE_LINK"))) {
			clickFunctions.textClickAtHomeClickDockbar(params[0], params[1]);
		}
		else if ((param1.equals("ADD_LINK_APPLICATION"))) {
			clickFunctions.partialTextClickAt(params[0], params[1]);
		}
		else if ((param1.equals("GOTO_LINK_CONTROL_PANEL"))) {
			clickFunctions.textClickAtAndWait(params[0], params[1]);
		}
		else if (param1.startsWith("link=")) {
			clickFunctions.textClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void mouseOver(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		MouseOverFunctions mouseOverFunctions = new MouseOverFunctions(selenium);

		if ((param1.equals("ADD_LINK") || param1.equals("GOTO_LINK") ||
				param1.equals("MANAGE_LINK"))) {
			mouseOverFunctions.textMouseOverHomeClickDockbar(params[0],
				params[1]);
		}
		else {
			super.mouseOver(params[0], params[1]);
		}
	}

	public void type(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		TypeFunctions typeFunctions = new TypeFunctions(selenium);

		if ((param1.equals("PORTLET_FIELD_SEARCH"))) {
			typeFunctions.sendKeysHomeAddApplication(params[0], params[1]);
		}
		else {
			super.type(params[0], params[1]);
		}
	}
}