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

package com.liferay.portal.search.elasticsearch6.internal.query.function.score;

import com.liferay.portal.search.elasticsearch6.internal.script.ScriptTranslator;
import com.liferay.portal.search.query.function.score.ExponentialDecayScoreFunction;
import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;
import com.liferay.portal.search.query.function.score.GuassianDecayScoreFunction;
import com.liferay.portal.search.query.function.score.LinearDecayScoreFunction;
import com.liferay.portal.search.query.function.score.RandomScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;
import com.liferay.portal.search.query.function.score.ScriptScoreFunction;
import com.liferay.portal.search.query.function.score.WeightScoreFunction;

import org.elasticsearch.index.query.functionscore.ExponentialDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.LinearDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.elasticsearch.script.Script;

/**
 * @author Michael C. Han
 */
public class ElasticsearchScoreFunctionTranslator
	implements ScoreFunctionTranslator<ScoreFunctionBuilder<?>> {

	@Override
	public ScoreFunctionBuilder<?> translate(
		ExponentialDecayScoreFunction exponentialDecayScoreFunction) {

		ExponentialDecayFunctionBuilder exponentialDecayFunctionBuilder = null;

		if (exponentialDecayScoreFunction.getDecay() == null) {
			exponentialDecayFunctionBuilder =
				new ExponentialDecayFunctionBuilder(
					exponentialDecayScoreFunction.getField(),
					exponentialDecayScoreFunction.getOrigin(),
					exponentialDecayScoreFunction.getScale(),
					exponentialDecayScoreFunction.getOffset());
		}
		else {
			exponentialDecayFunctionBuilder =
				new ExponentialDecayFunctionBuilder(
					exponentialDecayScoreFunction.getField(),
					exponentialDecayScoreFunction.getOrigin(),
					exponentialDecayScoreFunction.getScale(),
					exponentialDecayScoreFunction.getOffset(),
					exponentialDecayScoreFunction.getDecay());
		}

		if (exponentialDecayScoreFunction.getWeight() != null) {
			exponentialDecayFunctionBuilder.setWeight(
				exponentialDecayScoreFunction.getWeight());
		}

		return exponentialDecayFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		FieldValueFactorScoreFunction fieldValueFactorScoreFunction) {

		FieldValueFactorFunctionBuilder fieldValueFactorFunctionBuilder =
			new FieldValueFactorFunctionBuilder(
				fieldValueFactorScoreFunction.getField());

		if (fieldValueFactorScoreFunction.getWeight() != null) {
			fieldValueFactorFunctionBuilder.setWeight(
				fieldValueFactorScoreFunction.getWeight());
		}

		return fieldValueFactorFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		GuassianDecayScoreFunction guassianDecayScoreFunction) {

		GaussDecayFunctionBuilder gaussDecayFunctionBuilder = null;

		if (guassianDecayScoreFunction.getDecay() == null) {
			gaussDecayFunctionBuilder = new GaussDecayFunctionBuilder(
				guassianDecayScoreFunction.getField(),
				guassianDecayScoreFunction.getOrigin(),
				guassianDecayScoreFunction.getScale(),
				guassianDecayScoreFunction.getOffset());
		}
		else {
			gaussDecayFunctionBuilder = new GaussDecayFunctionBuilder(
				guassianDecayScoreFunction.getField(),
				guassianDecayScoreFunction.getOrigin(),
				guassianDecayScoreFunction.getScale(),
				guassianDecayScoreFunction.getOffset(),
				guassianDecayScoreFunction.getDecay());
		}

		if (guassianDecayScoreFunction.getWeight() != null) {
			gaussDecayFunctionBuilder.setWeight(
				guassianDecayScoreFunction.getWeight());
		}

		return gaussDecayFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		LinearDecayScoreFunction linearDecayScoreFunction) {

		LinearDecayFunctionBuilder linearDecayFunctionBuilder = null;

		if (linearDecayScoreFunction.getDecay() == null) {
			linearDecayFunctionBuilder = new LinearDecayFunctionBuilder(
				linearDecayScoreFunction.getField(),
				linearDecayScoreFunction.getOrigin(),
				linearDecayScoreFunction.getScale(),
				linearDecayScoreFunction.getOffset());
		}
		else {
			linearDecayFunctionBuilder = new LinearDecayFunctionBuilder(
				linearDecayScoreFunction.getField(),
				linearDecayScoreFunction.getOrigin(),
				linearDecayScoreFunction.getScale(),
				linearDecayScoreFunction.getOffset(),
				linearDecayScoreFunction.getDecay());
		}

		if (linearDecayScoreFunction.getWeight() != null) {
			linearDecayFunctionBuilder.setWeight(
				linearDecayScoreFunction.getWeight());
		}

		return linearDecayFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		RandomScoreFunction randomScoreFunction) {

		RandomScoreFunctionBuilder randomScoreFunctionBuilder =
			new RandomScoreFunctionBuilder();

		if (randomScoreFunction.getField() != null) {
			randomScoreFunctionBuilder.setField(randomScoreFunction.getField());
		}

		if (randomScoreFunction.getSeed() != null) {
			randomScoreFunctionBuilder.seed(randomScoreFunction.getSeed());
		}

		if (randomScoreFunction.getWeight() != null) {
			randomScoreFunctionBuilder.setWeight(
				randomScoreFunction.getWeight());
		}

		return randomScoreFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		ScriptScoreFunction scriptScoreFunction) {

		Script script = _scriptTranslator.translate(
			scriptScoreFunction.getScript());

		ScriptScoreFunctionBuilder scriptScoreFunctionBuilder =
			new ScriptScoreFunctionBuilder(script);

		if (scriptScoreFunction.getWeight() != null) {
			scriptScoreFunctionBuilder.setWeight(
				scriptScoreFunction.getWeight());
		}

		return scriptScoreFunctionBuilder;
	}

	@Override
	public ScoreFunctionBuilder<?> translate(
		WeightScoreFunction weightScoreFunction) {

		WeightBuilder weightBuilder = new WeightBuilder();

		if (weightScoreFunction.getWeight() != null) {
			weightBuilder.setWeight(weightScoreFunction.getWeight());
		}

		return weightBuilder;
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}