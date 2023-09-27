/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.function.score.RandomScoreFunction;
import com.liferay.portal.search.query.function.score.ScoreFunctionTranslator;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class RandomScoreFunctionImpl
	extends BaseScoreFunctionImpl implements RandomScoreFunction {

	@Override
	public <T> T accept(ScoreFunctionTranslator<T> scoreFunctionTranslator) {
		return scoreFunctionTranslator.translate(this);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Integer getSeed() {
		return _seed;
	}

	@Override
	public void setField(String field) {
		_field = field;
	}

	@Override
	public void setSeed(Integer seed) {
		_seed = seed;
	}

	private String _field;
	private Integer _seed;

}