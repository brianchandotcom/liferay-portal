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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class SearchBarPortletDisplayBuilderTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpHttp();
		setUpThemeDisplay();
	}

	@Test
	public void testDestinationBlank() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(StringPool.BLANK);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationNull() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(null);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationUnreachable() {
		String destination = RandomTestUtil.randomString();

		whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, null);

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(destination);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertTrue(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationWithLeadingSlash() throws Exception {
		String destination = RandomTestUtil.randomString();

		Layout layout = Mockito.mock(Layout.class);

		whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, layout);

		String layoutFriendlyURL = RandomTestUtil.randomString();

		whenPortalGetLayoutFriendlyURL(layout, layoutFriendlyURL);

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(
			StringPool.SLASH.concat(destination));

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertEquals(
			layoutFriendlyURL, searchBarPortletDisplayContext.getSearchURL());

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testDestinationWithoutLeadingSlash() throws Exception {
		String destination = RandomTestUtil.randomString();

		Layout layout = Mockito.mock(Layout.class);

		whenLayoutLocalServiceFetchLayoutByFriendlyURL(destination, layout);

		String layoutFriendlyURL = RandomTestUtil.randomString();

		whenPortalGetLayoutFriendlyURL(layout, layoutFriendlyURL);

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(destination);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertEquals(
			layoutFriendlyURL, searchBarPortletDisplayContext.getSearchURL());

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());
	}

	@Test
	public void testSamePageNoDestination() {
		Mockito.doReturn(
			"http://example.com/web/guest/home?param=arg"
		).when(
			_themeDisplay
		).getURLCurrent();

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationUnreachable());

		Assert.assertEquals(
			"/web/guest/home", searchBarPortletDisplayContext.getSearchURL());
	}

	@Test
	public void testSearchScope() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setScopeParameterValue(
			Optional.of(SearchScope.EVERYTHING.getParameterString()));

		Assert.assertEquals(
			SearchScope.EVERYTHING,
			searchBarPortletDisplayBuilder.getSearchScope());
	}

	protected SearchBarPortletDisplayBuilder
		createSearchBarPortletDisplayBuilder() {

		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			new SearchBarPortletDisplayBuilder(
				_http, _layoutLocalService, _portal);

		searchBarPortletDisplayBuilder.setSearchScopePreference(
			SearchScopePreference.getSearchScopePreference("everything"));
		searchBarPortletDisplayBuilder.setThemeDisplay(_themeDisplay);

		return searchBarPortletDisplayBuilder;
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

	protected void setUpHttp() {
		Mockito.doAnswer(
			invocation -> getPath(invocation.getArgumentAt(0, String.class))
		).when(
			_http
		).getPath(
			Mockito.anyString()
		);
	}

	protected void setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);
	}

	protected void whenLayoutLocalServiceFetchLayoutByFriendlyURL(
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

	protected void whenPortalGetLayoutFriendlyURL(
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
	private ThemeDisplay _themeDisplay;

}