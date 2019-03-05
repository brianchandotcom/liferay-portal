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

import com.liferay.portal.search.query.function.score.ExponentialDecayScoreFunction;
import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;
import com.liferay.portal.search.query.function.score.GaussianDecayScoreFunction;
import com.liferay.portal.search.query.function.score.LinearDecayScoreFunction;
import com.liferay.portal.search.query.function.score.RandomScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctions;
import com.liferay.portal.search.query.function.score.ScriptScoreFunction;
import com.liferay.portal.search.query.function.score.WeightScoreFunction;
import com.liferay.portal.search.script.Script;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = ScoreFunctions.class)
public class ScoreFunctionsImpl implements ScoreFunctions {

	@Override
	public ExponentialDecayScoreFunction exponentialDecay(
		String field, Object origin, Object scale, Object offset) {

		return new ExponentialDecayScoreFunctionImpl(
			field, origin, scale, offset);
	}

	@Override
	public ExponentialDecayScoreFunction exponentialDecay(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		return new ExponentialDecayScoreFunctionImpl(
			field, origin, scale, offset, decay);
	}

	@Override
	public FieldValueFactorScoreFunction fieldValueFactor(String field) {
		return new FieldValueFactorScoreFunctionImpl(field);
	}

	@Override
	public GaussianDecayScoreFunction gaussianDecay(
		String field, Object origin, Object scale, Object offset) {

		return new GaussianDecayScoreFunctionImpl(field, origin, scale, offset);
	}

	@Override
	public GaussianDecayScoreFunction gaussianDecay(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		return new GaussianDecayScoreFunctionImpl(
			field, origin, scale, offset, decay);
	}

	@Override
	public LinearDecayScoreFunction linearDecay(
		String field, Object origin, Object scale, Object offset) {

		return new LinearDecayScoreFunctionImpl(field, origin, scale, offset);
	}

	@Override
	public LinearDecayScoreFunction linearDecay(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		return new LinearDecayScoreFunctionImpl(
			field, origin, scale, offset, decay);
	}

	@Override
	public RandomScoreFunction random() {
		return new RandomScoreFunctionImpl();
	}

	@Override
	public ScriptScoreFunction script(Script script) {
		return new ScriptScoreFunctionImpl(script);
	}

	@Override
	public WeightScoreFunction weight(float weight) {
		WeightScoreFunction weightScoreFunction = new WeightScoreFunctionImpl();

		weightScoreFunction.setWeight(weight);

		return weightScoreFunction;
	}

}