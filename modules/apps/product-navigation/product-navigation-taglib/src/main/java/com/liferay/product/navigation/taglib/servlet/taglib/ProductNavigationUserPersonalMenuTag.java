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

package com.liferay.product.navigation.taglib.servlet.taglib;

import com.liferay.product.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.product.navigation.taglib.util.UserPersonalMenuHelper;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Pei-Jung Lan
 */
public class ProductNavigationUserPersonalMenuTag extends IncludeTag {

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_label = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-product-navigation:user-personal-menu:dropdownItems",
			UserPersonalMenuHelper.getDropdownItems(request));
		request.setAttribute(
			"liferay-product-navigation:user-personal-menu:label", _label);
	}

	private static final String _PAGE = "/user_personal_menu/page.jsp";

	private String _label;

}