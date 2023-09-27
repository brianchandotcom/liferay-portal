/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.query.BaseMoreLikeThisQueryTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.ResponseException;

import org.hamcrest.CoreMatchers;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Wade Cao
 */
public class MoreLikeThisQueryTest extends BaseMoreLikeThisQueryTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore
	@Override
	@Test
	public void testMoreLikeThisWithoutFields() throws Exception {
	}

	@Test
	public void testMoreLikeThisWithoutFieldsElasticsearch7() throws Throwable {
		SearchSearchRequest searchSearchRequest = createSearchSearchRequest();

		searchSearchRequest.setQuery(
			queries.moreLikeThis(
				Collections.emptyList(), RandomTestUtil.randomString()));

		SearchEngineAdapter searchEngineAdapter = getSearchEngineAdapter();

		expectedException.expect(ResponseException.class);
		expectedException.expectMessage(
			CoreMatchers.containsString(
				"[more_like_this] query cannot infer the field"));

		try {
			searchEngineAdapter.execute(searchSearchRequest);
		}
		catch (ElasticsearchStatusException elasticsearchStatusException) {
			throw elasticsearchStatusException.getSuppressed()[0];
		}
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}