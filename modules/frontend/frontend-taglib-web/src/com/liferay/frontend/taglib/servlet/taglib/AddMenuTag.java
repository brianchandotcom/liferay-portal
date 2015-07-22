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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.ui.AddMenuItem;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-ui:add-menu:addMenuItems");

		if (ListUtil.isEmpty(addMenuItems)) {
			return SKIP_BODY;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		request.setAttribute("liferay-ui:add-menu:addMenuItems", _addMenuItems);

		return EVAL_BODY_INCLUDE;
	}

	public void setAddMenuItems(List<AddMenuItem> addMenuItems) {
		_addMenuItems = addMenuItems;
	}

	@Override
	protected void cleanUp() {
		_addMenuItems = Collections.emptyList();
	}

	@Override
	protected String getPage() {
		return "/html/taglib/ui/add_menu/end.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		List<AddMenuItem> addMenuItems =
			(List<AddMenuItem>)request.getAttribute(
				"liferay-ui:add-menu:addMenuItems");

		request.setAttribute("liferay-ui:add-menu:addMenuItems", addMenuItems);
	}

	private List<AddMenuItem> _addMenuItems = Collections.emptyList();

}