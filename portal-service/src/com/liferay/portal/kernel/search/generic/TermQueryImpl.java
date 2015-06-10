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

package com.liferay.portal.kernel.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class TermQueryImpl extends BaseQueryImpl implements TermQuery {

	public TermQueryImpl(QueryTerm queryTerm) {
		_queryTerm = queryTerm;
	}

	public TermQueryImpl(String field, String value) {
		this(new QueryTermImpl(field, value));
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public QueryTerm getQueryTerm() {
		return _queryTerm;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	private String _analyzer;
	private final QueryTerm _queryTerm;

}