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

package com.liferay.portal.search.elasticsearch6.internal.logging;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.search.elasticsearch6.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.logging.ExpectedLogTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class ElasticsearchIndexSearcherLogExceptionsOnlyTest
	extends BaseIndexingTestCase {

	@Test
	public void testExceptionOnlyLoggedWhenQueryMalformedSearch() {
		expectedLogTestRule.expectMessage(
			"Failed to execute phase [query], all shards failed");

		search(createSearchContext(), getMalformedQuery());
	}

	@Test
	public void testExceptionOnlyLoggedWhenQueryMalformedSearchCount() {
		expectedLogTestRule.expectMessage(
			"Failed to execute phase [query], all shards failed");

		searchCount(createSearchContext(), getMalformedQuery());
	}

	@Rule
	public ExpectedLogTestRule expectedLogTestRule = ExpectedLogTestRule.none();

	protected ElasticsearchFixture createElasticsearchFixture() {
		Map<String, Object> elasticsearchConfigurationProperties =
			new HashMap<>();

		elasticsearchConfigurationProperties.put("logExceptionsOnly", true);

		return new ElasticsearchFixture(
			ElasticsearchIndexWriterLogExceptionsOnlyTest.class.getSimpleName(),
			elasticsearchConfigurationProperties);
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.builder(
		).elasticsearchFixture(
			createElasticsearchFixture()
		).build();
	}

	protected Query getMalformedQuery() {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new TermQueryImpl(Field.EXPIRATION_DATE, "text"),
			BooleanClauseOccur.MUST);

		return booleanQueryImpl;
	}

}