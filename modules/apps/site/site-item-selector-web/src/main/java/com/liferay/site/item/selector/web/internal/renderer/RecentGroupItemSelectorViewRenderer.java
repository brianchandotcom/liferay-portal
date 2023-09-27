/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.item.selector.web.internal.renderer;

import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.item.selector.web.internal.constants.SitesItemSelectorWebKeys;
import com.liferay.site.item.selector.web.internal.display.context.RecentSitesItemSelectorViewDisplayContext;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.site.util.RecentGroupManager;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class RecentGroupItemSelectorViewRenderer {

	public RecentGroupItemSelectorViewRenderer(
		GroupURLProvider groupURLProvider,
		RecentGroupManager recentGroupManager, ServletContext servletContext) {

		_groupURLProvider = groupURLProvider;
		_recentGroupManager = recentGroupManager;
		_servletContext = servletContext;
	}

	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			GroupItemSelectorCriterion groupItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		servletRequest.setAttribute(
			SiteWebKeys.GROUP_URL_PROVIDER, _groupURLProvider);

		RecentSitesItemSelectorViewDisplayContext
			siteItemSelectorViewDisplayContext =
				new RecentSitesItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest,
					groupItemSelectorCriterion, itemSelectedEventName,
					portletURL, _recentGroupManager);

		servletRequest.setAttribute(
			SitesItemSelectorWebKeys.SITES_ITEM_SELECTOR_DISPLAY_CONTEXT,
			siteItemSelectorViewDisplayContext);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/view_sites.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private final GroupURLProvider _groupURLProvider;
	private final RecentGroupManager _recentGroupManager;
	private final ServletContext _servletContext;

}