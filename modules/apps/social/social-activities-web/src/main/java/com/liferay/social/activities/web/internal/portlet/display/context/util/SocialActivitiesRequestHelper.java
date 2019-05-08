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

package com.liferay.social.activities.web.internal.portlet.display.context.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.rss.util.RSSUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class SocialActivitiesRequestHelper extends BaseRequestHelper {

	public SocialActivitiesRequestHelper(
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);
	}

	public int getEnd() {
		if (_end != null) {
			return _end;
		}

		_end = ParamUtil.getInteger(getRequest(), "end", getMax());

		return _end;
	}

	public int getMax() {
		if (_max != null) {
			return _max;
		}

		PortletPreferences portletPreferences = _getPortletPreferences();

		_max = GetterUtil.getInteger(portletPreferences.getValue("max", "10"));

		return _max;
	}

	public int getRSSDelta() {
		if (_rssDelta != null) {
			return _rssDelta;
		}

		PortletPreferences portletPreferences = _getPortletPreferences();

		_rssDelta = GetterUtil.getInteger(
			portletPreferences.getValue("rssDelta", StringPool.BLANK),
			SearchContainer.DEFAULT_DELTA);

		return _rssDelta;
	}

	public String getRSSDisplayStyle() {
		if (_rssDisplayStyle != null) {
			return _rssDisplayStyle;
		}

		PortletPreferences portletPreferences = _getPortletPreferences();

		_rssDisplayStyle = portletPreferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		return _rssDisplayStyle;
	}

	public String getRSSFeedType() {
		if (_rssFeedType != null) {
			return _rssFeedType;
		}

		PortletPreferences portletPreferences = _getPortletPreferences();

		_rssFeedType = portletPreferences.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);

		return _rssFeedType;
	}

	public Group getScopeGroup() {
		if (_scopeGroup == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_scopeGroup = themeDisplay.getScopeGroup();
		}

		return _scopeGroup;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(getRequest(), "tabs1", "all");

		return _tabs1;
	}

	public boolean isRSSEnabled() {
		if (_rssEnabled != null) {
			return _rssEnabled;
		}

		if (PortalUtil.isRSSFeedsEnabled()) {
			PortletPreferences portletPreferences = _getPortletPreferences();

			_rssEnabled = GetterUtil.getBoolean(
				portletPreferences.getValue("enableRss", null), true);
		}
		else {
			_rssEnabled = false;
		}

		return _rssEnabled;
	}

	private PortletPreferences _getPortletPreferences() {
		PortletRequest portletRequest = _getPortletRequest();

		return portletRequest.getPreferences();
	}

	private PortletRequest _getPortletRequest() {
		if (_portletRequest != null) {
			return _portletRequest;
		}

		HttpServletRequest httpServletRequest = getRequest();

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		return _portletRequest;
	}

	private Integer _end;
	private Integer _max;
	private PortletRequest _portletRequest;
	private Integer _rssDelta;
	private String _rssDisplayStyle;
	private Boolean _rssEnabled;
	private String _rssFeedType;
	private Group _scopeGroup;
	private String _tabs1;

}