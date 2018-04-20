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

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPNavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class StagingProcessesWebDisplayContext {

	public StagingProcessesWebDisplayContext(
		RenderResponse renderResponse, HttpServletRequest request,
		PageContext pageContext) {

		_renderResponse = renderResponse;
		_request = request;
		_pageContext = pageContext;
	}

	public JSPNavigationItemList getJSPNavigationItemList() {
		String activeTab = ParamUtil.getString(_request, "tabs1", "processes");

		PortletURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter("tabs1", "processes");

		NavigationItem processesNavigationItem = _createNavigationItem(
			activeTab.equals("processes"), renderURL,
			LanguageUtil.get(_request, "processes"));

		renderURL.setParameter("tabs1", "scheduled");

		NavigationItem scheduledProcessesNavigationItem = _createNavigationItem(
			activeTab.equals("scheduled"), renderURL,
			LanguageUtil.get(_request, "scheduled"));

		JSPNavigationItemList navigationItemList = new JSPNavigationItemList(
			_pageContext);

		navigationItemList.add(processesNavigationItem);
		navigationItemList.add(scheduledProcessesNavigationItem);

		return navigationItemList;
	}

	private NavigationItem _createNavigationItem(
		boolean active, PortletURL href, String label) {

		NavigationItem navigationItem = new NavigationItem();

		navigationItem.setActive(active);
		navigationItem.setHref(href);
		navigationItem.setLabel(label);

		return navigationItem;
	}

	private final PageContext _pageContext;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}