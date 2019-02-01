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

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public class FieldValueFactorScoreFunction extends ScoreFunction {

	public FieldValueFactorScoreFunction(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

	public Float getFactor() {
		return _factor;
	}

	public String getField() {
		return _field;
	}

	public Double getMissing() {
		return _missing;
	}

	public Modifier getModifier() {
		return _modifier;
	}

	public void setFactor(Float factor) {
		_factor = factor;
	}

	public void setMissing(Double missing) {
		_missing = missing;
	}

	public void setModifier(Modifier modifier) {
		_modifier = modifier;
	}

	public enum Modifier {

		LN, LN1P, LN2P, LOG, LOG1P, LOG2P, NONE, RECIPROCAL, SQRT, SQUARE

	}

	private Float _factor;
	private final String _field;
	private Double _missing;
	private Modifier _modifier;

}