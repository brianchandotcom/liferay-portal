/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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