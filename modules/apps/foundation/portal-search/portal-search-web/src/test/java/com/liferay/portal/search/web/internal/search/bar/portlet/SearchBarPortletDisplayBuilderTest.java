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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

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

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			_group
		);
	}

	@Test
	public void testIsDestinationConfiguredHasDestinationNoPage() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination("/search");
		searchBarPortletDisplayBuilder.setSearchLayoutAvailable(false);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertTrue(
			searchBarPortletDisplayContext.isDestinationConfigured());
	}

	@Test
	public void testIsDestinationConfiguredHasDestinationPage() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination("/search");
		searchBarPortletDisplayBuilder.setSearchLayoutAvailable(true);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationConfigured());
	}

	@Test
	public void testIsDestinationConfiguredHasEmptyDestination() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(StringPool.BLANK);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationConfigured());
	}

	@Test
	public void testIsDestinationConfiguredHasNullDestination() {
		SearchBarPortletDisplayBuilder searchBarPortletDisplayBuilder =
			createSearchBarPortletDisplayBuilder();

		searchBarPortletDisplayBuilder.setDestination(null);

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			searchBarPortletDisplayBuilder.build();

		Assert.assertFalse(
			searchBarPortletDisplayContext.isDestinationConfigured());
	}

	protected SearchBarPortletDisplayBuilder
		createSearchBarPortletDisplayBuilder() {

		SearchBarPortletDisplayBuilder displayBuilder =
			new SearchBarPortletDisplayBuilder();

		displayBuilder.setSearchScopePreference(
			SearchScopePreference.EVERYTHING);
		displayBuilder.setThemeDisplay(_themeDisplay);

		return displayBuilder;
	}

	@Mock
	private Group _group;

	@Mock
	private ThemeDisplay _themeDisplay;

}