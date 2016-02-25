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

package com.liferay.portal.kernel.search.query;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.generic.FuzzyQuery;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MoreLikeThisQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
public interface QueryVisitor<T> {

	public T visitQuery(BooleanQuery booleanQuery);

	public T visitQuery(DisMaxQuery disMaxQuery);

	public T visitQuery(FuzzyQuery fuzzyQuery);

	public T visitQuery(MatchAllQuery matchAllQuery);

	public T visitQuery(MatchQuery matchQuery);

	public T visitQuery(MoreLikeThisQuery moreLikeThisQuery);

	public T visitQuery(MultiMatchQuery multiMatchQuery);

	public T visitQuery(NestedQuery nestedQuery);

	public T visitQuery(StringQuery stringQuery);

	public T visitQuery(TermQuery termQuery);

	public T visitQuery(TermRangeQuery termRangeQuery);

	public T visitQuery(WildcardQuery wildcardQuery);

}