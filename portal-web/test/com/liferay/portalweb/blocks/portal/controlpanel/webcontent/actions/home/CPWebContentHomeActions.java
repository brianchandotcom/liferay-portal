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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.home;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.paths.home.CPWebContentHomePaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentHomeActions extends BaseActionsImpl
	implements LiferayActions {
	public CPWebContentHomeActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPWebContentHomePaths.getPaths();
	}

	public void check(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		if ((param1.equals("ENTRIES_ARTICLE_1_CHECKBOX"))) {
			selenium.waitForVisible(
				"xpath=(//a[contains(@data-folder,'false')])[1]");
			selenium.mouseOver("xpath=(//a[contains(@data-folder,'false')])[1]");
			super.check(params[0], params[1]);
		}
		else {
			super.check(params[0], params[1]);
		}
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		if ((param1.equals("BUTTONS_ACTIONS") || param1.equals(" BUTTONS_ADD") ||
				param1.equals(" BUTTONS_MANAGE"))) {
			selenium.pause("1000");
			super.click(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}
}