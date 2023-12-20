/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.groupby;

import com.liferay.portal.search.groupby.GroupByRequest;

import java.util.Locale;

import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public interface GroupByTranslator {

	public static final String BUCKET_SORT_AGGREGATION_NAME = "_bucketSort";

	public static final String GROUP_BY_AGGREGATION_PREFIX = "GroupBy_";

	public static final String TOP_HITS_AGGREGATION_NAME = "_topHits";

	public void translate(
		GroupByRequest groupByRequest, boolean highlightEnabled,
		String[] highlightFieldNames, int highlightFragmentSize,
		boolean highlightRequireFieldMatch, int highlightSnippetSize,
		Locale locale, SearchRequest.Builder searchRequestBuilder,
		String[] selectedFieldNames);

}