/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.metrics;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseTopHitsAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testTopHitsAggregation() throws Exception {
		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "Jonh");
				document.addNumber(Field.PRIORITY, 1);
			});

		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "Jonh");
				document.addNumber(Field.PRIORITY, 2);
			});

		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "Jonh");
				document.addNumber(Field.PRIORITY, 3);
			});

		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "Bob");
				document.addNumber(Field.PRIORITY, 4);
			});

		addDocument(
			document -> {
				document.addKeyword(Field.USER_NAME, "Bob");
				document.addNumber(Field.PRIORITY, 5);
			});

		TermsAggregation termsAggregation = aggregations.terms(
			"terms", Field.USER_NAME);

		TopHitsAggregation topHitsAggregation = aggregations.topHits("topHits");

		topHitsAggregation.addSortFields(
			sorts.field(Field.PRIORITY, SortOrder.DESC));
		topHitsAggregation.setSize(1);

		termsAggregation.addChildAggregation(topHitsAggregation);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> searchRequestBuilder.addAggregation(
						termsAggregation));

				indexingTestHelper.search();

				TermsAggregationResult termsAggregationResult =
					indexingTestHelper.getAggregationResult(termsAggregation);

				for (Bucket bucket : termsAggregationResult.getBuckets()) {
					String userName = bucket.getKey();

					if (Objects.equals(userName, "Jonh")) {
						assertBucket(bucket, 3, 3);
					}
					else if (Objects.equals(userName, "Bob")) {
						assertBucket(bucket, 2, 5);
					}
				}
			});
	}

	protected void assertBucket(Bucket bucket, long count, Integer priority) {
		Assert.assertEquals(count, bucket.getDocCount());

		Map<String, AggregationResult> childrenAggregationResults =
			bucket.getChildrenAggregationResults();

		TopHitsAggregationResult topHitsAggregationResult =
			(TopHitsAggregationResult)childrenAggregationResults.get("topHits");

		SearchHits searchHits = topHitsAggregationResult.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		SearchHit searchHit = searchHitsList.get(0);

		Document document = searchHit.getDocument();

		if (MapUtil.isNotEmpty(document.getFields())) {
			Assert.assertEquals(priority, document.getInteger(Field.PRIORITY));
		}
		else {
			Map<String, Object> sourcesMap = searchHit.getSourcesMap();

			Assert.assertEquals(
				priority.toString(), sourcesMap.get(Field.PRIORITY));
		}
	}

}