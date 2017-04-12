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

package com.liferay.portal.search.web.internal.searchbar.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.searchbar.constants.SearchBarConstants;
import com.liferay.portal.search.web.internal.searchbar.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.searchbar.constants.SearchBarWebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

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
		"com.liferay.portlet.css-class-wrapper=" +
			SearchBarConstants.CSS_CLASS_WRAPPER,
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=" +
			SearchBarConstants.HEADER_PORTLET_CSS,
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=" + SearchBarConstants.DISPLAY_NAME,
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=" +
			SearchBarConstants.VIEW_TEMPLATE,
		"javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class SearchBarPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		filter(searchBarPortletPreferences, portletSharedSearchSettings);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		Optional<String> keywordsOptional =
			portletSharedSearchResponse.getKeywords();

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				Optional.ofNullable(renderRequest.getPreferences()));

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			new SearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setKeywords(
			keywordsOptional.orElse(StringPool.BLANK));
		searchBarPortletDisplayBuilder.setKeywordsParameterName(
			searchBarPortletPreferences.getKeywordsParameterNameString());
		searchBarPortletDisplayBuilder.setScope(SearchScope.EVERYTHING);
		searchBarPortletDisplayBuilder.setSearchScopePreference(
			searchBarPortletPreferences.getSearchScopePreference());
		searchBarPortletDisplayBuilder.setScopePreferenceEverythingAvailable(
			true);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		renderRequest.setAttribute(
			SearchBarWebKeys.DISPLAY_CONTEXT, searchBarPortletDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	protected void filter(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String keywordsParameterName =
			searchBarPortletPreferences.getKeywordsParameterNameString();

		Optional<String> paramValueOptional =
			portletSharedSearchSettings.getParameter(keywordsParameterName);

		paramValueOptional.ifPresent(portletSharedSearchSettings::setKeywords);
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}