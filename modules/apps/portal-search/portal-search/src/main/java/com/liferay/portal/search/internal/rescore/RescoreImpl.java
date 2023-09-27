/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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