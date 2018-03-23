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

package com.liferay.frontend.taglib.servlet.taglib.base;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseButtonRowTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getId() {
		return _id;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_id = null;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:button-row:cssClass", _cssClass);
		request.setAttribute("liferay-frontend:button-row:id", _id);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:button-row:";

	private static final String _START_PAGE = "/button_row/start.jsp";

	private String _cssClass;
	private String _id;

}