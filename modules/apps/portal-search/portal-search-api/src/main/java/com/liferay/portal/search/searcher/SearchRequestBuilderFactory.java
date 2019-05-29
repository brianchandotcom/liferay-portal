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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Creates a search request builder for building a search request object that
 * can be sent to the search engine when performing a search.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchRequestBuilderFactory {

	/**
	 * Instantiates a new search request builder.
	 *
	 * @return the search request builder
	 */
	public SearchRequestBuilder builder();

	public SearchRequestBuilder builder(SearchRequest searchRequest);

}