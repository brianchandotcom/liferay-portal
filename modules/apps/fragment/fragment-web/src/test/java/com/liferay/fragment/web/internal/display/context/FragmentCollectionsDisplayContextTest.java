/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.web.internal.util.FragmentPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class FragmentCollectionsDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpFragmentPortletUtil();
		_setUpHttpServletRequest();
		_setUpPortalInstancePool();
		_setUpPortletURLBuilder();
		_setUpSearchOrderByUtil();
		_setUpThemeDisplay();
	}

	@After
	public void tearDown() {
		_fragmentCollectionServiceUtilMockedStatic.close();
		_fragmentPortletUtilMockedStatic.close();
		_portalInstancePoolMockedStatic.close();
		_portletURLBuilderMockedStatic.close();
		_searchOrderByUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerIteratorURLContainsExportAction()
		throws Exception {

		_mockAction("export");
		_mockExportableFragmentCollections(List.of(), 0);

		SearchContainer<FragmentCollection> searchContainer =
			_getSearchContainer();

		MockLiferayPortletURL mockLiferayPortletURL =
			(MockLiferayPortletURL)searchContainer.getIteratorURL();

		Assert.assertEquals(
			"export", mockLiferayPortletURL.getParameter("action"));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerReturnsExportableFragmentCollections()
		throws Exception {

		_mockAction("export");
		Mockito.when(
			_httpServletRequest.getParameter(
				"includeMarketplaceFragmentCollections")
		).thenReturn(
			Boolean.TRUE.toString()
		);

		FragmentCollection exportableFragmentCollection = Mockito.mock(
			FragmentCollection.class);

		List<FragmentCollection> fragmentCollections = List.of(
			exportableFragmentCollection);

		_mockExportableFragmentCollections(fragmentCollections, 1);

		SearchContainer<FragmentCollection> searchContainer =
			_getSearchContainer();

		List<FragmentCollection> results = searchContainer.getResults();

		Assert.assertSame(exportableFragmentCollection, results.get(0));

		Assert.assertEquals(results.toString(), 1, results.size());

		Assert.assertEquals(1, searchContainer.getTotal());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerReturnsSearchedExportableFragmentCollections()
		throws Exception {

		_mockAction("export");
		Mockito.when(
			_httpServletRequest.getParameter("keywords")
		).thenReturn(
			"Banner"
		);

		FragmentCollection exportableFragmentCollection = Mockito.mock(
			FragmentCollection.class);

		_mockSearchedExportableFragmentCollections(
			"Banner", List.of(exportableFragmentCollection), 1);

		SearchContainer<FragmentCollection> searchContainer =
			_getSearchContainer();

		List<FragmentCollection> results = searchContainer.getResults();

		Assert.assertSame(exportableFragmentCollection, results.get(0));

		Assert.assertEquals(results.toString(), 1, results.size());

		Assert.assertEquals(1, searchContainer.getTotal());
	}

	private SearchContainer<FragmentCollection> _getSearchContainer() {
		FragmentCollectionsDisplayContext fragmentCollectionsDisplayContext =
			new FragmentCollectionsDisplayContext(
				_httpServletRequest, _renderRequest, _renderResponse);

		return fragmentCollectionsDisplayContext.getSearchContainer();
	}

	private void _mockAction(String action) {
		Mockito.when(
			_httpServletRequest.getParameter("action")
		).thenReturn(
			action
		);
	}

	private void _mockExportableFragmentCollections(
		List<FragmentCollection> fragmentCollections, int total) {

		_fragmentCollectionServiceUtilMockedStatic.when(
			() ->
				FragmentCollectionServiceUtil.getExportableFragmentCollections(
					Mockito.any(long[].class), Mockito.eq(0), Mockito.eq(20),
					Mockito.any())
		).thenReturn(
			fragmentCollections
		);

		_fragmentCollectionServiceUtilMockedStatic.when(
			() ->
				FragmentCollectionServiceUtil.
					getExportableFragmentCollectionsCount(
						Mockito.any(long[].class))
		).thenReturn(
			total
		);
	}

	private void _mockSearchedExportableFragmentCollections(
		String keywords, List<FragmentCollection> fragmentCollections,
		int total) {

		_fragmentCollectionServiceUtilMockedStatic.when(
			() ->
				FragmentCollectionServiceUtil.getExportableFragmentCollections(
					Mockito.any(long[].class), Mockito.eq(keywords),
					Mockito.eq(0), Mockito.eq(20), Mockito.any())
		).thenReturn(
			fragmentCollections
		);

		_fragmentCollectionServiceUtilMockedStatic.when(
			() ->
				FragmentCollectionServiceUtil.
					getExportableFragmentCollectionsCount(
						Mockito.any(long[].class), Mockito.eq(keywords))
		).thenReturn(
			total
		);
	}

	private void _setUpFragmentPortletUtil() {
		_fragmentPortletUtilMockedStatic.when(
			() -> FragmentPortletUtil.getFragmentCollectionOrderByComparator(
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			Mockito.mock(OrderByComparator.class)
		);
	}

	private void _setUpHttpServletRequest() {
		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	private void _setUpPortalInstancePool() {
		_portalInstancePoolMockedStatic.when(
			PortalInstancePool::getDefaultCompanyId
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private void _setUpPortletURLBuilder() {
		_portletURLBuilderMockedStatic.when(
			() -> PortletURLBuilder.createRenderURL(_renderResponse)
		).thenReturn(
			new PortletURLBuilder.PortletURLStep(new MockLiferayPortletURL())
		);
	}

	private void _setUpSearchOrderByUtil() {
		_searchOrderByUtilMockedStatic.when(
			() -> SearchOrderByUtil.getOrderByCol(
				Mockito.eq(_httpServletRequest), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			"create-date"
		);

		_searchOrderByUtilMockedStatic.when(
			() -> SearchOrderByUtil.getOrderByType(
				Mockito.eq(_httpServletRequest), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			"asc"
		);
	}

	private void _setUpThemeDisplay() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			false
		);

		Mockito.when(
			_themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	private final MockedStatic<FragmentCollectionServiceUtil>
		_fragmentCollectionServiceUtilMockedStatic = Mockito.mockStatic(
			FragmentCollectionServiceUtil.class);
	private final MockedStatic<FragmentPortletUtil>
		_fragmentPortletUtilMockedStatic = Mockito.mockStatic(
			FragmentPortletUtil.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final MockedStatic<PortalInstancePool>
		_portalInstancePoolMockedStatic = Mockito.mockStatic(
			PortalInstancePool.class);
	private final MockedStatic<PortletURLBuilder>
		_portletURLBuilderMockedStatic = Mockito.mockStatic(
			PortletURLBuilder.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);
	private final RenderResponse _renderResponse = Mockito.mock(
		RenderResponse.class);
	private final MockedStatic<SearchOrderByUtil>
		_searchOrderByUtilMockedStatic = Mockito.mockStatic(
			SearchOrderByUtil.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}