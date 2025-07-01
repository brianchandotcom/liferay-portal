/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.asset.AssetURLViewProvider;
import com.liferay.portal.search.constants.SearchContextAttributes;
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
import com.liferay.portal.search.test.util.SearchHitsTestHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.ArgumentCaptor;
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

		_setUpAssetURLViewProvider();
		_setUpBasicSuggestionsContributor();
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

		SearchHitsTestHelper searchHitsTestHelper = _setUpSearcher(totalHits);

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
			Assert.assertNotNull(suggestion.getAttribute("assetURL"));
			Assert.assertEquals(
				HashMapBuilder.<String, Object>put(
					Field.ENTRY_CLASS_NAME, _MODEL_LAYOUT + i
				).build(),
				suggestion.getAttribute("fields"));
			Assert.assertEquals(i, suggestion.getScore(), i);
			Assert.assertEquals("Asset Renderer Title", suggestion.getText());

			SearchHit searchHit = searchHitsTestHelper.getSearchHits(
			).get(
				i
			);

			Document document = searchHit.getDocument();

			long classPK = GetterUtil.getLong(
				document.getValue(Field.CLASS_PK));

			Mockito.verify(
				_assetURLViewProvider
			).getAssetURLView(
				Mockito.any(), Mockito.any(),
				Mockito.eq(_MODEL_LAYOUT_PAGE_TEMPLATE_ENTRY + i),
				Mockito.eq(classPK), Mockito.any(), Mockito.any()
			);
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
					Field.ENTRY_CLASS_NAME, _MODEL_LAYOUT + i
				).build(),
				suggestion.getAttribute("fields"));
			Assert.assertEquals(i, suggestion.getScore(), i);
			Assert.assertEquals("Document Title " + i, suggestion.getText());
		}
	}

	@Test
	public void testGetSuggestionWithoutAssetRenderer() throws Exception {
		int totalHits = 2;

		SearchHitsTestHelper searchHitsTestHelper = _setUpSearcher(totalHits);

		ClassName classNameWithoutAssetRenderer =
			searchHitsTestHelper.getClassNames(
			).get(
				0
			);

		_setUpAssetRendererFactoryRegistryUtil(
			false, "Asset Renderer Title", "Asset Renderer Summary",
			classNameWithoutAssetRenderer.getClassName());

		SuggestionsContributorResults suggestionsContributorResults =
			_getSuggestionsContributorResults();

		Assert.assertEquals(
			"testGetSuggestionWithoutAssetRenderer",
			suggestionsContributorResults.getDisplayGroupName());

		List<Suggestion> suggestions =
			suggestionsContributorResults.getSuggestions();

		for (int i = 0; i < totalHits; i++) {
			Suggestion suggestion = suggestions.get(i);

			Assert.assertEquals(
				"Asset Renderer Summary",
				suggestion.getAttribute("assetSearchSummary"));
			Assert.assertNotNull(suggestion.getAttribute("assetURL"));

			Assert.assertEquals(
				HashMapBuilder.<String, Object>put(
					Field.ENTRY_CLASS_NAME, _MODEL_LAYOUT + i
				).build(),
				suggestion.getAttribute("fields"));
			Assert.assertEquals(i, suggestion.getScore(), i);
			Assert.assertEquals("Asset Renderer Title", suggestion.getText());
		}

		Mockito.verify(
			_assetURLViewProvider, Mockito.times(1)
		).getAssetURLView(
			Mockito.any(), Mockito.any(), Mockito.eq(_MODEL_LAYOUT + "0"),
			Mockito.eq(Long.valueOf(0)), Mockito.any(), Mockito.any()
		);

		Mockito.verify(
			_assetURLViewProvider, Mockito.times(1)
		).getAssetURLView(
			Mockito.any(), Mockito.any(),
			Mockito.eq(_MODEL_LAYOUT_PAGE_TEMPLATE_ENTRY + "1"),
			Mockito.anyLong(), Mockito.any(), Mockito.any()
		);
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

	@Test
	public void testSearchTuningRankingsIsContributed() throws Exception {
		_setUpSearcher(0);

		_getSuggestionsContributorResults();

		SearchContext searchContext = Mockito.mock(SearchContext.class);

		ArgumentCaptor<Consumer<SearchContext>> argumentCaptor =
			ArgumentCaptor.forClass(
				(Class<Consumer<SearchContext>>)(Class)Consumer.class);

		Mockito.verify(
			_searchRequestBuilder
		).withSearchContext(
			argumentCaptor.capture()
		);

		Consumer<SearchContext> argumentCaptorValue = argumentCaptor.getValue();

		argumentCaptorValue.accept(searchContext);

		Mockito.verify(
			searchContext
		).setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_CONTRIBUTE_TUNING_RANKINGS,
			Boolean.TRUE
		);
	}

	@Rule
	public TestName testName = new TestName();

	private SuggestionsContributorResults _getSuggestionsContributorResults() {
		return _basicSuggestionsContributor.getSuggestionsContributorResults(
			_liferayPortletRequest, _liferayPortletResponse, _searchContext,
			_suggestionsContributorConfiguration);
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
			boolean assetRendererFactoryNull, String title, String summary)
		throws Exception {

		_setUpAssetRendererFactoryRegistryUtil(
			assetRendererFactoryNull, title, summary, null);
	}

	private void _setUpAssetRendererFactoryRegistryUtil(
			boolean assetRendererFactoryNull, String title, String summary,
			String classNameWithoutAssetRenderer)
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
		).getSummary(
			Mockito.any(), Mockito.any()
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

		Mockito.when(
			_serviceTrackerMap.getService(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> {
				if (Objects.nonNull(classNameWithoutAssetRenderer) &&
					Objects.equals(
						invocationOnMock.getArgument(0),
						classNameWithoutAssetRenderer)) {

					return null;
				}

				return _assetRendererFactory;
			}
		);
	}

	private void _setUpAssetURLViewProvider() {
		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			_assetURLViewProvider
		).getAssetURLView(
			Mockito.any(), Mockito.any(), Mockito.anyString(),
			Mockito.anyLong(), Mockito.any(), Mockito.any()
		);
	}

	private void _setUpBasicSuggestionsContributor() {
		_basicSuggestionsContributor = new BasicSuggestionsContributor();

		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_assetURLViewProvider",
			_assetURLViewProvider);
		ReflectionTestUtil.setFieldValue(
			_basicSuggestionsContributor, "_classNameLocalService",
			_classNameLocalService);
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

	private SearchHitsTestHelper _setUpSearcher(int totalHits)
		throws Exception {

		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		SearchHits searchHits = Mockito.mock(SearchHits.class);

		SearchHitsTestHelper searchHitsTestHelper = new SearchHitsTestHelper();

		for (int i = 0; i < totalHits; i++) {
			SearchHit searchHit = Mockito.mock(SearchHit.class);

			Document document = Mockito.mock(Document.class);

			ClassName className = Mockito.mock(ClassName.class);

			searchHitsTestHelper.add(i, className);

			long classNameId = RandomTestUtil.randomLong();
			long classPK = RandomTestUtil.randomLong();

			Mockito.doReturn(
				_MODEL_LAYOUT_PAGE_TEMPLATE_ENTRY + i
			).when(
				className
			).getClassName();

			Mockito.doReturn(
				className
			).when(
				_classNameLocalService
			).getClassName(
				Mockito.eq(classNameId)
			);

			Mockito.doReturn(
				_MODEL_LAYOUT + i
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
				classNameId
			).when(
				document
			).getValue(
				Mockito.eq(Field.CLASS_NAME_ID)
			);

			Mockito.doReturn(
				classPK
			).when(
				document
			).getValue(
				Mockito.eq(Field.CLASS_PK)
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

			searchHitsTestHelper.add(i, searchHit);
		}

		Mockito.doReturn(
			searchHitsTestHelper.getSearchHits()
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

		return searchHitsTestHelper;
	}

	private void _setUpSearchRequestBuilderFactory() {
		Mockito.doReturn(
			Mockito.mock(SearchRequest.class)
		).when(
			_searchRequestBuilder
		).build();

		Mockito.doReturn(
			_searchRequestBuilder
		).when(
			_searchRequestBuilder
		).from(
			Mockito.anyInt()
		);

		Mockito.doReturn(
			_searchRequestBuilder
		).when(
			_searchRequestBuilder
		).queryString(
			Mockito.any()
		);

		Mockito.doReturn(
			_searchRequestBuilder
		).when(
			_searchRequestBuilder
		).size(
			Mockito.anyInt()
		);

		Mockito.doReturn(
			_searchRequestBuilder
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

	private static final String _MODEL_LAYOUT =
		"com.liferay.portal.kernel.model.Layout_";

	private static final String _MODEL_LAYOUT_PAGE_TEMPLATE_ENTRY =
		"com.liferay.layout.page.template.model.LayoutPageTemplateEntry_";

	@Mock
	private AssetRendererFactory<?> _assetRendererFactory;

	@Mock
	private AssetURLViewProvider _assetURLViewProvider;

	private BasicSuggestionsContributor _basicSuggestionsContributor;

	@Mock
	private ClassNameLocalService _classNameLocalService;

	@Mock
	private LiferayPortletRequest _liferayPortletRequest;

	@Mock
	private LiferayPortletResponse _liferayPortletResponse;

	@Mock
	private SearchContext _searchContext;

	@Mock
	private Searcher _searcher;

	private final SearchRequestBuilder _searchRequestBuilder = Mockito.mock(
		SearchRequestBuilder.class);

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_serviceTrackerMap;

	private SuggestionsContributorConfiguration
		_suggestionsContributorConfiguration;

}