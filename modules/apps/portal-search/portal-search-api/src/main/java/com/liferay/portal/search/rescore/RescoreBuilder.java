/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rescore;

import com.liferay.portal.search.query.Query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface RescoreBuilder {

	public Rescore build();

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             RescoreBuilderFactory#builder(Query)}
	 */
	@Deprecated
	public RescoreBuilder query(Query query);

	public RescoreBuilder queryWeight(Float queryWeight);

	public RescoreBuilder rescoreQueryWeight(Float rescoreQueryWeight);

	public RescoreBuilder scoreMode(Rescore.ScoreMode scoreMode);

	public RescoreBuilder windowSize(Integer windowSize);

}