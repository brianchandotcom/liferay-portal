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

package com.liferay.search.experiences.internal.suggestions.spi;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.suggestions.SuggestionBuilder;
import com.liferay.portal.search.suggestions.SuggestionBuilderFactory;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilder;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
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
public class SXPBlueprintSuggestionsContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_sxpBlueprintSuggestionsContributor =
			new SXPBlueprintSuggestionsContributor();

		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor, "_assetEntryLocalService",
			_assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor, "_layoutLocalService",
			_layoutLocalService);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor, "_searchRequestBuilderFactory",
			_searchRequestBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor, "_searcher", _searcher);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor, "_suggestionBuilderFactory",
			_suggestionBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			_sxpBlueprintSuggestionsContributor,
			"_suggestionsContributorResultsBuilderFactory",
			_suggestionsContributorResultsBuilderFactory);
	}

	@Test
	public void testGetSuggestionsContributorResults() throws Exception {
		_setUpAssetEntryLocalService();
		_setUpLayoutLocalService();
		_setUpPortalUtil();
		_setUpSearcher(1L);
		_setUpSearchHits(_setUpDocument("testField"));
		_setUpSearchRequestBuilderFactory();
		_setUpServiceTrackerMap();
		_setUpSuggestionBuilderFactory();
		_setUpSuggestionsContributorConfiguration("testField");

		SuggestionsContributorResults suggestionsContributorResults =
			Mockito.mock(SuggestionsContributorResults.class);

		_setUpSuggestionsContributorResultsBuilderFactory(
			suggestionsContributorResults);

		Assert.assertEquals(
			suggestionsContributorResults,
			_sxpBlueprintSuggestionsContributor.
				getSuggestionsContributorResults(
					_setUpliferayPortletRequest(),
					_setUpLiferayPortletResponse(),
					Mockito.mock(SearchContext.class),
					_suggestionsContributorConfiguration));

		Mockito.verify(
			_suggestionBuilder, Mockito.times(1)
		).text(
			Mockito.anyString()
		);
	}

	@Test
	public void testGetSuggestionsContributorResultsWithNullTextFieldName()
		throws Exception {

		_setUpAssetEntryLocalService();
		_setUpLayoutLocalService();
		_setUpPortalUtil();
		_setUpSearcher(1L);
		_setUpSearchHits(_setUpDocument(RandomTestUtil.randomString()));
		_setUpSearchRequestBuilderFactory();
		_setUpServiceTrackerMap();
		_setUpSuggestionBuilderFactory();
		_setUpSuggestionsContributorConfiguration(null);

		SuggestionsContributorResults suggestionsContributorResults =
			Mockito.mock(SuggestionsContributorResults.class);

		_setUpSuggestionsContributorResultsBuilderFactory(
			suggestionsContributorResults);

		Assert.assertEquals(
			suggestionsContributorResults,
			_sxpBlueprintSuggestionsContributor.
				getSuggestionsContributorResults(
					_setUpliferayPortletRequest(),
					_setUpLiferayPortletResponse(),
					Mockito.mock(SearchContext.class),
					_suggestionsContributorConfiguration));

		Mockito.verify(
			_suggestionBuilder, Mockito.times(2)
		).text(
			Mockito.anyString()
		);
	}

	@Test
	public void testSearchHitsWithZeroTotalHits() {
		_setUpSearchRequestBuilderFactory();
		_setUpSearcher(0L);
		_setUpSuggestionsContributorConfiguration(null);

		Assert.assertNull(
			_sxpBlueprintSuggestionsContributor.
				getSuggestionsContributorResults(
					Mockito.mock(LiferayPortletRequest.class),
					Mockito.mock(LiferayPortletResponse.class),
					Mockito.mock(SearchContext.class),
					_suggestionsContributorConfiguration));

		Mockito.verify(
			_searcher, Mockito.times(1)
		).search(
			Mockito.any()
		);

		Mockito.verify(
			_suggestionsContributorResultsBuilderFactory, Mockito.never()
		).builder();
	}

	@Test
	public void testSuggestionsContributorConfigurationWithNullAttributes() {
		Assert.assertNull(
			_sxpBlueprintSuggestionsContributor.
				getSuggestionsContributorResults(
					Mockito.mock(LiferayPortletRequest.class),
					Mockito.mock(LiferayPortletResponse.class),
					Mockito.mock(SearchContext.class),
					_suggestionsContributorConfiguration));

		Mockito.verify(
			_searcher, Mockito.never()
		).search(
			Mockito.any()
		);
	}

	private void _setUpAssetEntryLocalService() throws Exception {
		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			assetEntry
		).getEntryId();

		Mockito.doReturn(
			assetEntry
		).when(
			_assetEntryLocalService
		).getEntry(
			Mockito.anyString(), Mockito.anyLong()
		);
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

	private LiferayPortletRequest _setUpliferayPortletRequest() {
		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		Mockito.doReturn(
			Mockito.mock(ThemeDisplay.class)
		).when(
			liferayPortletRequest
		).getAttribute(
			Mockito.anyString()
		);

		return liferayPortletRequest;
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

		return liferayPortletResponse;
	}

	private void _setUpPortalUtil() {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgumentAt(0, String.class), StringPool.BLANK
			}
		).when(
			portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	private void _setUpSearcher(long totalHits) {
		Mockito.doReturn(
			totalHits
		).when(
			_searchHits
		).getTotalHits();

		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		Mockito.doReturn(
			_searchHits
		).when(
			searchResponse
		).getSearchHits();

		Mockito.doReturn(
			searchResponse
		).when(
			_searcher
		).search(
			Mockito.anyObject()
		);
	}

	private void _setUpSearchHits(Document document) {
		Mockito.doReturn(
			document
		).when(
			_searchHit
		).getDocument();

		Mockito.doReturn(
			ListUtil.fromArray(_searchHit)
		).when(
			_searchHits
		).getSearchHits();
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
			_searchRequestBuilderFactory
		).builder();

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

	private void _setUpServiceTrackerMap() throws Exception {
		AssetRendererFactory<?> assetRendererFactory = Mockito.mock(
			AssetRendererFactory.class);

		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.doReturn(
			assetRenderer
		).when(
			assetRendererFactory
		).getAssetRenderer(
			Matchers.anyLong()
		);

		Mockito.doReturn(
			assetRendererFactory
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

	private void _setUpSuggestionBuilderFactory() {
		Mockito.doReturn(
			_suggestionBuilder
		).when(
			_suggestionBuilder
		).score(
			Mockito.anyFloat()
		);

		Mockito.doReturn(
			_suggestionBuilder
		).when(
			_suggestionBuilder
		).text(
			Mockito.anyString()
		);

		Mockito.doReturn(
			_suggestionBuilder
		).when(
			_suggestionBuilderFactory
		).builder();
	}

	private void _setUpSuggestionsContributorConfiguration(String textField) {
		Mockito.doReturn(
			HashMapBuilder.<String, Object>put(
				"fields", ListUtil.fromArray("field")
			).put(
				"sxpBlueprintId", RandomTestUtil.randomLong()
			).put(
				"textField", textField
			).build()
		).when(
			_suggestionsContributorConfiguration
		).getAttributes();

		Mockito.doReturn(
			RandomTestUtil.randomInt()
		).when(
			_suggestionsContributorConfiguration
		).getSize();
	}

	private void _setUpSuggestionsContributorResultsBuilderFactory(
		SuggestionsContributorResults suggestionsContributorResults) {

		SuggestionsContributorResultsBuilder
			suggestionsContributorResultsBuilder = Mockito.mock(
				SuggestionsContributorResultsBuilder.class);

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

		Mockito.doReturn(
			suggestionsContributorResultsBuilder
		).when(
			_suggestionsContributorResultsBuilderFactory
		).builder();
	}

	@Mock
	private AssetEntryLocalService _assetEntryLocalService;

	@Mock
	private LayoutLocalService _layoutLocalService;

	@Mock
	private Searcher _searcher;

	@Mock
	private SearchHit _searchHit;

	@Mock
	private SearchHits _searchHits;

	@Mock
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Mock
	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_serviceTrackerMap;

	@Mock
	private SuggestionBuilder _suggestionBuilder;

	@Mock
	private SuggestionBuilderFactory _suggestionBuilderFactory;

	@Mock
	private SuggestionsContributorConfiguration
		_suggestionsContributorConfiguration;

	@Mock
	private SuggestionsContributorResultsBuilderFactory
		_suggestionsContributorResultsBuilderFactory;

	private SXPBlueprintSuggestionsContributor
		_sxpBlueprintSuggestionsContributor;

}