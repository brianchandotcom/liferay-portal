/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query.function.score;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface ScoreFunctionTranslator<T> {

	public T translate(
		ExponentialDecayScoreFunction exponentialDecayScoreFunction);

	public T translate(
		FieldValueFactorScoreFunction fieldValueFactorScoreFunction);

	public T translate(GaussianDecayScoreFunction gaussianDecayScoreFunction);

	public T translate(LinearDecayScoreFunction linearDecayScoreFunction);

	public T translate(RandomScoreFunction randomScoreFunction);

	public T translate(ScriptScoreFunction scriptScoreFunction);

	public T translate(WeightScoreFunction weightScoreFunction);

}