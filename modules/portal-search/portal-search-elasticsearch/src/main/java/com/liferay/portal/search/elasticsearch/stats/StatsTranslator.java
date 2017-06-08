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

package com.liferay.portal.search.elasticsearch.stats;

import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.Aggregation;

/**
 * @author Michael C. Han
 */
public interface StatsTranslator {

	public StatsResults translate(
		Map<String, Aggregation> aggregationMap, Stats stats);

	public void translate(
		SearchRequestBuilder searchRequestBuilder, Stats stats);

}