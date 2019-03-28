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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Lancha
 */
public class HorizontalCardTag extends CardTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setColHTML(String colHTML) {
		_colHTML = colHTML;
	}

	public void setLinkData(Map<String, Object> linkData) {
		_linkData = linkData;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setText(String text) {
		_text = text;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_colHTML = null;
		_linkData = null;
		_text = null;
	}

	@Override
	protected String getPage() {
		return "/card/horizontal_card/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("liferay-frontend:card:colHTML", _colHTML);
		request.setAttribute("liferay-frontend:card:linkData", _linkData);
		request.setAttribute("liferay-frontend:card:text", _text);
	}

	private String _colHTML;
	private Map<String, Object> _linkData;
	private String _text;

}