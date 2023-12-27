/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.search.admin.web.internal.display.context.builder.SearchAdminDisplayContextBuilder;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpIndexInformation();
		_setUpLanguage();
		_setUpPermissionChecker();
		_setUpPortalUtil();
		_setUpRenderRequest();
	}

	@Test
	public void testGetNavigationItemListWithIndexInformation() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		NavigationItemList navigationItemList =
			searchAdminDisplayContext.getNavigationItemList();

		Assert.assertEquals(
			navigationItemList.toString(), 3, navigationItemList.size());
	}

	@Test
	public void testGetNavigationItemListWithoutIndexInformation() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(null);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		NavigationItemList navigationItemList =
			searchAdminDisplayContext.getNavigationItemList();

		Assert.assertEquals(
			navigationItemList.toString(), 2, navigationItemList.size());
	}

	@Test
	public void testGetTabConnections() {
		_mockRenderRequest.setParameter("tabs1", "connections");

		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabDefault() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabFieldMappings() {
		_mockRenderRequest.setParameter("tabs1", "field-mappings");

		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"field-mappings", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabFieldMappingsNoIndexInformation() {
		_mockRenderRequest.setParameter("tabs1", "field-mappings");

		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(null);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabIndexActions() {
		_mockRenderRequest.setParameter("tabs1", "index-actions");

		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"index-actions", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabUnavailable() {
		_mockRenderRequest.setParameter("tabs1", RandomTestUtil.randomString());

		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, _mockRenderRequest,
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	protected IndexInformation indexInformation;

	private void _setUpIndexInformation() {
		indexInformation = Mockito.mock(IndexInformation.class);

		Mockito.when(
			indexInformation.getIndexNames()
		).thenReturn(
			new String[] {"index1", "index2"}
		);

		Mockito.when(
			indexInformation.getCompanyIndexName(Mockito.anyLong())
		).thenAnswer(
			invocation -> "index" + invocation.getArguments()[0]
		);
	}

	private void _setUpLanguage() {
		_language = new LanguageImpl();
	}

	private void _setUpPermissionChecker() {
		Mockito.doReturn(
			_permissionChecker
		).when(
			_themeDisplay
		).getPermissionChecker();

		Mockito.doReturn(
			true
		).when(
			_permissionChecker
		).isOmniadmin();
	}

	private void _setUpPortalUtil() {
		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private void _setUpRenderRequest() {
		_mockRenderRequest = new MockRenderRequest();

		_mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);
	}

	private static MockRenderRequest _mockRenderRequest;

	private Language _language;
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}