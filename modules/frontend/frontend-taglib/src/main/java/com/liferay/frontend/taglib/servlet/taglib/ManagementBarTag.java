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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarTag extends IncludeTag implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActionButtons(String actionButtons) {
		_actionButtons = actionButtons;
	}

	public void setButtons(String buttons) {
		_buttons = buttons;
	}

	public void setFilters(String filters) {
		_filters = filters;
	}

	public void setIncludeCheckBox(boolean includeCheckBox) {
		_includeCheckBox = includeCheckBox;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSearchContainerId(String searchContainerId) {
		_searchContainerId = searchContainerId;
	}

	@Override
	protected void cleanUp() {
		_actionButtons = null;
		_buttons = null;
		_filters = null;
		_includeCheckBox = false;
		_searchContainerId = null;
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
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-frontend:management-bar:actionButtons", _actionButtons);
		request.setAttribute(
			"liferay-frontend:management-bar:buttons", _buttons);
		request.setAttribute(
			"liferay-frontend:management-bar:filters", _filters);
		request.setAttribute(
			"liferay-frontend:management-bar:includeCheckBox",
			_includeCheckBox);
		request.setAttribute(
			"liferay-frontend:management-bar:searchContainerId",
			_searchContainerId);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar/page.jsp";

	private String _actionButtons;
	private String _buttons;
	private String _filters;
	private boolean _includeCheckBox;
	private String _searchContainerId;

}