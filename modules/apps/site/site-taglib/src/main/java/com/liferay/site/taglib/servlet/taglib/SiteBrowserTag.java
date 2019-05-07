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

package com.liferay.site.taglib.servlet.taglib;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class SiteBrowserTag extends IncludeTag {

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getEmptyResultsMessage() {
		return _emptyResultsMessage;
	}

	public String getEventName() {
		return _eventName;
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public int getGroupsCount() {
		return _groupsCount;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public long[] getSelectedGroupIds() {
		return _selectedGroupIds;
	}

	public boolean isShowSearch() {
		return _showSearch;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setEventName(String eventName) {
		_eventName = eventName;
	}

	public void setGroups(List<Group> groups) {
		_groups = groups;
	}

	public void setGroupsCount(int groupsCount) {
		_groupsCount = groupsCount;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setSelectedGroupIds(long[] selectedGroupIds) {
		_selectedGroupIds = selectedGroupIds;
	}

	public void setShowSearch(boolean showSearch) {
		_showSearch = showSearch;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = "icon";
		_emptyResultsMessage = null;
		_eventName = null;
		_groups = null;
		_groupsCount = 0;
		_portletURL = null;
		_selectedGroupIds = null;
		_showSearch = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:displayStyle", _displayStyle);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:emptyResultsMessage",
			_getEmptyResultsMessage(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:eventName", _eventName);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:groups", _groups);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:groupsCount", _groupsCount);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:portletURL", _portletURL);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:selectedGroupIds", _selectedGroupIds);
		httpServletRequest.setAttribute(
			"liferay-site:site-browser:showSearch", _showSearch);
	}

	private String _getEmptyResultsMessage(
		HttpServletRequest httpServletRequest) {

		if (Validator.isNotNull(_emptyResultsMessage)) {
			return _emptyResultsMessage;
		}

		return LanguageUtil.get(httpServletRequest, "no-results-were-found");
	}

	private static final String _PAGE = "/site_browser/page.jsp";

	private String _displayStyle = "icon";
	private String _emptyResultsMessage;
	private String _eventName;
	private List<Group> _groups;
	private int _groupsCount;
	private PortletURL _portletURL;
	private long[] _selectedGroupIds;
	private boolean _showSearch;

}