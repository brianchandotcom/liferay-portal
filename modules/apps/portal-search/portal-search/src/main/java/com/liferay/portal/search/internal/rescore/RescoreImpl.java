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

package com.liferay.portal.search.internal.rescore;

import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;

/**
 * @author Bryan Engler
 */
public class RescoreImpl implements Rescore {

	public RescoreImpl(
		Query query, Integer windowSize, Float queryWeight,
		Float rescoreQueryWeight, ScoreMode scoreMode) {

		_query = query;
		_windowSize = windowSize;
		_queryWeight = queryWeight;
		_rescoreQueryWeight = rescoreQueryWeight;
		_scoreMode = scoreMode;
	}

	@Override
	public Query getQuery() {
		return _query;
	}

	@Override
	public Float getQueryWeight() {
		return _queryWeight;
	}

	@Override
	public Float getRescoreQueryWeight() {
		return _rescoreQueryWeight;
	}

	@Override
	public ScoreMode getScoreMode() {
		return _scoreMode;
	}

	@Override
	public Integer getWindowSize() {
		return _windowSize;
	}

	private final Query _query;
	private final Float _queryWeight;
	private final Float _rescoreQueryWeight;
	private final ScoreMode _scoreMode;
	private final Integer _windowSize;

}