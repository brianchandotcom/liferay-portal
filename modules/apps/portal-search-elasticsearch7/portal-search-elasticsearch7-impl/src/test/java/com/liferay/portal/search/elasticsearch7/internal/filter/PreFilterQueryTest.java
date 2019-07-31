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

package com.liferay.portal.search.elasticsearch6.internal.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.elasticsearch6.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Wade Cao
 */
public class PreFilterQueryTest extends BaseIndexingTestCase {

	@Test
	public void testPreFilterQuery() throws Exception {
		index("One");
		index("Two");
		index("Three");

		assertTermsFilter(new String[] {"Two", "Three"});
	}

	@SuppressWarnings("unchecked")
	protected void assertTermsFilter(String[] values) throws Exception {
		assertSearch(
			indexingTestHelper -> {
				BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

				booleanQueryImpl.add(
					new MatchAllQuery(), BooleanClauseOccur.MUST);

				indexingTestHelper.setQuery(booleanQueryImpl);

				BooleanFilter booleanFilter = new BooleanFilter();

				TermsFilter filter = new TermsFilter(Field.FOLDER_ID) {
					{
						addValues(values);
					}
				};

				booleanFilter.add(filter, BooleanClauseOccur.MUST);

				booleanQueryImpl.setPreBooleanFilter(booleanFilter);

				@SuppressWarnings("rawtypes")
				BooleanClause booleanClause = BooleanClauseFactoryUtil.create(
					booleanQueryImpl, BooleanClauseOccur.MUST.getName());

				indexingTestHelper.define(
					searchContext -> searchContext.setBooleanClauses(
						new BooleanClause[] {booleanClause}));

				indexingTestHelper.search();

				indexingTestHelper.assertValues(
					Field.FOLDER_ID, Arrays.asList(values));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	protected void index(String value) throws Exception {
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.FOLDER_ID, value));
	}

}