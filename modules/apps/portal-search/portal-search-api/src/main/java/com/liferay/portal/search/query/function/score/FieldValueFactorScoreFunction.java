/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query.function.score;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface FieldValueFactorScoreFunction extends ScoreFunction {

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator);

	public Float getFactor();

	public String getField();

	public Double getMissing();

	public Modifier getModifier();

	public void setFactor(Float factor);

	public void setMissing(Double missing);

	public void setModifier(Modifier modifier);

	public enum Modifier {

		LN, LN1P, LN2P, LOG, LOG1P, LOG2P, NONE, RECIPROCAL, SQRT, SQUARE

	}

}