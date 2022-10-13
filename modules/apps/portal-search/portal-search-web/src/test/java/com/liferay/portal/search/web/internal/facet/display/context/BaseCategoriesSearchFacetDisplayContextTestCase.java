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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetCategoriesSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetCategoryPermissionChecker;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public abstract class BaseCategoriesSearchFacetDisplayContextTestCase {

	@Before
	public void setUp() throws Exception {
		setUpAssetVocabularyLocalService();
		setUpConfigurationProvider();
		setUpFacet();
	}

	@Test
	public void testEmptySearchResults() throws Exception {
		String facetParam = StringPool.BLANK;

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 0,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		Assert.assertEquals(
			facetParam,
			assetCategoriesSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		_setUpAssetCategory(assetCategoryId, 0);

		String facetParam = String.valueOf(assetCategoryId);

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 1,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		AssetCategoriesSearchFacetTermDisplayContext
			assetCategoriesSearchFacetTermDisplayContext =
				assetCategoriesSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getFilterValue());
		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			0, assetCategoriesSearchFacetTermDisplayContext.getFrequency());
		Assert.assertTrue(
			assetCategoriesSearchFacetTermDisplayContext.isFrequencyVisible());
		Assert.assertTrue(
			assetCategoriesSearchFacetTermDisplayContext.isSelected());

		Assert.assertEquals(
			facetParam,
			assetCategoriesSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testExcludedGroup() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		long groupId = RandomTestUtil.randomLong();

		long stagingGroupId = RandomTestUtil.randomLong();

		createGroup(groupId, stagingGroupId);

		_setUpAssetCategory(assetCategoryId, stagingGroupId);

		_excludedGroupId = stagingGroupId;

		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(assetCategoryId, frequency);

		String facetParam = StringPool.BLANK;

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 0,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		_excludedGroupId = 0;
	}

	@Test
	public void testOneTerm() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		_setUpAssetCategory(assetCategoryId, 0);

		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(assetCategoryId, frequency);

		String facetParam = StringPool.BLANK;

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 1,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		AssetCategoriesSearchFacetTermDisplayContext
			assetCategoriesSearchFacetTermDisplayContext =
				assetCategoriesSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getFilterValue());
		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			frequency,
			assetCategoriesSearchFacetTermDisplayContext.getFrequency());
		Assert.assertTrue(
			assetCategoriesSearchFacetTermDisplayContext.isFrequencyVisible());
		Assert.assertFalse(
			assetCategoriesSearchFacetTermDisplayContext.isSelected());

		Assert.assertEquals(
			facetParam,
			assetCategoriesSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		_setUpAssetCategory(assetCategoryId, 0);

		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(assetCategoryId, frequency);

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				String.valueOf(assetCategoryId));

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 1,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		AssetCategoriesSearchFacetTermDisplayContext
			assetCategoriesSearchFacetTermDisplayContext =
				assetCategoriesSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getFilterValue());
		Assert.assertEquals(
			String.valueOf(assetCategoryId),
			assetCategoriesSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			frequency,
			assetCategoriesSearchFacetTermDisplayContext.getFrequency());
		Assert.assertTrue(
			assetCategoriesSearchFacetTermDisplayContext.isFrequencyVisible());
		Assert.assertTrue(
			assetCategoriesSearchFacetTermDisplayContext.isSelected());

		Assert.assertEquals(
			assetCategoryId,
			GetterUtil.getLong(
				assetCategoriesSearchFacetDisplayContext.getParameterValue()));
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testUnauthorized() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		_setUpAssetCategoryUnauthorized(assetCategoryId);

		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(assetCategoryId, frequency);

		String facetParam = StringPool.BLANK;

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 0,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		Assert.assertEquals(
			facetParam,
			assetCategoriesSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testUnauthorizedWithPreviousSelection() throws Exception {
		long assetCategoryId = RandomTestUtil.randomLong();

		_setUpAssetCategoryUnauthorized(assetCategoryId);

		String facetParam = String.valueOf(assetCategoryId);

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = createDisplayContext(
				facetParam);

		List<AssetCategoriesSearchFacetTermDisplayContext>
			assetCategoriesSearchFacetTermDisplayContexts =
				assetCategoriesSearchFacetDisplayContext.
					getTermDisplayContexts();

		Assert.assertEquals(
			assetCategoriesSearchFacetTermDisplayContexts.toString(), 0,
			assetCategoriesSearchFacetTermDisplayContexts.size());

		Assert.assertEquals(
			facetParam,
			assetCategoriesSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetCategoriesSearchFacetDisplayContext.isRenderNothing());
	}

	protected AssetCategoriesSearchFacetDisplayContext createDisplayContext(
		String parameterValue) {

		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		AssetCategoriesSearchFacetDisplayContextBuilder
			assetCategoriesSearchFacetDisplayContextBuilder =
				new AssetCategoriesSearchFacetDisplayContextBuilder(
					renderRequest);

		assetCategoriesSearchFacetDisplayContextBuilder.
			setAssetCategoryLocalService(_assetCategoryLocalService);
		assetCategoriesSearchFacetDisplayContextBuilder.
			setAssetCategoryPermissionChecker(_assetCategoryPermissionChecker);
		assetCategoriesSearchFacetDisplayContextBuilder.
			setAssetVocabularyLocalService(_assetVocabularyLocalService);
		assetCategoriesSearchFacetDisplayContextBuilder.setDisplayStyle(
			"cloud");
		assetCategoriesSearchFacetDisplayContextBuilder.setFacet(_facet);
		assetCategoriesSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			true);
		assetCategoriesSearchFacetDisplayContextBuilder.setFrequencyThreshold(
			0);
		assetCategoriesSearchFacetDisplayContextBuilder.setLocale(
			LocaleUtil.getDefault());
		assetCategoriesSearchFacetDisplayContextBuilder.setMaxTerms(0);
		assetCategoriesSearchFacetDisplayContextBuilder.setParameterName(
			_facet.getFieldId());
		assetCategoriesSearchFacetDisplayContextBuilder.setParameterValue(
			parameterValue);
		assetCategoriesSearchFacetDisplayContextBuilder.setPortal(_getPortal());

		if (_excludedGroupId > 0) {
			assetCategoriesSearchFacetDisplayContextBuilder.setExcludedGroupId(
				_excludedGroupId);
		}

		return assetCategoriesSearchFacetDisplayContextBuilder.build();
	}

	protected Group createGroup(long groupId, long stagingGroupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		return group;
	}

	protected TermCollector createTermCollector(
		long assetCategoryId, int frequency) {

		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			frequency
		).when(
			termCollector
		).getFrequency();

		if (_isLegacyField()) {
			Mockito.doReturn(
				String.valueOf(assetCategoryId)
			).when(
				termCollector
			).getTerm();
		}
		else {
			Mockito.doReturn(
				"vocabularyId-" + assetCategoryId
			).when(
				termCollector
			).getTerm();
		}

		return termCollector;
	}

	protected abstract String getFacetFieldName();

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			Mockito.mock(PortletDisplay.class)
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
	}

	protected void setUpAssetVocabularyLocalService() {
		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.doReturn(
			"name"
		).when(
			assetVocabulary
		).getTitle(
			Mockito.any(Locale.class)
		);

		Mockito.doReturn(
			assetVocabulary
		).when(
			_assetVocabularyLocalService
		).fetchAssetVocabulary(
			Mockito.anyLong()
		);
	}

	protected void setUpConfigurationProvider() throws Exception {
		Mockito.doReturn(
			getFacetFieldName()
		).when(
			_categoryFacetFieldConfiguration
		).categoryFacetField();

		Mockito.doReturn(
			_categoryFacetFieldConfiguration
		).when(
			_configurationProvider
		).getSystemConfiguration(
			Mockito.any(Class.class)
		);

		ReflectionTestUtil.setFieldValue(
			ConfigurationProviderUtil.class, "_configurationProvider",
			_configurationProvider);
	}

	protected void setUpFacet() {
		Mockito.doReturn(
			_facetCollector
		).when(
			_facet
		).getFacetCollector();

		Mockito.doReturn(
			getFacetFieldName()
		).when(
			_facet
		).getFieldName();
	}

	protected void setUpOneTermCollector(long assetCategoryId, int frequency) {
		Mockito.doReturn(
			Collections.singletonList(
				createTermCollector(assetCategoryId, frequency))
		).when(
			_facetCollector
		).getTermCollectors();
	}

	private AssetCategory _createAssetCategory(
		long assetCategoryId, long groupId) {

		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			assetCategoryId
		).when(
			assetCategory
		).getCategoryId();

		Mockito.doReturn(
			groupId
		).when(
			assetCategory
		).getGroupId();

		Mockito.doReturn(
			String.valueOf(assetCategoryId)
		).when(
			assetCategory
		).getTitle(
			(Locale)Mockito.any()
		);

		Mockito.doReturn(
			assetCategory
		).when(
			_assetCategoryLocalService
		).fetchAssetCategory(
			assetCategoryId
		);

		return assetCategory;
	}

	private HttpServletRequest _getHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			getThemeDisplay()
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return httpServletRequest;
	}

	private Portal _getPortal() {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.doReturn(
			_getHttpServletRequest()
		).when(
			portal
		).getHttpServletRequest(
			Mockito.any()
		);

		return portal;
	}

	private boolean _isLegacyField() {
		String fieldName = getFacetFieldName();

		return fieldName.equals("assetCategoryIds");
	}

	private void _setUpAssetCategory(long assetCategoryId, long groupId) {
		AssetCategory assetCategory = _createAssetCategory(
			assetCategoryId, groupId);

		Mockito.doReturn(
			true
		).when(
			_assetCategoryPermissionChecker
		).hasPermission(
			assetCategory
		);
	}

	private void _setUpAssetCategoryUnauthorized(long assetCategoryId) {
		AssetCategory assetCategory = _createAssetCategory(assetCategoryId, 0);

		Mockito.doReturn(
			false
		).when(
			_assetCategoryPermissionChecker
		).hasPermission(
			assetCategory
		);
	}

	private final AssetCategoryLocalService _assetCategoryLocalService =
		Mockito.mock(AssetCategoryLocalService.class);
	private final AssetCategoryPermissionChecker
		_assetCategoryPermissionChecker = Mockito.mock(
			AssetCategoryPermissionChecker.class);
	private final AssetVocabularyLocalService _assetVocabularyLocalService =
		Mockito.mock(AssetVocabularyLocalService.class);
	private final CategoryFacetFieldConfiguration
		_categoryFacetFieldConfiguration = Mockito.mock(
			CategoryFacetFieldConfiguration.class);
	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);
	private long _excludedGroupId;
	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}