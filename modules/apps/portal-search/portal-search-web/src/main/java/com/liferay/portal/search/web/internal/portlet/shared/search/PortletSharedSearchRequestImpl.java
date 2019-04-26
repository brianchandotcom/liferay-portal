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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.search.request.SearchContainerBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchContextBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchRequestImpl;
import com.liferay.portal.search.web.internal.search.request.SearchResponseImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.task.PortletSharedTaskExecutor;
import com.liferay.portal.search.web.search.request.SearchSettings;
import com.liferay.portal.search.web.search.request.SearchSettingsContributor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PortletSharedSearchRequest.class)
public class PortletSharedSearchRequestImpl
	implements PortletSharedSearchRequest {

	@Override
	public PortletSharedSearchResponse search(RenderRequest renderRequest) {
		return portletSharedTaskExecutor.executeOnlyOnce(
			() -> doSearch(renderRequest),
			PortletSharedSearchResponse.class.getSimpleName(), renderRequest);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_portletSharedSearchContributors =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PortletSharedSearchContributor.class,
				"javax.portlet.name");
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

		return new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);
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

	protected SearchRequestImpl createSearchRequestImpl(
		ThemeDisplay themeDisplay, RenderRequest renderRequest) {

		SearchContextBuilder searchContextBuilder = () -> buildSearchContext(
			themeDisplay);

		SearchContainerBuilder searchContainerBuilder =
			searchSettings -> buildSearchContainer(
				searchSettings, renderRequest);

		return new SearchRequestImpl(
			searchContextBuilder, searchContainerBuilder, searcher,
			searchRequestBuilderFactory);
	}

	@Deactivate
	protected void deactivate() {
		_portletSharedSearchContributors.close();
	}

	protected PortletSharedSearchResponse doSearch(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = getThemeDisplay(renderRequest);

		Stream<SearchSettingsContributor> stream =
			getSearchSettingsContributorsStream(themeDisplay, renderRequest);

		SearchRequestImpl searchRequestImpl = createSearchRequestImpl(
			themeDisplay, renderRequest);

		stream.forEach(searchRequestImpl::addSearchSettingsContributor);

		SearchResponseImpl searchResponseImpl = searchRequestImpl.search();

		return new PortletSharedSearchResponseImpl(
			searchResponseImpl, portletSharedRequestHelper);
	}

	protected Stream<Portlet> getPortletsStream(Layout layout, long companyId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(false);

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			return portlets.stream();
		}

		return Stream.concat(
			portlets.stream(), _getInstantiatedPortletsStream(layout, companyId)
		).distinct();
	}

	protected SearchSettingsContributor getSearchSettingsContributor(
		PortletSharedSearchContributor portletSharedSearchContributor,
		Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

		Optional<PortletPreferences> portletPreferencesOptional =
			portletPreferencesLookup.fetchPreferences(portlet, themeDisplay);

		return searchSettings -> portletSharedSearchContributor.contribute(
			new PortletSharedSearchSettingsImpl(
				searchSettings, portlet.getPortletId(),
				portletPreferencesOptional, portletSharedRequestHelper,
				renderRequest));
	}

	protected Optional<SearchSettingsContributor>
		getSearchSettingsContributorOptional(
			Portlet portlet, ThemeDisplay themeDisplay,
			RenderRequest renderRequest) {

		return Optional.ofNullable(
			_portletSharedSearchContributors.getService(
				portlet.getPortletName())
		).map(
			portletSharedSearchContributor -> getSearchSettingsContributor(
				portletSharedSearchContributor, portlet, themeDisplay,
				renderRequest)
		);
	}

	protected Stream<SearchSettingsContributor>
		getSearchSettingsContributorsStream(
			ThemeDisplay themeDisplay, RenderRequest renderRequest) {

		Stream<Portlet> portletsStream = getPortletsStream(
			themeDisplay.getLayout(), themeDisplay.getCompanyId());

		return portletsStream.map(
			portlet -> getSearchSettingsContributorOptional(
				portlet, themeDisplay, renderRequest)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		);
	}

	protected ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Reference
	protected PortletLocalService portletLocalService;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@Reference
	protected PortletPreferencesLookup portletPreferencesLookup;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

	@Reference
	protected PortletSharedTaskExecutor portletSharedTaskExecutor;

	@Reference
	protected Searcher searcher;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private Stream<Portlet> _getInstantiatedPortletsStream(
		Layout layout, long companyId) {

		List<com.liferay.portal.kernel.model.PortletPreferences>
			portletPreferencesList =
				portletPreferencesLocalService.getPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		return portletPreferencesList.stream(
		).map(
			portletPreferences -> portletLocalService.getPortletById(
				companyId, portletPreferences.getPortletId())
		).filter(
			portlet ->
				portlet.isInstanceable() &&
				Validator.isNotNull(portlet.getInstanceId())
		);
	}

	private ServiceTrackerMap<String, PortletSharedSearchContributor>
		_portletSharedSearchContributors;

}