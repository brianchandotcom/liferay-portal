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

package com.liferay.portal.search.web.internal.search.bar.portlet.display.context.factory;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferences;
import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletDisplayContext;
import com.liferay.portal.search.web.internal.search.bar.portlet.helper.SearchBarPrecedenceHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
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

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Adam Brandizzi
 */
public class SearchBarPortletDisplayContextFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_setUpLanguageUtil();
		_setUpPortal();
		_setUpThemeDisplay();
	}

	@Test
	public void testDestinationBlank() throws PortletException {
		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(StringPool.BLANK);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationNull() throws PortletException {
		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(null);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationUnreachable() throws PortletException {
		String destination = RandomTestUtil.randomString();

		_whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, null);

		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(destination);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertTrue(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationWithLeadingSlash() throws Exception {
		String destination = RandomTestUtil.randomString();

		Layout layout = Mockito.mock(Layout.class);

		_whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, layout);

		String layoutFriendlyURL = RandomTestUtil.randomString();

		_whenPortalGetLayoutFriendlyURL(layout, layoutFriendlyURL);

		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(
					StringPool.SLASH.concat(destination));

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertEquals(
			layoutFriendlyURL, searchBarPortletDisplayContext.getSearchURL());

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationWithoutLeadingSlash() throws Exception {
		String destination = RandomTestUtil.randomString();

		Layout layout = Mockito.mock(Layout.class);

		_whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, layout);

		String layoutFriendlyURL = RandomTestUtil.randomString();

		_whenPortalGetLayoutFriendlyURL(layout, layoutFriendlyURL);

		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(destination);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertEquals(
			layoutFriendlyURL, searchBarPortletDisplayContext.getSearchURL());

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testSamePageNoDestination() throws PortletException {
		Mockito.doReturn(
			"http://example.com/web/guest/home?param=arg"
		).when(
			_themeDisplay
		).getURLCurrent();

		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(null);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());

		Assert.assertEquals(
			"/web/guest/home", searchBarPortletDisplayContext.getSearchURL());
	}

	@Test
	public void testSearchScope() throws ReadOnlyException {
		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				_createSearchBarPortletDisplayContextFactory(null);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayContextFactory.create(
				_portletPreferencesLookup, _portletSharedSearchRequest,
				_searchBarPrecedenceHelper);

		Assert.assertEquals(
			SearchScope.EVERYTHING,
			searchBarPortletDisplayContextFactory.getSearchScope(
				SearchScopePreference.THIS_SITE,
				searchBarPortletDisplayContext.getScopeParameterValue()));
	}

	protected HttpServletRequest getHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		return httpServletRequest;
	}

	protected String getPath(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		if (url.startsWith(Http.HTTP)) {
			int pos = url.indexOf(
				CharPool.SLASH, Http.HTTPS_WITH_SLASH.length());

			url = url.substring(pos);
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		return url.substring(0, pos);
	}

	private LiferayPortletRequest _createLiferayPortletRequest() {
		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		Mockito.doReturn(
			getHttpServletRequest()
		).when(
			liferayPortletRequest
		).getHttpServletRequest();

		return liferayPortletRequest;
	}

	private SearchBarPortletDisplayContextFactory
			_createSearchBarPortletDisplayContextFactory(String destination)
		throws ReadOnlyException {

		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		SearchBarPortletDisplayContextFactory
			searchBarPortletDisplayContextFactory =
				new SearchBarPortletDisplayContextFactory(
					_layoutLocalService, _portal, renderRequest);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue(
			SearchBarPortletPreferences.PREFERENCE_KEY_DESTINATION,
			destination);
		portletPreferences.setValue(
			SearchBarPortletPreferences.PREFERENCE_KEY_SEARCH_SCOPE,
			"everything");

		Mockito.when(
			renderRequest.getPreferences()
		).thenReturn(
			portletPreferences
		);

		Mockito.when(
			renderRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		PortletSharedSearchResponse portletSharedSearchResponse = Mockito.mock(
			PortletSharedSearchResponse.class);

		Mockito.when(
			_portletSharedSearchRequest.search(renderRequest)
		).thenReturn(
			portletSharedSearchResponse
		);

		Mockito.when(
			_searchBarPrecedenceHelper.findHeaderSearchBarPortlet(_themeDisplay)
		).thenReturn(
			null
		);

		Mockito.when(
			portletSharedSearchResponse.getParameter(
				Mockito.anyObject(), Mockito.anyObject())
		).thenReturn(
			Optional.of(SearchScope.EVERYTHING.getParameterString())
		);

		SearchResponse searchResponse = Mockito.mock(SearchResponse.class);

		Mockito.when(
			portletSharedSearchResponse.getFederatedSearchResponse(
				Mockito.anyObject())
		).thenReturn(
			searchResponse
		);

		Mockito.when(
			searchResponse.getRequest()
		).thenReturn(
			Mockito.mock(SearchRequest.class)
		);

		Mockito.when(
			portletSharedSearchResponse.getSearchResponse()
		).thenReturn(
			searchResponse
		);

		return searchBarPortletDisplayContextFactory;
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(PowerMockito.mock(Language.class));
	}

	private void _setUpPortal() {
		Mockito.doReturn(
			_createLiferayPortletRequest()
		).when(
			_portal
		).getLiferayPortletRequest(
			Mockito.anyObject()
		);
	}

	private void _setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);

		try {
			Mockito.when(
				_portletDisplay.getPortletInstanceConfiguration(Mockito.any())
			).thenReturn(
				Mockito.mock(SearchBarPortletInstanceConfiguration.class)
			);
		}
		catch (Exception exception) {
		}

		Mockito.when(
			_themeDisplay.getPortletDisplay()
		).thenReturn(
			_portletDisplay
		);
	}

	private void _whenLayoutLocalServiceFetchLayoutByFriendlyURL(
		String friendlyURL, Layout layout) {

		if (!StringUtil.startsWith(friendlyURL, CharPool.SLASH)) {
			friendlyURL = StringPool.SLASH.concat(friendlyURL);
		}

		Mockito.doReturn(
			layout
		).when(
			_layoutLocalService
		).fetchLayoutByFriendlyURL(
			Mockito.anyLong(), Mockito.anyBoolean(), Mockito.eq(friendlyURL)
		);
	}

	private void _whenPortalGetLayoutFriendlyURL(
			Layout layout, String layoutFriendlyURL)
		throws Exception {

		Mockito.doReturn(
			layoutFriendlyURL
		).when(
			_portal
		).getLayoutFriendlyURL(
			Mockito.eq(layout), Mockito.any()
		);
	}

	@Mock
	private Group _group;

	@Mock
	private Http _http;

	@Mock
	private LayoutLocalService _layoutLocalService;

	@Mock
	private Portal _portal;

	@Mock
	private PortletDisplay _portletDisplay;

	@Mock
	private PortletPreferencesLookup _portletPreferencesLookup;

	@Mock
	private PortletSharedSearchRequest _portletSharedSearchRequest;

	@Mock
	private SearchBarPrecedenceHelper _searchBarPrecedenceHelper;

	@Mock
	private ThemeDisplay _themeDisplay;

}