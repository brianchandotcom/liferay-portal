/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Michael C. Han
 */
public class BaseQueryFactoryUtil<T> {

	public T getQueryFactory(SearchContext searchContext) {
		String searchEngineId = searchContext.getSearchEngineId();

		SearchEngine searchEngine = null;

		if (Validator.isNotNull(searchEngineId)) {
			searchEngine = SearchEngineUtil.getSearchEngine(searchEngineId);
		}
		else {
			searchEngine = SearchEngineUtil.getSearchEngine();
		}

		if (searchEngine.isLuceneBased()) {
			return _luceneBasedQueryFactory;
		}
		else {
			return _genericQueryFactory;
		}
	}

	public void setGenericQueryFactory(T genericQueryFactory) {
		_genericQueryFactory = genericQueryFactory;
	}

	public void setLuceneBasedQueryFactory(T luceneBasedQueryFactory) {
		_luceneBasedQueryFactory = luceneBasedQueryFactory;
	}

	private T _genericQueryFactory;
	private T _luceneBasedQueryFactory;

}