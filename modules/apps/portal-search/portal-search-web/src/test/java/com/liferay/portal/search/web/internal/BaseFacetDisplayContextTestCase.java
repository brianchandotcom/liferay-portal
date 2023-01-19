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

package com.liferay.portal.search.web.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.custom.facet.configuration.CustomFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.FacetDisplayContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Amanda Costa, Joshua Cords
 */
public abstract class BaseFacetDisplayContextTestCase {

	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws Exception {

		return null;
	}

	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws Exception {

		return null;
	}

	public String getFacetDisplayContextParameterValue() {
		return null;
	}

	@Test
	public void testEmptySearchResults() throws Exception {
		String parameterValue = getFacetDisplayContextParameterValue();

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			parameterValue);

		List<BucketDisplayContext> bucketDisplayContexts =
			facetDisplayContext.getBucketDisplayContexts();

		Assert.assertEquals(
			bucketDisplayContexts.toString(), 0, bucketDisplayContexts.size());

		if (parameterValue == null) {
			parameterValue = StringPool.BLANK;
		}

		Assert.assertEquals(
			parameterValue, facetDisplayContext.getParameterValue());
		Assert.assertTrue(facetDisplayContext.isNothingSelected());
		Assert.assertTrue(facetDisplayContext.isRenderNothing());
	}

	protected static void assertFacetOrder(
		List<BucketDisplayContext> bucketDisplayContexts,
		String[] expectedDisplayText, int[] expectedFrequencies) {

		String expectedResult = buildExpectedNameFrequencyString(
			expectedDisplayText, expectedFrequencies);

		String actualNameFrequencyString = buildNameFrequencyString(
			bucketDisplayContexts);

		Assert.assertEquals(expectedResult, actualNameFrequencyString);
	}

	protected static String buildExpectedNameFrequencyString(
		String[] expectedDisplayText, int[] expectedFrequencies) {

		StringBundler sb = new StringBundler(expectedDisplayText.length * 4);

		for (int i = 0; i < expectedDisplayText.length; i++) {
			sb.append(expectedDisplayText[i]);
			sb.append(StringPool.COLON);
			sb.append(expectedFrequencies[i]);
			sb.append(StringPool.PIPE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected static String buildNameFrequencyString(
		List<BucketDisplayContext> bucketDisplayContexts) {

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

	protected static TermCollector createTermCollector(
		String term, int frequency) {

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

	protected static HttpServletRequest getHttpServletRequest()
		throws ConfigurationException {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			getThemeDisplay(CustomFacetPortletInstanceConfiguration.class)
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return httpServletRequest;
	}

	protected static PortletDisplay getPortletDisplay(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.doReturn(
			Mockito.mock(facetPortletConfiguration)
		).when(
			portletDisplay
		).getPortletInstanceConfiguration(
			Mockito.any()
		);

		return portletDisplay;
	}

	protected static RenderRequest getRenderRequest(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.doReturn(
			getThemeDisplay(facetPortletConfiguration)
		).when(
			renderRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return renderRequest;
	}

	protected static List<TermCollector> getTermCollectors(
		String[] terms, int[] frequencies) {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 0; i < terms.length; i++) {
			termCollectors.add(createTermCollector(terms[i], frequencies[i]));
		}

		return termCollectors;
	}

	protected static ThemeDisplay getThemeDisplay(
			Class<?> facetPortletConfiguration)
		throws ConfigurationException {

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			getPortletDisplay(facetPortletConfiguration)
		).when(
			themeDisplay
		).getPortletDisplay();

		return themeDisplay;
	}

	protected static void setUpTermCollector(
		FacetCollector facetCollector, String term, int frequency) {

		Mockito.doReturn(
			Collections.singletonList(createTermCollector(term, frequency))
		).when(
			facetCollector
		).getTermCollectors();
	}

	protected static void setUpTermCollectors(
		FacetCollector facetCollector, List<TermCollector> termCollectors) {

		Mockito.doReturn(
			termCollectors
		).when(
			facetCollector
		).getTermCollectors();
	}

	protected int[] expectedFrequenciesFrequencyAscending = {4, 5, 5, 6};
	protected int[] expectedFrequenciesFrequencyDescending = {6, 5, 5, 4};
	protected int[] expectedFrequenciesValueAscending = {3, 4, 2, 5};
	protected int[] expectedFrequenciesValueDescending = {5, 4, 2, 3};
	protected String[] expectedTermsFrequencyAscending = {
		"alpha", "bravo", "delta", "charlie"
	};
	protected String[] expectedTermsFrequencyDescending = {
		"charlie", "bravo", "delta", "alpha"
	};
	protected String[] expectedTermsValueAscending = {
		"alpha", "bravo", "bravo", "charlie"
	};
	protected String[] expectedTermsValueDescending = {
		"charlie", "bravo", "bravo", "alpha"
	};

}