/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class PreFilterQueryTest extends BaseIndexingTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

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

				booleanFilter.add(
					new TermsFilter(Field.FOLDER_ID) {
						{
							addValues(values);
						}
					},
					BooleanClauseOccur.MUST);

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
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	protected void index(String value) throws Exception {
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.FOLDER_ID, value));
	}

}