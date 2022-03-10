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

package com.liferay.portal.search.web.internal.custom.filter.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayContext;
import com.liferay.portal.search.web.internal.custom.filter.display.context.builder.CustomFilterDisplayContextBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Nazar
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-custom-filter",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Custom Filter",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/custom/filter/view.jsp",
		"javax.portlet.name=" + CustomFilterPortletKeys.CUSTOM_FILTER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class CustomFilterPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		CustomFilterPortletPreferences customFilterPortletPreferences =
			new CustomFilterPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		CustomFilterDisplayContext customFilterDisplayContext =
			_createCustomFilterDisplayContext(
				customFilterPortletPreferences, portletSharedSearchResponse,
				renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, customFilterDisplayContext);

		if (customFilterDisplayContext.isRenderNothing() ||
			customFilterPortletPreferences.isImmutable() ||
			customFilterPortletPreferences.isInvisible()) {

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected Http http;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private CustomFilterDisplayContext _buildDisplayContext(
			CustomFilterPortletPreferences customFilterPortletPreferences,
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest)
		throws ConfigurationException {

		String parameterName = CustomFilterPortletUtil.getParameterName(
			customFilterPortletPreferences);

		SearchResponse searchResponse = _getSearchResponse(
			portletSharedSearchResponse, customFilterPortletPreferences);

		SearchRequest searchRequest = searchResponse.getRequest();

		return CustomFilterDisplayContextBuilder.builder(
		).customHeadingOptional(
			customFilterPortletPreferences.getCustomHeadingOptional()
		).disabled(
			customFilterPortletPreferences.isDisabled()
		).filterFieldOptional(
			customFilterPortletPreferences.getFilterFieldOptional()
		).http(
			http
		).immutable(
			customFilterPortletPreferences.isImmutable()
		).filterValueOptional(
			customFilterPortletPreferences.getFilterValueOptional()
		).parameterName(
			parameterName
		).parameterValueOptional(
			portletSharedSearchResponse.getParameter(
				parameterName, renderRequest)
		).queryNameOptional(
			customFilterPortletPreferences.getQueryNameOptional()
		).renderNothing(
			_isRenderNothing(searchRequest)
		).themeDisplay(
			portletSharedSearchResponse.getThemeDisplay(renderRequest)
		).build();
	}

	private CustomFilterDisplayContext _createCustomFilterDisplayContext(
		CustomFilterPortletPreferences customFilterPortletPreferences,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		try {
			return _buildDisplayContext(
				customFilterPortletPreferences, portletSharedSearchResponse,
				renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private SearchResponse _getSearchResponse(
		PortletSharedSearchResponse portletSharedSearchResponse,
		CustomFilterPortletPreferences customFilterPortletPreferences) {

		return portletSharedSearchResponse.getFederatedSearchResponse(
			customFilterPortletPreferences.getFederatedSearchKeyOptional());
	}

	private boolean _isRenderNothing(SearchRequest searchRequest) {
		if ((searchRequest.getQueryString() == null) &&
			!searchRequest.isEmptySearchEnabled()) {

			return true;
		}

		return false;
	}

}