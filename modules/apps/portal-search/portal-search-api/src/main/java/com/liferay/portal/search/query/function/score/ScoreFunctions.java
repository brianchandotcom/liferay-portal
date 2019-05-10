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

package com.liferay.portal.search.query.function.score;

import com.liferay.portal.search.script.Script;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface ScoreFunctions {

	public ExponentialDecayScoreFunction exponentialDecay(
		String field, Object origin, Object scale, Object offset);

	public ExponentialDecayScoreFunction exponentialDecay(
		String field, Object origin, Object scale, Object offset, Double decay);

	public FieldValueFactorScoreFunction fieldValueFactor(String field);

	public GaussianDecayScoreFunction gaussianDecay(
		String field, Object origin, Object scale, Object offset);

	public GaussianDecayScoreFunction gaussianDecay(
		String field, Object origin, Object scale, Object offset, Double decay);

	public LinearDecayScoreFunction linearDecay(
		String field, Object origin, Object scale, Object offset);

	public LinearDecayScoreFunction linearDecay(
		String field, Object origin, Object scale, Object offset, Double decay);

	public RandomScoreFunction random();

	public ScriptScoreFunction script(Script script);

	public WeightScoreFunction weight(float weight);

}