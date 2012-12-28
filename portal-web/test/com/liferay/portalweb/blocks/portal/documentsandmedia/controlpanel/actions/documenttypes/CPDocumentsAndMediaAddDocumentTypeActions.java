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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes;

import com.liferay.portalweb.blocks.base.actions.BaseActionsImpl;
import com.liferay.portalweb.blocks.base.actions.LiferayActions;
import com.liferay.portalweb.blocks.base.functions.ClickFunctions;
import com.liferay.portalweb.blocks.base.functions.SelectWindowFunctions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaAddDocumentTypeActions extends BaseActionsImpl
	implements LiferayActions {
	public CPDocumentsAndMediaAddDocumentTypeActions(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
		paths = CPDocumentsAndMediaAddDocumentTypePaths.getPaths();
	}

	public void click(String param1, String param2) throws Exception {
		String[] params = getParams(param1, param2);

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		if ((param1.equals("BUTTON_CANCEL") || param1.equals("BUTTON_SAVE"))) {
			clickFunctions.valueClickAt(params[0], params[1]);
		}
		else {
			super.click(params[0], params[1]);
		}
	}

	public void selectWindow(String param1, String param2)
		throws Exception {
		String[] params = getParams(param1, param2);

		SelectWindowFunctions selectWindowFunctions = new SelectWindowFunctions(selenium);

		if ((param1.equals("TOP"))) {
			selectWindowFunctions.selectFrameTop(params[0], params[1]);
		}
		else {
			super.selectWindow(params[0], params[1]);
		}
	}
}