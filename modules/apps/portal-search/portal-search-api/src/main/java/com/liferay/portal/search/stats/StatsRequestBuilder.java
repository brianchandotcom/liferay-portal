/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface StatsRequestBuilder {

	public StatsRequest build();

	public StatsRequestBuilder cardinality(boolean cardinality);

	public StatsRequestBuilder count(boolean count);

	public StatsRequestBuilder field(String field);

	public StatsRequestBuilder max(boolean max);

	public StatsRequestBuilder mean(boolean mean);

	public StatsRequestBuilder min(boolean min);

	public StatsRequestBuilder missing(boolean missing);

	public StatsRequestBuilder standardDeviation(boolean standardDeviation);

	public StatsRequestBuilder sum(boolean sum);

	public StatsRequestBuilder sumOfSquares(boolean sumOfSquares);

}