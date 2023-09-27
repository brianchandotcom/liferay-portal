/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.function.score.GaussianDecayScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class GaussianDecayScoreFunctionImpl
	extends BaseDecayScoreFunctionImpl implements GaussianDecayScoreFunction {

	public GaussianDecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset) {

		super(field, origin, scale, offset);
	}

	public GaussianDecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		super(field, origin, scale, offset, decay);
	}

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

}