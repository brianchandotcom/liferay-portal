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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoriesSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoryPermissionChecker;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class AssetCategoriesSearchFacetDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.doReturn(
			_facetCollector
		).when(
			_facet
		).getFacetCollector();
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
			assetCategoryId,
			assetCategoriesSearchFacetTermDisplayContext.getAssetCategoryId());
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
			assetCategoryId,
			assetCategoriesSearchFacetTermDisplayContext.getAssetCategoryId());
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
			assetCategoryId,
			assetCategoriesSearchFacetTermDisplayContext.getAssetCategoryId());
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

		AssetCategoriesSearchFacetDisplayBuilder
			assetCategoriesSearchFacetDisplayBuilder =
				new AssetCategoriesSearchFacetDisplayBuilder(renderRequest);

		assetCategoriesSearchFacetDisplayBuilder.setAssetCategoryLocalService(
			_assetCategoryLocalService);
		assetCategoriesSearchFacetDisplayBuilder.
			setAssetCategoryPermissionChecker(_assetCategoryPermissionChecker);
		assetCategoriesSearchFacetDisplayBuilder.setDisplayStyle("cloud");
		assetCategoriesSearchFacetDisplayBuilder.setFacet(_facet);
		assetCategoriesSearchFacetDisplayBuilder.setFrequenciesVisible(true);
		assetCategoriesSearchFacetDisplayBuilder.setFrequencyThreshold(0);
		assetCategoriesSearchFacetDisplayBuilder.setMaxTerms(0);
		assetCategoriesSearchFacetDisplayBuilder.setParameterName(
			_facet.getFieldId());
		assetCategoriesSearchFacetDisplayBuilder.setParameterValue(
			parameterValue);
		assetCategoriesSearchFacetDisplayBuilder.setPortal(_getPortal());

		if (_excludedGroupId > 0) {
			assetCategoriesSearchFacetDisplayBuilder.setExcludedGroupId(
				_excludedGroupId);
		}

		return assetCategoriesSearchFacetDisplayBuilder.build();
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

		Mockito.doReturn(
			String.valueOf(assetCategoryId)
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			Mockito.mock(PortletDisplay.class)
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
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
			Matchers.any()
		);

		return portal;
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

	@Mock
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Mock
	private AssetCategoryPermissionChecker _assetCategoryPermissionChecker;

	private long _excludedGroupId;

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

	@Mock
	private Group _group;

}