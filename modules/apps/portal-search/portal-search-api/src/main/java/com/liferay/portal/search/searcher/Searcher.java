/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.searcher;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Performs a search using the Liferay Search Framework.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface Searcher {

	/**
	 * Performs a search.
	 *
	 * @param  searchRequest the search request to execute
	 * @return the search response. Its format and content depend on the search
	 *         request that was executed.
	 */
	public SearchResponse search(SearchRequest searchRequest);

}