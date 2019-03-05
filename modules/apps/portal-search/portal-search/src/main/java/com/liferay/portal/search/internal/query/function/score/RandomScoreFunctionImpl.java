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

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.function.score.RandomScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class RandomScoreFunctionImpl
	extends ScoreFunctionImpl implements RandomScoreFunction {

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Integer getSeed() {
		return _seed;
	}

	@Override
	public void setField(String field) {
		_field = field;
	}

	@Override
	public void setSeed(Integer seed) {
		_seed = seed;
	}

	private String _field;
	private Integer _seed;

}