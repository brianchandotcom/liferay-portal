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

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class VerticalCardSmallImageTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSrc(String src) {
		_src = src;
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_src = null;
	}

	@Override
	protected String getPage() {
		return "/card/vertical_card_small_image/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute(
			"liferay-frontend:vertical-card-small-image:cssClass", _cssClass);
		request.setAttribute(
			"liferay-frontend:vertical-card-small-image:src", _src);
	}

	private String _cssClass;
	private String _src;

}