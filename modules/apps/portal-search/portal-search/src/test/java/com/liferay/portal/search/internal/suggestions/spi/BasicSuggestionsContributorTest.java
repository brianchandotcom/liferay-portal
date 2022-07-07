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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionBuilder;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilder;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Consumer;

import javax.portlet.MutableRenderParameters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
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

		_basicSuggestionsContributor = new BasicSuggestionsContributor();

		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_assetEntryLocalService",
			_assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_layoutLocalService",
			_layoutLocalService);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_searchRequestBuilderFactory",
			_searchRequestBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_searcher", _searcher);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_suggestionBuilderFactory",
			_suggestionBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor,
			"_suggestionsContributorResultsBuilderFactory",
			_suggestionsContributorResultsBuilderFactory);
	}

	@Test
	public void testGetSuggestionWithAssetRendererFactoryNotNull()
		throws Exception {

		_setUpAssetRendererFactoryRegistryUtil();
		_setUpLayoutLocalService();
		_setUpLiferayPortletRequest();
		_setUpSearchHits();
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(1L);
		_setUpSuggestionBuilderFactory();

		Mockito.doReturn(
			Mockito.mock(AssetEntry.class)
		).when(
			_assetEntryLocalService
		).getEntry(
			Mockito.anyString(), Mockito.anyLong()
		);

		LiferayPortletResponse liferayPortletResponse = Mockito.mock(
			LiferayPortletResponse.class);

		LiferayPortletURL liferayPortletURL = Mockito.mock(
			LiferayPortletURL.class);

		Mockito.doReturn(
			liferayPortletURL
		).when(
			liferayPortletResponse
		).createLiferayPortletURL(
			Mockito.anyLong(), Mockito.anyString(), Mockito.anyString()
		);

		Mockito.doReturn(
			Mockito.mock(MutableRenderParameters.class)
		).when(
			liferayPortletURL
		).getRenderParameters();

		Mockito.doReturn(
			null
		).when(
			liferayPortletURL
		).toString();

		SuggestionsContributorResults suggestionsContributorResults =
			Mockito.mock(SuggestionsContributorResults.class);

		_setUpSuggestionsContributorResultsBuilderFactory(
			suggestionsContributorResults);

		Assert.assertEquals(
			suggestionsContributorResults,
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest, liferayPortletResponse,
				Mockito.mock(SearchContext.class),
				Mockito.mock(SuggestionsContributorConfiguration.class)));

		Mockito.verify(
			_assetRendererFactory, Mockito.times(1)
		).getAssetRenderer(
			Mockito.anyLong()
		);
	}

	@Test
	public void testGetSuggestionWithAssetRendererFactoryNull()
		throws Exception {

		_setUpLayoutLocalService();
		_setUpLiferayPortletRequest();
		_setUpSearchHits();
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(1L);
		_setUpSuggestionBuilderFactory();

		SuggestionsContributorResults suggestionsContributorResults =
			Mockito.mock(SuggestionsContributorResults.class);

		_setUpSuggestionsContributorResultsBuilderFactory(
			suggestionsContributorResults);

		Assert.assertEquals(
			suggestionsContributorResults,
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest,
				Mockito.mock(LiferayPortletResponse.class),
				Mockito.mock(SearchContext.class),
				Mockito.mock(SuggestionsContributorConfiguration.class)));

		Mockito.verify(
			_assetRendererFactory, Mockito.never()
		).getAssetRenderer(
			Mockito.anyLong()
		);
	}

	@Test
	public void testSearchHitsWithZeroTotalHits() throws Exception {
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(0L);

		Assert.assertNull(
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest,
				Mockito.mock(LiferayPortletResponse.class),
				Mockito.mock(SearchContext.class),
				Mockito.mock(SuggestionsContributorConfiguration.class)));

		Mockito.verify(
			_liferayPortletRequest, Mockito.never()
		).getAttribute(
			Mockito.anyString()
		);
	}

	private void _setUpAssetRendererFactoryRegistryUtil() throws Exception {
		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			assetRenderer
		).when(
			_assetRendererFactory
		).getAssetRenderer(
			Matchers.anyLong()
		);

		Mockito.doReturn(
			_assetRendererFactory
		).when(
			_serviceTrackerMap
		).getService(
			Mockito.anyString()
		);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			assetRenderer
		).getSearchSummary(
			Matchers.anyObject()
		);

		ReflectionTestUtil.setFieldValue(
			AssetRendererFactoryRegistryUtil.class,
			"_classNameAssetRenderFactoriesServiceTrackerMap",
			_serviceTrackerMap);
	}

	private void _setUpLayoutLocalService() {
		Mockito.doReturn(
			Mockito.mock(Layout.class)
		).when(
			_layoutLocalService
		).fetchLayoutByFriendlyURL(
			Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyString()
		);
	}

	private void _setUpLiferayPortletRequest() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			themeDisplay
		).when(
			_liferayPortletRequest
		).getAttribute(
			Mockito.anyString()
		);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			themeDisplay
		).getScopeGroupId();
	}

	private void _setUpSearcher(long totalHits) {
		Mockito.doReturn(
			totalHits
		).when(
			_searchHits
		).getTotalHits();

		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		Mockito.doReturn(
			searchResponse
		).when(
			_searcher
		).search(
			Mockito.anyObject()
		);

		Mockito.doReturn(
			_searchHits
		).when(
			searchResponse
		).getSearchHits();
	}

	private void _setUpSearchHits() {
		Document document = Mockito.mock(Document.class);

		SearchHit searchHit = Mockito.mock(SearchHit.class);

		Mockito.doReturn(
			ListUtil.fromArray(searchHit)
		).when(
			_searchHits
		).getSearchHits();

		Mockito.doReturn(
			document
		).when(
			searchHit
		).getDocument();
	}

	private void _setUpSearchRequestBuilderFactory() {
		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			_searchRequestBuilderFactory
		).builder();

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
			Mockito.anyString()
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
			searchRequestBuilder
		).withSearchContext(
			Mockito.any(Consumer.class)
		);
	}

	private void _setUpSuggestionBuilderFactory() {
		SuggestionBuilder suggestionBuilder = Mockito.mock(
			SuggestionBuilder.class);

		Mockito.doReturn(
			suggestionBuilder
		).when(
			_suggestionBuilderFactory
		).builder();

		Mockito.doReturn(
			suggestionBuilder
		).when(
			suggestionBuilder
		).attribute(
			Mockito.anyString(), Mockito.anyMap()
		);

		Mockito.doReturn(
			Mockito.mock(Suggestion.class)
		).when(
			suggestionBuilder
		).build();

		Mockito.doReturn(
			suggestionBuilder
		).when(
			suggestionBuilder
		).score(
			Mockito.anyFloat()
		);

		Mockito.doReturn(
			suggestionBuilder
		).when(
			suggestionBuilder
		).text(
			Mockito.anyString()
		);
	}

	private void _setUpSuggestionsContributorResultsBuilderFactory(
		SuggestionsContributorResults suggestionsContributorResults) {

		SuggestionsContributorResultsBuilder
			suggestionsContributorResultsBuilder = Mockito.mock(
				SuggestionsContributorResultsBuilder.class);

		Mockito.doReturn(
			suggestionsContributorResultsBuilder
		).when(
			_suggestionsContributorResultsBuilderFactory
		).builder();

		Mockito.doReturn(
			suggestionsContributorResults
		).when(
			suggestionsContributorResultsBuilder
		).build();

		Mockito.doReturn(
			suggestionsContributorResultsBuilder
		).when(
			suggestionsContributorResultsBuilder
		).displayGroupName(
			Mockito.anyString()
		);

		Mockito.doReturn(
			suggestionsContributorResultsBuilder
		).when(
			suggestionsContributorResultsBuilder
		).suggestions(
			Mockito.anyList()
		);
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
	private Searcher _searcher;

	@Mock
	private SearchHits _searchHits;

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_serviceTrackerMap;

	@Mock
	private SuggestionBuilderFactory _suggestionBuilderFactory;

	@Mock
	private SuggestionsContributorResultsBuilderFactory
		_suggestionsContributorResultsBuilderFactory;

}