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

package com.liferay.asset.publisher.web.portlet.filter;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderParametersPool;

import java.io.IOException;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Minhchau Dang
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.RELATED_ASSETS
	},
	service = PortletFilter.class
)
public class RelatedAssetsRenderParametersPortletFilter
	implements RenderFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		if (request.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY) == null) {
			clearDynamicServletRequestParameters(request);

			clearRenderRequestParameters(renderRequest, request);
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	protected void clearDynamicServletRequestParameters(
		HttpServletRequest request) {

		DynamicServletRequest dynamicServletRequest = null;

		while (request instanceof HttpServletRequestWrapper) {
			if (request instanceof DynamicServletRequest) {
				dynamicServletRequest = (DynamicServletRequest)request;

				break;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)request;

			request =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (dynamicServletRequest != null) {
			Map<String, String[]> dynamicParameterMap =
				dynamicServletRequest.getDynamicParameterMap();

			dynamicParameterMap.clear();
		}
	}

	protected void clearRenderRequestParameters(
		RenderRequest renderRequest, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = PortalUtil.getPortletId(renderRequest);

		RenderParametersPool.clear(request, themeDisplay.getPlid(), portletId);
	}

}