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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.summary.Summary;
import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.search.summary.SummaryBuilderFactory;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class SearchResultsPortletTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_setUpPortletSharedSearchResponse();
		_setUpPropsUtil();
		_setUpSearchSettings();

		_portletURLFactory = _createPortletURLFactory();
		_renderRequest = _createRenderRequest();
		_renderResponse = _createRenderResponse();
		_searchResultsPortlet = _createSearchResultsPortlet();
	}

	@Test
	public void testDocumentWithoutSummaryIsRemoved() throws Exception {
		Document document = _createDocumentWithSummary();

		_setUpSearchResponseDocuments(document, _createDocument());

		render();

		_assertDisplayContextDocuments(document);
	}

	protected void render() throws IOException, PortletException {
		_searchResultsPortlet.render(_renderRequest, _renderResponse);
	}

	private void _assertDisplayContextDocuments(Document... expectedDocuments) {
		SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
			_getDisplayContext();

		Assert.assertEquals(
			Arrays.asList(expectedDocuments),
			searchResultsPortletDisplayContext.getDocuments());
	}

	private Document _createDocument() {
		Document document = new DocumentImpl();

		String className = RandomTestUtil.randomString();

		document.addKeyword(Field.ENTRY_CLASS_NAME, className);

		return document;
	}

	private Document _createDocumentWithSummary() throws Exception {
		Document document = new DocumentImpl();

		String className = RandomTestUtil.randomString();

		document.addKeyword(Field.ENTRY_CLASS_NAME, className);

		Mockito.doReturn(
			_createIndexerWithSummary()
		).when(
			_indexerRegistry
		).getIndexer(
			className
		);

		return document;
	}

	private Indexer<?> _createIndexerWithSummary() throws Exception {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doReturn(
			new com.liferay.portal.kernel.search.Summary(null, null, null)
		).when(
			indexer
		).getSummary(
			Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any()
		);

		return indexer;
	}

	private PortletSharedSearchRequest _createPortletSharedSearchRequest() {
		PortletSharedSearchRequest portletSharedSearchRequest = Mockito.mock(
			PortletSharedSearchRequest.class);

		Mockito.doReturn(
			_portletSharedSearchResponse
		).when(
			portletSharedSearchRequest
		).search(
			Mockito.any()
		);

		return portletSharedSearchRequest;
	}

	private PortletURLFactory _createPortletURLFactory() throws Exception {
		PortletURLFactory portletURLFactory = Mockito.mock(
			PortletURLFactory.class);

		Mockito.doReturn(
			Mockito.mock(PortletURL.class)
		).when(
			portletURLFactory
		).getPortletURL();

		return portletURLFactory;
	}

	private RenderRequest _createRenderRequest() {
		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			renderRequest
		).getParameter(
			"mvcPath"
		);

		Mockito.doReturn(
			RenderRequest.RENDER_MARKUP
		).when(
			renderRequest
		).getAttribute(
			RenderRequest.RENDER_PART
		);

		return renderRequest;
	}

	private RenderResponse _createRenderResponse() {
		RenderResponse renderResponse = Mockito.mock(RenderResponse.class);

		Mockito.doReturn(
			Mockito.mock(RenderURL.class)
		).when(
			renderResponse
		).createRenderURL();

		return renderResponse;
	}

	private SearchResultsPortlet _createSearchResultsPortlet()
		throws Exception {

		SearchResultsPortlet searchResultsPortlet = new SearchResultsPortlet() {
			{
				assetEntryLocalService = Mockito.mock(
					AssetEntryLocalService.class);
				assetRendererFactoryLookup = Mockito.mock(
					AssetRendererFactoryLookup.class);
				http = Mockito.mock(Http.class);
				indexerRegistry = _indexerRegistry;
				portletSharedRequestHelper = Mockito.mock(
					PortletSharedRequestHelper.class);
				portletSharedSearchRequest =
					_createPortletSharedSearchRequest();
				resourceActions = Mockito.mock(ResourceActions.class);
				summaryBuilderFactory = _createSummaryBuilderFactory();
			}

			@Override
			public void init() {
			}

			@Override
			protected void doDispatch(
				RenderRequest renderRequest, RenderResponse renderResponse) {
			}

			@Override
			protected String getCurrentURL(RenderRequest renderRequest) {
				return RandomTestUtil.randomString();
			}

			@Override
			protected HttpServletRequest getHttpServletRequest(
				RenderRequest renderRequest) {

				ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

				Mockito.when(
					themeDisplay.getPortletDisplay()
				).thenReturn(
					Mockito.mock(PortletDisplay.class)
				);

				HttpServletRequest httpServletRequest = Mockito.mock(
					HttpServletRequest.class);

				Mockito.when(
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY)
				).thenReturn(
					themeDisplay
				);

				return httpServletRequest;
			}

			@Override
			protected PortletURLFactory getPortletURLFactory(
				RenderRequest renderRequest, RenderResponse renderResponse) {

				return _portletURLFactory;
			}

		};

		searchResultsPortlet.init(Mockito.mock(LiferayPortletConfig.class));

		return searchResultsPortlet;
	}

	private SummaryBuilderFactory _createSummaryBuilderFactory() {
		SummaryBuilder summaryBuilder = Mockito.mock(SummaryBuilder.class);

		Mockito.doReturn(
			Mockito.mock(Summary.class)
		).when(
			summaryBuilder
		).build();

		SummaryBuilderFactory summaryBuilderFactory = Mockito.mock(
			SummaryBuilderFactory.class);

		Mockito.doReturn(
			summaryBuilder
		).when(
			summaryBuilderFactory
		).newInstance();

		return summaryBuilderFactory;
	}

	private SearchResultsPortletDisplayContext _getDisplayContext() {
		ArgumentCaptor<SearchResultsPortletDisplayContext> argumentCaptor =
			ArgumentCaptor.forClass(SearchResultsPortletDisplayContext.class);

		Mockito.verify(
			_renderRequest
		).setAttribute(
			Matchers.eq(WebKeys.PORTLET_DISPLAY_CONTEXT),
			argumentCaptor.capture()
		);

		return argumentCaptor.getValue();
	}

	private void _setUpPortletSharedSearchResponse() {
		Mockito.doReturn(
			Optional.empty()
		).when(
			_portletSharedSearchResponse
		).getKeywordsOptional();

		Mockito.doReturn(
			Optional.empty()
		).when(
			_portletSharedSearchResponse
		).getPortletPreferences(
			Mockito.any()
		);

		Mockito.doReturn(
			_searchResponse
		).when(
			_portletSharedSearchResponse
		).getFederatedSearchResponse(
			Mockito.any()
		);

		Mockito.doReturn(
			_searchRequest
		).when(
			_searchResponse
		).getRequest();

		Mockito.doReturn(
			_searchSettings
		).when(
			_portletSharedSearchResponse
		).getSearchSettings();

		Mockito.doReturn(
			new ThemeDisplay()
		).when(
			_portletSharedSearchResponse
		).getThemeDisplay(
			Mockito.any()
		);
	}

	private void _setUpPropsUtil() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	private void _setUpSearchResponseDocuments(Document... documents) {
		Mockito.doReturn(
			Arrays.asList(documents)
		).when(
			_searchResponse
		).getDocuments71();
	}

	private void _setUpSearchSettings() {
		Mockito.when(
			_searchSettings.getSearchContext()
		).thenReturn(
			_searchContext
		);
	}

	@Mock
	private IndexerRegistry _indexerRegistry;

	@Mock
	private PortletSharedSearchResponse _portletSharedSearchResponse;

	private PortletURLFactory _portletURLFactory;
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

	@Mock
	private SearchContext _searchContext;

	@Mock
	private SearchRequest _searchRequest;

	@Mock
	private SearchResponse _searchResponse;

	private SearchResultsPortlet _searchResultsPortlet;

	@Mock
	private SearchSettings _searchSettings;

}