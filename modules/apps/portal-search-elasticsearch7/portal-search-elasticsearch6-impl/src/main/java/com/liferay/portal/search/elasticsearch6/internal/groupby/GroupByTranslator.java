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

package com.liferay.portal.search.elasticsearch6.internal.groupby;

import com.liferay.portal.search.groupby.GroupByRequest;

import java.util.Locale;

import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * @author Michael C. Han
 */
public interface GroupByTranslator {

	public static final String BUCKET_SORT_AGGREGATION_NAME = "_bucketSort";

	public static final String GROUP_BY_AGGREGATION_PREFIX = "GroupBy_";

	public static final String TOP_HITS_AGGREGATION_NAME = "_topHits";

	public void translate(
		SearchRequestBuilder searchRequestBuilder,
		GroupByRequest groupByRequest, Locale locale,
		String[] selectedFieldNames, String[] highlightFieldNames,
		boolean highlightEnabled, boolean highlightRequireFieldMatch,
		int highlightFragmentSize, int highlightSnippetSize);

}