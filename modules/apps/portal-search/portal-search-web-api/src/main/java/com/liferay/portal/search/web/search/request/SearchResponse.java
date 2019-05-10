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

package com.liferay.portal.search.web.search.request;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.List;
import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     Rodrigo Paulino
 * @author     André de Oliveira
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
@ProviderType
public interface SearchResponse {

	public List<Document> getDocuments();

	public Facet getFacet(String fieldName);

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public String[] getHighlights();

	public Optional<String> getKeywordsOptional();

	public int getPaginationDelta();

	public int getPaginationStart();

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getSearchResponse()} and {@link
	 *             com.liferay.portal.search.searcher.SearchResponse#getRequestString(
	 *             )}
	 */
	@Deprecated
	public String getQueryString();

	public List<String> getRelatedQueriesSuggestions();

	/**
	 * Returns the search response.
	 *
	 * @return the search response as processed by the Liferay Search Framework
	 */
	public com.liferay.portal.search.searcher.SearchResponse
		getSearchResponse();

	public SearchSettings getSearchSettings();

	public Optional<String> getSpellCheckSuggestionOptional();

	public int getTotalHits();

}