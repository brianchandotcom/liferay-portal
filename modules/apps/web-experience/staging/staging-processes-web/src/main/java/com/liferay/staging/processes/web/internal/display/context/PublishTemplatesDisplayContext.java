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

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Péter Alius
 */
public class PublishTemplatesDisplayContext {

	public PublishTemplatesDisplayContext(
		RenderResponse renderResponse, HttpServletRequest request,
		PageContext pageContext) {

		_renderResponse = renderResponse;
		_request = request;
		_pageContext = pageContext;
	}

	public JSPNavigationItemList getJSPNavigationItemList() {
		PortletURL renderURL = _renderResponse.createRenderURL();

		renderURL.setParameter(
			"mvcRenderCommandName", "viewPublishConfigurations");

		NavigationItem publishTemplatesNavigationItem = _createNavigationItem(
			true, renderURL, LanguageUtil.get(_request, "publish-templates"));

		JSPNavigationItemList navigationItemList = new JSPNavigationItemList(
			_pageContext);

		navigationItemList.add(publishTemplatesNavigationItem);

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