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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Lancha
 */
public class SidebarPanelTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setNamespacedAttribute(
			request, "searchContainerId", _searchContainerId);
		setNamespacedAttribute(request, "resourceURL", _resourceURL);

		super.doEndTag();

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		super.doStartTag();

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setResourceURL(String resourceURL) {
		_resourceURL = resourceURL;

		setScopedAttribute("resourceURL", _resourceURL);
	}

	public void setSearchContainerId(String searchContainerId) {
		_searchContainerId = searchContainerId;

		setScopedAttribute("searchContainerId", _searchContainerId);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_resourceURL = null;
		_searchContainerId = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:sidebar-panel:";

	private static final String _END_PAGE = "/sidebar_panel/end.jsp";

	private static final String _START_PAGE = "/sidebar_panel/start.jsp";

	private String _resourceURL = null;
	private String _searchContainerId = null;

}