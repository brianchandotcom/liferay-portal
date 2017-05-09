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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ScreenNavigationTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (ListUtil.isEmpty(_screenNavigationCategories)) {
			return SKIP_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		ScreenNavigationRegistry screenNavigationHelper =
			ServletContextUtil.getScreenNavigationHelper();

		_screenNavigationCategories = ListUtil.filter(
			screenNavigationHelper.getScreenNavigationCategories(_key),
			new PredicateFilter<ScreenNavigationCategory>() {

				@Override
				public boolean filter(
					ScreenNavigationCategory screenNavigationCategory) {

					List<ScreenNavigationEntry> screenNavigationEntries =
						screenNavigationHelper.getScreenNavigationEntries(
							screenNavigationCategory);

					return ListUtil.isNotEmpty(screenNavigationEntries);
				}

			});

		return super.doStartTag();
	}

	public void setKey(String key) {
		_key = key;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	protected void cleanUp() {
		_key = null;
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
			"liferay-frontend:screen-navigation:portletURL", _portletURL);
		request.setAttribute(
			"liferay-frontend:screen-navigation:screenNavigationCategories",
			_screenNavigationCategories);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/screen_navigation/page.jsp";

	private String _key;
	private PortletURL _portletURL;
	private List<ScreenNavigationCategory> _screenNavigationCategories;

}