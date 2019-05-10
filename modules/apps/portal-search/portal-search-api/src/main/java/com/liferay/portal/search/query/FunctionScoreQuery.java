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

package com.liferay.portal.search.query;

import com.liferay.portal.search.query.function.CombineFunction;
import com.liferay.portal.search.query.function.score.ScoreFunction;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface FunctionScoreQuery extends Query {

	public void addFilterQueryScoreFunctionHolder(
		Query filterQuery, ScoreFunction scoreFunction);

	public CombineFunction getCombineFunction();

	public List<FilterQueryScoreFunctionHolder>
		getFilterQueryScoreFunctionHolders();

	public Float getMaxBoost();

	public Float getMinScore();

	public Query getQuery();

	public ScoreMode getScoreMode();

	public void setCombineFunction(CombineFunction combineFunction);

	public void setMaxBoost(Float maxBoost);

	public void setMinScore(Float minScore);

	public void setScoreMode(ScoreMode scoreMode);

	public interface FilterQueryScoreFunctionHolder {

		public Query getFilterQuery();

		public ScoreFunction getScoreFunction();

	}

	public enum ScoreMode {

		AVG, FIRST, MAX, MIN, MULTIPLY, SUM

	}

}