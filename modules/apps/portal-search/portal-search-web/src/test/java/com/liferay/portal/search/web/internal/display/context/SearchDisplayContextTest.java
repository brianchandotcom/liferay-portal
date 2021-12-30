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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.context.SearchContextFactory;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.summary.SummaryBuilderFactory;
import com.liferay.portal.search.web.constants.SearchPortletParameterNames;
import com.liferay.portal.search.web.internal.facet.SearchFacetTracker;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portlet.portletconfiguration.util.ConfigurationRenderRequest;

import java.util.Collections;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class SearchDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		themeDisplay = _createThemeDisplay();

		_setUpHttpServletRequest();
		_setUpPortletURLFactory();
		_setUpRenderRequest();
		_setUpSearchContextFactory();
		_setUpSearcher();
		_setUpSearchResponseBuilderFactory();
	}

	@Test
	public void testConfigurationKeywordsEmptySkipsSearch() throws Exception {
		_assertSearchSkippedAndNullResults(
			null,
			new ConfigurationRenderRequest(renderRequest, portletPreferences));
	}

	@Test
	public void testSearchKeywordsBlank() throws Exception {
		_assertSearchKeywords(StringPool.BLANK, StringPool.BLANK);
	}

	@Test
	public void testSearchKeywordsNullWord() throws Exception {
		_assertSearchKeywords(StringPool.NULL, StringPool.NULL);
	}

	@Test
	public void testSearchKeywordsSpaces() throws Exception {
		_assertSearchKeywords(StringPool.DOUBLE_SPACE, StringPool.BLANK);
	}

	@Mock
	protected HttpServletRequest httpServletRequest;

	@Mock
	protected PortletPreferences portletPreferences;

	@Mock
	protected PortletURLFactory portletURLFactory;

	@Mock
	protected RenderRequest renderRequest;

	@Mock
	protected SearchContextFactory searchContextFactory;

	@Mock
	protected Searcher searcher;

	protected SearchRequestBuilderFactory searchRequestBuilderFactory =
		new SearchRequestBuilderFactoryImpl();

	@Mock
	protected SearchResponse searchResponse;

	@Mock
	protected SearchResponseBuilder searchResponseBuilder;

	@Mock
	protected SearchResponseBuilderFactory searchResponseBuilderFactory;

	protected ThemeDisplay themeDisplay;

	private void _assertSearchKeywords(
			String requestKeywords, String searchDisplayContextKeywords)
		throws Exception {

		SearchDisplayContext searchDisplayContext = _createSearchDisplayContext(
			requestKeywords, renderRequest);

		Assert.assertEquals(
			searchDisplayContextKeywords, searchDisplayContext.getKeywords());

		Assert.assertNotNull(searchDisplayContext.getHits());
		Assert.assertNotNull(searchDisplayContext.getSearchContainer());
		Assert.assertNotNull(searchDisplayContext.getSearchContext());

		SearchContext searchContext = searchDisplayContext.getSearchContext();

		Mockito.verify(
			searcher
		).search(
			Mockito.any()
		);

		Assert.assertEquals(
			searchDisplayContextKeywords, searchContext.getKeywords());
	}

	private void _assertSearchSkippedAndNullResults(
			String requestKeywords, RenderRequest renderRequest)
		throws Exception {

		SearchDisplayContext searchDisplayContext = _createSearchDisplayContext(
			requestKeywords, renderRequest);

		Assert.assertNull(searchDisplayContext.getHits());
		Assert.assertNull(searchDisplayContext.getKeywords());
		Assert.assertNull(searchDisplayContext.getSearchContainer());
		Assert.assertNull(searchDisplayContext.getSearchContext());

		Mockito.verifyZeroInteractions(searcher);
	}

	private JSONArray _createJSONArray() {
		JSONArray jsonArray = Mockito.mock(JSONArray.class);

		Mockito.doReturn(
			1
		).when(
			jsonArray
		).length();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			jsonArray
		).getString(
			0
		);

		return jsonArray;
	}

	private JSONFactory _createJSONFactory() {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.doReturn(
			_createJSONObject()
		).when(
			jsonFactory
		).createJSONObject();

		return jsonFactory;
	}

	private JSONObject _createJSONObject() {
		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			true
		).when(
			jsonObject
		).has(
			"values"
		);

		Mockito.doReturn(
			_createJSONArray()
		).when(
			jsonObject
		).getJSONArray(
			"values"
		);

		return jsonObject;
	}

	private Portal _createPortal(RenderRequest renderRequest) throws Exception {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.doReturn(
			httpServletRequest
		).when(
			portal
		).getHttpServletRequest(
			renderRequest
		);

		return portal;
	}

	private SearchDisplayContext _createSearchDisplayContext(
			String keywords, RenderRequest renderRequest)
		throws Exception {

		_setUpRequestKeywords(keywords);

		PropsTestUtil.setProps(Collections.emptyMap());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(_createJSONFactory());

		return new SearchDisplayContext(
			renderRequest, portletPreferences, _createPortal(renderRequest),
			Mockito.mock(Html.class), Mockito.mock(Language.class), searcher,
			Mockito.mock(IndexSearchPropsValues.class), portletURLFactory,
			Mockito.mock(SummaryBuilderFactory.class), searchContextFactory,
			searchRequestBuilderFactory, new SearchFacetTracker());
	}

	private ThemeDisplay _createThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setUser(Mockito.mock(User.class));

		return themeDisplay;
	}

	private void _setUpHttpServletRequest() throws Exception {
		Mockito.doReturn(
			themeDisplay
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);
	}

	private void _setUpPortletURLFactory() throws Exception {
		Mockito.doReturn(
			Mockito.mock(PortletURL.class)
		).when(
			portletURLFactory
		).getPortletURL();
	}

	private void _setUpRenderRequest() throws Exception {
		Mockito.doReturn(
			themeDisplay
		).when(
			renderRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);
	}

	private void _setUpRequestKeywords(String keywords) {
		Mockito.doReturn(
			keywords
		).when(
			httpServletRequest
		).getParameter(
			SearchPortletParameterNames.KEYWORDS
		);

		Mockito.doReturn(
			keywords
		).when(
			renderRequest
		).getParameter(
			SearchPortletParameterNames.KEYWORDS
		);
	}

	private void _setUpSearchContextFactory() throws Exception {
		Mockito.doReturn(
			new SearchContext()
		).when(
			searchContextFactory
		).getSearchContext(
			Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any(),
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(),
			Mockito.any(), Mockito.anyLong()
		);
	}

	private void _setUpSearcher() throws Exception {
		Mockito.doReturn(
			Mockito.mock(Hits.class)
		).when(
			searchResponse
		).withHitsGet(
			Mockito.any()
		);

		Mockito.doReturn(
			searchResponse
		).when(
			searcher
		).search(
			Mockito.any()
		);
	}

	private void _setUpSearchResponseBuilderFactory() {
		Mockito.doReturn(
			searchResponseBuilder
		).when(
			searchResponseBuilderFactory
		).builder(
			Mockito.any()
		);

		Mockito.doReturn(
			searchResponse
		).when(
			searchResponseBuilder
		).build();
	}

}