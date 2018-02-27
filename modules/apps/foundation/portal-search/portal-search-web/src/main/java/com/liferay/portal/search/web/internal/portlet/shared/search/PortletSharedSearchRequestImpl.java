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

package com.liferay.portal.search.web.internal.portlet.shared.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.search.request.SearchContainerBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchContextBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchRequestImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.task.PortletSharedTaskExecutor;
import com.liferay.portal.search.web.search.request.SearchRequest;
import com.liferay.portal.search.web.search.request.SearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;
import com.liferay.portal.search.web.search.request.SearchSettingsContributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(service = PortletSharedSearchRequest.class)
public class PortletSharedSearchRequestImpl
	implements PortletSharedSearchRequest {

	@Override
	public PortletSharedSearchResponse search(RenderRequest renderRequest) {
		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedTaskExecutor.executeOnlyOnce(
				() -> doSearch(renderRequest),
				PortletSharedSearchResponse.class.getSimpleName(),
				renderRequest);

		return portletSharedSearchResponse;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removePortletSharedSearchContributor"
	)
	protected void addPortletSharedSearchContributor(
		PortletSharedSearchContributor portletSharedSearchContributor) {

		Class<?> clazz = portletSharedSearchContributor.getClass();

		String className = clazz.getName();

		_portletSharedSearchContributors.put(
			className, portletSharedSearchContributor);
	}

	protected SearchContainer<Document> buildSearchContainer(
		SearchSettings searchSettings, RenderRequest renderRequest) {

		Optional<String> paginationStartParameterNameOptional =
			searchSettings.getPaginationStartParameterName();

		Optional<Integer> paginationStartOptional =
			searchSettings.getPaginationStart();

		Optional<Integer> paginationDeltaOptional =
			searchSettings.getPaginationDelta();

		PortletRequest portletRequest = renderRequest;

		DisplayTerms displayTerms = null;
		DisplayTerms searchTerms = null;

		String curParam = paginationStartParameterNameOptional.orElse(
			SearchContainer.DEFAULT_CUR_PARAM);

		int cur = paginationStartOptional.orElse(0);

		int delta = paginationDeltaOptional.orElse(
			SearchContainer.DEFAULT_DELTA);

		PortletURL portletURL = new NullPortletURL();

		List<String> headerNames = null;
		String emptyResultsMessage = null;
		String cssClass = null;

		SearchContainer<Document> searchContainer = new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);

		return searchContainer;
	}

	protected SearchContext buildSearchContext(ThemeDisplay themeDisplay) {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setCollatedSpellCheckResultEnabled(false);
		queryConfig.setLocale(themeDisplay.getLocale());

		return searchContext;
	}

	protected void contributeSearchSettings(
		SearchRequest searchRequest, Stream<Portlet> portletsStream,
		ThemeDisplay themeDisplay, RenderRequest renderRequest) {

		Stream<Optional<SearchSettingsContributor>>
			searchSettingsContributorOptionalsStream = portletsStream.map(
				portlet -> getSearchSettingsContributor(
					portlet, themeDisplay, renderRequest));

		searchSettingsContributorOptionalsStream.forEach(
			searchSettingsContributorOptional ->
				searchSettingsContributorOptional.ifPresent(
					searchRequest::addSearchSettingsContributor));
	}

	protected PortletSharedSearchResponse doSearch(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = getThemeDisplay(renderRequest);

		SearchContextBuilder searchContextBuilder =
			() -> buildSearchContext(themeDisplay);

		SearchContainerBuilder searchContainerBuilder =
			searchSettings -> buildSearchContainer(
				searchSettings, renderRequest);

		SearchRequest searchRequest = new SearchRequestImpl(
			searchContextBuilder, searchContainerBuilder,
			facetedSearcherManager);

		Stream<Portlet> portletsStream = getExplicitlyAddedPortlets(
			themeDisplay);

		contributeSearchSettings(
			searchRequest, portletsStream, themeDisplay, renderRequest);

		SearchResponse searchResponse = searchRequest.search();

		return new PortletSharedSearchResponseImpl(
			searchResponse, portletSharedRequestHelper);
	}

	protected Stream<Portlet> getExplicitlyAddedPortlets(
		ThemeDisplay themeDisplay) {

		Layout layout = themeDisplay.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getExplicitlyAddedPortlets();

		return portlets.stream();
	}

	protected Optional<PortletPreferences> getPortletPreferences(
		ThemeDisplay themeDisplay, String portletId) {

		PortletPreferences portletPreferences =
			portletPreferencesLocalService.fetchPreferences(
				themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
				portletId);

		return Optional.ofNullable(portletPreferences);
	}

	protected Optional<PortletSharedSearchContributor>
		getPortletSharedSearchContributor(String className) {

		return Optional.ofNullable(
			_portletSharedSearchContributors.get(className));
	}

	protected Optional<SearchSettingsContributor> getSearchSettingsContributor(
		Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

		Optional<PortletSharedSearchContributor>
			portletSharedSearchContributorOptional =
				getPortletSharedSearchContributor(portlet.getPortletClass());

		Optional<SearchSettingsContributor> searchSettingsContributorOptional =
			portletSharedSearchContributorOptional.map(
				portletSharedSearchContributor -> getSearchSettingsContributor(
					portletSharedSearchContributor, portlet.getPortletId(),
					themeDisplay, renderRequest));

		return searchSettingsContributorOptional;
	}

	protected SearchSettingsContributor getSearchSettingsContributor(
		PortletSharedSearchContributor portletSharedSearchContributor,
		String portletId, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

		Optional<PortletPreferences> portletPreferencesOptional =
			getPortletPreferences(themeDisplay, portletId);

		return searchSettings -> portletSharedSearchContributor.contribute(
			new PortletSharedSearchSettingsImpl(
				searchSettings, portletPreferencesOptional,
				portletSharedRequestHelper, renderRequest));
	}

	protected ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	protected void removePortletSharedSearchContributor(
		PortletSharedSearchContributor portletSharedSearchContributor) {

		Class<?> clazz = portletSharedSearchContributor.getClass();

		String className = clazz.getName();

		_portletSharedSearchContributors.remove(className);
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

	@Reference
	protected PortletSharedTaskExecutor portletSharedTaskExecutor;

	private final Map<String, PortletSharedSearchContributor>
		_portletSharedSearchContributors = new HashMap<>();

}