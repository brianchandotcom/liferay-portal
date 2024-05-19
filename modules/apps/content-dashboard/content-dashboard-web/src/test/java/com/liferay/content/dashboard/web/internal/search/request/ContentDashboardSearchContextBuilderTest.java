/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.action.exception.ContentDashboardItemActionException;
import com.liferay.content.dashboard.item.filter.ContentDashboardItemFilter;
import com.liferay.content.dashboard.item.filter.provider.ContentDashboardItemFilterProvider;
import com.liferay.content.dashboard.web.internal.item.filter.ContentDashboardItemFilterProviderRegistry;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.context.SearchContextFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class ContentDashboardSearchContextBuilderTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpCompanyLocalServiceUtil();
		_setUpDLFileEntryTypeLocalServiceUtil();
		_setUpPortal();
		_setUpSearchContextFactory();
	}

	@Test
	public void testBuildWithAvailableContentDashboardItemFilterProvider()
		throws ContentDashboardItemActionException {

		String parameterName = RandomTestUtil.randomString();
		String[] parameterValues = {RandomTestUtil.randomString()};

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			parameterName, parameterValues);

		AssetCategoryLocalService assetCategoryLocalService = Mockito.mock(
			AssetCategoryLocalService.class);
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		ContentDashboardItemFilterProviderRegistry
			contentDashboardItemFilterProviderRegistry =
				_getContentDashboardItemFilterProviderRegistry(
					_getContentDashboardItemFilterProvider(
						_getContentDashboardItemFilter(
							parameterName, parameterValues),
						httpServletRequest, true));

		ContentDashboardSearchContextBuilder
			contentDashboardSearchContextBuilder =
				new ContentDashboardSearchContextBuilder(
					httpServletRequest, assetCategoryLocalService,
					assetVocabularyLocalService,
					contentDashboardItemFilterProviderRegistry);

		Assert.assertNotNull(contentDashboardSearchContextBuilder);

		SearchContext searchContext =
			contentDashboardSearchContextBuilder.build();

		List<BooleanClause<Filter>> mustBooleanClauses = _getBooleanClauses(
			searchContext);

		Assert.assertEquals(
			mustBooleanClauses.toString(), 1, mustBooleanClauses.size());

		BooleanClause<Filter> filterBooleanClause = mustBooleanClauses.get(0);

		TermsFilter termsFilter = (TermsFilter)filterBooleanClause.getClause();

		Assert.assertEquals(parameterName, termsFilter.getField());
		Assert.assertEquals(parameterValues, termsFilter.getValues());
	}

	@Test
	public void testBuildWithUnavailableContentDashboardItemFilterProvider()
		throws ContentDashboardItemActionException {

		String parameterName = RandomTestUtil.randomString();
		String[] parameterValues = {RandomTestUtil.randomString()};

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			parameterName, parameterValues);

		AssetCategoryLocalService assetCategoryLocalService = Mockito.mock(
			AssetCategoryLocalService.class);
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		ContentDashboardItemFilterProviderRegistry
			contentDashboardItemFilterProviderRegistry =
				_getContentDashboardItemFilterProviderRegistry(
					_getContentDashboardItemFilterProvider(
						_getContentDashboardItemFilter(
							parameterName, parameterValues),
						httpServletRequest, false));

		ContentDashboardSearchContextBuilder
			contentDashboardSearchContextBuilder =
				new ContentDashboardSearchContextBuilder(
					httpServletRequest, assetCategoryLocalService,
					assetVocabularyLocalService,
					contentDashboardItemFilterProviderRegistry);

		Assert.assertNotNull(contentDashboardSearchContextBuilder);

		SearchContext searchContext =
			contentDashboardSearchContextBuilder.build();

		List<BooleanClause<Filter>> mustBooleanClauses = _getBooleanClauses(
			searchContext);

		Assert.assertEquals(
			mustBooleanClauses.toString(), 0, mustBooleanClauses.size());
	}

	private List<BooleanClause<Filter>> _getBooleanClauses(
		SearchContext searchContext) {

		BooleanClause<Query>[] booleanClauses =
			searchContext.getBooleanClauses();

		Assert.assertNotNull(booleanClauses);

		Assert.assertEquals(
			booleanClauses.toString(), 1, booleanClauses.length);

		BooleanClause<Query> booleanClause = booleanClauses[0];

		BooleanQuery booleanQuery = (BooleanQuery)booleanClause.getClause();

		BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

		return preBooleanFilter.getMustBooleanClauses();
	}

	private ContentDashboardItemFilter _getContentDashboardItemFilter(
		String parameterName, String... parameterValues) {

		ContentDashboardItemFilter contentDashboardItemFilter = Mockito.mock(
			ContentDashboardItemFilter.class);

		TermsFilter termsFilter = new TermsFilter(parameterName);

		for (String parameterValue : parameterValues) {
			termsFilter.addValue(parameterValue);
		}

		Mockito.when(
			contentDashboardItemFilter.getFilter()
		).thenReturn(
			termsFilter
		);

		return contentDashboardItemFilter;
	}

	private ContentDashboardItemFilterProvider
			_getContentDashboardItemFilterProvider(
				ContentDashboardItemFilter contentDashboardItemFilter,
				HttpServletRequest httpServletRequest, boolean show)
		throws ContentDashboardItemActionException {

		ContentDashboardItemFilterProvider contentDashboardItemFilterProvider =
			Mockito.mock(ContentDashboardItemFilterProvider.class);

		Mockito.when(
			contentDashboardItemFilterProvider.getContentDashboardItemFilter(
				httpServletRequest)
		).thenReturn(
			contentDashboardItemFilter
		);

		Mockito.when(
			contentDashboardItemFilterProvider.isShow(httpServletRequest)
		).thenReturn(
			show
		);

		return contentDashboardItemFilterProvider;
	}

	private ContentDashboardItemFilterProviderRegistry
		_getContentDashboardItemFilterProviderRegistry(
			ContentDashboardItemFilterProvider
				contentDashboardItemFilterProvider) {

		ContentDashboardItemFilterProviderRegistry
			contentDashboardItemFilterProviderRegistry = Mockito.mock(
				ContentDashboardItemFilterProviderRegistry.class);

		Mockito.when(
			contentDashboardItemFilterProviderRegistry.
				getContentDashboardItemFilterProviders()
		).thenReturn(
			Arrays.asList(contentDashboardItemFilterProvider)
		);

		return contentDashboardItemFilterProviderRegistry;
	}

	private HttpServletRequest _getHttpServletRequest(
		String parameterName, String... parameterValues) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			httpServletRequest.getParameterValues(parameterName)
		).thenReturn(
			parameterValues
		);

		return httpServletRequest;
	}

	private void _setUpCompanyLocalServiceUtil() throws Exception {
		CompanyLocalServiceUtil companyLocalServiceUtil =
			new CompanyLocalServiceUtil();

		Company company = Mockito.mock(Company.class);

		Mockito.when(
			_companyLocalService.getCompany(Mockito.anyLong())
		).thenReturn(
			company
		);

		companyLocalServiceUtil.setService(_companyLocalService);
	}

	private void _setUpDLFileEntryTypeLocalServiceUtil() {
		DLFileEntryTypeLocalServiceUtil dlFileEntryTypeLocalServiceUtil =
			new DLFileEntryTypeLocalServiceUtil();

		Mockito.when(
			_dlFileEntryTypeLocalService.fetchFileEntryType(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			null
		);

		dlFileEntryTypeLocalServiceUtil.setService(
			_dlFileEntryTypeLocalService);
	}

	private void _setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		Mockito.when(
			_portal.getCompanyId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		portalUtil.setPortal(_portal);
	}

	private void _setUpSearchContextFactory() {
		Mockito.doReturn(
			new SearchContext()
		).when(
			_searchContextFactory
		).getSearchContext(
			Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any(),
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(),
			Mockito.any(), Mockito.anyLong()
		);
	}

	private final CompanyLocalService _companyLocalService = Mockito.mock(
		CompanyLocalService.class);
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService =
		Mockito.mock(DLFileEntryTypeLocalService.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final SearchContextFactory _searchContextFactory = Mockito.mock(
		SearchContextFactory.class);

}