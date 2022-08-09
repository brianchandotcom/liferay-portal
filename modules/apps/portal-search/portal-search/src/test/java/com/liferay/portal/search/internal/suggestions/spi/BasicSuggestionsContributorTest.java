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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
import com.liferay.portal.search.suggestions.SuggestionBuilder;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.portlet.MutableRenderParameters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
		_suggestionsContributorConfiguration =
			new SuggestionsContributorConfiguration();

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
			new SuggestionBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor,
			"_suggestionsContributorResultsBuilderFactory",
			new SuggestionsContributorResultsBuilderFactoryImpl());
	}

	@Test
	public void testGetSuggestionWithAssetRendererFactoryNotNull()
		throws Exception {

		_setUpAssetEntryLocalService();
		_setUpAssetRendererFactoryRegistryUtilNotNull("Title");
		_setUpLayoutLocalService();
		_setUpLiferayPortletRequest();
		_setUpSearchContext();
		_setUpSearchHits(_setUpDocument("testField"));
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(1L);
		_setUpSuggestionBuilderFactory();
		_setUpSuggestionsContributorConfiguration("testField");

		SuggestionsContributorResults suggestionsContributorResults =
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest, _setUpLiferayPortletResponse(),
				_searchContext, _suggestionsContributorConfiguration);

		List<Suggestion> suggestions =
			suggestionsContributorResults.getSuggestions();

		Suggestion suggestion = suggestions.get(0);

		Assert.assertEquals("Title", suggestion.getText());

		Assert.assertEquals(
			"testField", suggestionsContributorResults.getDisplayGroupName());

		Mockito.verify(
			_assetRendererFactory, Mockito.times(1)
		).getAssetRenderer(
			Mockito.anyLong()
		);
	}

	@Test
	public void testGetSuggestionWithAssetRendererFactoryNull()
		throws Exception {

		_setUpAssetRendererFactoryRegistryUtilNull();
		_setUpLayoutLocalService();
		_setUpLiferayPortletRequest();
		_setUpSearchContext();
		_setUpSearchHits(_setUpDocument("testField"));
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(1L);
		_setUpSuggestionBuilderFactory();
		_setUpSuggestionsContributorConfiguration("testField");

		SuggestionsContributorResults suggestionsContributorResults =
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest,
				Mockito.mock(LiferayPortletResponse.class), _searchContext,
				_suggestionsContributorConfiguration);

		Assert.assertEquals(
			"testField", suggestionsContributorResults.getDisplayGroupName());

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
	public void testSearchHitsWithZeroTotalHits() throws Exception {
		_setUpSearchContext();
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(0L);

		Assert.assertNull(
			_basicSuggestionsContributor.getSuggestionsContributorResults(
				_liferayPortletRequest,
				Mockito.mock(LiferayPortletResponse.class), _searchContext,
				Mockito.mock(SuggestionsContributorConfiguration.class)));

		Mockito.verify(
			_liferayPortletRequest, Mockito.never()
		).getAttribute(
			Mockito.anyString()
		);
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

	private void _setUpAssetRendererFactoryRegistryUtilNotNull(String title)
		throws Exception {

		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

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

		Mockito.doReturn(
			RandomTestUtil.randomString()
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

		ReflectionTestUtil.setFieldValue(
			AssetRendererFactoryRegistryUtil.class,
			"_classNameAssetRenderFactoriesServiceTrackerMap",
			_serviceTrackerMap);
	}

	private void _setUpAssetRendererFactoryRegistryUtilNull() {
		Mockito.doReturn(
			null
		).when(
			_serviceTrackerMap
		).getService(
			Mockito.anyString()
		);

		ReflectionTestUtil.setFieldValue(
			AssetRendererFactoryRegistryUtil.class,
			"_classNameAssetRenderFactoriesServiceTrackerMap",
			_serviceTrackerMap);
	}

	private Document _setUpDocument(String... field) {
		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			Arrays.asList(field)
		).when(
			document
		).getStrings(
			Mockito.anyString()
		);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			document
		).getString(
			Field.ENTRY_CLASS_NAME
		);

		return document;
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

	private LiferayPortletResponse _setUpLiferayPortletResponse() {
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

		return liferayPortletResponse;
	}

	private void _setUpSearchContext() {
		Mockito.doReturn(
			"test"
		).when(
			_searchContext
		).getKeywords();

		Mockito.doReturn(
			LocaleUtil.US
		).when(
			_searchContext
		).getLocale();
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
			Mockito.any()
		);

		Mockito.doReturn(
			_searchHits
		).when(
			searchResponse
		).getSearchHits();
	}

	private void _setUpSearchHits(Document document) {
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

	private void _setUpSuggestionsContributorConfiguration(String textField) {
		_suggestionsContributorConfiguration.setDisplayGroupName(textField);

		_suggestionsContributorConfiguration.setSize(1);
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
	private SearchContext _searchContext;

	@Mock
	private Searcher _searcher;

	@Mock
	private SearchHits _searchHits;

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_serviceTrackerMap;

	private SuggestionsContributorConfiguration
		_suggestionsContributorConfiguration;

}