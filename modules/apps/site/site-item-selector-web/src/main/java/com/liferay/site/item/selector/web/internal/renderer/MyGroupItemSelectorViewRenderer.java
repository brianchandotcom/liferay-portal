/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.item.selector.web.internal.renderer;

import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.item.selector.web.internal.constants.SitesItemSelectorWebKeys;
import com.liferay.site.item.selector.web.internal.display.context.MySitesItemSelectorViewDisplayContext;
import com.liferay.site.util.GroupSearchProvider;
import com.liferay.site.util.GroupURLProvider;

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
public class MyGroupItemSelectorViewRenderer {

	public MyGroupItemSelectorViewRenderer(
		GroupSearchProvider groupSearchProvider,
		GroupURLProvider groupURLProvider, ServletContext servletContext) {

		_groupSearchProvider = groupSearchProvider;
		_groupURLProvider = groupURLProvider;
		_servletContext = servletContext;
	}

	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			GroupItemSelectorCriterion t, PortletURL portletURL,
			String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		servletRequest.setAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER, _groupSearchProvider);
		servletRequest.setAttribute(
			SiteWebKeys.GROUP_URL_PROVIDER, _groupURLProvider);

		MySitesItemSelectorViewDisplayContext
			mySitesItemSelectorViewDisplayContext =
				new MySitesItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest, t,
					itemSelectedEventName, portletURL, _groupSearchProvider);

		servletRequest.setAttribute(
			SitesItemSelectorWebKeys.SITES_ITEM_SELECTOR_DISPLAY_CONTEXT,
			mySitesItemSelectorViewDisplayContext);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/view_sites.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private final GroupSearchProvider _groupSearchProvider;
	private final GroupURLProvider _groupURLProvider;
	private final ServletContext _servletContext;

}