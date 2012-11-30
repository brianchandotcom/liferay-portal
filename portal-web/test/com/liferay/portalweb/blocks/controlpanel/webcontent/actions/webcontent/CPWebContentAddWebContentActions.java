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

package com.liferay.portalweb.blocks.controlpanel.webcontent.actions.webcontent;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.units.ClickUnits;
import com.liferay.portalweb.blocks.base.units.TypeUnits;
import com.liferay.portalweb.blocks.controlpanel.webcontent.paths.webcontent.CPWebContentAddWebContentPaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentAddWebContentActions extends BaseActionsImpl
	implements LiferayActions {
	public CPWebContentAddWebContentActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPWebContentAddWebContentPaths.getPaths();
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickUnits clickUnits = new ClickUnits(selenium);

		if ((param1.equals("SIDEBAR_PUBLISH"))) {
			clickUnits.clickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void type(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		TypeUnits typeUnits = new TypeUnits(selenium);

		if ((param1.equals("ARTICLE_CONTENT"))) {
			typeUnits.typeCKEditorWebContent(params[0], params[1]);
		}
		else {
			super.type(params[0], params[1]);
		}
	}
}