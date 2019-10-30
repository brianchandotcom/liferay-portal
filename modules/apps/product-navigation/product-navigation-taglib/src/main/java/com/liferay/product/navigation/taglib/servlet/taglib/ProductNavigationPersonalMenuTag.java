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

import com.liferay.portal.kernel.model.User;
import com.liferay.product.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Pei-Jung Lan
 */
public class ProductNavigationPersonalMenuTag extends IncludeTag {

	public String getLabel() {
		return _label;
	}

	public String getSize() {
		return _size;
	}

	public User getUSer() {
		return _user;
	}

	public boolean isExpanded() {
		return _expanded;
	}

	public void setExpanded(boolean expanded) {
		_expanded = expanded;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setSize(String size) {
		_size = size;
	}

	public void setUser(User user) {
		_user = user;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_expanded = false;
		_label = null;
		_size = null;
		_user = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		long color = 0;

		if (_user != null) {
			color = _user.getUserId() % 10;
		}

		httpServletRequest.setAttribute(
			"liferay-product-navigation:personal-menu:color", color);
		httpServletRequest.setAttribute(
			"liferay-product-navigation:personal-menu:expanded", _expanded);
		httpServletRequest.setAttribute(
			"liferay-product-navigation:personal-menu:label", _label);
		httpServletRequest.setAttribute(
			"liferay-product-navigation:personal-menu:size", _size);
	}

	private static final String _PAGE = "/personal_menu/page.jsp";

	private boolean _expanded;
	private String _label;
	private String _size;
	private User _user;

}