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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.search.request.SearchResponseImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.List;
import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * @author André de Oliveira
 */
public class PortletSharedSearchResponseImpl
	implements PortletSharedSearchResponse {

	public PortletSharedSearchResponseImpl(
		SearchResponseImpl searchResponseImpl,
		PortletSharedRequestHelper portletSharedRequestHelper) {

		_searchResponseImpl = searchResponseImpl;
		_portletSharedRequestHelper = portletSharedRequestHelper;
	}

	@Override
	public List<Document> getDocuments() {
		SearchResponse searchResponse = _searchResponseImpl.getSearchResponse();

		return searchResponse.getDocuments71();
	}

	@Override
	public Facet getFacet(String name) {
		SearchResponse searchResponse = getSearchResponse();

		return searchResponse.withFacetContextGet(
			facetContext -> facetContext.getFacet(name));
	}

	@Override
	public SearchResponse getFederatedSearchResponse(
		Optional<String> federatedSearchKeyOptional) {

		return _searchResponseImpl.getFederatedSearchResponse(
			federatedSearchKeyOptional);
	}

	@Override
	public String[] getHighlights() {
		return null;
	}

	@Override
	public Optional<String> getKeywordsOptional() {
		return _searchResponseImpl.getKeywordsOptional();
	}

	@Override
	public int getPaginationDelta() {
		return _searchResponseImpl.getPaginationDelta();
	}

	@Override
	public int getPaginationStart() {
		return _searchResponseImpl.getPaginationStart();
	}

	@Override
	public Optional<String> getParameter(
		String name, RenderRequest renderRequest) {

		return _portletSharedRequestHelper.getParameter(name, renderRequest);
	}

	@Override
	public Optional<String[]> getParameterValues(
		String name, RenderRequest renderRequest) {

		return _portletSharedRequestHelper.getParameterValues(
			name, renderRequest);
	}

	@Override
	public Optional<PortletPreferences> getPortletPreferences(
		RenderRequest renderRequest) {

		return Optional.ofNullable(renderRequest.getPreferences());
	}

	@Override
	public String getQueryString() {
		return _searchResponseImpl.getQueryString();
	}

	@Override
	public List<String> getRelatedQueriesSuggestions() {
		return _searchResponseImpl.getRelatedQueriesSuggestions();
	}

	@Override
	public SearchResponse getSearchResponse() {
		return _searchResponseImpl.getSearchResponse();
	}

	@Override
	public SearchSettings getSearchSettings() {
		return _searchResponseImpl.getSearchSettings();
	}

	@Override
	public Optional<String> getSpellCheckSuggestionOptional() {
		return _searchResponseImpl.getSpellCheckSuggestionOptional();
	}

	@Override
	public ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Override
	public int getTotalHits() {
		return _searchResponseImpl.getTotalHits();
	}

	private final PortletSharedRequestHelper _portletSharedRequestHelper;
	private final SearchResponseImpl _searchResponseImpl;

}