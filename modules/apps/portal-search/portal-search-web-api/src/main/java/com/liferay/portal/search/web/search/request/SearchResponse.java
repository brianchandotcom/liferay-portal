/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.search.request;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.List;

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

	public String getKeywords();

	public int getPaginationDelta();

	public int getPaginationStart();

	public List<String> getRelatedQueriesSuggestions();

	/**
	 * Returns the search response.
	 *
	 * @return the search response as processed by the Liferay Search Framework
	 */
	public com.liferay.portal.search.searcher.SearchResponse
		getSearchResponse();

	public SearchSettings getSearchSettings();

	public int getTotalHits();

}