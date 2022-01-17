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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.search.experiences.blueprint.exception.InvalidElementInstanceException;
import com.liferay.search.experiences.blueprint.exception.InvalidParameterException;
import com.liferay.search.experiences.blueprint.exception.InvalidQueryEntryException;
import com.liferay.search.experiences.blueprint.exception.UnresolvedTemplateVariableException;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.rest.dto.v1_0.Hit;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SearchHits;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class SearchResponseResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSearchErrors() throws Exception {
		_initSearcher();

		RuntimeException runtimeException = new RuntimeException();

		runtimeException.addSuppressed(InvalidQueryEntryException.at(0));
		runtimeException.addSuppressed(InvalidQueryEntryException.at(2));

		Mockito.doThrow(
			runtimeException
		).when(
			_sxpBlueprintSearchRequestEnhancer
		).enhance(
			Mockito.any(), Mockito.anyString()
		);

		SearchResponseResourceImpl searchResponseResourceImpl =
			_createSearchResponseResourceImpl();

		SearchResponse searchResponse = searchResponseResourceImpl.search(
			Mockito.mock(Pagination.class), null, new SXPBlueprint());

		Map[] errorMaps = searchResponse.getErrors();

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass", InvalidQueryEntryException.class.getName()
			).put(
				"localizedMessage", "Error"
			).put(
				"msg", "Invalid query entry at: 0"
			).put(
				"severity", "ERROR"
			).build(),
			errorMaps[0]);

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass", InvalidQueryEntryException.class.getName()
			).put(
				"localizedMessage", "Error"
			).put(
				"msg", "Invalid query entry at: 2"
			).put(
				"severity", "ERROR"
			).build(),
			errorMaps[1]);
	}

	@Test
	public void testSearchWarnings() throws Exception {
		_initSearcher();

		RuntimeException runtimeException = new RuntimeException();

		Throwable throwable1 = InvalidElementInstanceException.at(1);

		Throwable throwable2 = InvalidQueryEntryException.at(2);

		throwable2.addSuppressed(
			UnresolvedTemplateVariableException.with(
				RandomTestUtil.randomString()));

		throwable1.addSuppressed(throwable2);

		runtimeException.addSuppressed(throwable1);

		Throwable throwable3 = InvalidElementInstanceException.at(3);

		throwable3.addSuppressed(
			InvalidParameterException.with(RandomTestUtil.randomString()));

		runtimeException.addSuppressed(throwable3);

		Mockito.doThrow(
			runtimeException
		).when(
			_sxpBlueprintSearchRequestEnhancer
		).enhance(
			Mockito.any(), Mockito.anyString()
		);

		SearchResponseResourceImpl searchResponseResourceImpl =
			_createSearchResponseResourceImpl();

		SearchResponse searchResponse = searchResponseResourceImpl.search(
			Mockito.mock(Pagination.class), null, new SXPBlueprint());

		Map[] errorMaps = searchResponse.getErrors();

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass",
				InvalidElementInstanceException.class.getName()
			).put(
				"localizedMessage", "Element skipped"
			).put(
				"msg", "Invalid element instance at: 1"
			).put(
				"severity", "WARN"
			).put(
				"sxpElementId", "querySXPElement-1"
			).build(),
			errorMaps[0]);

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass", InvalidQueryEntryException.class.getName()
			).put(
				"localizedMessage", "Error"
			).put(
				"msg", "Invalid query entry at: 2"
			).put(
				"severity", "ERROR"
			).put(
				"sxpElementId", "querySXPElement-1"
			).build(),
			errorMaps[1]);

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass",
				UnresolvedTemplateVariableException.class.getName()
			).put(
				"localizedMessage", "Error"
			).put(
				"severity", "ERROR"
			).put(
				"sxpElementId", "querySXPElement-1"
			).build(),
			errorMaps[2]);

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass",
				InvalidElementInstanceException.class.getName()
			).put(
				"localizedMessage", "Element skipped"
			).put(
				"msg", "Invalid element instance at: 3"
			).put(
				"severity", "WARN"
			).put(
				"sxpElementId", "querySXPElement-3"
			).build(),
			errorMaps[3]);

		_assertEquals(
			HashMapBuilder.put(
				"exceptionClass", InvalidParameterException.class.getName()
			).put(
				"localizedMessage", "Error"
			).put(
				"severity", "ERROR"
			).put(
				"sxpElementId", "querySXPElement-3"
			).build(),
			errorMaps[4]);
	}

	@Test
	public void testToSearchResponse() throws Exception {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		SearchResponseBuilder searchResponseBuilder =
			searchRequestBuilder.withSearchContextGet(
				searchContext -> _searchResponseBuilderFactory.builder(
					searchContext));

		String requestString = JSONUtil.put(
			"query", JSONUtil.put("term", JSONUtil.put("title", "Liferay"))
		).put(
			"took", 5
		).toString();

		String responseString = JSONUtil.put(
			"hits", JSONUtil.put("max_score", RandomTestUtil.randomDouble())
		).put(
			"took", 5
		).toString();

		SearchHitBuilder searchHitBuilder =
			_searchHitBuilderFactory.getSearchHitBuilder();
		SearchHitsBuilder searchHitsBuilder =
			_searchHitsBuilderFactory.getSearchHitsBuilder();

		com.liferay.portal.search.searcher.SearchResponse portalSearchResponse =
			searchResponseBuilder.searchHits(
				searchHitsBuilder.addSearchHit(
					searchHitBuilder.document(
						_documentBuilderFactory.builder(
						).setString(
							"field1st", "Lone Value"
						).setIntegers(
							"field2nd", 4, 8, 15, 16, 23, 42
						).build()
					).explanation(
						RandomTestUtil.randomString()
					).score(
						(float)RandomTestUtil.randomDouble()
					).version(
						RandomTestUtil.randomLong()
					).build()
				).maxScore(
					Float.NaN
				).totalHits(
					RandomTestUtil.randomLong()
				).build()
			).request(
				searchRequestBuilder.build()
			).requestString(
				requestString
			).responseString(
				responseString
			).build();

		SearchResponseResourceImpl searchResponseResourceImpl =
			_createSearchResponseResourceImpl();

		SearchResponse searchResponse =
			searchResponseResourceImpl.toSearchResponse(portalSearchResponse);

		com.liferay.portal.search.hits.SearchHits portalSearchHits =
			portalSearchResponse.getSearchHits();
		SearchHits searchHits = searchResponse.getSearchHits();

		Assert.assertEquals(null, searchHits.getMaxScore());
		Assert.assertEquals(
			Long.valueOf(portalSearchHits.getTotalHits()),
			searchHits.getTotalHits());

		List<SearchHit> list = portalSearchHits.getSearchHits();

		SearchHit portalSearchHit = list.get(0);

		Hit hit = searchHits.getHits()[0];

		Assert.assertEquals(
			"{field1st={\"values\": [\"Lone Value\"]}, field2nd={\"values\": " +
				"[\"4\", \"8\", \"15\", \"16\", \"23\", \"42\"]}}",
			String.valueOf(hit.getDocumentFields()));
		Assert.assertEquals(
			portalSearchHit.getExplanation(), hit.getExplanation());
		Assert.assertEquals(portalSearchHit.getId(), hit.getId());
		Assert.assertEquals(
			Float.valueOf(portalSearchHit.getScore()), hit.getScore());
		Assert.assertEquals(
			Long.valueOf(portalSearchHit.getVersion()), hit.getVersion());

		Assert.assertTrue(searchResponse.getRequest() instanceof Map);
		Assert.assertEquals(requestString, searchResponse.getRequestString());
		Assert.assertTrue(searchResponse.getResponse() instanceof Map);
		Assert.assertEquals(responseString, searchResponse.getResponseString());
	}

	private void _assertEquals(
		Map<String, String> expectedMap, Map<String, String> actualMap) {

		Set<Map.Entry<String, String>> entries = actualMap.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		AssertUtils.assertEquals(
			() -> String.valueOf(actualMap), expectedMap,
			stream.filter(
				entry -> expectedMap.containsKey(entry.getKey())
			).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
			));
	}

	private SearchResponseResourceImpl _createSearchResponseResourceImpl() {
		SearchResponseResourceImpl searchResponseResourceImpl =
			new SearchResponseResourceImpl();

		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "contextCompany",
			Mockito.mock(Company.class));
		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "contextHttpServletRequest",
			Mockito.mock(HttpServletRequest.class));
		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "contextUser",
			Mockito.mock(User.class));
		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "_searcher", _searcher);
		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "_searchRequestBuilderFactory",
			_searchRequestBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			searchResponseResourceImpl, "_sxpBlueprintSearchRequestEnhancer",
			_sxpBlueprintSearchRequestEnhancer);

		return searchResponseResourceImpl;
	}

	private void _initSearcher() {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		SearchResponseBuilder searchResponseBuilder =
			searchRequestBuilder.withSearchContextGet(
				searchContext -> _searchResponseBuilderFactory.builder(
					searchContext));

		searchResponseBuilder.request(searchRequestBuilder.build());

		Mockito.doReturn(
			searchResponseBuilder.build()
		).when(
			_searcher
		).search(
			Mockito.any()
		);
	}

	private final DocumentBuilderFactory _documentBuilderFactory =
		new DocumentBuilderFactoryImpl();
	private final Searcher _searcher = Mockito.mock(Searcher.class);
	private final SearchHitBuilderFactory _searchHitBuilderFactory =
		new SearchHitBuilderFactoryImpl();
	private final SearchHitsBuilderFactory _searchHitsBuilderFactory =
		new SearchHitsBuilderFactoryImpl();
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory =
		new SearchRequestBuilderFactoryImpl();
	private final SearchResponseBuilderFactory _searchResponseBuilderFactory =
		new SearchResponseBuilderFactoryImpl();
	private final SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer = Mockito.mock(
			SXPBlueprintSearchRequestEnhancer.class);

}