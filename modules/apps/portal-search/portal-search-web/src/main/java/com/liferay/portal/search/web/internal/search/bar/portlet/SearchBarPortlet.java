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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.web.internal.search.bar.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
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
		"com.liferay.portlet.header-portlet-css=/search/bar/css/main.css",
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
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
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
				searchBarPortletPreferences, portletSharedSearchResponse,
				renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, searchBarPortletDisplayContext);

		if (searchBarPortletDisplayContext.isDestinationUnreachable()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	protected SearchBarPortletDisplayContext buildDisplayContext(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			new SearchBarPortletDisplayBuilder(
				http, layoutLocalService, portal);

		searchBarPortletDisplayBuilder.setDestination(
			searchBarPortletPreferences.getDestinationString());

		SearchOptionalUtil.copy(
			portletSharedSearchResponse::getKeywordsOptional,
			searchBarPortletDisplayBuilder::setKeywords);

		SearchSettings searchSettings =
			portletSharedSearchResponse.getSearchSettings();

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		searchBarPortletDisplayBuilder.setKeywordsParameterName(
			getKeywordsParameterName(
				searchSettings, searchBarPortletPreferences, themeDisplay));

		String scopeParameterName =
			searchBarPortletPreferences.getScopeParameterName();

		searchBarPortletDisplayBuilder.setScopeParameterName(
			scopeParameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameter(
				scopeParameterName, renderRequest),
			searchBarPortletDisplayBuilder::setScopeParameterValue);

		searchBarPortletDisplayBuilder.setSearchScopePreference(
			searchBarPortletPreferences.getSearchScopePreference());
		searchBarPortletDisplayBuilder.setThemeDisplay(themeDisplay);

		searchBarPortletDisplayBuilder.setEmptySearchEnabled(
			isEmptySearchEnabled(searchSettings));

		return searchBarPortletDisplayBuilder.build();
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

	protected boolean isEmptySearchEnabled(SearchSettings searchSettings) {
		SearchContext searchContext = searchSettings.getSearchContext();

		if (GetterUtil.getBoolean(
				searchContext.getAttribute(
					SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH))) {

			return true;
		}

		return false;
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