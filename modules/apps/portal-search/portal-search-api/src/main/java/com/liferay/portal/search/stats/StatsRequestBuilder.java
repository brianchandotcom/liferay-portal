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