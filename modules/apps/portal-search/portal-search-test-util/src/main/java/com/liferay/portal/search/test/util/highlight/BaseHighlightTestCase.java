/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.highlight;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class BaseHighlightTestCase extends BaseIndexingTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(createLocalization());
	}

	protected void assertSearch(
			String fieldName, Query query, Consumer<QueryConfig> consumer,
			List<String> expectedValues)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> doAssertSearch(fieldName, query, consumer, expectedValues));
	}

	protected Localization createLocalization() {
		Localization localization = Mockito.mock(Localization.class);

		Mockito.doReturn(
			StringPool.BLANK
		).when(
			localization
		).getLocalizedName(
			Mockito.anyString(), Mockito.anyString()
		);

		return localization;
	}

	protected Void doAssertSearch(
			String fieldName, Query query, Consumer<QueryConfig> consumer,
			List<String> expectedValues)
		throws Exception {

		SearchContext searchContext = createSearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addHighlightFieldNames(fieldName);
		queryConfig.addSelectedFieldNames(fieldName);
		queryConfig.setHighlightEnabled(true);
		queryConfig.setHighlightRequireFieldMatch(true);

		consumer.accept(queryConfig);

		query.setQueryConfig(queryConfig);

		Hits hits = search(searchContext, query);

		String snippetFieldName = "snippet_".concat(fieldName);

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			snippetFieldName, expectedValues);

		return null;
	}

	protected List<String> toFullHighlights(String... strings) {
		return TransformUtil.transformToList(
			strings,
			string -> StringUtil.replace(
				string, new String[] {"[H]", "[/H]"},
				new String[] {
					HighlightUtil.HIGHLIGHT_TAG_OPEN,
					HighlightUtil.HIGHLIGHT_TAG_CLOSE
				}));
	}

}