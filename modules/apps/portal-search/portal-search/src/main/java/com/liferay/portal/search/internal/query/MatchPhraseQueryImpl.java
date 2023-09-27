/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Integer getSlop() {
		return _slop;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	@Override
	public void setSlop(Integer slop) {
		_slop = slop;
	}

	private static final long serialVersionUID = 1L;

	private String _analyzer;
	private final String _field;
	private Integer _slop;
	private final Object _value;

}