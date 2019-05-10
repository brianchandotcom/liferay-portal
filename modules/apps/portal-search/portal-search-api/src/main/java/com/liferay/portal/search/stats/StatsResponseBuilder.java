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
public interface StatsResponseBuilder {

	public StatsResponse build();

	public StatsResponseBuilder cardinality(long cardinality);

	public StatsResponseBuilder count(long count);

	public StatsResponseBuilder field(String field);

	public StatsResponseBuilder max(double max);

	public StatsResponseBuilder mean(double mean);

	public StatsResponseBuilder min(double min);

	public StatsResponseBuilder missing(long missing);

	public StatsResponseBuilder standardDeviation(double standardDeviation);

	public StatsResponseBuilder sum(double sum);

	public StatsResponseBuilder sumOfSquares(double sumOfSquares);

}