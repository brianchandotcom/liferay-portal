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

package com.liferay.site.item.selector.web.internal.renderer;

import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.item.selector.display.context.SitesItemSelectorViewDisplayContext;
import com.liferay.site.item.selector.renderer.SiteItemSelectorViewRenderer;
import com.liferay.site.item.selector.web.internal.constants.SitesItemSelectorWebKeys;
import com.liferay.site.util.GroupURLProvider;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = SiteItemSelectorViewRenderer.class)
public class SiteItemSelectorViewRendererImpl
	implements SiteItemSelectorViewRenderer {

	@Override
	public void renderHTML(
			SitesItemSelectorViewDisplayContext
				sitesItemSelectorViewDisplayContext)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			sitesItemSelectorViewDisplayContext.getPortletRequest());

		httpServletRequest.setAttribute(
			SiteWebKeys.GROUP_URL_PROVIDER, _groupURLProvider);

		httpServletRequest.setAttribute(
			SitesItemSelectorWebKeys.SITES_ITEM_SELECTOR_DISPLAY_CONTEXT,
			sitesItemSelectorViewDisplayContext);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/view_sites.jsp");

		requestDispatcher.include(
			httpServletRequest,
			_portal.getHttpServletResponse(
				sitesItemSelectorViewDisplayContext.getPortletResponse()));
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.item.selector.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}