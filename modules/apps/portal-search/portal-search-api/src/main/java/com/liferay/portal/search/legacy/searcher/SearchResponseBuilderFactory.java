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

package com.liferay.portal.search.legacy.searcher;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Creates a search response builder for building a search response from a
 * legacy search context. This is for backward compatibility only; new code
 * should use {@link
 * com.liferay.portal.search.searcher.SearchResponseBuilderFactory}.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponseBuilderFactory {

	/**
	 * Creates a search response builder from a legacy search context.
	 *
	 * @param  searchContext the search context
	 * @return the search response builder
	 */
	public SearchResponseBuilder builder(SearchContext searchContext);

}