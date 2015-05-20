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

package com.liferay.portal.kernel.portlet.toolbar.bundle.portlettoolbar;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Philip Jones
 */
public class TestPortletToolbarContributor
	implements PortletToolbarContributor {

	public static final String LABEL = "LABEL";

	@Override
	public List<Menu> getPortletTitleMenus(PortletRequest portletRequest) {
		List<Menu> portletTitleMenus = new ArrayList<>();

		Menu menu = new Menu();

		menu.setLabel(LABEL);

		portletTitleMenus.add(menu);

		return portletTitleMenus;
	}

}