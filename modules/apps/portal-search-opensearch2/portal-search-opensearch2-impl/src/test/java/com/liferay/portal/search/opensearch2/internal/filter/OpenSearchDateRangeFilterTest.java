/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.filter;

import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.filter.BaseDateRangeFilterTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.opensearch._types.OpenSearchException;

/**
 * @author Eric Yan
 */
public class OpenSearchDateRangeFilterTest extends BaseDateRangeFilterTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testDateFormat() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFormat("MMddyyyyHHmmss");
		dateRangeFilterBuilder.setFrom("11212000000000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertHits("20001122000000");
	}

	@Test
	public void testDateFormatWithMultiplePatterns() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFormat("MMddyyyyHHmmss || yyyy");
		dateRangeFilterBuilder.setFrom("2000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertHits("20001122000000");
	}

	@Test
	public void testMalformed() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("11212000000000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertOpenSearchException();
	}

	@Test
	public void testMalformedMultiple() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("2000");
		dateRangeFilterBuilder.setTo("11232000000000");

		assertOpenSearchException();
	}

	@Test
	public void testTimeZone() throws Exception {
		addDocument(getDate(2000, 11, 22));

		dateRangeFilterBuilder.setFrom("20001122010000");
		dateRangeFilterBuilder.setTimeZoneId("Etc/GMT-2");
		dateRangeFilterBuilder.setTo("20001122030000");

		assertHits("20001122000000");
	}

	protected void assertOpenSearchException() {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(dateRangeFilterBuilder.build());

				try {
					indexingTestHelper.search();

					Assert.fail();
				}
				catch (OpenSearchException openSearchException) {
					Assert.assertEquals(
						"Request failed: [search_phase_execution_exception] " +
							"all shards failed",
						openSearchException.getMessage());
				}
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

}