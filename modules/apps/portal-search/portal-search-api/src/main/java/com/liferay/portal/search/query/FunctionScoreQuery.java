/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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