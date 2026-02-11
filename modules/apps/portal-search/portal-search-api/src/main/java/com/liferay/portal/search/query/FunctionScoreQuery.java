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
public abstract class FunctionScoreQuery extends Query {

	public abstract void addFilterQueryScoreFunctionHolder(
		Query filterQuery, ScoreFunction scoreFunction);

	public abstract CombineFunction getCombineFunction();

	public abstract List<FilterQueryScoreFunctionHolder>
		getFilterQueryScoreFunctionHolders();

	public abstract Float getMaxBoost();

	public abstract Float getMinScore();

	public abstract Query getQuery();

	public abstract ScoreMode getScoreMode();

	public abstract void setCombineFunction(CombineFunction combineFunction);

	public abstract void setMaxBoost(Float maxBoost);

	public abstract void setMinScore(Float minScore);

	public abstract void setScoreMode(ScoreMode scoreMode);

	public interface FilterQueryScoreFunctionHolder {

		public Query getFilterQuery();

		public ScoreFunction getScoreFunction();

	}

	public enum ScoreMode {

		AVG, FIRST, MAX, MIN, MULTIPLY, SUM

	}

}