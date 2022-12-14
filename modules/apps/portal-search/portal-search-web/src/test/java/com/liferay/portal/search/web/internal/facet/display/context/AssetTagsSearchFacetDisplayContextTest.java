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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetTagsSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
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

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

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

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(0, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

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

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertFalse(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

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

		List<BucketDisplayContext> bucketDisplayContexts =
			assetTagsSearchFacetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 1, bucketDisplayContexts.size());

		BucketDisplayContext bucketDisplayContext = bucketDisplayContexts.get(
			0);

		Assert.assertEquals(term, bucketDisplayContext.getBucketText());
		Assert.assertEquals(term, bucketDisplayContext.getFilterValue());
		Assert.assertEquals(frequency, bucketDisplayContext.getFrequency());
		Assert.assertTrue(bucketDisplayContext.isSelected());
		Assert.assertTrue(bucketDisplayContext.isFrequencyVisible());

		Assert.assertEquals(
			facetParam, assetTagsSearchFacetDisplayContext.getParameterValue());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isNothingSelected());
		Assert.assertFalse(
			assetTagsSearchFacetDisplayContext.isRenderNothing());
	}

	@Test
	public void testOrderByTermFrequencyAscending() throws Exception {
		List<TermCollector> termCollectors1 = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {3, 4, 5, 6});

		_setUpMultipleTermCollectors(termCollectors1);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext1 =
			createDisplayContext(StringPool.BLANK, "count:asc");

		List<BucketDisplayContext> bucketDisplayContexts1 =
			assetTagsSearchFacetDisplayContext1.getBucketDisplayContexts();

		String nameFrequencyString1 = _buildNameFrequencyString(
			bucketDisplayContexts1);

		Assert.assertEquals(
			bucketDisplayContexts1.toString(),
			"alpha:3|delta:4|bravo:5|charlie:6", nameFrequencyString1);

		List<TermCollector> termCollectors2 = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {4, 5, 5, 6});

		_setUpMultipleTermCollectors(termCollectors2);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext2 =
			createDisplayContext(StringPool.BLANK, "count:asc");

		List<BucketDisplayContext> bucketDisplayContexts2 =
			assetTagsSearchFacetDisplayContext2.getBucketDisplayContexts();

		String nameFrequencyString2 = _buildNameFrequencyString(
			bucketDisplayContexts2);

		Assert.assertEquals(
			bucketDisplayContexts2.toString(),
			"alpha:4|bravo:5|delta:5|charlie:6", nameFrequencyString2);
	}

	@Test
	public void testOrderByTermFrequencyDescending() throws Exception {
		List<TermCollector> termCollectors1 = _getTermCollectors(
			new String[] {"alpha", "charlie", "bravo", "delta"},
			new int[] {3, 4, 5, 6});

		_setUpMultipleTermCollectors(termCollectors1);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext1 =
			createDisplayContext(StringPool.BLANK, "count:desc");

		List<BucketDisplayContext> bucketDisplayContexts1 =
			assetTagsSearchFacetDisplayContext1.getBucketDisplayContexts();

		String nameFrequencyString1 = _buildNameFrequencyString(
			bucketDisplayContexts1);

		Assert.assertEquals(
			bucketDisplayContexts1.toString(),
			"delta:6|bravo:5|charlie:4|alpha:3", nameFrequencyString1);

		List<TermCollector> termCollectors2 = _getTermCollectors(
			new String[] {"alpha", "delta", "bravo", "charlie"},
			new int[] {4, 5, 5, 6});

		_setUpMultipleTermCollectors(termCollectors2);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext2 =
			createDisplayContext(StringPool.BLANK, "count:desc");

		List<BucketDisplayContext> bucketDisplayContexts2 =
			assetTagsSearchFacetDisplayContext2.getBucketDisplayContexts();

		String nameFrequencyString2 = _buildNameFrequencyString(
			bucketDisplayContexts2);

		Assert.assertEquals(
			bucketDisplayContexts2.toString(),
			"charlie:6|bravo:5|delta:5|alpha:4", nameFrequencyString2);
	}

	@Test
	public void testOrderByTermValueAscending() throws Exception {
		List<TermCollector> termCollectors1 = _getTermCollectors(
			"bravo", "delta", "alpha", "charlie");

		_setUpMultipleTermCollectors(termCollectors1);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext1 =
			createDisplayContext(StringPool.BLANK, "key:asc");

		List<BucketDisplayContext> bucketDisplayContexts1 =
			assetTagsSearchFacetDisplayContext1.getBucketDisplayContexts();

		String nameFrequencyString1 = _buildNameFrequencyString(
			bucketDisplayContexts1);

		Assert.assertEquals(
			bucketDisplayContexts1.toString(),
			"alpha:3|bravo:1|charlie:4|delta:2", nameFrequencyString1);

		List<TermCollector> termCollectors2 = _getTermCollectors(
			"bravo", "alpha", "bravo", "charlie");

		_setUpMultipleTermCollectors(termCollectors2);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext2 =
			createDisplayContext(StringPool.BLANK, "key:asc");

		List<BucketDisplayContext> bucketDisplayContexts2 =
			assetTagsSearchFacetDisplayContext2.getBucketDisplayContexts();

		String nameFrequencyString2 = _buildNameFrequencyString(
			bucketDisplayContexts2);

		Assert.assertEquals(
			bucketDisplayContexts1.toString(),
			"alpha:2|bravo:3|bravo:1|charlie:4", nameFrequencyString2);
	}

	@Test
	public void testOrderByTermValueDescending() throws Exception {
		List<TermCollector> termCollectors1 = _getTermCollectors(
			"bravo", "delta", "alpha", "charlie");

		_setUpMultipleTermCollectors(termCollectors1);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext1 =
			createDisplayContext(StringPool.BLANK, "key:desc");

		List<BucketDisplayContext> bucketDisplayContexts1 =
			assetTagsSearchFacetDisplayContext1.getBucketDisplayContexts();

		String nameFrequencyString1 = _buildNameFrequencyString(
			bucketDisplayContexts1);

		Assert.assertEquals(
			bucketDisplayContexts1.toString(),
			"delta:2|charlie:4|bravo:1|alpha:3", nameFrequencyString1);

		List<TermCollector> termCollectors2 = _getTermCollectors(
			"bravo", "alpha", "bravo", "charlie");

		_setUpMultipleTermCollectors(termCollectors2);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext2 =
			createDisplayContext(StringPool.BLANK, "key:desc");

		List<BucketDisplayContext> bucketDisplayContexts2 =
			assetTagsSearchFacetDisplayContext2.getBucketDisplayContexts();

		String nameFrequencyString2 = _buildNameFrequencyString(
			bucketDisplayContexts2);

		Assert.assertEquals(
			bucketDisplayContexts2.toString(),
			"charlie:4|bravo:3|bravo:1|alpha:2", nameFrequencyString2);
	}

	protected AssetTagsSearchFacetDisplayContext createDisplayContext(
			String facetParam)
		throws Exception {

		return createDisplayContext(facetParam, "count:desc");
	}

	protected AssetTagsSearchFacetDisplayContext createDisplayContext(
			String facetParam, String order)
		throws ConfigurationException {

		AssetTagsSearchFacetDisplayContextBuilder
			assetTagsSearchFacetDisplayContextBuilder =
				new AssetTagsSearchFacetDisplayContextBuilder(
					getRenderRequest());

		assetTagsSearchFacetDisplayContextBuilder.setDisplayStyle("cloud");
		assetTagsSearchFacetDisplayContextBuilder.setFacet(_facet);
		assetTagsSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		assetTagsSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		assetTagsSearchFacetDisplayContextBuilder.setMaxTerms(0);
		assetTagsSearchFacetDisplayContextBuilder.setOrder(order);
		assetTagsSearchFacetDisplayContextBuilder.setParameterName(
			_facet.getFieldId());
		assetTagsSearchFacetDisplayContextBuilder.setParameterValue(facetParam);

		return assetTagsSearchFacetDisplayContextBuilder.build();
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
			Mockito.any()
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

	private String _buildNameFrequencyString(
			List<BucketDisplayContext> bucketDisplayContexts)
		throws Exception {

		StringBundler sb = new StringBundler(bucketDisplayContexts.size() * 4);

		for (BucketDisplayContext bucketDisplayContext :
				bucketDisplayContexts) {

			sb.append(bucketDisplayContext.getBucketText());
			sb.append(StringPool.COLON);
			sb.append(bucketDisplayContext.getFrequency());
			sb.append(StringPool.PIPE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private List<TermCollector> _getTermCollectors(String... terms) {
		int[] frequencies = new int[terms.length];

		for (int i = 0; i < terms.length; i++) {
			frequencies[i] = i + 1;
		}

		return _getTermCollectors(terms, frequencies);
	}

	private List<TermCollector> _getTermCollectors(
		String[] terms, int[] frequencies) {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 1; i <= terms.length; i++) {
			termCollectors.add(
				createTermCollector(terms[i - 1], frequencies[i - 1]));
		}

		return termCollectors;
	}

	private void _setUpMultipleTermCollectors(
		List<TermCollector> termCollectors) {

		Mockito.doReturn(
			termCollectors
		).when(
			_facetCollector
		).getTermCollectors();
	}

	private final Facet _facet = Mockito.mock(Facet.class);
	private final FacetCollector _facetCollector = Mockito.mock(
		FacetCollector.class);

}