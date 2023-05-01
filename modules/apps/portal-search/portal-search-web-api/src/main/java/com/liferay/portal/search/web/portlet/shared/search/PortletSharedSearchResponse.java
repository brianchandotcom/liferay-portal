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

package com.liferay.portal.search.web.portlet.shared.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.List;
import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface PortletSharedSearchResponse {

	public List<Document> getDocuments();

	public Facet getFacet(String name);

	public SearchResponse getFederatedSearchResponse(String federatedSearchKey);

	public String getKeywords();

	public int getPaginationDelta();

	public int getPaginationStart();

	public String getParameter(String name, RenderRequest renderRequest);

	public String[] getParameterValues(
		String name, RenderRequest renderRequest);

	public Optional<PortletPreferences> getPortletPreferences(
		RenderRequest renderRequest);

	public List<String> getRelatedQueriesSuggestions();

	/**
	 * Returns the search response shared by the portlets.
	 *
	 * @return the search response as processed by the Liferay Search Framework
	 */
	public SearchResponse getSearchResponse();

	public SearchSettings getSearchSettings();

	public String getSpellCheckSuggestion();

	public ThemeDisplay getThemeDisplay(RenderRequest renderRequest);

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #getSearchResponse()} and {@link
	 *             SearchResponse#getTotalHits()}
	 */
	@Deprecated
	public int getTotalHits();

}