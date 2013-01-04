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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes.rc;

import com.liferay.portalweb.blocks.base.actions.rc.ActionsUtil;
import com.liferay.portalweb.blocks.base.actions.rc.BaseLiferayActions;
import com.liferay.portalweb.blocks.base.functions.rc.ClickFunctions;
import com.liferay.portalweb.blocks.base.functions.rc.SelectWindowFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentTypeHomeActions
	extends BaseLiferayActions {
	public CPDocumentsAndMediaDocumentTypeHomeActions(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPDocumentsAndMediaDocumentTypeHomePaths.getPaths();
	}

	public void click(String target, String value) throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((target.equals("SEARCH_BUTTON"))) {
			clickFunctions.valueClickAtAndWait(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void selectWindow(String target, String value)
		throws Exception {
		String[] params = ActionsUtil.getParams(paths, target, value);

		SelectWindowFunctions selectWindowFunctions = new SelectWindowFunctions(selenium);

		if ((target.equals("TOP"))) {
			selectWindowFunctions.selectFrameTop(params[0], params[1]);
		}
		else {
			super.selectWindow(params[0], params[1]);
		}
	}
}