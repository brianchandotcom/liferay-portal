/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

/**
 * @author Michael C. Han
 */
public class MatchPhraseQuery extends Query {

	public MatchPhraseQuery(String field, Object value) {
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