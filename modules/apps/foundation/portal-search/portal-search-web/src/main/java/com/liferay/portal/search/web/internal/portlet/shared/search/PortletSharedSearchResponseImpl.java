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
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchResponse;

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
		SearchResponse searchResponse,
		PortletSharedRequestHelper portletSharedRequestHelper) {

		_searchResponse = searchResponse;
		_portletSharedRequestHelper = portletSharedRequestHelper;
	}

	@Override
	public List<Document> getDocuments() {
		return _searchResponse.getDocuments();
	}

	@Override
	public Facet getFacet(String fieldName) {
		return _searchResponse.getFacet(fieldName);
	}

	@Override
	public String[] getHighlights() {
		return _searchResponse.getHighlights();
	}

	@Override
	public Optional<String> getKeywords() {
		return _searchResponse.getKeywords();
	}

	@Override
	public int getPaginationDelta() {
		return _searchResponse.getPaginationDelta();
	}

	@Override
	public int getPaginationStart() {
		return _searchResponse.getPaginationStart();
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
		return _searchResponse.getQueryString();
	}

	@Override
	public ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Override
	public int getTotalHits() {
		return _searchResponse.getTotalHits();
	}

	private final PortletSharedRequestHelper _portletSharedRequestHelper;
	private final SearchResponse _searchResponse;

}