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