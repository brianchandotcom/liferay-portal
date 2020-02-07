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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetTagsSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContextTest {

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
		String facetParam = "";

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<AssetTagsSearchFacetTermDisplayContext>
			assetTagsSearchFacetTermDisplayContexts =
				assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			assetTagsSearchFacetTermDisplayContexts.toString(), 0,
			assetTagsSearchFacetTermDisplayContexts.size());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertTrue(assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testEmptySearchResultsWithPreviousSelection() throws Exception {
		String term = RandomTestUtil.randomString();

		String facetParam = term;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<AssetTagsSearchFacetTermDisplayContext>
			assetTagsSearchFacetTermDisplayContexts =
				assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			assetTagsSearchFacetTermDisplayContexts.toString(), 1,
			assetTagsSearchFacetTermDisplayContexts.size());

		AssetTagsSearchFacetTermDisplayContext
			assetTagsSearchFacetTermDisplayContext =
				assetTagsSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			0, assetTagsSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getValue());
		Assert.assertTrue(assetTagsSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			assetTagsSearchFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTerm() throws Exception {
		String term = RandomTestUtil.randomString();
		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(term, frequency);

		String facetParam = StringPool.BLANK;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<AssetTagsSearchFacetTermDisplayContext>
			assetTagsSearchFacetTermDisplayContexts =
				assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			assetTagsSearchFacetTermDisplayContexts.toString(), 1,
			assetTagsSearchFacetTermDisplayContexts.size());

		AssetTagsSearchFacetTermDisplayContext
			assetTagsSearchFacetTermDisplayContext =
				assetTagsSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			frequency, assetTagsSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getValue());
		Assert.assertFalse(assetTagsSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			assetTagsSearchFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertTrue(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOneTermWithPreviousSelection() throws Exception {
		String term = RandomTestUtil.randomString();
		int frequency = RandomTestUtil.randomInt();

		setUpOneTermCollector(term, frequency);

		String facetParam = term;

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			createDisplayContext(facetParam);

		List<AssetTagsSearchFacetTermDisplayContext>
			assetTagsSearchFacetTermDisplayContexts =
				assetTagsSearchFacetDisplayContext.getTermDisplayContexts();

		Assert.assertEquals(
			assetTagsSearchFacetTermDisplayContexts.toString(), 1,
			assetTagsSearchFacetTermDisplayContexts.size());

		AssetTagsSearchFacetTermDisplayContext
			assetTagsSearchFacetTermDisplayContext =
				assetTagsSearchFacetTermDisplayContexts.get(0);

		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getDisplayName());
		Assert.assertEquals(
			frequency, assetTagsSearchFacetTermDisplayContext.getFrequency());
		Assert.assertEquals(
			term, assetTagsSearchFacetTermDisplayContext.getValue());
		Assert.assertTrue(assetTagsSearchFacetTermDisplayContext.isSelected());
		Assert.assertTrue(
			assetTagsSearchFacetTermDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	protected AssetTagsSearchFacetDisplayContext createDisplayContext(
			String facetParam)
		throws ConfigurationException {

		AssetTagsSearchFacetDisplayBuilder assetTagsSearchFacetDisplayBuilder =
			new AssetTagsSearchFacetDisplayBuilder(getRenderRequest());

		assetTagsSearchFacetDisplayBuilder.setDisplayStyle("cloud");
		assetTagsSearchFacetDisplayBuilder.setFacet(_facet);
		assetTagsSearchFacetDisplayBuilder.setFrequenciesVisible(true);
		assetTagsSearchFacetDisplayBuilder.setFrequencyThreshold(0);
		assetTagsSearchFacetDisplayBuilder.setMaxTerms(0);
		assetTagsSearchFacetDisplayBuilder.setParameterName(
			_facet.getFieldId());
		assetTagsSearchFacetDisplayBuilder.setParameterValue(facetParam);

		return assetTagsSearchFacetDisplayBuilder.build();
	}

	protected TermCollector createTermCollector(String term, int frequency) {
		TermCollector termCollector = Mockito.mock(TermCollector.class);

		Mockito.doReturn(
			frequency
		).when(
			termCollector
		).getFrequency();

		Mockito.doReturn(
			term
		).when(
			termCollector
		).getTerm();

		return termCollector;
	}

	protected PortletDisplay getPortletDisplay() throws ConfigurationException {
		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.doReturn(
			Mockito.mock(TagFacetPortletInstanceConfiguration.class)
		).when(
			portletDisplay
		).getPortletInstanceConfiguration(
			Matchers.any()
		);

		return portletDisplay;
	}

	protected RenderRequest getRenderRequest() throws ConfigurationException {
		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.doReturn(
			getThemeDisplay()
		).when(
			renderRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return renderRequest;
	}

	protected ThemeDisplay getThemeDisplay() throws ConfigurationException {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			getPortletDisplay()
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
	}

	protected void setUpOneTermCollector(String facetParam, int frequency) {
		Mockito.doReturn(
			Collections.singletonList(
				createTermCollector(facetParam, frequency))
		).when(
			_facetCollector
		).getTermCollectors();
	}

	@Mock
	private Facet _facet;

	@Mock
	private FacetCollector _facetCollector;

}