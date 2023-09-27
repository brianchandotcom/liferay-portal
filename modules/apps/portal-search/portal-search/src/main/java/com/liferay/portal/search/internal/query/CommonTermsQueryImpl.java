/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.query.CommonTermsQuery;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class CommonTermsQueryImpl
	extends BaseQueryImpl implements CommonTermsQuery {

	public CommonTermsQueryImpl(String field, String text) {
		_field = field;
		_text = text;
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
	public Float getCutoffFrequency() {
		return _cutoffFrequency;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public String getHighFreqMinimumShouldMatch() {
		return _highFreqMinimumShouldMatch;
	}

	@Override
	public Operator getHighFreqOperator() {
		return _highFreqOperator;
	}

	@Override
	public String getLowFreqMinimumShouldMatch() {
		return _lowFreqMinimumShouldMatch;
	}

	@Override
	public Operator getLowFreqOperator() {
		return _lowFreqOperator;
	}

	@Override
	public String getText() {
		return _text;
	}

	@Override
	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	@Override
	public void setCutoffFrequency(Float cutoffFrequency) {
		_cutoffFrequency = cutoffFrequency;
	}

	@Override
	public void setHighFreqMinimumShouldMatch(
		String highFreqMinimumShouldMatch) {

		_highFreqMinimumShouldMatch = highFreqMinimumShouldMatch;
	}

	@Override
	public void setHighFreqOperator(Operator highFreqOperator) {
		_highFreqOperator = highFreqOperator;
	}

	@Override
	public void setLowFreqMinimumShouldMatch(String lowFreqMinimumShouldMatch) {
		_lowFreqMinimumShouldMatch = lowFreqMinimumShouldMatch;
	}

	@Override
	public void setLowFreqOperator(Operator lowFreqOperator) {
		_lowFreqOperator = lowFreqOperator;
	}

	private static final long serialVersionUID = 1L;

	private String _analyzer;
	private Float _cutoffFrequency;
	private final String _field;
	private String _highFreqMinimumShouldMatch;
	private Operator _highFreqOperator = Operator.OR;
	private String _lowFreqMinimumShouldMatch;
	private Operator _lowFreqOperator = Operator.OR;
	private final String _text;

}