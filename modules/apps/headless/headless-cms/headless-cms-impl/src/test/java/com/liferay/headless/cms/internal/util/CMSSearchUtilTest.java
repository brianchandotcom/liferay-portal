/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mikel Lorza
 */
public class CMSSearchUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-105769")
	public void testAddBucketKeys() {
		List<String> keys = Arrays.asList(
			"1", "10", "11", "12", "2", "20", "21", "3", "30");

		List<IncludeExcludeClause> includeExcludeClauses = new ArrayList<>();

		Set<String> bucketKeys = new HashSet<>();

		CMSSearchUtil.addBucketKeys(
			includeExcludeClause -> {
				includeExcludeClauses.add(includeExcludeClause);

				return _getTermsAggregationResult(
					includeExcludeClause, keys, 3);
			},
			bucketKeys, StringPool.BLANK);

		Assert.assertEquals(new TreeSet<>(keys), new TreeSet<>(bucketKeys));

		Assert.assertEquals(
			includeExcludeClauses.toString(), 8, includeExcludeClauses.size());
	}

	@Test
	@TestInfo("LPD-105769")
	public void testGetEntryClassPKs() {
		List<String> entryClassPKs = new ArrayList<>();

		for (int i = 0; i < 200; i++) {
			entryClassPKs.add(String.valueOf(RandomTestUtil.randomLong()));
		}

		List<IncludeExcludeClause> includeExcludeClauses = new ArrayList<>();

		Aggregations aggregations = Mockito.mock(Aggregations.class);

		Mockito.when(
			aggregations.terms(Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			invocation -> {
				TermsAggregation termsAggregation = Mockito.mock(
					TermsAggregation.class);

				Mockito.doAnswer(
					setIncludeExcludeClauseInvocation -> {
						includeExcludeClauses.add(
							setIncludeExcludeClauseInvocation.getArgument(0));

						return null;
					}
				).when(
					termsAggregation
				).setIncludeExcludeClause(
					Mockito.any()
				);

				return termsAggregation;
			}
		);

		Searcher searcher = Mockito.mock(Searcher.class);

		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		Mockito.when(
			searchResponse.getCount()
		).thenReturn(
			1000000L
		);

		Mockito.when(
			searcher.search(Mockito.any())
		).thenReturn(
			searchResponse
		).thenAnswer(
			invocation -> {
				TermsAggregationResult termsAggregationResult =
					_getTermsAggregationResult(
						includeExcludeClauses.get(
							includeExcludeClauses.size() - 1),
						entryClassPKs, entryClassPKs.size());

				SearchResponse aggregationSearchResponse = Mockito.mock(
					SearchResponse.class);

				Mockito.when(
					aggregationSearchResponse.getAggregationResult(
						Field.ENTRY_CLASS_PK)
				).thenReturn(
					termsAggregationResult
				);

				return aggregationSearchResponse;
			}
		);

		Assert.assertEquals(
			new TreeSet<>(entryClassPKs),
			new TreeSet<>(
				CMSSearchUtil.getEntryClassPKs(
					aggregations, searcher,
					() -> Mockito.mock(
						SearchRequestBuilder.class, Mockito.RETURNS_SELF))));
		Assert.assertEquals(
			includeExcludeClauses.toString(), 25, includeExcludeClauses.size());
	}

	private TermsAggregationResult _getTermsAggregationResult(
		IncludeExcludeClause includeExcludeClause, List<String> keys,
		int size) {

		List<Bucket> buckets = new ArrayList<>();

		for (String key : keys) {
			if (!key.matches(includeExcludeClause.getIncludeRegex()) ||
				((includeExcludeClause.getExcludeRegex() != null) &&
				 key.matches(includeExcludeClause.getExcludeRegex()))) {

				continue;
			}

			buckets.add(new Bucket(key, RandomTestUtil.randomLong()));
		}

		TermsAggregationResult termsAggregationResult = Mockito.mock(
			TermsAggregationResult.class);

		Mockito.when(
			termsAggregationResult.getBuckets()
		).thenReturn(
			buckets.subList(0, Math.min(buckets.size(), size))
		);

		Mockito.when(
			termsAggregationResult.getOtherDocCounts()
		).thenReturn(
			(long)Math.max(buckets.size() - size, 0)
		);

		return termsAggregationResult;
	}

}