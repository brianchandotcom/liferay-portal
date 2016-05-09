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
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-frontend:add-menu:addMenuItems");

		if (ListUtil.isEmpty(addMenuItems)) {
			return SKIP_BODY;
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

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_addMenuItems = new ArrayList<>();
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
	}

	private List<AddMenuItem> _addMenuItems = new ArrayList<>();

}