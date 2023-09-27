/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.function.score.FieldValueFactorScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class FieldValueFactorScoreFunctionImpl
	extends BaseScoreFunctionImpl implements FieldValueFactorScoreFunction {

	public FieldValueFactorScoreFunctionImpl(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

	@Override
	public Float getFactor() {
		return _factor;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Double getMissing() {
		return _missing;
	}

	@Override
	public Modifier getModifier() {
		return _modifier;
	}

	@Override
	public void setFactor(Float factor) {
		_factor = factor;
	}

	@Override
	public void setMissing(Double missing) {
		_missing = missing;
	}

	@Override
	public void setModifier(Modifier modifier) {
		_modifier = modifier;
	}

	private Float _factor;
	private final String _field;
	private Double _missing;
	private Modifier _modifier;

}