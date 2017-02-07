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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchPortletParameterNames;
import com.liferay.portlet.portletconfiguration.util.ConfigurationRenderRequest;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class SearchDisplayContextTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		themeDisplay = createThemeDisplay();

		setUpFacetedSearcherManager();
		setUpHttpServletRequest();
		setUpPortletURLFactory();
		setUpRenderRequest();
	}

	@Test
	public void testConfigurationKeywordsEmptySkipsSearch() throws Exception {
		assertSearchSkippedAndNullResults(
			null,
			new ConfigurationRenderRequest(renderRequest, portletPreferences));
	}

	@Test
	public void testSearchKeywordsBlank() throws Exception {
		assertSearchSkipped(StringPool.BLANK, StringPool.BLANK);
	}

	@Test
	public void testSearchKeywordsNullWord() throws Exception {
		assertSearchKeywords(StringPool.NULL, StringPool.NULL);
	}

	@Test
	public void testSearchKeywordsSpaces() throws Exception {
		assertSearchSkipped(StringPool.DOUBLE_SPACE, StringPool.BLANK);
	}

	protected void assertSearchKeywords(
			String requestKeywords, String searchDisplayContextKeywords)
		throws Exception {

		SearchDisplayContext searchDisplayContext = createSearchDisplayContext(
			requestKeywords, renderRequest);

		Assert.assertEquals(
			searchDisplayContextKeywords, searchDisplayContext.getKeywords());

		SearchContext searchContext = searchDisplayContext.getSearchContext();

		Mockito.verify(
			facetedSearcher
		).search(
			searchContext
		);

		Assert.assertEquals(
			searchDisplayContextKeywords, searchContext.getKeywords());
	}

	protected void assertSearchSkipped(
			String requestKeywords, String searchDisplayContextKeywords)
		throws Exception {

		SearchDisplayContext searchDisplayContext = createSearchDisplayContext(
			requestKeywords, renderRequest);

		Assert.assertEquals(
			searchDisplayContextKeywords, searchDisplayContext.getKeywords());

		Assert.assertNotNull(searchDisplayContext.getHits());
		Assert.assertNotNull(searchDisplayContext.getSearchContainer());
		Assert.assertNotNull(searchDisplayContext.getSearchContext());

		Mockito.verifyZeroInteractions(facetedSearcher);
	}

	protected void assertSearchSkippedAndNullResults(
			String requestKeywords, RenderRequest renderRequest)
		throws Exception {

		SearchDisplayContext searchDisplayContext = createSearchDisplayContext(
			requestKeywords, renderRequest);

		Assert.assertNull(searchDisplayContext.getHits());
		Assert.assertNull(searchDisplayContext.getKeywords());
		Assert.assertNull(searchDisplayContext.getSearchContainer());
		Assert.assertNull(searchDisplayContext.getSearchContext());

		Mockito.verifyZeroInteractions(facetedSearcher);
	}

	protected JSONArray createJSONArray() {
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

	protected JSONFactory createJSONFactory() {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.doReturn(
			createJSONObject()
		).when(
			jsonFactory
		).createJSONObject();

		return jsonFactory;
	}

	protected JSONObject createJSONObject() {
		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			true
		).when(
			jsonObject
		).has(
			"values"
		);

		Mockito.doReturn(
			createJSONArray()
		).when(
			jsonObject
		).getJSONArray(
			"values"
		);

		return jsonObject;
	}

	protected Portal createPortal(
			ThemeDisplay themeDisplay, RenderRequest renderRequest)
		throws Exception {

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

	protected SearchDisplayContext createSearchDisplayContext(
			String keywords, RenderRequest renderRequest)
		throws Exception {

		setUpRequestKeywords(keywords);

		PropsUtil.setProps(Mockito.mock(Props.class));

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(createJSONFactory());

		return new SearchDisplayContext(
			renderRequest, portletPreferences,
			createPortal(themeDisplay, renderRequest), Mockito.mock(Html.class),
			Mockito.mock(Language.class), facetedSearcherManager,
			Mockito.mock(IndexSearchPropsValues.class), portletURLFactory);
	}

	protected ThemeDisplay createThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setUser(Mockito.mock(User.class));

		return themeDisplay;
	}

	protected void setUpFacetedSearcherManager() throws Exception {
		Mockito.doReturn(
			Mockito.mock(Hits.class)
		).when(
			facetedSearcher
		).search(
			Mockito.<SearchContext>any()
		);

		Mockito.doReturn(
			facetedSearcher
		).when(
			facetedSearcherManager
		).createFacetedSearcher();
	}

	protected void setUpHttpServletRequest() throws Exception {
		Mockito.doReturn(
			themeDisplay
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);
	}

	protected void setUpPortletURLFactory() throws Exception {
		Mockito.doReturn(
			Mockito.mock(PortletURL.class)
		).when(
			portletURLFactory
		).getPortletURL();
	}

	protected void setUpRenderRequest() throws Exception {
		Mockito.doReturn(
			themeDisplay
		).when(
			renderRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);
	}

	protected void setUpRequestKeywords(String keywords) {
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

	@Mock
	protected FacetedSearcher facetedSearcher;

	@Mock
	protected FacetedSearcherManager facetedSearcherManager;

	@Mock
	protected HttpServletRequest httpServletRequest;

	@Mock
	protected PortletPreferences portletPreferences;

	@Mock
	protected PortletURLFactory portletURLFactory;

	@Mock
	protected RenderRequest renderRequest;

	protected ThemeDisplay themeDisplay;

}