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

package com.liferay.flags.taglib.servlet.taglib;

import com.liferay.flags.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Julio Camarero
 * @deprecated As of 2.0.0, with no direct replacement
 */
@Deprecated
public class FlagsTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentTitle(String contentTitle) {
		_contentTitle = contentTitle;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setMessage(String message) {
		_message = message;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setReportedUserId(long reportedUserId) {
		_reportedUserId = reportedUserId;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_contentTitle = null;
		_enabled = true;
		_label = true;
		_message = null;
		_reportedUserId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-flags:flags:className", _className);
		request.setAttribute(
			"liferay-flags:flags:classPK", String.valueOf(_classPK));
		request.setAttribute("liferay-flags:flags:contentTitle", _contentTitle);
		request.setAttribute(
			"liferay-flags:flags:enabled", String.valueOf(_enabled));
		request.setAttribute(
			"liferay-flags:flags:label", String.valueOf(_label));
		request.setAttribute("liferay-flags:flags:message", _message);
		request.setAttribute(
			"liferay-flags:flags:reportedUserId",
			String.valueOf(_reportedUserId));
	}

	private static final String _PAGE = "/flags/page.jsp";

	private String _className;
	private long _classPK;
	private String _contentTitle;
	private boolean _enabled = true;
	private boolean _label = true;
	private String _message;
	private long _reportedUserId;

}