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

package com.liferay.portal.search.internal.suggestions.spi;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.internal.suggestions.SuggestionBuilderFactoryImpl;
import com.liferay.portal.search.internal.suggestions.SuggestionsContributorResultsBuilderFactoryImpl;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public class BasicSuggestionsContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_setUpAssetEntryLocalService();
		_setUpBasicSuggestionsContributor();
		_setUpLayoutLocalService();
		_setUpLiferayPortletRequest();
		_setUpSearchContext();
		_setUpSearchRequestBuilderFactory();
		_setUpSuggestionsContributorConfiguration();
	}

	@Test
	public void testGetSuggestionWithAssetRenderer() throws Exception {
		int totalHits = 2;

		_setUpAssetRendererFactoryRegistryUtil(
			false, "Asset Renderer Title", "Asset Renderer Summary");
		_setUpSearcher(totalHits);

		SuggestionsContributorResults suggestionsContributorResults =
			_getSuggestionsContributorResults();

		Assert.assertEquals(
			"testGetSuggestionWithAssetRenderer",
			suggestionsContributorResults.getDisplayGroupName());

		List<Suggestion> suggestions =
			suggestionsContributorResults.getSuggestions();

		for (int i = 0; i < totalHits; i++) {
			Suggestion suggestion = suggestions.get(i);

			Assert.assertEquals(
				"Asset Renderer Summary",
				suggestion.getAttribute("assetSearchSummary"));
			Assert.assertEquals(
				HashMapBuilder.<String, Object>put(
					Field.ENTRY_CLASS_NAME, "Class Name " + i
				).build(),
				suggestion.getAttribute("fields"));
			Assert.assertEquals(i, suggestion.getScore(), i);
			Assert.assertEquals("Asset Renderer Title", suggestion.getText());
		}
	}

	@Test
	public void testGetSuggestionWithAssetRendererFactoryNull()
		throws Exception {

		_setUpAssetRendererFactoryRegistryUtil(true, null, null);
		_setUpSearcher(1);

		SuggestionsContributorResults suggestionsContributorResults =
			_getSuggestionsContributorResults();

		Assert.assertEquals(
			"testGetSuggestionWithAssetRendererFactoryNull",
			suggestionsContributorResults.getDisplayGroupName());

		List<Suggestion> suggestions =
			suggestionsContributorResults.getSuggestions();

		Assert.assertEquals(suggestions.toString(), 0, suggestions.size());

		Mockito.verify(
			_assetRendererFactory, Mockito.never()
		).getAssetRenderer(
			Mockito.anyLong()
		);
	}

	@Test
	public void testGetSuggestionWithAssetRendererTitleNull() throws Exception {
		int totalHits = 2;

		_setUpAssetRendererFactoryRegistryUtil(false, null, null);
		_setUpSearcher(totalHits);

		SuggestionsContributorResults suggestionsContributorResults =
			_getSuggestionsContributorResults();

		Assert.assertEquals(
			"testGetSuggestionWithAssetRendererTitleNull",
			suggestionsContributorResults.getDisplayGroupName());

		List<Suggestion> suggestions =
			suggestionsContributorResults.getSuggestions();

		for (int i = 0; i < totalHits; i++) {
			Suggestion suggestion = suggestions.get(i);

			Assert.assertEquals(
				null, suggestion.getAttribute("assetSearchSummary"));
			Assert.assertEquals(
				HashMapBuilder.<String, Object>put(
					Field.ENTRY_CLASS_NAME, "Class Name " + i
				).build(),
				suggestion.getAttribute("fields"));
			Assert.assertEquals(i, suggestion.getScore(), i);
			Assert.assertEquals("Document Title " + i, suggestion.getText());
		}
	}

	@Test
	public void testSearchHitsWithZeroTotalHits() throws Exception {
		_setUpSearcher(0);

		Assert.assertNull(_getSuggestionsContributorResults());

		Mockito.verify(
			_liferayPortletRequest, Mockito.never()
		).getAttribute(
			Mockito.anyString()
		);
	}

	@Rule
	public TestName testName = new TestName();

	private SuggestionsContributorResults _getSuggestionsContributorResults() {
		return _basicSuggestionsContributor.getSuggestionsContributorResults(
			_liferayPortletRequest, _liferayPortletResponse, _searchContext,
			_suggestionsContributorConfiguration);
	}

	private void _setUpAssetEntryLocalService() throws Exception {
		Mockito.doReturn(
			Mockito.mock(AssetEntry.class)
		).when(
			_assetEntryLocalService
		).getEntry(
			Mockito.anyString(), Mockito.anyLong()
		);
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
			boolean assetRendererFactoryNull, String title, String summary)
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			AssetRendererFactoryRegistryUtil.class,
			"_classNameAssetRenderFactoriesServiceTrackerMap",
			_serviceTrackerMap);

		if (assetRendererFactoryNull) {
			Mockito.doReturn(
				null
			).when(
				_serviceTrackerMap
			).getService(
				Mockito.anyString()
			);

			return;
		}

		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			summary
		).when(
			assetRenderer
		).getSearchSummary(
			Mockito.any()
		);

		Mockito.doReturn(
			title
		).when(
			assetRenderer
		).getTitle(
			Mockito.any()
		);

		Mockito.doReturn(
			assetRenderer
		).when(
			_assetRendererFactory
		).getAssetRenderer(
			Mockito.anyLong()
		);

		Mockito.doReturn(
			_assetRendererFactory
		).when(
			_serviceTrackerMap
		).getService(
			Mockito.anyString()
		);
	}

	private void _setUpBasicSuggestionsContributor() {
		_basicSuggestionsContributor = new BasicSuggestionsContributor();

		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_assetEntryLocalService",
			_assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_layoutLocalService",
			_layoutLocalService);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_searcher", _searcher);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_searchRequestBuilderFactory",
			_searchRequestBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor,
			"_suggestionsContributorResultsBuilderFactory",
			new SuggestionsContributorResultsBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_suggestionBuilderFactory",
			new SuggestionBuilderFactoryImpl());
	}

	private void _setUpLayoutLocalService() {
		Mockito.doReturn(
			null
		).when(
			_layoutLocalService
		).fetchLayoutByFriendlyURL(
			Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyString()
		);
	}

	private void _setUpLiferayPortletRequest() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			themeDisplay
		).getScopeGroupId();

		Mockito.doReturn(
			themeDisplay
		).when(
			_liferayPortletRequest
		).getAttribute(
			Mockito.anyString()
		);
	}

	private void _setUpSearchContext() {
		Mockito.doReturn(
			"title"
		).when(
			_searchContext
		).getKeywords();
	}

	private void _setUpSearcher(int totalHits) {
		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		List<SearchHit> searchHitList = new ArrayList<>();

		for (int i = 0; i < totalHits; i++) {
			SearchHit searchHit = Mockito.mock(SearchHit.class);

			Document document = Mockito.mock(Document.class);

			Mockito.doReturn(
				"Class Name " + i
			).when(
				document
			).getString(
				Mockito.eq(Field.ENTRY_CLASS_NAME)
			);

			Mockito.doReturn(
				"Document Title " + i
			).when(
				document
			).getString(
				Mockito.startsWith("title")
			);

			Mockito.doReturn(
				document
			).when(
				searchHit
			).getDocument();

			Mockito.doReturn(
				Float.valueOf(i)
			).when(
				searchHit
			).getScore();

			searchHitList.add(searchHit);
		}

		Mockito.doReturn(
			searchHitList
		).when(
			searchHits
		).getSearchHits();

		Mockito.doReturn(
			Long.valueOf(totalHits)
		).when(
			searchHits
		).getTotalHits();

		Mockito.doReturn(
			searchHits
		).when(
			searchResponse
		).getSearchHits();

		Mockito.doReturn(
			searchResponse
		).when(
			_searcher
		).search(
			Mockito.any()
		);
	}

	private void _setUpSearchRequestBuilderFactory() {
		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.doReturn(
			Mockito.mock(SearchRequest.class)
		).when(
			searchRequestBuilder
		).build();

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).from(
			Mockito.anyInt()
		);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).queryString(
			Mockito.any()
		);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).size(
			Mockito.anyInt()
		);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			_searchRequestBuilderFactory
		).builder();
	}

	private void _setUpSuggestionsContributorConfiguration() {
		_suggestionsContributorConfiguration =
			new SuggestionsContributorConfiguration();

		_suggestionsContributorConfiguration.setDisplayGroupName(
			testName.getMethodName());
	}

	@Mock
	private AssetEntryLocalService _assetEntryLocalService;

	@Mock
	private AssetRendererFactory<?> _assetRendererFactory;

	private BasicSuggestionsContributor _basicSuggestionsContributor;

	@Mock
	private LayoutLocalService _layoutLocalService;

	@Mock
	private LiferayPortletRequest _liferayPortletRequest;

	@Mock
	private LiferayPortletResponse _liferayPortletResponse;

	@Mock
	private SearchContext _searchContext;

	@Mock
	private Searcher _searcher;

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_serviceTrackerMap;

	private SuggestionsContributorConfiguration
		_suggestionsContributorConfiguration;

}