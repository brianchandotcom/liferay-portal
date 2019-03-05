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

import com.liferay.portal.search.query.function.score.LinearDecayScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class LinearDecayScoreFunctionImpl
	extends DecayScoreFunctionImpl implements LinearDecayScoreFunction {

	public LinearDecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset) {

		super(field, origin, scale, offset);
	}

	public LinearDecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		super(field, origin, scale, offset, decay);
	}

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

}