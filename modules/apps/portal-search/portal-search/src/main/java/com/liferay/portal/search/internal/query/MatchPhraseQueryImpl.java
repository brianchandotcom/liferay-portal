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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.query.MatchPhraseQuery;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class MatchPhraseQueryImpl
	extends BaseQueryImpl implements MatchPhraseQuery {

	public MatchPhraseQueryImpl(String field, Object value) {
		_field = field;
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public String getField() {
		return _field;
	}

	public Integer getSlop() {
		return _slop;
	}

	public Object getValue() {
		return _value;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setSlop(Integer slop) {
		_slop = slop;
	}

	private static final long serialVersionUID = 1L;

	private String _analyzer;
	private final String _field;
	private Integer _slop;
	private final Object _value;

}