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

import com.liferay.frontend.taglib.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.servlet.taglib.util.AddMenuKeys;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		List<AddMenuItem> addMenuFavItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuFavItems");

		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuItems");

		List<AddMenuItem> addMenuPrimaryItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuPrimaryItems");

		List<AddMenuItem> addMenuRecentItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuRecentItems");

		if (ListUtil.isEmpty(addMenuFavItems) &&
			ListUtil.isEmpty(addMenuItems) &&
			ListUtil.isEmpty(addMenuRecentItems) &&
			ListUtil.isEmpty(addMenuPrimaryItems)) {

			return SKIP_BODY;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		request.setAttribute(
			"liferay-frontend:add-menu:addMenuFavItems", _addMenuFavItems);

		request.setAttribute(
			"liferay-frontend:add-menu:addMenuItems", _addMenuItems);

		request.setAttribute(
			"liferay-frontend:add-menu:addMenuPrimaryItems",
			_addMenuPrimaryItems);

		request.setAttribute(
			"liferay-frontend:add-menu:addMenuRecentItems",
			_addMenuRecentItems);

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

	public void setViewMoreUrl(String viewMoreUrl) {
		_viewMoreUrl = viewMoreUrl;
	}

	@Override
	protected void cleanUp() {
		_addMenuFavItems = new ArrayList<>();
		_addMenuItems = new ArrayList<>();
		_addMenuPrimaryItems = new ArrayList<>();
		_addMenuRecentItems = new ArrayList<>();
		_maxItems = AddMenuKeys.MAX_ITEMS;
		_viewMoreUrl = null;
	}

	@Override
	protected String getPage() {
		return "/add_menu/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuItems");

		request.setAttribute(
			"liferay-frontend:add-menu:addMenuItems", addMenuItems);
		request.setAttribute("liferay-frontend:add-menu:maxItems", _maxItems);
		request.setAttribute(
			"liferay-frontend:add-menu:viewMoreUrl", _viewMoreUrl);
	}

	private List<AddMenuItem> _addMenuFavItems = new ArrayList<>();
	private List<AddMenuItem> _addMenuItems = new ArrayList<>();
	private List<AddMenuItem> _addMenuPrimaryItems = new ArrayList<>();
	private List<AddMenuItem> _addMenuRecentItems = new ArrayList<>();
	private int _maxItems = AddMenuKeys.MAX_ITEMS;
	private String _viewMoreUrl;

}