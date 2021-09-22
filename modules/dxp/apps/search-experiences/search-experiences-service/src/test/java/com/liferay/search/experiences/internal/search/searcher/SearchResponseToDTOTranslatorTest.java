/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.search.searcher;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.Document;
import com.liferay.search.experiences.rest.dto.v1_0.DocumentField;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SearchResponseToDTOTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testTranslate() throws Exception {
		SearchResponseBuilderFactory searchResponseBuilderFactory =
			new SearchResponseBuilderFactoryImpl();

		SearchResponseBuilder searchResponseBuilder =
			searchResponseBuilderFactory.builder(new SearchContext());

		SearchHitsBuilder searchHitsBuilder = _getSearchHitsBuilder();

		SearchHitBuilder searchHitBuilder = _getSearchHitBuilder();

		DocumentBuilderFactory documentBuilderFactory =
			new DocumentBuilderFactoryImpl();

		String fieldName = RandomTestUtil.randomString();
		String fieldValue = RandomTestUtil.randomString();

		com.liferay.portal.search.document.Document portalSearchDocument =
			documentBuilderFactory.builder(
			).setString(
				fieldName, fieldValue
			).build();

		float score = 1.3862942F;

		String requestString = JSONUtil.put(
			"query", JSONUtil.put("term", JSONUtil.put("title", "Liferay"))
		).put(
			"took", 5
		).toString();

		String responseString = JSONUtil.put(
			"hits", JSONUtil.put("max_score", score)
		).put(
			"took", 5
		).toString();

		searchResponseBuilder.searchHits(
			searchHitsBuilder.addSearchHit(
				searchHitBuilder.document(
					portalSearchDocument
				).score(
					score
				).build()
			).maxScore(
				score
			).build()
		).requestString(
			requestString
		).responseString(
			responseString
		);

		SearchResponseToDTOTranslator searchResponseToDTOTranslator =
			new SearchResponseToDTOTranslator(searchResponseBuilder.build());

		SearchResponse searchResponse =
			searchResponseToDTOTranslator.translate();

		Assert.assertEquals(requestString, searchResponse.getRequestString());
		Assert.assertEquals(responseString, searchResponse.getResponseString());

		Document document = searchResponse.getDocuments()[0];

		DocumentField documentField = document.getDocumentFields()[0];

		Assert.assertEquals(fieldName, documentField.getName());
		Assert.assertEquals(fieldValue, documentField.getValueString());
	}

	private SearchHitBuilder _getSearchHitBuilder() {
		SearchHitBuilderFactory searchHitBuilderFactory =
			new SearchHitBuilderFactoryImpl();

		return searchHitBuilderFactory.getSearchHitBuilder();
	}

	private SearchHitsBuilder _getSearchHitsBuilder() {
		SearchHitsBuilderFactory searchHitsBuilderFactory =
			new SearchHitsBuilderFactoryImpl();

		return searchHitsBuilderFactory.getSearchHitsBuilder();
	}

}