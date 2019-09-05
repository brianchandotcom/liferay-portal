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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.staging.taglib.servlet.taglib.base.BaseCssTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Borkuti
 */
public class PopoverTag extends BaseCssTag {

	public String getId() {
		return _id;
	}

	@Override
	public String getTagNameForCssPath() {
		return "popover";
	}

	public String getText() {
		return _text;
	}

	public String getTitle() {
		return _title;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setText(String text) {
		_text = text;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_id = StringPool.BLANK;
		_text = StringPool.BLANK;
		_title = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-staging:popover:id", _id);
		httpServletRequest.setAttribute("liferay-staging:popover:text", _text);
		httpServletRequest.setAttribute(
			"liferay-staging:popover:title", _title);
	}

	private static final String _PAGE = "/popover/page.jsp";

	private String _id = StringPool.BLANK;
	private String _text = StringPool.BLANK;
	private String _title = StringPool.BLANK;

}