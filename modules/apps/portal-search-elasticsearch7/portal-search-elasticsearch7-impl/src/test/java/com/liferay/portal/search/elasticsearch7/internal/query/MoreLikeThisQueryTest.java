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

import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.query.BaseMoreLikeThisQueryTestCase;

import java.util.Collections;

import org.elasticsearch.action.search.SearchPhaseExecutionException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class MoreLikeThisQueryTest extends BaseMoreLikeThisQueryTestCase {

	@Override
	@Test
	public void testMoreLikeThisWithoutFields() throws Exception {
		addDocuments("java eclipse", "eclipse liferay", "java liferay eclipse");

		MoreLikeThisQuery moreLikeThisQuery = queries.moreLikeThis(
			Collections.emptyList(), "java");

		try {
			assertSearch(moreLikeThisQuery, Collections.emptyList());

			Assert.fail();
		}
		catch (SearchPhaseExecutionException spee) {
			Throwable throwable = spee.getRootCause();

			String message = throwable.getMessage();

			Assert.assertTrue(
				message,
				message.contains(
					"[more_like_this] query cannot infer the field"));
		}
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

}