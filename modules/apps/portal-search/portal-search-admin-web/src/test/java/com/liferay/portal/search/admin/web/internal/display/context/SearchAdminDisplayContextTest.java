/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.search.admin.web.internal.display.context.builder.SearchAdminDisplayContextBuilder;
import com.liferay.portal.search.cluster.StatsInformation;
import com.liferay.portal.search.cluster.StatsInformationFactory;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderResponse;

import javax.portlet.RenderRequest;

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
		setUpIndexInformation();
		_setUpLanguage();
		setUpPortalUtil();
	}

	@Test
	public void testGetNavigationItemListWithIndexInformation() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, new MockRenderRequest(),
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
				_language, _portal, new MockRenderRequest(),
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
	public void testGetStatsInformation() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal, new MockRenderRequest(),
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		searchAdminDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(100.0, 50.0, 80.0));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		double availableDiskSpace =
			searchAdminDisplayContext.getAvailableDiskSpace();

		double currentDiskSpaceUsed =
			searchAdminDisplayContext.getCurrentDiskSpaceUsed();

		Assert.assertEquals(100.0, availableDiskSpace, 0);

		Assert.assertEquals(80.0, currentDiskSpaceUsed, 0);

		searchAdminDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(16.0, 10.0, 10.0));

		searchAdminDisplayContext = searchAdminDisplayContextBuilder.build();

		Assert.assertFalse(searchAdminDisplayContext.isLowOnDiskSpace());

		searchAdminDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(14.0, 10.0, 10.0));

		searchAdminDisplayContext = searchAdminDisplayContextBuilder.build();

		Assert.assertTrue(searchAdminDisplayContext.isLowOnDiskSpace());
	}

	@Test
	public void testGetTabConnections() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal,
				_getRenderRequestWithSelectedTab("connections"),
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
				_language, _portal, new MockRenderRequest(),
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
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal,
				_getRenderRequestWithSelectedTab("field-mappings"),
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
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal,
				_getRenderRequestWithSelectedTab("field-mappings"),
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(null);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabIndexActions() {
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal,
				_getRenderRequestWithSelectedTab("index-actions"),
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
		SearchAdminDisplayContextBuilder searchAdminDisplayContextBuilder =
			new SearchAdminDisplayContextBuilder(
				_language, _portal,
				_getRenderRequestWithSelectedTab(RandomTestUtil.randomString()),
				new MockRenderResponse());

		searchAdminDisplayContextBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayContextBuilder.build();

		Assert.assertEquals(
			"connections", searchAdminDisplayContext.getSelectedTab());
	}

	protected StatsInformationFactory getStatsInformationFactory(
		double available, double largest, double used) {

		StatsInformationFactory statsInformationFactory = Mockito.mock(
			StatsInformationFactory.class);

		StatsInformation statsInformation = Mockito.mock(
			StatsInformation.class);

		Mockito.when(
			statsInformation.getAvailableDiskSpace()
		).thenReturn(
			available
		);

		Mockito.when(
			statsInformation.getSizeOfLargestIndex()
		).thenReturn(
			largest
		);

		Mockito.when(
			statsInformation.getUsedDiskSpace()
		).thenReturn(
			used
		);

		Mockito.when(
			statsInformationFactory.getStatsInformation()
		).thenReturn(
			statsInformation
		);

		return statsInformationFactory;
	}

	protected void setUpIndexInformation() {
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

	protected void setUpPortalUtil() {
		_portal = Mockito.mock(Portal.class);

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

	protected IndexInformation indexInformation;

	private RenderRequest _getRenderRequestWithSelectedTab(String selectedTab) {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		mockRenderRequest.setParameter("tabs1", selectedTab);

		return mockRenderRequest;
	}

	private void _setUpLanguage() {
		_language = new LanguageImpl();
	}

	private Language _language;
	private Portal _portal;

}