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

package com.liferay.control.menu.taglib.servlet.taglib;

import com.liferay.control.menu.ControlMenuCategory;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.control.menu.taglib.servlet.ServletContextUtil;
import com.liferay.control.menu.util.ControlMenuCategoryRegistry;
import com.liferay.control.menu.util.ControlMenuEntryRegistry;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ControlMenuTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ControlMenuCategoryRegistry controlMenuCategoryRegistry =
			ServletContextUtil.getControlMenuCategoryRegistry();

		List<ControlMenuCategory> controlMenuCategories =
			controlMenuCategoryRegistry.getControlMenuCategories(
				ControlMenuCategoryKeys.ROOT);

		request.setAttribute(
			"liferay-control-menu:control-menu:control-menu-categories",
			controlMenuCategories);

		ControlMenuEntryRegistry controlMenuEntryRegistry =
			ServletContextUtil.getControlMenuEntryRegistry();

		request.setAttribute(
			"liferay-control-menu:control-menu:control-menu-entry-registry",
			controlMenuEntryRegistry);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/control_menu/page.jsp";

}