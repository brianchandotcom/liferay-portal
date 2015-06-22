/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.application.list.taglib.servlet.taglib.display.context.logic;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;

/**
 * @author Adolfo Pérez
 */
public class PanelCategoryHelper {

	public PanelCategoryHelper(
		PanelAppRegistry panelAppRegistry, PanelCategory panelCategory) {

		_panelAppRegistry = panelAppRegistry;
		_panelCategory = panelCategory;
	}

	public boolean containsPortlet(String portletId) {
		Iterable<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			_panelCategory);

		for (PanelApp panelApp : panelApps) {
			if (portletId.equals(panelApp.getPortletId())) {
				return true;
			}
		}

		return false;
	}

	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategory _panelCategory;

}