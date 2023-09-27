/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.suggest;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.solr8.internal.SolrQuerySuggester;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class SolrQuerySuggesterSafeguardsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testErrorReturnsEmptyResults() throws Exception {
		SolrQuerySuggester solrQuerySuggester = createSolrQuerySuggester();

		String[] querySuggestions = solrQuerySuggester.suggestKeywordQueries(
			createSearchContext(), 0);

		Assert.assertEquals(
			Arrays.toString(querySuggestions), 0, querySuggestions.length);
	}

	protected SearchContext createSearchContext() {
		return new SearchContext() {
			{
				setKeywords(RandomTestUtil.randomString());
			}
		};
	}

	protected SolrQuerySuggester createSolrQuerySuggester() {
		SolrQuerySuggester solrQuerySuggester = new SolrQuerySuggester();

		ReflectionTestUtil.setFieldValue(
			solrQuerySuggester, "_solrClientManager",
			Mockito.mock(SolrClientManager.class));

		return solrQuerySuggester;
	}

}