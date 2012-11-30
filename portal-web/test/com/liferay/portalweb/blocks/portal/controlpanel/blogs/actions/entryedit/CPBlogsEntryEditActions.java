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

package com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entryedit;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.units.AssertTextEqualsUnits;
import com.liferay.portalweb.blocks.base.units.ClickUnits;
import com.liferay.portalweb.blocks.base.units.TypeUnits;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.paths.entryedit.CPBlogsEntryEditPaths;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryEditActions extends BaseActionsImpl
	implements LiferayActions {
	public CPBlogsEntryEditActions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPBlogsEntryEditPaths.getPaths();
	}

	public void assertTextEquals(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		AssertTextEqualsUnits assertTextEqualsUnits = new AssertTextEqualsUnits(selenium);

		if ((param1.equals("CONTENT_TEXT_STATUS") ||
				param1.equals("CONTENT_TEXT_SAVE_STATUS"))) {
			assertTextEqualsUnits.assertPartialText(params[0], params[1]);
		}
		else {
			super.assertTextEquals(params[0], params[1]);
		}
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickUnits clickUnits = new ClickUnits(selenium);

		if ((param1.equals("ADD_LINK") || param1.equals("GOTO_LINK") ||
				param1.equals("MANAGE_LINK"))) {
			selenium.waitForElementPresent(
				"//textarea[@id='_161_editor' and @style='display: none;']");
			clickUnits.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void type(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		TypeUnits typeUnits = new TypeUnits(selenium);

		if ((param1.equals("CONTENT_FIELD_CONTENT"))) {
			typeUnits.typeCKEditorBlogs(params[0], params[1]);
		}
		else {
			super.type(params[0], params[1]);
		}
	}
}