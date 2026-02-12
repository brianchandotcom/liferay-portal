/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.query.function.CombineFunction;
import com.liferay.portal.search.query.function.score.ScoreFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class FunctionScoreQuery extends Query {

	public FunctionScoreQuery(Query query) {
		_query = query;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public void addFilterQueryScoreFunctionHolder(
		Query filterQuery, ScoreFunction scoreFunction) {

		_filterQueryScoreFunctionHolders.add(
			new FilterQueryScoreFunctionHolderImpl(filterQuery, scoreFunction));
	}

	public CombineFunction getCombineFunction() {
		return _combineFunction;
	}

	public List<FilterQueryScoreFunctionHolder>
		getFilterQueryScoreFunctionHolders() {

		return Collections.unmodifiableList(_filterQueryScoreFunctionHolders);
	}

	public Float getMaxBoost() {
		return _maxBoost;
	}

	public Float getMinScore() {
		return _minScore;
	}

	public Query getQuery() {
		return _query;
	}

	public ScoreMode getScoreMode() {
		return _scoreMode;
	}

	public void setCombineFunction(CombineFunction combineFunction) {
		_combineFunction = combineFunction;
	}

	public void setMaxBoost(Float maxBoost) {
		_maxBoost = maxBoost;
	}

	public void setMinScore(Float minScore) {
		_minScore = minScore;
	}

	public void setScoreMode(ScoreMode scoreMode) {
		_scoreMode = scoreMode;
	}

	public interface FilterQueryScoreFunctionHolder {

		public Query getFilterQuery();

		public ScoreFunction getScoreFunction();

	}

	public class FilterQueryScoreFunctionHolderImpl
		implements FilterQueryScoreFunctionHolder {

		public FilterQueryScoreFunctionHolderImpl(
			Query filterQuery, ScoreFunction scoreFunction) {

			_filterQuery = filterQuery;
			_scoreFunction = scoreFunction;
		}

		public FilterQueryScoreFunctionHolderImpl(ScoreFunction scoreFunction) {
			_scoreFunction = scoreFunction;

			_filterQuery = null;
		}

		@Override
		public Query getFilterQuery() {
			return _filterQuery;
		}

		@Override
		public ScoreFunction getScoreFunction() {
			return _scoreFunction;
		}

		private final Query _filterQuery;
		private final ScoreFunction _scoreFunction;

	}

	public enum ScoreMode {

		AVG, FIRST, MAX, MIN, MULTIPLY, SUM

	}

	private static final long serialVersionUID = 1L;

	private CombineFunction _combineFunction;
	private final List<FilterQueryScoreFunctionHolder>
		_filterQueryScoreFunctionHolders = new ArrayList<>();
	private Float _maxBoost;
	private Float _minScore;
	private final Query _query;
	private ScoreMode _scoreMode;

}