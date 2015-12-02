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
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarButtonTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setIconCssClass(String iconCssClass) {
		_iconCssClass = iconCssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_active = false;
		_cssClass = null;
		_data = null;
		_href = null;
		_icon = null;
		_iconCssClass = null;
		_id = null;
		_label = null;
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
		request.setAttribute(
			"liferay-frontend:management-bar-button:active", _active);
		request.setAttribute(
			"liferay-frontend:management-bar-button:cssClass", _cssClass);
		request.setAttribute(
			"liferay-frontend:management-bar-button:data", _data);
		request.setAttribute(
			"liferay-frontend:management-bar-button:href", _href);
		request.setAttribute(
			"liferay-frontend:management-bar-button:icon", _icon);
		request.setAttribute(
			"liferay-frontend:management-bar-button:iconCssClass",
			_iconCssClass);
		request.setAttribute("liferay-frontend:management-bar-button:id", _id);
		request.setAttribute(
			"liferay-frontend:management-bar-button:label", _label);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar_button/page.jsp";

	private boolean _active;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _href;
	private String _icon;
	private String _iconCssClass;
	private String _id;
	private String _label;

}