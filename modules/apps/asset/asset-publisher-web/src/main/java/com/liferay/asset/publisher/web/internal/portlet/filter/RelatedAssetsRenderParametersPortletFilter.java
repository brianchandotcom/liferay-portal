/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.portlet.filter;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 * @author Preston Crary
 */
@Component(
	property = "javax.portlet.name=" + AssetPublisherPortletKeys.RELATED_ASSETS,
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

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		if (httpServletRequest.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY) ==
				null) {

			_clearDynamicServletRequestParameters(httpServletRequest);

			clearRenderRequestParameters(httpServletRequest, renderRequest);
		}

		filterChain.doFilter(renderRequest, renderResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	protected void clearRenderRequestParameters(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		RenderParametersPool.clear(
			httpServletRequest, themeDisplay.getPlid(),
			_portal.getPortletId(renderRequest));
	}

	private void _clearDynamicServletRequestParameters(
		HttpServletRequest httpServletRequest) {

		DynamicServletRequest dynamicServletRequest = null;

		while (httpServletRequest instanceof HttpServletRequestWrapper) {
			if (httpServletRequest instanceof DynamicServletRequest) {
				dynamicServletRequest =
					(DynamicServletRequest)httpServletRequest;

				break;
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)httpServletRequest;

			httpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (dynamicServletRequest != null) {
			Map<String, String[]> dynamicParameterMap =
				dynamicServletRequest.getDynamicParameterMap();

			dynamicParameterMap.clear();
		}
	}

	@Reference
	private Portal _portal;

}