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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.web.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferences;
import com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
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
				portletSharedSearchSettings.getPortletPreferences());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				searchResultsPortletPreferences.getFederatedSearchKey());

		_paginate(
			searchResultsPortletPreferences, portletSharedSearchSettings,
			searchRequestBuilder);

		if (searchResultsPortletPreferences.isHighlightEnabled()) {
			searchRequestBuilder.highlightEnabled(true);

			String[] fieldsToDisplay = SearchStringUtil.splitAndUnquote(
				searchResultsPortletPreferences.getFieldsToDisplay());

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

		int paginationDelta = GetterUtil.getInteger(
			portletSharedSearchSettings.getParameter(
				searchResultsPortletPreferences.
					getPaginationDeltaParameterName()),
			searchResultsPortletPreferences.getPaginationDelta());

		portletSharedSearchSettings.setPaginationDelta(paginationDelta);
		searchRequestBuilder.size(paginationDelta);

		int paginationStart = GetterUtil.getInteger(
			portletSharedSearchSettings.getParameter(
				paginationStartParameterName));

		if (paginationStart > 0) {
			portletSharedSearchSettings.setPaginationStart(paginationStart);

			searchRequestBuilder.from((paginationStart - 1) * paginationDelta);
		}
	}

}