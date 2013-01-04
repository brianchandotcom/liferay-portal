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

package com.liferay.portalweb.blocks.portal.home.page.actions.rc;

import com.liferay.portalweb.blocks.base.actions.rc.ActionsUtil;
import com.liferay.portalweb.blocks.base.actions.rc.BaseLiferayActions;
import com.liferay.portalweb.blocks.base.functions.rc.ClickFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.MouseOverFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.TypeFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class HomeActions extends BaseLiferayActions {
	public HomeActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = HomePaths.getPaths();
	}

	public void click(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((target.equals("ADD_LINK") || target.equals("GOTO_LINK") ||
				target.equals("MANAGE_LINK"))) {
			clickFunctions.textClickAtHomeClickDockbar(params[0], params[1]);
		}
		else if ((target.equals("ADD_LINK_APPLICATION"))) {
			clickFunctions.partialTextClickAt(params[0], params[1]);
		}
		else if ((target.equals("GOTO_LINK_CONTROL_PANEL"))) {
			clickFunctions.textClickAtAndWait(params[0], params[1]);
		}
		else if (params[0].startsWith("link=")) {
			clickFunctions.textClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void mouseOver(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		MouseOverFunctions mouseOverFunctions = new MouseOverFunctions(selenium);

		if ((target.equals("ADD_LINK") || target.equals("GOTO_LINK") ||
				target.equals("MANAGE_LINK"))) {
			mouseOverFunctions.textMouseOverHomeClickDockbar(params[0],
				params[1]);
		}
		else {
			super.mouseOver(params[0], params[1]);
		}
	}

	public void type(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		TypeFunctions typeFunctions = new TypeFunctions(selenium);

		if ((target.equals("PORTLET_FIELD_SEARCH"))) {
			typeFunctions.sendKeysHomeAddApplication(params[0], params[1]);
		}
		else {
			super.type(params[0], params[1]);
		}
	}
}