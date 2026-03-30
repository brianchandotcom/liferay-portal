/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
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
		_portalInstancePoolMockedStatic.when(
			PortalInstancePool::getDefaultCompanyId
		).thenReturn(
			RandomTestUtil.randomLong()
		);
	}

	@After
	public void tearDown() {
		_portalInstancePoolMockedStatic.close();
		_searchOrderByUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerIteratorURLContainsExportAction() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		Mockito.when(
			_fragmentCollectionService.getExportableFragmentCollections(
				Mockito.any(long[].class), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any())
		).thenReturn(
			List.of()
		);

		Mockito.when(
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
				Mockito.any(long[].class))
		).thenReturn(
			0
		);

		SearchContainer<FragmentCollection> searchContainer =
			_getSearchContainer(httpServletRequest);

		MockLiferayPortletURL mockLiferayPortletURL =
			(MockLiferayPortletURL)searchContainer.getIteratorURL();

		Assert.assertEquals(
			"export", mockLiferayPortletURL.getParameter("action"));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerReturnsExportableFragmentCollections() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		Mockito.when(
			httpServletRequest.getParameter(
				"includeMarketplaceFragmentCollections")
		).thenReturn(
			Boolean.TRUE.toString()
		);

		_getSearchContainer(httpServletRequest);

		Mockito.verify(
			_fragmentCollectionService
		).getExportableFragmentCollections(
			Mockito.any(long[].class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any()
		);
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetSearchContainerReturnsExportableFragmentCollectionsFilteredByKeywords() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		Mockito.when(
			httpServletRequest.getParameter("keywords")
		).thenReturn(
			"Banner"
		);

		_getSearchContainer(httpServletRequest);

		Mockito.verify(
			_fragmentCollectionService
		).getExportableFragmentCollections(
			Mockito.any(long[].class), Mockito.eq("Banner"), Mockito.eq(0),
			Mockito.eq(20), Mockito.any()
		);
	}

	private HttpServletRequest _getHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isCompany()
		).thenReturn(
			false
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			httpServletRequest.getParameter("action")
		).thenReturn(
			Constants.EXPORT
		);

		return httpServletRequest;
	}

	private SearchContainer<FragmentCollection> _getSearchContainer(
		HttpServletRequest httpServletRequest) {

		_searchOrderByUtilMockedStatic.when(
			() -> SearchOrderByUtil.getOrderByCol(
				Mockito.eq(httpServletRequest), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			"create-date"
		);

		_searchOrderByUtilMockedStatic.when(
			() -> SearchOrderByUtil.getOrderByType(
				Mockito.eq(httpServletRequest), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			"asc"
		);

		RenderResponse renderResponse = Mockito.mock(RenderResponse.class);

		Mockito.when(
			renderResponse.createRenderURL()
		).thenReturn(
			new MockLiferayPortletURL()
		);

		FragmentCollectionsDisplayContext fragmentCollectionsDisplayContext =
			new FragmentCollectionsDisplayContext(
				_fragmentCollectionService, httpServletRequest,
				Mockito.mock(RenderRequest.class), renderResponse);

		return fragmentCollectionsDisplayContext.getSearchContainer();
	}

	private final FragmentCollectionService _fragmentCollectionService =
		Mockito.mock(FragmentCollectionService.class);
	private final MockedStatic<PortalInstancePool>
		_portalInstancePoolMockedStatic = Mockito.mockStatic(
			PortalInstancePool.class);
	private final MockedStatic<SearchOrderByUtil>
		_searchOrderByUtilMockedStatic = Mockito.mockStatic(
			SearchOrderByUtil.class);

}