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

package com.liferay.portal.kernel.portlet.toolbar.contributor;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eduardo García
 */
public abstract class BasePortletToolbarContributor
	implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (group.isLayoutPrototype()) {
			return Collections.emptyList();
		}

		List<MenuItem> portletTitleMenuItems = getPortletTitleMenuItems(
			portletRequest, portletResponse);

		if (ListUtil.isEmpty(portletTitleMenuItems)) {
			return Collections.emptyList();
		}

		List<Menu> menus = new ArrayList<>();

		Menu menu = new Menu();

		Map<String, Object> data = new HashMap<>();

		data.put("qa-id", "addButton");

		menu.setData(data);

		menu.setDirection("right");
		menu.setExtended(false);
		menu.setIcon("plus");
		menu.setMarkupView("lexicon");
		menu.setMenuItems(portletTitleMenuItems);
		menu.setMessage("add");
		menu.setScroll(false);
		menu.setShowArrow(false);
		menu.setShowWhenSingleIcon(true);

		menus.add(menu);

		return menus;
	}

	protected abstract List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse);

}