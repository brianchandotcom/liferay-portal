/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.groupby;

import com.liferay.portal.search.groupby.GroupByRequest;

import java.util.Locale;

import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author Michael C. Han
 */
public interface GroupByTranslator {

	public static final String BUCKET_SORT_AGGREGATION_NAME = "_bucketSort";

	public static final String GROUP_BY_AGGREGATION_PREFIX = "GroupBy_";

	public static final String TOP_HITS_AGGREGATION_NAME = "_topHits";

	public void translate(
		SearchSourceBuilder searchSourceBuilder, GroupByRequest groupByRequest,
		Locale locale, String[] selectedFieldNames,
		String[] highlightFieldNames, boolean highlightEnabled,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize);

}