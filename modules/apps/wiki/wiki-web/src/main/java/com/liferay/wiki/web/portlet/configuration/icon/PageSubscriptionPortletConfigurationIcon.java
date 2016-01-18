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

package com.liferay.wiki.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class PageSubscriptionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PageSubscriptionPortletConfigurationIcon(
		PortletRequest portletRequest, WikiPage page, boolean subscribed) {

		super(portletRequest);

		_page = page;
		_subscribed = subscribed;
	}

	@Override
	public String getMessage() {
		if (_subscribed) {
			return "unsubscribe";
		}

		return "subscribe";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, WikiPortletKeys.WIKI_ADMIN,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/edit_page");

		if (_subscribed) {
			portletURL.setParameter(Constants.CMD, Constants.UNSUBSCRIBE);
		}
		else {
			portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
		}

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(request);

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

		if (WikiPagePermissionChecker.contains(
				wikiRequestHelper.getPermissionChecker(), _page,
				ActionKeys.SUBSCRIBE) &&
			(wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled() ||
			 wikiGroupServiceOverriddenConfiguration.
				 emailPageUpdatedEnabled())) {

			return true;
		}

		return false;
	}

	private final WikiPage _page;
	private final boolean _subscribed;

}