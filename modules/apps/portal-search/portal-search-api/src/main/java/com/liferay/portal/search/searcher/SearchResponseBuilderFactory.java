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
 * Creates a search response builder for building a search response object from
 * the search engine's response to an executed search request. This interface's
 * usage is intended for the Liferay Search Framework only.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponseBuilderFactory {

	/**
	 * Instantiates a new search response builder.
	 *
	 * @return the search response builder
	 */
	public SearchResponseBuilder builder();

}