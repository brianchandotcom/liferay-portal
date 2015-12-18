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

package com.liferay.portal.kernel.search;

/**
 * @author     Raymond Augé
 * @author     Michael C. Han
 * @deprecated As of 7.0.0 , replaced by {@link
 *             com.liferay.portal.kernel.search.generic.TermRangeQueryImpl}
 */
@Deprecated
public class TermRangeQueryFactoryUtil {

	public static TermRangeQuery create(
		SearchContext searchContext, String field, String lowerTerm,
		String upperTerm, boolean includesLower, boolean includesUpper) {

		return getTermRangeQueryFactory(searchContext).create(
			field, lowerTerm, upperTerm, includesLower, includesUpper);
	}

	public static TermRangeQueryFactory getTermRangeQueryFactory(
		SearchContext searchContext) {

		String searchEngineId = searchContext.getSearchEngineId();

		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
			searchEngineId);

		return searchEngine.getTermRangeQueryFactory();
	}

}