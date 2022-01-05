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

package com.liferay.portal.search.web.internal.search.results.portlet.shared.search;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferences;
import com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchResultsPortletKeys.SEARCH_RESULTS,
	service = PortletSharedSearchContributor.class
)
public class SearchResultsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchResultsPortletPreferences searchResultsPortletPreferences =
			new SearchResultsPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				searchResultsPortletPreferences.
					getFederatedSearchKeyOptional());

		_paginate(
			searchResultsPortletPreferences, portletSharedSearchSettings,
			searchRequestBuilder);

		if (searchResultsPortletPreferences.isHighlightEnabled()) {
			searchRequestBuilder.highlightEnabled(true);

			String[] fieldsToDisplay = SearchStringUtil.splitAndUnquote(
				searchResultsPortletPreferences.getFieldsToDisplayOptional());

			searchRequestBuilder.highlightFields(fieldsToDisplay);
		}
	}

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private void _paginate(
		SearchResultsPortletPreferences searchResultsPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings,
		SearchRequestBuilder searchRequestBuilder) {

		String paginationStartParameterName =
			searchResultsPortletPreferences.getPaginationStartParameterName();

		portletSharedSearchSettings.setPaginationStartParameterName(
			paginationStartParameterName);
		searchRequestBuilder.paginationStartParameterName(
			paginationStartParameterName);

		Optional<String> paginationStartParameterValueOptional =
			portletSharedSearchSettings.getParameterOptional(
				paginationStartParameterName);

		SearchOptionalUtil.copy(
			() -> paginationStartParameterValueOptional.map(Integer::valueOf),
			portletSharedSearchSettings::setPaginationStart);

		Optional<String> paginationDeltaParameterValueOptional =
			portletSharedSearchSettings.getParameterOptional(
				searchResultsPortletPreferences.
					getPaginationDeltaParameterName());

		Optional<Integer> paginationDeltaOptional =
			paginationDeltaParameterValueOptional.map(Integer::valueOf);

		int paginationDelta = paginationDeltaOptional.orElse(
			searchResultsPortletPreferences.getPaginationDelta());

		portletSharedSearchSettings.setPaginationDelta(paginationDelta);
		searchRequestBuilder.size(paginationDelta);

		SearchOptionalUtil.copy(
			() -> paginationStartParameterValueOptional.map(
				paginationStartValue ->
					(Integer.valueOf(paginationStartValue) - 1) *
						paginationDelta),
			searchRequestBuilder::from);
	}

}