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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.Document;
import com.liferay.search.experiences.rest.dto.v1_0.DocumentField;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SearchResponseResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToSearchResponse() throws Exception {
		SearchContext searchContext = new SearchContext();

		SearchResponseBuilder searchResponseBuilder =
			_searchResponseBuilderFactory.builder(searchContext);

		SearchHitsBuilder searchHitsBuilder =
			_searchHitsBuilderFactory.getSearchHitsBuilder();

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();

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
					_documentBuilderFactory.builder(
					).setString(
						"field1st", "Lone Value"
					).setIntegers(
						"field2nd", 4, 8, 15, 16, 23, 42
					).build()
				).score(
					score
				).build()
			).maxScore(
				score
			).build()
		).request(
			_searchRequestBuilderFactory.builder(
				searchContext
			).build()
		).requestString(
			requestString
		).responseString(
			responseString
		);

		SearchResponseResourceImpl searchResponseResourceImpl =
			new SearchResponseResourceImpl();

		SearchResponse searchResponse =
			searchResponseResourceImpl.toSearchResponse(
				searchResponseBuilder.build());

		Assert.assertEquals(requestString, searchResponse.getRequestString());
		Assert.assertEquals(responseString, searchResponse.getResponseString());

		Document document = searchResponse.getDocuments()[0];

		Map<String, DocumentField> map = document.getDocumentFields();

		Assert.assertEquals(
			"{field1st={\"values\": [\"Lone Value\"]}, field2nd={\"values\": " +
				"[\"4\", \"8\", \"15\", \"16\", \"23\", \"42\"]}}",
			map.toString());
	}

	private final DocumentBuilderFactory _documentBuilderFactory =
		new DocumentBuilderFactoryImpl();
	private final SearchHitBuilderFactory _searchHitBuilderFactory =
		new SearchHitBuilderFactoryImpl();
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory =
		new SearchHitsBuilderFactoryImpl();
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory =
		new SearchRequestBuilderFactoryImpl();
	private final SearchResponseBuilderFactory _searchResponseBuilderFactory =
		new SearchResponseBuilderFactoryImpl();

}