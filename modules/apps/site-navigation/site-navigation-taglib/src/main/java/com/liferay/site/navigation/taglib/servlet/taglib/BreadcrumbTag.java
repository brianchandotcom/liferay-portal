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

package com.liferay.site.navigation.taglib.servlet.taglib;

import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class BreadcrumbTag extends IncludeTag {

	public List<BreadcrumbEntry> getBreadcrumbEntries() {
		return _breadcrumbEntries;
	}

	public void setBreadcrumbEntries(List<BreadcrumbEntry> breadcrumbEntries) {
		_breadcrumbEntries = breadcrumbEntries;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_breadcrumbEntries = new ArrayList<>();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-site-navigation:breadcrumb:breadcrumbEntries",
			_breadcrumbEntries);
	}

	private static final String _PAGE = "/breadcrumb/page.jsp";

	private List<BreadcrumbEntry> _breadcrumbEntries = new ArrayList<>();

}