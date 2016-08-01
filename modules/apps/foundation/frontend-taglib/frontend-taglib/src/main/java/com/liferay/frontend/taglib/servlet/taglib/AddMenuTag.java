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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.servlet.taglib.util.AddMenuKeys;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuItems");

		if (ListUtil.isEmpty(addMenuItems)) {
			return SKIP_BODY;
		}

		_menuItemsCount = addMenuItems.size();

		List<MenuItemGroup> menuItemGroups = new ArrayList<>();

		if (_menuItemsCount == 1) {
			List<MenuItem> menuItems = new ArrayList<>();

			menuItems.addAll(getAddMenuItems());

			MenuItemGroup menuItem = new MenuItemGroup(
				AddMenuKeys.getAddMenuTypeLabel(
					AddMenuKeys.AddMenuType.DEFAULT),
				menuItems);

			menuItemGroups.add(menuItem);

			_menuItemGroups = menuItemGroups;
		}
		else {
			List<MenuItem> primaryMenuItems = new ArrayList<>();
			List<MenuItem> favoriteMenuItems = new ArrayList<>();
			List<MenuItem> recentMenuItems = new ArrayList<>();
			List<MenuItem> defaultMenuItems = new ArrayList<>();

			for (AddMenuItem addMenuItem : getAddMenuItems()) {
				if (Objects.equals(
						AddMenuKeys.AddMenuType.DEFAULT,
						addMenuItem.getType())) {

					defaultMenuItems.add(addMenuItem);
				}
				else if (Objects.equals(
							AddMenuKeys.AddMenuType.FAVORITE,
							addMenuItem.getType())) {

					favoriteMenuItems.add(addMenuItem);
				}
				else if (Objects.equals(
							AddMenuKeys.AddMenuType.PRIMARY,
							addMenuItem.getType())) {

					primaryMenuItems.add(addMenuItem);
				}
				else if (Objects.equals(
							AddMenuKeys.AddMenuType.RECENT,
							addMenuItem.getType())) {

					recentMenuItems.add(addMenuItem);
				}
			}

			boolean showDivider = false;

			if (!primaryMenuItems.isEmpty() &&
				(!defaultMenuItems.isEmpty() || !favoriteMenuItems.isEmpty() ||
				 !recentMenuItems.isEmpty())) {

				showDivider = true;
			}

			menuItemGroups.add(
				new MenuItemGroup(primaryMenuItems, showDivider));

			showDivider = false;

			if ((primaryMenuItems.size() > 0) && !favoriteMenuItems.isEmpty() &&
				(!recentMenuItems.isEmpty() || !defaultMenuItems.isEmpty())) {

				showDivider = true;
			}

			MenuItemGroup favoriteMenuItem = new MenuItemGroup(
				AddMenuKeys.getAddMenuTypeLabel(
					AddMenuKeys.AddMenuType.FAVORITE),
				favoriteMenuItems, showDivider);

			menuItemGroups.add(favoriteMenuItem);

			menuItemGroups.add(new MenuItemGroup(recentMenuItems));
			menuItemGroups.add(new MenuItemGroup(defaultMenuItems));

			_menuItemGroups = menuItemGroups;

			_numMenuItems = _maxItems;

			if (primaryMenuItems.size() > 0) {
				_numMenuItems += primaryMenuItems.size();

				_menuItemsCount -= primaryMenuItems.size();
			}
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		request.setAttribute(
			"liferay-frontend:add-menu:addMenuItems", _addMenuItems);

		return EVAL_BODY_INCLUDE;
	}

	public void setAddMenuItems(List<AddMenuItem> addMenuItems) {
		_addMenuItems = addMenuItems;
	}

	public void setMaxItems(int maxItems) {
		_maxItems = maxItems;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setViewMoreURL(String viewMoreURL) {
		_viewMoreURL = viewMoreURL;
	}

	@Override
	protected void cleanUp() {
		_addMenuItems = new ArrayList<>();
		_maxItems = AddMenuKeys.MAX_ITEMS;
		_menuItemGroups = new ArrayList<>();
		_menuItemsCount = 0;
		_numMenuItems = 0;
		_total = 0;
		_viewMoreURL = null;
	}

	protected List<AddMenuItem> getAddMenuItems() {
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuItems");

		return addMenuItems;
	}

	@Override
	protected String getPage() {
		return "/add_menu/page.jsp";
	}

	protected int getTotal() {
		List<AddMenuItem> addMenuItems = getAddMenuItems();

		return addMenuItems.size();
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:add-menu:maxItems", _maxItems);
		request.setAttribute(
			"liferay-frontend:add-menu:menuItemGroups", _menuItemGroups);
		request.setAttribute(
			"liferay-frontend:add-menu:menuItemsCount", _menuItemsCount);
		request.setAttribute(
			"liferay-frontend:add-menu:numMenuItems", _numMenuItems);
		request.setAttribute("liferay-frontend:add-menu:total", getTotal());
		request.setAttribute(
			"liferay-frontend:add-menu:viewMoreURL", _viewMoreURL);
	}

	private List<AddMenuItem> _addMenuItems = new ArrayList<>();
	private int _maxItems = AddMenuKeys.MAX_ITEMS;
	private List<MenuItemGroup> _menuItemGroups = new ArrayList<>();
	private int _menuItemsCount;
	private int _numMenuItems;
	private int _total;
	private String _viewMoreURL;

}