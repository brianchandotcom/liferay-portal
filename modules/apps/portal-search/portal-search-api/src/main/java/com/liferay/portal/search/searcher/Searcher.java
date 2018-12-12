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

package com.liferay.portal.search.searcher;

import aQute.bnd.annotation.ProviderType;

/**
 * Performs a search using the Liferay Search Framework.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface Searcher {

	/**
	 * Performs a search.
	 *
	 * @param searchRequest the search request with parameters to be used
	 * @return the search response with various outcomes returned
	 *
	 * @review
	 */
	public SearchResponse search(SearchRequest searchRequest);

}