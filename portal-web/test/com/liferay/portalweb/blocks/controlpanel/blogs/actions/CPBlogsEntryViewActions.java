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

package com.liferay.portalweb.blocks.controlpanel.blogs.actions;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.units.AssertTextEqualsUnits;
import com.liferay.portalweb.blocks.controlpanel.blogs.paths.CPBlogsEntryViewPaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryViewActions extends BaseActionsImpl
	implements LiferayActions {
	public CPBlogsEntryViewActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPBlogsEntryViewPaths.getPaths();
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		AssertTextEqualsUnits assertTextEqualsUnits = new AssertTextEqualsUnits(selenium);

		if ((param1.equals("BLOGS_ENTRY_TEXT_RATING_AVERAGE"))) {
			assertTextEqualsUnits.assertPartialText(params[0], params[1]);
		}
		else {
			super.assertTextEquals(params[0], params[1]);
		}
	}
}