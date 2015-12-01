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

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarStatusTag extends IncludeTag implements BodyTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setStatuses(Map<Integer, String> statuses) {
		_statuses = statuses;
	}

	@Override
	protected void cleanUp() {
		_portletURL = null;
		_status = -1;
		_statuses = null;
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
			"liferay-frontend:management-bar-status:portletURL", _portletURL);
		request.setAttribute(
			"liferay-frontend:management-bar-status:status", _status);
		request.setAttribute(
			"liferay-frontend:management-bar-status:statuses", _statuses);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:management-bar-status:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar_status/page.jsp";

	private PortletURL _portletURL;
	private int _status;
	private Map<Integer, String> _statuses;

}