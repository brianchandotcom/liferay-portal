/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.logging;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.opensearch.client.opensearch._types.OpenSearchException;

/**
 * @author Bryan Engler
 */
public class OpenSearchIndexSearcherExceptionsTest
	extends BaseIndexingTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testExceptionThrownWhenQueryMalformedSearch() {
		expectedException.expect(OpenSearchException.class);
		expectedException.expectMessage(
			"[search_phase_execution_exception] all shards failed");

		search(createSearchContext(), getMalformedQuery());
	}

	@Test
	public void testExceptionThrownWhenQueryMalformedSearchCount() {
		expectedException.expect(OpenSearchException.class);
		expectedException.expectMessage(
			"[search_phase_execution_exception] all shards failed");

		searchCount(createSearchContext(), getMalformedQuery());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	protected Query getMalformedQuery() {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new TermQueryImpl(Field.EXPIRATION_DATE, "text"),
			BooleanClauseOccur.MUST);

		return booleanQueryImpl;
	}

}