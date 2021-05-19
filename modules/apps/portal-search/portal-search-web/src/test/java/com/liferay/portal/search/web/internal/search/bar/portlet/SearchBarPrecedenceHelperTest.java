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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Joshua Cords
 * @author André de Oliveira
 */
public class SearchBarPrecedenceHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Layout layout = _createLayout(_portlets);

		_layout = layout;

		_searchBarPrecedenceHelper = _createSearchBarPrecedenceHelper();
		_themeDisplay = _createThemeDisplay(layout);
	}

	@Test
	public void testDifferentDestinationDifferentFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(RandomTestUtil.randomString());

		addSearchBarPortletToHeader(RandomTestUtil.randomString());

		Portlet portlet = addSearchBarPortletToPage(
			RandomTestUtil.randomString());

		Assert.assertFalse(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	@Test
	public void testDifferentDestinationSameFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(RandomTestUtil.randomString());

		String federatedSearchKey = RandomTestUtil.randomString();

		addSearchBarPortletToHeader(federatedSearchKey);

		Portlet portlet = addSearchBarPortletToPage(federatedSearchKey);

		Assert.assertFalse(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	@Test
	public void testOverlappingDestinationDifferentFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(
			_DESTINATION + RandomTestUtil.randomString());

		addSearchBarPortletToHeader(RandomTestUtil.randomString());

		Portlet portlet = addSearchBarPortletToPage(
			RandomTestUtil.randomString());

		Assert.assertFalse(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	@Test
	public void testOverlappingDestinationSameFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(
			_DESTINATION + RandomTestUtil.randomString());

		String federatedSearchKey = RandomTestUtil.randomString();

		addSearchBarPortletToHeader(federatedSearchKey);

		Portlet portlet = addSearchBarPortletToPage(federatedSearchKey);

		Assert.assertFalse(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	@Test
	public void testSameDestinationDifferentFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(_DESTINATION);

		addSearchBarPortletToHeader(RandomTestUtil.randomString());

		Portlet portlet = addSearchBarPortletToPage(
			RandomTestUtil.randomString());

		Assert.assertFalse(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	@Test
	public void testSameDestinationSameFederatedKey() {
		setThemeDisplayLayoutFriendlyURL(_DESTINATION);

		String federatedSearchKey = RandomTestUtil.randomString();

		addSearchBarPortletToHeader(federatedSearchKey);

		Portlet portlet = addSearchBarPortletToPage(federatedSearchKey);

		Assert.assertTrue(
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(portlet));
	}

	protected Portlet addPortlet(
		String portletName, String portletId, String federatedSearchKey,
		boolean isStatic) {

		Portlet portlet = _createPortlet(portletName, portletId, isStatic);

		_portlets.add(portlet);

		Mockito.doReturn(
			Optional.ofNullable(
				_createPortletPreferences(federatedSearchKey, _DESTINATION))
		).when(
			_portletPreferencesLookup
		).fetchPreferences(
			portlet, _themeDisplay
		);

		Mockito.when(
			_portletLocalService.getPortletById(0, portlet.getPortletId())
		).thenReturn(
			portlet
		);

		return portlet;
	}

	protected void addSearchBarPortletToHeader(String federatedSearchKey) {
		addPortlet(
			SearchBarPortletKeys.SEARCH_BAR, "headerSearchBarPortletId",
			federatedSearchKey, true);
	}

	protected Portlet addSearchBarPortletToPage(String federatedSearchKey) {
		return addPortlet(
			"searchBar", "searchBarPortletId", federatedSearchKey, false);
	}

	protected boolean isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(
		Portlet portlet) {

		return _searchBarPrecedenceHelper.
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(
				_themeDisplay, portlet.getPortletId());
	}

	protected void setThemeDisplayLayoutFriendlyURL(String destination) {
		Mockito.when(
			_themeDisplay.getLayoutFriendlyURL(_layout)
		).thenReturn(
			"/" + destination
		);
	}

	private Layout _createLayout(List<Portlet> portlets) {
		LayoutTypePortlet layoutTypePortlet = Mockito.mock(
			LayoutTypePortlet.class);

		Mockito.when(
			layoutTypePortlet.getAllPortlets(false)
		).thenReturn(
			portlets
		);

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getLayoutType()
		).thenReturn(
			layoutTypePortlet
		);

		return layout;
	}

	private Portlet _createPortlet(
		String portletName, String portletId, boolean isStatic) {

		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.when(
			portlet.getPortletId()
		).thenReturn(
			portletId
		);

		Mockito.when(
			portlet.getPortletName()
		).thenReturn(
			portletName
		);

		Mockito.when(
			portlet.isStatic()
		).thenReturn(
			isStatic
		);

		return portlet;
	}

	private PortletDisplay _createPortletDisplay() throws Exception {
		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.doReturn(
			Mockito.mock(SearchBarPortletInstanceConfiguration.class)
		).when(
			portletDisplay
		).getPortletInstanceConfiguration(
			Mockito.any()
		);

		return portletDisplay;
	}

	private PortletPreferences _createPortletPreferences(
		String federatedSearchKey, String destination) {

		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		Mockito.when(
			portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_DESTINATION,
				StringPool.BLANK)
		).thenReturn(
			destination
		);

		Mockito.when(
			portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
				StringPool.BLANK)
		).thenReturn(
			federatedSearchKey
		);

		return portletPreferences;
	}

	private SearchBarPrecedenceHelper _createSearchBarPrecedenceHelper() {
		SearchBarPrecedenceHelper searchBarPrecedenceHelper =
			new SearchBarPrecedenceHelper();

		searchBarPrecedenceHelper.setPortletLocalService(_portletLocalService);

		searchBarPrecedenceHelper.setPortletPreferencesLookup(
			_portletPreferencesLookup);

		return searchBarPrecedenceHelper;
	}

	private ThemeDisplay _createThemeDisplay(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			Mockito.mock(Group.class)
		);

		Mockito.doReturn(
			_createPortletDisplay()
		).when(
			themeDisplay
		).getPortletDisplay();

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		return themeDisplay;
	}

	private static final String _DESTINATION = RandomTestUtil.randomString();

	private Layout _layout;

	@Mock
	private PortletLocalService _portletLocalService;

	@Mock
	private PortletPreferencesLookup _portletPreferencesLookup;

	private final List<Portlet> _portlets = new ArrayList<>();
	private SearchBarPrecedenceHelper _searchBarPrecedenceHelper;
	private ThemeDisplay _themeDisplay;

}