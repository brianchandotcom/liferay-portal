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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchRequestBuilderFactory.class)
public class SearchRequestBuilderFactoryImpl
	implements SearchRequestBuilderFactory {

	@Override
	public SearchRequestBuilder builder() {
		return new SearchRequestBuilderImpl(this);
	}

	@Override
	public SearchRequestBuilder builder(SearchRequest searchRequest) {
		return new SearchRequestBuilderImpl(this, searchRequest);
	}

}