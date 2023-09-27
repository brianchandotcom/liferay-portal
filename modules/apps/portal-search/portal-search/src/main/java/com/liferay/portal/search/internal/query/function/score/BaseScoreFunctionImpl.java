/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.function.score.ScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class BaseScoreFunctionImpl implements ScoreFunction {

	@Override
	public abstract <T> T accept(
		ScoreFunctionTranslator<T> scoreFunctionTranslator);

	@Override
	public Float getWeight() {
		return _weight;
	}

	@Override
	public void setWeight(Float weight) {
		_weight = weight;
	}

	private Float _weight;

}