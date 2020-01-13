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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-search-bar",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search Bar",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/search/bar/view.jsp",
		"javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class SearchBarPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				Optional.ofNullable(renderRequest.getPreferences()));

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			buildDisplayContext(
				portletSharedSearchResponse, renderRequest,
				searchBarPortletPreferences);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchBarPortletDisplayContext);

		if (searchBarPortletDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	protected SearchBarPortletDisplayContext buildDisplayContext(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest,
			SearchBarPortletPreferences searchBarPortletPreferences)
		throws PortletException {

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			new SearchBarPortletDisplayBuilder(
				http, layoutLocalService, portal, renderRequest);

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		String keywordsParameterName = getKeywordsParameterName(
			portletSharedSearchResponse.getSearchSettings(),
			searchBarPortletPreferences, themeDisplay);

		String scopeParameterName =
			searchBarPortletPreferences.getScopeParameterName();

		return searchBarPortletDisplayBuilder.setDestination(
			searchBarPortletPreferences.getDestinationString()
		).setEmptySearchEnabled(
			isEmptySearchEnabled(portletSharedSearchResponse)
		).setInvisible(
			searchBarPortletPreferences.isInvisible()
		).setKeywords(
			portletSharedSearchResponse.getKeywordsOptional()
		).setKeywordsParameterName(
			keywordsParameterName
		).setPaginationStartParameterName(
			getPaginationStartParameterName(portletSharedSearchResponse)
		).setScopeParameterName(
			scopeParameterName
		).setScopeParameterValue(
			portletSharedSearchResponse.getParameter(
				scopeParameterName, renderRequest)
		).setSearchScopePreference(
			searchBarPortletPreferences.getSearchScopePreference()
		).setThemeDisplay(
			themeDisplay
		).build();
	}

	protected String getKeywordsParameterName(
		SearchSettings searchSettings,
		SearchBarPortletPreferences searchBarPortletPreferences,
		ThemeDisplay themeDisplay) {

		if (!SearchBarPortletDestinationUtil.isSameDestination(
				searchBarPortletPreferences, themeDisplay)) {

			return searchBarPortletPreferences.getKeywordsParameterName();
		}

		Optional<String> optional = searchSettings.getKeywordsParameterName();

		return optional.orElse(
			searchBarPortletPreferences.getKeywordsParameterName());
	}

	protected String getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest request = searchResponse.getRequest();

		return request.getPaginationStartParameterName();
	}

	protected boolean isEmptySearchEnabled(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest request = searchResponse.getRequest();

		return request.isEmptySearchEnabled();
	}

	@Reference
	protected Http http;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}