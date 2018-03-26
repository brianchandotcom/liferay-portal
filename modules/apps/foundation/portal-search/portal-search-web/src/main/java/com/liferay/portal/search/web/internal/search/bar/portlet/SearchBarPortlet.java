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
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.search.bar.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/search/bar/view.jsp",
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

		setKeywords(searchBarPortletPreferences, portletSharedSearchSettings);

		filterByThisSite(
			searchBarPortletPreferences, portletSharedSearchSettings);
	}

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

		super.render(renderRequest, renderResponse);
	}

	protected static <T> void copy(Supplier<Optional<T>> from, Consumer<T> to) {
		Optional<T> optional = from.get();

		optional.ifPresent(to);
	}

	protected SearchBarPortletDisplayContext buildDisplayContext(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			new SearchBarPortletDisplayBuilder();

		copy(
			portletSharedSearchResponse::getKeywordsOptional,
			searchBarPortletDisplayBuilder::setKeywords);

		searchBarPortletDisplayBuilder.setKeywordsParameterName(
			searchBarPortletPreferences.getKeywordsParameterName());

		String scopeParameterName =
			searchBarPortletPreferences.getScopeParameterName();

		searchBarPortletDisplayBuilder.setScopeParameterName(
			scopeParameterName);

		copy(
			() -> portletSharedSearchResponse.getParameter(
				scopeParameterName, renderRequest),
			searchBarPortletDisplayBuilder::setScopeParameterValue);

		searchBarPortletDisplayBuilder.setSearchScopePreference(
			searchBarPortletPreferences.getSearchScopePreference());
		searchBarPortletDisplayBuilder.setThemeDisplay(
			portletSharedSearchResponse.getThemeDisplay(renderRequest));

		return searchBarPortletDisplayBuilder.build();
	}

	protected void filterByThisSite(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<Long> groupIdOptional = getThisSiteGroupId(
			searchBarPortletPreferences, portletSharedSearchSettings);

		groupIdOptional.ifPresent(
			groupId -> {
				portletSharedSearchSettings.addCondition(
					new BooleanClauseImpl(
						new TermQueryImpl(
							Field.GROUP_ID, String.valueOf(groupId)),
						BooleanClauseOccur.MUST));
			});
	}

	protected long getScopeGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected Optional<SearchScope> getSearchScope(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String parameterName =
			searchBarPortletPreferences.getScopeParameterName();

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter(parameterName);

		Optional<SearchScope> searchScopeOptional = parameterValueOptional.map(
			SearchScope::getSearchScope);

		return searchScopeOptional;
	}

	protected Optional<Long> getThisSiteGroupId(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<SearchScope> searchScopeOptional = getSearchScope(
			searchBarPortletPreferences, portletSharedSearchSettings);

		Optional<SearchScope> thisSiteSearchScopeOptional =
			searchScopeOptional.filter(SearchScope.THIS_SITE::equals);

		return thisSiteSearchScopeOptional.map(
			searchScope -> getScopeGroupId(portletSharedSearchSettings));
	}

	protected void setKeywords(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter(
				searchBarPortletPreferences.getKeywordsParameterName());

		parameterValueOptional.ifPresent(
			portletSharedSearchSettings::setKeywords);

		portletSharedSearchSettings.setKeywordsParameterName(
			searchBarPortletPreferences.getKeywordsParameterName());
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}