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

package com.liferay.rss.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.rss.taglib.internal.servlet.ServletContextUtil;
import com.liferay.rss.util.RSSUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo García
 */
public class RSSTag extends IncludeTag {

	public int getDelta() {
		return _delta;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getFeedType() {
		return _feedType;
	}

	public String getMessage() {
		return _message;
	}

	public String getName() {
		return _name;
	}

	public ResourceURL getResourceURL() {
		return _resourceURL;
	}

	public String getUrl() {
		return _url;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setResourceURL(ResourceURL resourceURL) {
		_resourceURL = resourceURL;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_delta = SearchContainer.DEFAULT_DELTA;
		_displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
		_feedType = RSSUtil.FEED_TYPE_DEFAULT;
		_message = RSSUtil.RSS;
		_name = null;
		_resourceURL = null;
		_url = null;
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-rss:rss:message", _message);
		httpServletRequest.setAttribute("liferay-rss:rss:url", _getURL());
	}

	private String _getURL() {
		if (_resourceURL != null) {
			ResourceURL resourceURL = RSSUtil.getURL(
				_resourceURL, _delta, _displayStyle, _feedType, _name);

			return resourceURL.toString();
		}
		else if (Validator.isNotNull(_url)) {
			return RSSUtil.getURL(
				_url, _delta, _displayStyle, _feedType, _name);
		}

		return StringPool.BLANK;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/rss/page.jsp";

	private int _delta = SearchContainer.DEFAULT_DELTA;
	private String _displayStyle = RSSUtil.DISPLAY_STYLE_DEFAULT;
	private String _feedType = RSSUtil.FEED_TYPE_DEFAULT;
	private String _message = RSSUtil.RSS;
	private String _name;
	private ResourceURL _resourceURL;
	private String _url;

}